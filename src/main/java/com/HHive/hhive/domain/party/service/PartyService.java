package com.HHive.hhive.domain.party.service;

import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.hive.repository.HiveRepository;
import com.HHive.hhive.domain.party.entity.Party;
import com.HHive.hhive.domain.party.repository.PartyRepository;
import com.HHive.hhive.domain.party.request.PartyRequestDto;
import com.HHive.hhive.domain.party.response.PartyResponseDto;
import com.HHive.hhive.domain.user.dto.UserInfoResponseDTO;
import com.HHive.hhive.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyService {
    
    private final PartyRepository partyRepository;

    private final HiveRepository hiveRepository;

    public PartyResponseDto createParty(Long hiveId, PartyRequestDto dto, User user) {

        Hive hive = hiveRepository.findById(hiveId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HIVE));

        Party party = new Party(hive, dto, user);
        party.setUser(user);

        var saved = partyRepository.save(party);

        return new PartyResponseDto(saved);
    }

/*    public PartyResponseDto getPartyDto(Long partyId) {
        Party party = getParty(partyId);
        return new PartyResponseDto(party);
    }

    public Map<UserLoginRequestDTO, List<PartyResponseDto>> getUserPartyMap() {
        Map<UserLoginRequestDTO, List<PartyResponseDto>> userPartyMap = new HashMap<>();

        List<Party> partyList = partyRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate")); // 작성일 기준 내림차순

        partyList.forEach(party -> {
            var userDto = new UserLoginRequestDTO(party.getUser());
            var partyDto = new PartyResponseDto(party);
            if (userPartyMap.containsKey(userDto)) {
                // 유저 할일목록에 항목을 추가
                userPartyMap.get(userDto).add(partyDto);
            } else {
                // 유저 할일목록을 새로 추가
                userPartyMap.put(userDto, new ArrayList<>(List.of(partyDto)));
            }
        });

        return userPartyMap;
    }

    @Transactional
    public PartyResponseDto updateParty(Long partyId, PartyRequestDto partyRequestDto, User user) {
        Party party = getUserParty(partyId, user);

        party.setTitle(partyRequestDto.getPartyTitle());
        party.setContent(partyRequestDto.getPartyContent());

        return new PartyResponseDto(party);
    }

    @Transactional
    public void deleteParty(Long partyId, User user) {
        Party party = getUserParty(partyId, user);

        partyRepository.delete(party);
    }

    public Party getParty(Long partyId) {

        return partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);
    }

    public Party getUserParty(Long partyId, User user) {
        Party party = getParty(partyId);

        if(!user.getId().equals(party.getUser().getId())) {
            throw new UnauthorizedModifyException();
        }
        return party;
    }*/

}
