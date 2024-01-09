package com.HHive.hhive.domain.party.controller;

import com.HHive.hhive.domain.party.request.PartyRequestDto;
import com.HHive.hhive.domain.party.response.PartyListResponseDto;
import com.HHive.hhive.domain.party.response.PartyResponseDto;
import com.HHive.hhive.domain.party.service.PartyService;
import com.HHive.hhive.domain.user.UserDetailsImpl;
import com.HHive.hhive.global.common.CommonResponse;
import com.HHive.hhive.global.exception.common.CustomException;
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
    public ResponseEntity<CommonResponse<PartyResponseDto>> createParty(
            @PathVariable Long hiveId,
            @RequestBody PartyRequestDto partyRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        PartyResponseDto responseDto = partyService.createParty(hiveId, partyRequestDto, userDetails.getUser());
        return ResponseEntity.ok().body(CommonResponse.of(201, "파티 생성 성공", responseDto));
    }

    @GetMapping("/{party_id}")
    public ResponseEntity<CommonResponse<PartyResponseDto>> getParty(@PathVariable Long party_id) {
        PartyResponseDto responseDto = partyService.getPartyDto(party_id);
        return ResponseEntity.ok().body(CommonResponse.of(HttpStatus.OK.value(), "파티 조회 성공", responseDto));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<PartyListResponseDto>>> getPartyList() {
        List<PartyListResponseDto> response = partyService.getUserPartyMap().entrySet().stream()
                .map(entry -> new PartyListResponseDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(CommonResponse.of(HttpStatus.OK.value(), "파티 목록 조회 성공", response));
    }

    @PatchMapping("/{party_id}")
    public ResponseEntity<CommonResponse<PartyResponseDto>> updateParty(@PathVariable Long party_id,
                                                                        @RequestBody PartyRequestDto partyRequestDto,
                                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PartyResponseDto responseDto = partyService.updateParty(party_id, partyRequestDto, userDetails.getUser());
        return ResponseEntity.ok().body(CommonResponse.of(HttpStatus.OK.value(), "업데이트 성공", responseDto));
    }

    @DeleteMapping("/{party_id}")
    public ResponseEntity<CommonResponse<String>> deleteParty(@PathVariable Long party_id,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        partyService.deleteParty(party_id, userDetails.getUser());
        return ResponseEntity.ok().body(CommonResponse.of(HttpStatus.OK.value(), "정상적으로 삭제 되었습니다.", null));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse<String>> handleCustomException(CustomException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.of(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null));
    }


}

