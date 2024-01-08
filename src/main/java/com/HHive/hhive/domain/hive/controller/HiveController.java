package com.HHive.hhive.domain.hive.controller;

import com.HHive.hhive.domain.hive.dto.HiveDTO;
import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.hive.service.HiveService;
import com.HHive.hhive.domain.user.UserDetailsImpl;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.global.common.CommonResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
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



}
