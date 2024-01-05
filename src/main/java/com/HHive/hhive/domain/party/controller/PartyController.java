package com.HHive.hhive.domain.party.controller;

import com.HHive.hhive.domain.party.dto.PostPartyRequestDTO;
import com.HHive.hhive.domain.party.service.PartyService;
import com.HHive.hhive.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/parties")
public class PartyController {

    private final PartyService partyService;

    @PostMapping("{hiveId}")
    public ResponseEntity<CommonResponse<Void>> createParty(
            @PathVariable Long hiveId, @RequestBody PostPartyRequestDTO requestDTO) {

        String message = partyService.createParty(hiveId, requestDTO);

        return ResponseEntity.ok().body(CommonResponse.of(200, message, null));
    }
}
