package com.HHive.hhive.domain.party.controller;

import com.HHive.hhive.domain.party.request.PartyRequestDTO;
import com.HHive.hhive.domain.party.response.PartyListResponseDTO;
import com.HHive.hhive.domain.party.response.PartyResponseDTO;
import com.HHive.hhive.domain.party.service.PartyService;
import com.HHive.hhive.domain.user.UserDetailsImpl;
import com.HHive.hhive.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/parties")
public class PartyController {

    private final PartyService partyService;

    @PostMapping("/hives/{hiveId}")
    public ResponseEntity<CommonResponse<PartyResponseDTO>> createParty(
            @PathVariable Long hiveId,
            @RequestBody PartyRequestDTO partyRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        PartyResponseDTO responseDto = partyService.createParty(hiveId, partyRequestDto, userDetails.getUser());
        return ResponseEntity.ok().body(CommonResponse.of(HttpStatus.OK.value(), "파티 생성 성공", responseDto));
    }

    @GetMapping("/{partyId}")
    public ResponseEntity<CommonResponse<PartyResponseDTO>> getParty(@PathVariable Long partyId) {
        PartyResponseDTO responseDto = partyService.getPartyDto(partyId);
        return ResponseEntity.ok().body(CommonResponse.of(HttpStatus.OK.value(), "파티 조회 성공", responseDto));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<PartyListResponseDTO>>> getPartyList() {
        List<PartyListResponseDTO> response = partyService.getUserPartyMap().entrySet().stream()
                .map(entry -> new PartyListResponseDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(CommonResponse.of(HttpStatus.OK.value(), "파티 목록 조회 성공", response));
    }

    @PatchMapping("/{partyId}")
    public ResponseEntity<CommonResponse<PartyResponseDTO>> updateParty(@PathVariable Long partyId,
                                                                        @RequestBody PartyRequestDTO partyRequestDto,
                                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PartyResponseDTO responseDto = partyService.updateParty(partyId, partyRequestDto, userDetails.getUser());
        return ResponseEntity.ok().body(CommonResponse.of(HttpStatus.OK.value(), "업데이트 성공", responseDto));
    }

    @DeleteMapping("/{partyId}")
    public ResponseEntity<CommonResponse<String>> deleteParty(@PathVariable Long partyId,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        partyService.deleteParty(partyId, userDetails.getUser());
        return ResponseEntity.ok().body(CommonResponse.of(HttpStatus.OK.value(), "정상적으로 삭제 되었습니다.", null));
    }


}

