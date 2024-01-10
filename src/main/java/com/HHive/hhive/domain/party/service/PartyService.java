package com.HHive.hhive.domain.party.service;

import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.hive.repository.HiveRepository;
import com.HHive.hhive.domain.party.entity.Party;
import com.HHive.hhive.domain.party.repository.PartyRepository;
import com.HHive.hhive.domain.party.dto.PartyRequestDTO;
import com.HHive.hhive.domain.party.dto.PartyResponseDTO;
import com.HHive.hhive.domain.relationship.partyuser.entity.PartyUser;
import com.HHive.hhive.domain.relationship.partyuser.pk.PartyUserPK;
import com.HHive.hhive.domain.relationship.partyuser.repository.PartyUserRepository;
import com.HHive.hhive.domain.user.dto.UserInfoResponseDTO;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.global.exception.hive.NotFoundHiveException;
import com.HHive.hhive.global.exception.party.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;
    private final HiveRepository hiveRepository;
    private final PartyUserRepository partyUserRepository;


    public PartyResponseDTO createParty(Long hiveId, PartyRequestDTO dto, User user) {
        Hive hive = hiveRepository.findById(hiveId)
                .orElseThrow(NotFoundHiveException::new);

        Party party = new Party(hive, dto, user);
        party.setUser(user);

        var saved = partyRepository.save(party);

        return new PartyResponseDTO(saved);
    }

    //단건 조회
    @Transactional
    public PartyResponseDTO getPartyDto(Long partyId) {
        Party party = partyRepository.findById(partyId).orElseThrow(PartyNotFoundException::new);
        return new PartyResponseDTO(party);
    }

    //전체 조회
    @Transactional
    public Map<UserInfoResponseDTO, List<PartyResponseDTO>> getUserPartyMap() {
        Map<UserInfoResponseDTO, List<PartyResponseDTO>> userPartyMap = new HashMap<>();

        List<Party> partyList = partyRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")); // 작성일 기준 내림차순

        partyList.forEach(party -> {
            var userDto = new UserInfoResponseDTO(party.getUser());
            var partyDto = new PartyResponseDTO(party);
            if (userPartyMap.containsKey(userDto)) {
                userPartyMap.get(userDto).add(partyDto);
            } else {
                userPartyMap.put(userDto, new ArrayList<>(List.of(partyDto)));
            }
        });

        return userPartyMap;
    }

    @Transactional
    public PartyResponseDTO updateParty(Long partyId, PartyRequestDTO partyRequestDto, User user) {
        Party party = getUserParty(partyId, user);

        party.setTitle(partyRequestDto.getTitle());
        party.setContent(partyRequestDto.getContent());

        return new PartyResponseDTO(party);
    }

    @Transactional
    public void deleteParty(Long partyId, User user) {
        Party party = getUserParty(partyId, user);
        if (!party.getHostId().equals(user.getId())) {
            throw new UnauthorizedAccessException();
        }
        partyRepository.delete(party);
    }

    private Party getUserParty(Long partyId, User user) {
        return partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);
    }

    @Transactional
    public void joinParty(Long partyId, User user) {
        Party party = getUserParty(partyId, user);

        // 파티 호스트가 파티에 가입하는 것을 방지
        if (party.getHostId().equals(user.getId())) {
            throw new PartyHostNotJoinException();
        }

        // 중복 가입 검사
        if (partyUserRepository.existsByUserIdAndPartyId(user.getId(), partyId)) {
            throw new AlreadyJoinException();
        }
        else {
            PartyUser partyUser = new PartyUser(user, party);
            partyUserRepository.save(partyUser);
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
        // 파티 탈퇴 처리
        PartyUserPK partyUserPK = new PartyUserPK(user.getId(), party.getId());
        partyUserRepository.deleteById(partyUserPK);
    }

}