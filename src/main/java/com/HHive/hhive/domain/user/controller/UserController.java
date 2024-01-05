package com.HHive.hhive.domain.user.controller;

import com.HHive.hhive.domain.user.UserDetailsImpl;
import com.HHive.hhive.domain.user.dto.*;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.domain.user.service.UserService;
import com.HHive.hhive.global.common.CommonResponse;
import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserSignupRequestDTO requestDTO) {
        try {
            userService.signup(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED.value())
                    .body(new CommonResponse<>(200, "회원가입 성공", HttpStatus.CREATED.value()));
        } catch (CustomException customException) {
            return ResponseEntity.status(customException.getStatusCode())
                    .body(new CommonResponse<>(customException.getStatusCode(), customException.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDTO requestDTO, HttpServletResponse response) {
        try {
            userService.login(requestDTO);
        } catch (CustomException customException) {
            return ResponseEntity.status(customException.getStatusCode())
                    .body(new CommonResponse<>(customException.getStatusCode(), customException.getMessage(), null));
        }

        // 로그인 시 JWT 토큰이 헤더에 보임
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(requestDTO.getUsername()));

        return ResponseEntity.ok()
                .body(new CommonResponse<>(200, "로그인 성공", HttpStatus.OK.value()));
    }

    @GetMapping("/profile/{user_id}")
    public ResponseEntity<?> getProfile(@PathVariable Long user_id) {

        try {
            UserInfoResponseDTO responseDTO = userService.getProfile(user_id);
            return ResponseEntity.ok(responseDTO);
        } catch (CustomException customException) {
            return ResponseEntity.status(customException.getStatusCode())
                    .body(new CommonResponse<>(customException.getStatusCode(), customException.getMessage(), null));
        }
    }

    @PatchMapping("/profile/{user_id}")
    public ResponseEntity<?> updateProfile(
            @PathVariable Long user_id,
            @Valid @RequestBody UpdateUserProfileRequestDTO requestDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User loginUser = userDetails.getUser();

        try {
            userService.updateProfile(user_id, requestDTO, loginUser);
            return ResponseEntity.ok()
                    .body(new CommonResponse<>(200, "프로필 수정 성공", HttpStatus.OK.value()));
        } catch (CustomException customException) {
            return ResponseEntity.status(customException.getStatusCode())
                    .body(new CommonResponse<>(customException.getStatusCode(), customException.getMessage(), null));
        }
    }

    @PatchMapping("/profile/{user_id}/password")
    public ResponseEntity<?> updatePassword(
            @PathVariable Long user_id,
            @Valid @RequestBody UpdateUserPasswordRequestDTO requestDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User loginUser = userDetails.getUser();

        try {
            userService.updatePassword(user_id, requestDTO, loginUser);
            return ResponseEntity.ok()
                    .body(new CommonResponse<>(200, "비밀번호 수정 성공", HttpStatus.OK.value()));
        } catch (CustomException customException) {
            return ResponseEntity.status(customException.getStatusCode())
                    .body(new CommonResponse<>(customException.getStatusCode(), customException.getMessage(), null));
        }
    }

    //TODO: 카테고리 선택

    //TODO: 카카오 로그인
}
