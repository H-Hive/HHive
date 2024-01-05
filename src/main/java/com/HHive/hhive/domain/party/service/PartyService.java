package com.HHive.hhive.domain.party.service;

import com.HHive.hhive.domain.hive.Hive;
import com.HHive.hhive.domain.party.dto.PostPartyRequestDTO;
import com.HHive.hhive.domain.party.entity.Party;
import com.HHive.hhive.domain.party.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;

    private final HiveRepository hiveRepository;

    public String createParty(Long hiveId, PostPartyRequestDTO requestDTO) {

        Hive hive = hiveRepository.findById(hiveId).orElseThrow(NOT_FOUND_HIVE_EXCEPTION);

        Party party = Party.builder()
                .content(requestDTO.getContent())
                .hostId(requestDTO.getHostId())
                .build();

        partyRepository.save(party);

        return "파티 생성 성공";
    }
}
