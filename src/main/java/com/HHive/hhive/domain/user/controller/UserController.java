package com.HHive.hhive.domain.user.controller;

import com.HHive.hhive.domain.hive.dto.HiveResponseDTO;
import com.HHive.hhive.domain.user.UserDetailsImpl;
import com.HHive.hhive.domain.user.dto.*;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.domain.user.service.EmailService;
import com.HHive.hhive.domain.user.service.KakaoService;
import com.HHive.hhive.domain.user.service.UserService;
import com.HHive.hhive.global.common.CommonResponse;
import com.HHive.hhive.global.jwt.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final KakaoService kaKaoService;
    private final EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Void>> signup(
            @Valid @RequestBody UserSignupRequestDTO requestDTO) {

        userService.signup(requestDTO);
        return ResponseEntity.ok().body(CommonResponse.of(HttpStatus.CREATED.value(), "회원가입 성공", null));
    }

    @PostMapping("/email-confirm")
    public ResponseEntity<CommonResponse<Integer>> mailConfirm(@RequestBody EmailCheckRequestDTO emailCheckRequestDTO) {

        int num = emailService.sendEmail(emailCheckRequestDTO.getEmail());

        return ResponseEntity.ok().body(CommonResponse.of(HttpStatus.CREATED.value(), "인증코드 발급 성공", num));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<UserInfoResponseDTO>> login(
            @RequestBody UserLoginRequestDTO requestDTO, HttpServletResponse response) {

        UserInfoResponseDTO userInfo = userService.login(requestDTO);

        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(requestDTO.getUsername()));

        return ResponseEntity.ok()
                .body(CommonResponse.of(HttpStatus.OK.value(), "로그인 성공", userInfo));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponse<UserInfoResponseDTO>> getProfile(@PathVariable Long userId) {

        UserInfoResponseDTO responseDTO = userService.getProfile(userId);
        return ResponseEntity.ok()
                .body(CommonResponse.of(HttpStatus.OK.value(), "프로필 조회 성공", responseDTO));
    }



    @PatchMapping("/{userId}")
    public ResponseEntity<CommonResponse<Void>> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserProfileRequestDTO requestDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User loginUser = userDetails.getUser();
        userService.updateProfile(userId, requestDTO, loginUser);

        return ResponseEntity.ok()
                .body(CommonResponse.of(HttpStatus.OK.value(), "프로필 수정 성공", null));
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<CommonResponse<Void>> updatePassword(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserPasswordRequestDTO requestDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User loginUser = userDetails.getUser();
        userService.updatePassword(userId, requestDTO, loginUser);

        return ResponseEntity.ok()
                .body(CommonResponse.of(HttpStatus.OK.value(), "비밀번호 수정 성공", null));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<CommonResponse<Void>> deleteUser(
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User loginUser = userDetails.getUser();
        userService.deleteUser(userId, loginUser);

        return ResponseEntity.ok()
                .body(CommonResponse.of(HttpStatus.OK.value(), "회원 탈퇴 성공", null));
    }

    @GetMapping("/{userId}/hives")
    public ResponseEntity<CommonResponse> getMyHives(

            @PathVariable Long userId) {
        List<HiveResponseDTO> hivesResponses = userService.getMyHives(
                userId);
        return ResponseEntity.ok()
                .body(new CommonResponse<>(200, "참여 목록들이 조회되었습니다.", hivesResponses));
    }


    // 카테고리 설정
    @PostMapping("/{userId}/category")
    public ResponseEntity<CommonResponse<UserCategoryResponseDTO>> setCategory(
            @PathVariable Long userId, @RequestBody HobbyCategoryRequestDTO requestDTO
            , @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User loginUser = userDetails.getUser();
        UserCategoryResponseDTO response = userService.setCategory(userId, requestDTO, loginUser);

        return ResponseEntity.ok()
                .body(CommonResponse.of(HttpStatus.OK.value(), "카테고리 설정 성공", response));
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<CommonResponse<UserInfoResponseDTO>> kakaoLogin(@RequestParam String code, HttpServletResponse response)
            throws JsonProcessingException {

        // jwt 토큰 반환
        String token = kaKaoService.kakaoLogin(code);
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        // 토큰에서 username을 추출
        String username = jwtUtil.getUserInfoFromToken(token).getSubject();

        // username으로 UserInfoResponseDTO를 얻음
        UserInfoResponseDTO userInfo = userService.kakaoLogin(username);

        return ResponseEntity.ok()
                .body(CommonResponse.of(HttpStatus.CREATED.value(), "카카오 로그인 성공", userInfo));
    }
}
