package com.HHive.hhive.domain.party.controller;

import com.HHive.hhive.domain.party.dto.*;
import com.HHive.hhive.domain.party.service.PartyService;
import com.HHive.hhive.domain.relationship.partyuser.service.PartyUserService;
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
    private final PartyUserService partyUserService;

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
    public ResponseEntity<CommonResponse<List<PartyResponseDTO>>> getPartyList(@PathVariable Long hiveId) {

        List<PartyResponseDTO> response = partyService.getUserParties(hiveId);

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

        partyService.leaveParty(partyId, userDetails.getUser());

        return ResponseEntity.ok().body(CommonResponse.of("파티 탈퇴 성공", null));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<PartyResponseDTO>> getPartiesCreatedByUser(@PathVariable Long userId) {
        List<PartyResponseDTO> parties = partyService.getPartiesCreatedByUser(userId);

        return ResponseEntity.ok(parties);
    }

    @GetMapping("/users/createdParties/{userId}")
    public ResponseEntity<CommonResponse<List<PartyResponseDTO>>> getPartiesCreatedByUser(
            @PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<PartyResponseDTO> createdParties = partyService.getPartiesCreatedByUser(userId);

        return ResponseEntity.ok().body(CommonResponse.of("유저가 생성한 파티 조회 성공", createdParties));
    }

    @GetMapping("/users/joinedParties/{userId}")
    public ResponseEntity<CommonResponse<List<PartyResponseDTO>>> getPartiesJoinedByUser(
            @PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<PartyResponseDTO> joinedParties = partyUserService.getPartiesJoinedByUser(userId);

        return ResponseEntity.ok().body(CommonResponse.of("유저가 가입한 파티 조회 성공", joinedParties));
    }

}

