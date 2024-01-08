package com.HHive.hhive.domain.hive.controller;

import com.HHive.hhive.domain.hive.dto.HiveDTO;
import com.HHive.hhive.domain.hive.service.HiveService;
import com.HHive.hhive.domain.user.UserDetailsImpl;
import com.HHive.hhive.global.common.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HiveController {

    private final HiveService hiveService;

    @PostMapping("/hives")
    public ResponseEntity<CommonResponse> createHive(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid HiveDTO.CreateHiveRequest createHiveRequest) {
        HiveDTO.Response response = hiveService.createHive(userDetails.getUser(),
                createHiveRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(HttpStatus.OK.value(), "하이브가 작성되었습니다.", response));
    }

    @PatchMapping("/hives/{hive_id}/update")
    public ResponseEntity<CommonResponse> updateName(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long hive_id,
            @RequestBody @Valid HiveDTO.UpdateHiveRequest updateRequest) {
        HiveDTO.Response response = hiveService.updateHive(userDetails.getUser(), hive_id,
                updateRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK.value(), "하이브가 수정되었습니다.", response));
    }


}
