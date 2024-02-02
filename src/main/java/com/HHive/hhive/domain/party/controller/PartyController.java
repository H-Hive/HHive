package com.HHive.hhive.domain.party.controller;

import com.HHive.hhive.domain.party.dto.*;
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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of("파티 생성 성공", responseDto));
    }

    @GetMapping("/{partyId}/hives/{hiveId}")
    public ResponseEntity<CommonResponse<PartyResponseDTO>> getParty(@PathVariable Long hiveId, @PathVariable Long partyId) {

        PartyResponseDTO responseDto = partyService.getPartyDto(hiveId, partyId);

        return ResponseEntity.ok().body(CommonResponse.of("파티 조회 성공", responseDto));
    }


    @GetMapping("/{hiveId}")
    public ResponseEntity<CommonResponse<List<PartyListResponseDTO>>> getPartyList(@PathVariable Long hiveId) {

        List<PartyListResponseDTO> response = partyService.getUserPartyMap(hiveId).entrySet().stream()
                .map(entry -> new PartyListResponseDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(CommonResponse.of("파티 목록 조회 성공", response));
    }

    @PatchMapping("/{partyId}")
    public ResponseEntity<CommonResponse<PartyResponseDTO>> updateParty(@PathVariable Long partyId,
                                                                        @RequestBody PartyUpdateRequestDTO partyUpdateRequestDTO,
                                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        PartyResponseDTO responseDto = partyService.updateParty(partyId, partyUpdateRequestDTO, userDetails.getUser());

        return ResponseEntity.ok().body(CommonResponse.of("업데이트 성공", responseDto));
    }

    @DeleteMapping("/{partyId}")
    public ResponseEntity<CommonResponse<String>> deleteParty(@PathVariable Long partyId,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {

        partyService.deleteParty(partyId, userDetails.getUser());

        return ResponseEntity.ok().body(CommonResponse.of("정상적으로 삭제 되었습니다.", null));
    }

    @PostMapping("/{partyId}/hives/{hiveId}/join")
    public ResponseEntity<CommonResponse<String>> joinParty(@PathVariable Long partyId,@PathVariable Long hiveId,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        partyService.joinParty(partyId, hiveId, userDetails.getUser());

        return ResponseEntity.ok().body(CommonResponse.of("파티 가입 성공", null));
    }

    @DeleteMapping("/{partyId}/resign")
    public ResponseEntity<CommonResponse<String>> resignParty(@PathVariable Long partyId,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {

        partyService.resignParty(partyId, userDetails.getUser());

        return ResponseEntity.ok().body(CommonResponse.of("파티 탈퇴 성공", null));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<CommonResponse<List<MyPartyResponseDTO>>> getPartiesByUserId(
            @PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<MyPartyResponseDTO> response = partyService.getPartiesByUserId(userId);

        return ResponseEntity.ok().body(CommonResponse.of("유저의 파티 조회 성공", response));
    }

}

