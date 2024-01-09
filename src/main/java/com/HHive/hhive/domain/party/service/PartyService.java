package com.HHive.hhive.domain.party.service;

import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.hive.repository.HiveRepository;
import com.HHive.hhive.domain.party.entity.Party;
import com.HHive.hhive.domain.party.repository.PartyRepository;
import com.HHive.hhive.domain.party.request.PartyRequestDTO;
import com.HHive.hhive.domain.party.response.PartyResponseDTO;
import com.HHive.hhive.domain.user.dto.UserInfoResponseDTO;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.global.exception.party.HiveNotFoundException;
import com.HHive.hhive.global.exception.party.PartyNotFoundException;
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


    public PartyResponseDTO createParty(Long hiveId, PartyRequestDTO dto, User user) {
        Hive hive = hiveRepository.findById(hiveId)
                .orElseThrow(HiveNotFoundException::new);

        Party party = new Party(hive, dto, user);
        party.setUser(user);

        var saved = partyRepository.save(party);

        return new PartyResponseDTO(saved);
    }
    public Party getParty(Long partyId) {
        return partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);
    }
    public PartyResponseDTO getPartyDto(Long partyId) {
        Party party = getParty(partyId);
        return new PartyResponseDTO(party);
    }

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
        partyRepository.delete(party);
    }

    private Party getUserParty(Long partyId, User user) {
        return partyRepository.findById(partyId)
                .orElseThrow(PartyNotFoundException::new);
    }


}

