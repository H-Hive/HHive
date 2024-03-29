package com.HHive.hhive.domain.hive.controller;

import com.HHive.hhive.domain.category.data.MajorCategory;
import com.HHive.hhive.domain.category.data.SubCategory;
import com.HHive.hhive.domain.hive.dto.CreateHiveRequestDTO;
import com.HHive.hhive.domain.hive.dto.HiveResponseDTO;
import com.HHive.hhive.domain.hive.dto.UpdateHiveInfoRequestDTO;
import com.HHive.hhive.domain.hive.dto.UpdateHiveTitleRequestDTO;
import com.HHive.hhive.domain.hive.service.HiveService;
import com.HHive.hhive.domain.relationship.hiveuser.dto.HiveUserInviteRequestDTO;
import com.HHive.hhive.domain.user.UserDetailsImpl;
import com.HHive.hhive.domain.user.dto.UserInfoResponseDTO;
import com.HHive.hhive.global.common.CommonResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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


@RestController
@RequestMapping("/api/hives")
@RequiredArgsConstructor
public class HiveController {

    private final HiveService hiveService;

    @PostMapping
    public ResponseEntity<CommonResponse<HiveResponseDTO>> createHive(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid CreateHiveRequestDTO createHiveRequestDTO) {

        SubCategory.findByStringName(createHiveRequestDTO.getSubCategoryName());
        HiveResponseDTO response = hiveService.createHive(userDetails.getUser(),
                createHiveRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of("하이브가 작성되었습니다.", response));
    }

    @PatchMapping("{hiveId}/title")
    public ResponseEntity<CommonResponse<HiveResponseDTO>> updateHiveTitle(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long hiveId,
            @RequestBody @Valid UpdateHiveTitleRequestDTO updateHiveTitleRequest) {

        HiveResponseDTO response = hiveService.updateHiveTitle(userDetails.getUser(), hiveId,
                updateHiveTitleRequest);

        return ResponseEntity.ok().body(CommonResponse.of("하이브가 수정되었습니다.", response));
    }

    @PatchMapping("{hiveId}/info")
    public ResponseEntity<CommonResponse<HiveResponseDTO>> updateHiveInfo(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long hiveId,
            @RequestBody @Valid UpdateHiveInfoRequestDTO updateHiveInfoRequest) {

        HiveResponseDTO response = hiveService.updateHiveInfo(userDetails.getUser(), hiveId,
                updateHiveInfoRequest);

        return ResponseEntity.ok().body(CommonResponse.of("하이브가 수정되었습니다.", response));
    }

    @GetMapping("/{hiveId}")
    public ResponseEntity<CommonResponse<HiveResponseDTO>> getHive(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long hiveId) {

        HiveResponseDTO response = hiveService.getHive(userDetails.getUser(), hiveId);

        return ResponseEntity.ok().body(CommonResponse.of("하이브가 조회되었습니다.", response));
    }

    @GetMapping("")
    public ResponseEntity<CommonResponse<List<HiveResponseDTO>>> gethives() {
        List<HiveResponseDTO> responses = hiveService.getHives();

        return ResponseEntity.ok().body(CommonResponse.of("하이브들이 조회되었습니다.", responses));
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponse<List<HiveResponseDTO>>> getHivesByCategory(
            @RequestParam(required = false) String majorCategory,
            @RequestParam(required = false) String subCategory) {

        List<HiveResponseDTO> responses = hiveService.getHivesByCategory(
                MajorCategory.findByStringName(majorCategory),
                SubCategory.findByStringName(subCategory));

        return ResponseEntity.ok().body(CommonResponse.of("하이브들이 조회되었습니다.", responses));
    }


    @DeleteMapping("{hiveId}")
    public ResponseEntity<CommonResponse<String>> deleteHive(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long hiveId) {

        hiveService.deleteHive(userDetails.getUser(), hiveId);

        return ResponseEntity.ok().body(CommonResponse.of("하이브가 삭제되었습니다.", null));
    }

    @PostMapping("/{hiveId}")
    public ResponseEntity<CommonResponse<String>> inviteNewUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long hiveId,
            @RequestBody @Valid HiveUserInviteRequestDTO requestDTO) {

        hiveService.inviteNewUser(userDetails.getUser(), hiveId, requestDTO);

        return ResponseEntity.ok().body(CommonResponse.of("하이브에 참여하였습니다.", null));
    }

    @GetMapping("/{hiveId}/hiveUsers")
    public ResponseEntity<CommonResponse<List<UserInfoResponseDTO>>> getHiveUsersInHive(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long hiveId) {

        List<UserInfoResponseDTO> hiveUserResponses = hiveService.searchUsersInHive(
                userDetails.getUser(), hiveId);

        return ResponseEntity.ok()
                .body(CommonResponse.of("하이브 유저들이 조회되었습니다.", hiveUserResponses));
    }

    @GetMapping("/{hiveId}/hiveUser")
    public ResponseEntity<CommonResponse<UserInfoResponseDTO>> getHiveUserInHive(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long hiveId,
            @RequestParam String username) {

        UserInfoResponseDTO hiveUserResponse = hiveService.searchUserInHive(userDetails.getUser(),
                hiveId, username);

        return ResponseEntity.ok()
                .body(CommonResponse.of("하이브 유저가 조회되었습니다.", hiveUserResponse));
    }

    @DeleteMapping("{hiveId}/hiveUsers")
    public ResponseEntity<CommonResponse<String>> deleteHiveUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long hiveId,
            @RequestParam String username) {

        hiveService.deleteHiveUser(userDetails.getUser(), hiveId, username);

        return ResponseEntity.ok()
                .body(CommonResponse.of("하이브 유저가 탈퇴되었습니다.", null));
    }

}