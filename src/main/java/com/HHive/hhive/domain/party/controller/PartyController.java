package com.HHive.hhive.domain.party.controller;

import com.HHive.hhive.domain.party.request.PartyRequestDto;
import com.HHive.hhive.domain.party.response.PartyResponseDto;
import com.HHive.hhive.domain.party.service.PartyService;
import com.HHive.hhive.domain.user.UserDetailsImpl;
import com.HHive.hhive.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

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

    /*@GetMapping("/{party_id}")
    public ResponseEntity<?> getParty(@PathVariable Long party_id) {
        try {
            PartyResponseDto responseDto = partyService.getPartyDto(party_id);
            return ResponseEntity.ok().body(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping
    public ResponseEntity<List<PartyListResponseDto>> getPartyList() {
        List<PartyListResponseDto> response = new ArrayList<>();

        Map<UserRequestDTO, List<PartyResponseDto>> responseDtoMap = partyService.getUserPartyMap();

        responseDtoMap.forEach((key, value) -> response.add(new PartyListResponseDto(key, value)));

        return ResponseEntity.ok().body(response);
    }


    @PatchMapping("/{party_id}")
    public ResponseEntity<PartyResponseDto> updateParty(@PathVariable Long party_id, @RequestBody PartyRequestDto partyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            PartyResponseDto responseDto = partyService.updateParty(party_id, partyRequestDto, userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new PartyResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }


    @DeleteMapping("/{party_id}")
    public ResponseEntity<PartyResponseDto> deleteParty (@PathVariable Long party_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            partyService.deleteParty(party_id, userDetails.getUser());
            return ResponseEntity.ok().body(new PartyResponseDto("정상적으로 삭제 되었습니다.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new PartyResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }*/


}
