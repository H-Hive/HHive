package com.HHive.hhive.domain.party.service;

import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.hive.repository.HiveRepository;
import com.HHive.hhive.domain.party.dto.*;
import com.HHive.hhive.domain.party.entity.Party;
import com.HHive.hhive.domain.party.repository.PartyRepository;
import com.HHive.hhive.domain.relationship.partyuser.entity.PartyUser;
import com.HHive.hhive.domain.relationship.partyuser.pk.PartyUserPK;
import com.HHive.hhive.domain.relationship.partyuser.repository.PartyUserRepository;
import com.HHive.hhive.domain.relationship.partyuser.service.PartyUserService;
import com.HHive.hhive.domain.user.dto.UserInfoResponseDTO;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.global.exception.hive.NotFoundHiveException;
import com.HHive.hhive.global.exception.party.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;
    private final HiveRepository hiveRepository;
    private final PartyUserRepository partyUserRepository;
    private final PartyUserService partyUserService;


    @Transactional
    public PartyResponseDTO createParty(Long hiveId, PartyRequestDTO dto, User user) {

        // Hive 존재 여부 확인
        Hive hive = getHive(hiveId);

        // DTO에서 날짜와 시간 정보를 가져와서 LocalDateTime 객체 생성
        LocalDateTime dateTime = getDateTimeFromDto(dto);

        // 설정하려는 약속 시간이 과거인지 확인
        validatePartyTime(dateTime);

        Party party = new Party(hive, dto, user, dateTime);
        party.setUser(user);

        var savedParty = partyRepository.save(party);

        // 파티 생성자(호스트)를 파티의 멤버로 추가
        PartyUser partyUser = PartyUser.builder()
                .user(user)
                .party(savedParty)
                .build();
        partyUserRepository.save(partyUser);

        // 호스트 정보를 members 목록에 포함하여 PartyResponseDTO 생성
        PartyResponseDTO partyResponseDTO = createPartyResponseDTO(savedParty);
        return partyResponseDTO;
    }

    //단건 조회
    @Transactional
    public PartyResponseDTO getPartyDto(Long hiveId, Long partyId) {
        Party party = getParty(hiveId, partyId);
        return createPartyResponseDTO(party);
    }

    //전체 조회
    @Transactional
    public Map<UserInfoResponseDTO, List<PartyResponseDTO>> getUserPartyMap(Long hiveId) {
        Hive hive = getHive(hiveId);

        Map<UserInfoResponseDTO, List<PartyResponseDTO>> userPartyMap = new LinkedHashMap<>();

        List<Party> partyList = partyRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream()
                .filter(p -> !p.isDeleted() && p.getHive().equals(hive))
                .collect(Collectors.toList());

        for (Party party : partyList) {
            UserInfoResponseDTO userDto = new UserInfoResponseDTO(party.getUser());
            List<MemberResponseDTO> members = getPartyMembers(party.getId());

            PartyResponseDTO partyDto = new PartyResponseDTO(party.getId(), party.getTitle(), party.getUsername(), party.getContent(), party.getDateTime(), party.getCreatedAt(), party.getModifiedAt(), members);

            userPartyMap.computeIfAbsent(userDto, k -> new ArrayList<>()).add(partyDto);
        }

        return userPartyMap;
    }

    @Transactional
    public PartyResponseDTO updateParty(Long partyId, PartyUpdateRequestDTO partyUpdateRequestDto, User user) {
        Party party = getUserParty(partyId, user);

        if (!party.getHostId().equals(user.getId())) {
            throw new UnauthorizedAccessException();
        }

        if (partyUpdateRequestDto.getTitle() != null) {
            party.setTitle(partyUpdateRequestDto.getTitle());
        }
        if (partyUpdateRequestDto.getContent() != null) {
            party.setContent(partyUpdateRequestDto.getContent());
        }

        LocalDateTime newDateTime = getLocalDateTime(partyUpdateRequestDto, party);

        validatePartyTime(newDateTime);

        // 파티 날짜 및 시간 업데이트
        party.setDateTime(newDateTime);

        // 파티 저장
        partyRepository.save(party);

        return createPartyResponseDTO(party);
    }

    @Transactional
    public void deleteParty(Long partyId, User user) {
        Party party = getUserParty(partyId, user);

        if (!party.getHostId().equals(user.getId())) {
            throw new UnauthorizedAccessException();
        }

        party.setIsDeleted(true);
        partyRepository.save(party);
    }

    @Transactional
    public void joinParty(Long partyId, Long hiveId, User user) {
        Party party = getUserParty(partyId, user);

        // 현재 날짜를 가져옴
        LocalDateTime now = LocalDateTime.now();

        // Hive에 가입되어 있는지 확인
        if (!partyUserService.isUserMemberOfHive(user.getId(), hiveId)) {
            throw new NotMemberOfHiveException();
        }

        // 파티 호스트가 파티에 가입하는 것을 방지
        if (party.getHostId().equals(user.getId())) {
            throw new PartyHostNotJoinException();
        }

        // 파티 날짜가 현재 날짜보다 이전인 경우, 가입 불가 처리
        if (party.getDateTime().isBefore(now)) {
            throw new EndPartyNotJoinException();
        }

        // 중복 가입 검사
        if (partyUserRepository.existsByUserIdAndPartyId(user.getId(), partyId)) {
            throw new AlreadyJoinException();
        }
        else {
            partyUserService.addPartyUser(user, party);
        }
    }

    @Transactional
    public void resignParty(Long partyId, User user) {
        Party party = getUserParty(partyId, user);

        if (party.getHostId().equals(user.getId())) {
            throw new PartyHostNotResignException();
        }
        // 파티 가입 여부 검사
        if (!partyUserRepository.existsByPartyUserPK_UserIdAndPartyUserPK_PartyId(user.getId(), party.getId())) {
            throw new PartyNotResignException();
        }

        partyUserService.removePartyUser(user.getId(), party.getId());
    }


    //공통 메서드

    private Hive getHive(Long hiveId) {

        return hiveRepository.findById(hiveId)
                .orElseThrow(NotFoundHiveException::new);
    }

    private Party getUserParty(Long partyId, User user) {

        return partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);
    }

    // 파티 조회 및 예외 처리
    private Party getParty(Long hiveId, Long partyId) {

        Hive hive = hiveRepository.findById(hiveId)
                .orElseThrow(NotFoundHiveException::new);

        return partyRepository.findById(partyId)
                .filter(p -> !p.isDeleted() && p.getHive().equals(hive))
                .orElseThrow(PartyNotFoundException::new);
    }

    // 파티에 속한 사용자 목록 조회
    private List<MemberResponseDTO> getPartyMembers(Long partyId) {

        return partyUserService.getPartyMembers(partyId);
    }

    public List<MyPartyResponseDTO> getPartiesByUserId(Long userId) {

        return partyUserRepository.findPartiesByUserId(userId).stream().map(party ->
                new MyPartyResponseDTO(party.getId(), party.getTitle(), party.getUsername(),
                        party.getContent(),party.getHive().getId(),party.getHostId(), party.getDateTime(), party.getCreatedAt(),
                        party.getModifiedAt(), getPartyMembers(party.getId()))).toList();
    }

    // 날짜와 시간 정보를 가져와 LocalDateTime 객체 생성
    private LocalDateTime getDateTimeFromDto(PartyRequestDTO dto) {
        return LocalDateTime.of(
                dto.getYear(),
                dto.getMonth(),
                dto.getDay(),
                dto.getHours(),
                dto.getMinutes());
    }

    private LocalDateTime getLocalDateTime(PartyUpdateRequestDTO dto, Party party) {
        LocalDateTime currentDateTime = party.getDateTime();
        int year = dto.getYear() != null ? dto.getYear() : currentDateTime.getYear();
        int month = dto.getMonth() != null ? dto.getMonth() : currentDateTime.getMonthValue();
        int day = dto.getDay() != null ? dto.getDay() : currentDateTime.getDayOfMonth();
        int hours = dto.getHours() != null ? dto.getHours() : currentDateTime.getHour();
        int minutes = dto.getMinutes() != null ? dto.getMinutes() : currentDateTime.getMinute();

        return LocalDateTime.of(year, month, day, hours, minutes);
    }

    // 설정한 파티 시간의 유효성 검증
    private void validatePartyTime(LocalDateTime dateTime) {
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new InvalidPartyTimeException();
        }
    }

    // PartyResponseDTO 생성 로직
    private PartyResponseDTO createPartyResponseDTO(Party party) {
        List<MemberResponseDTO> members = getPartyMembers(party.getId());
        return new PartyResponseDTO(party.getId(), party.getTitle(),
                party.getUsername(), party.getContent(), party.getDateTime(),
                party.getCreatedAt(), party.getModifiedAt(), members);
    }

}