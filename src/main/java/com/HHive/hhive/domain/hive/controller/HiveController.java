package com.HHive.hhive.domain.hive.controller;

import com.HHive.hhive.domain.hive.dto.CreateHiveRequestDTO;
import com.HHive.hhive.domain.hive.dto.HiveResponseDTO;
import com.HHive.hhive.domain.hive.dto.UpdateHiveRequestDTO;
import com.HHive.hhive.domain.hive.service.HiveService;
import com.HHive.hhive.domain.relationship.hiveuser.dto.HiveUserInviteRequestDTO;
import com.HHive.hhive.domain.relationship.hiveuser.service.HiveUserService;
import com.HHive.hhive.domain.user.UserDetailsImpl;
import com.HHive.hhive.domain.user.dto.UserInfoResponseDTO;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.global.common.CommonResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/hives")
@RequiredArgsConstructor
public class HiveController {

    private final HiveService hiveService;
    private final HiveUserService hiveUserService;

    @PostMapping
    public ResponseEntity<CommonResponse> createHive(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid CreateHiveRequestDTO createHiveRequestDTO) {
        HiveResponseDTO response = hiveService.createHive(userDetails.getUser(),
                createHiveRequestDTO);
        return ResponseEntity.ok()
                .body(new CommonResponse<>(200, "하이브가 작성되었습니다.", response));
    }

    @PatchMapping("{hive_id}/update")
    public ResponseEntity<CommonResponse> updateHive(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long hive_id,
            @RequestBody @Valid UpdateHiveRequestDTO updateRequest) {
        HiveResponseDTO response = hiveService.updateHive(userDetails.getUser(), hive_id,
                updateRequest);
        return ResponseEntity.ok()
                .body(new CommonResponse<>(200, "하이브가 수정되었습니다.", response));
    }

    @GetMapping("/{hive_id}")
    public ResponseEntity<CommonResponse> getHive(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long hive_id) {
        HiveResponseDTO response = hiveService.getHive(userDetails.getUser(), hive_id);
        return ResponseEntity.ok()
                .body(new CommonResponse<>(200, "하이브가 조회되었습니다.", response));
    }

    @GetMapping("")
    public ResponseEntity<CommonResponse> gethives() {
        List<HiveResponseDTO> responses = hiveService.getHives();
        return ResponseEntity.ok()
                .body(new CommonResponse<>(200, "하이브들이 조회되었습니다.", responses));
    }

    @PatchMapping("{hive_id}")
    public ResponseEntity<CommonResponse> deleteHive(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long hive_id) {
        hiveService.deleteHive(userDetails.getUser(), hive_id);
        return ResponseEntity.ok()
                .body(new CommonResponse<>(200, "하이브가 삭제되었습니다.", null));
    }

    @PostMapping("/{hive_id}")
    public ResponseEntity<CommonResponse> inviteNewUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long hive_id,
            @RequestBody @Valid HiveUserInviteRequestDTO requestDTO) {
        hiveService.inviteNewUser(userDetails.getUser(), hive_id, requestDTO);
        return ResponseEntity.ok()
                .body(new CommonResponse<>(200, "하이브에 참여하였습니다.", null));
    }

    @GetMapping("/{hive_id}/hiveUsers")
    public ResponseEntity<CommonResponse> getHiveUsersInHive(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long hive_id) {
        List<UserInfoResponseDTO> hiveUserResponses = hiveService.searchUsersInHive(
                userDetails.getUser(), hive_id);
        return ResponseEntity.ok()
                .body(new CommonResponse<>(200, "하이브 유저들이 조회되었습니다.", hiveUserResponses));
    }

    @GetMapping("/{hive_id}/hiveUsers/search")
    public ResponseEntity<CommonResponse> getHiveUserInHive(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long hive_id,
            @RequestParam String username) {
        UserInfoResponseDTO hiveUserResponse = hiveService.searchUserInHive(userDetails.getUser(), hive_id, username);
        return ResponseEntity.ok()
                .body(new CommonResponse<>(200, "하이브 유저가 조회되었습니다.", hiveUserResponse));
    }

    @DeleteMapping("{hive_id}/hiveUsers")
    public ResponseEntity<CommonResponse> deleteHiveUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long hive_id,
            @RequestParam String username) {
        hiveService.deleteHiveUser(userDetails.getUser(), hive_id, username);
        return ResponseEntity.ok()
                .body(new CommonResponse<>(200, "하이브 유저가 탈퇴되었습니다.", null));
    }

}