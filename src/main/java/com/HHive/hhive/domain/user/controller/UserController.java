package com.HHive.hhive.domain.user.controller;

import com.HHive.hhive.domain.hive.dto.HiveResponseDTO;
import com.HHive.hhive.domain.user.UserDetailsImpl;
import com.HHive.hhive.domain.user.dto.*;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.domain.user.service.AuthService;
import com.HHive.hhive.domain.user.service.EmailService;
import com.HHive.hhive.domain.user.service.KakaoService;
import com.HHive.hhive.domain.user.service.UserService;
import com.HHive.hhive.global.common.CommonResponse;
import com.HHive.hhive.global.jwt.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final KakaoService kaKaoService;
    private final EmailService emailService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Void>> signup(
            @Valid @RequestBody UserSignupRequestDTO requestDTO) {

        userService.signup(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of("회원가입 성공", null));
    }

    // 이메일 인증 코드 생성
    @PostMapping("/{userId}/email-verification")
    public ResponseEntity<CommonResponse<Integer>> mailConfirm(@PathVariable Long userId,
                                                               @RequestBody EmailCheckRequestDTO emailCheckRequestDTO) {

        // 이메일 인증 코드 생성 및 이메일 전송
        int verificationCode = emailService.sendEmail(emailCheckRequestDTO.getEmail());

        // 생성된 이메일 인증 코드를 사용자 엔티티에 저장
        userService.requestEmailVerification(userId, verificationCode);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of("인증코드 발급 성공", verificationCode));
    }

    // 이메일 인증 코드 검증
    @PostMapping("/{userId}/email-verification/verify")
    public ResponseEntity<CommonResponse<Void>> verifyEmail(@PathVariable Long userId, @RequestBody EmailCheckRequestDTO requestDTO) {

        userService.verifyEmail(userId, requestDTO.getVerificationCode());

        return ResponseEntity.ok().body(CommonResponse.of("이메일 인증 성공", null));
    }



    @PostMapping("/login")
    public ResponseEntity<CommonResponse<UserInfoResponseDTO>> login(
            @RequestBody UserLoginRequestDTO requestDTO, HttpServletResponse response) {

        UserInfoResponseDTO userInfo = userService.login(requestDTO);

        // 액세스 토큰 생성 및 헤더에 저장
        String accessToken = jwtUtil.createToken(requestDTO.getUsername());
        response.setHeader("Access-Token", accessToken);

        // 리프레시 토큰 생성 및 DB에 저장
        String refreshToken = authService.createRefreshToken(userInfo.getUserId());
        response.setHeader("Refresh-Token", refreshToken);

        return ResponseEntity.ok().body(CommonResponse.of("로그인 성공", userInfo));
    }


//
//    // 로그아웃 메서드
//    @PostMapping("/logout")
//    public ResponseEntity<CommonResponse<Void>> logout(HttpServletRequest request) {
//        String refreshToken = request.getHeader("Authorization");
//        System.out.println("Refresh token: " + refreshToken);
//        authService.logout(refreshToken);
//        return ResponseEntity.ok().body(CommonResponse.of("로그아웃 성공", null));
//    }



    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponse<UserInfoResponseDTO>> getProfile(@PathVariable Long userId) {

        UserInfoResponseDTO responseDTO = userService.getProfile(userId);
        return ResponseEntity.ok().body(CommonResponse.of("프로필 조회 성공", responseDTO));
    }



    @PatchMapping("/{userId}")
    public ResponseEntity<CommonResponse<Void>> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserProfileRequestDTO requestDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User loginUser = userDetails.getUser();
        userService.updateProfile(userId, requestDTO, loginUser);

        return ResponseEntity.ok().body(CommonResponse.of("프로필 수정 성공", null));
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<CommonResponse<Void>> updatePassword(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserPasswordRequestDTO requestDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User loginUser = userDetails.getUser();
        userService.updatePassword(userId, requestDTO, loginUser);

        return ResponseEntity.ok().body(CommonResponse.of("비밀번호 수정 성공", null));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<CommonResponse<Void>> deleteUser(
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User loginUser = userDetails.getUser();
        userService.deleteUser(userId, loginUser);

        return ResponseEntity.ok().body(CommonResponse.of("회원 탈퇴 성공", null));
    }

    @GetMapping("/{userId}/hives")
    public ResponseEntity<CommonResponse<List<HiveResponseDTO>>> getMyHives(

            @PathVariable Long userId) {
        List<HiveResponseDTO> hivesResponses = userService.getMyHives(
                userId);
        return ResponseEntity.ok()
                .body(CommonResponse.of("참여 목록들이 조회되었습니다.", hivesResponses));
    }


    // 카테고리 설정
    @PostMapping("/{userId}/category")
    public ResponseEntity<CommonResponse<UserCategoryResponseDTO>> setCategory(
            @PathVariable Long userId, @RequestBody HobbyCategoryRequestDTO requestDTO
            , @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User loginUser = userDetails.getUser();
        UserCategoryResponseDTO response = userService.setCategory(userId, requestDTO, loginUser);

        return ResponseEntity.ok().body(CommonResponse.of("카테고리 설정 성공", response));
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<CommonResponse<UserInfoResponseDTO>> kakaoLogin(@RequestParam String code, HttpServletResponse response)
            throws JsonProcessingException {

        // jwt 토큰 반환
        String token = kaKaoService.kakaoLogin(code);
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        // 토큰에서 username을 추출
        String username = jwtUtil.getUserInfoFromToken(token.substring(7)).getSubject();

        // username으로 UserInfoResponseDTO를 얻음
        UserInfoResponseDTO userInfo = userService.kakaoLogin(username);

        return ResponseEntity.ok().body(CommonResponse.of("카카오 로그인 성공", userInfo));
    }

    // 리프레시 토큰을 이용한 액세스 토큰 갱신
    @PostMapping("/refresh-token")
    public ResponseEntity<CommonResponse<String>> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {

        String newAccessToken = authService.createAccessTokenWithRefreshToken(refreshTokenDTO.getRefreshToken());

        return ResponseEntity.ok().body(CommonResponse.of("액세스 토큰 갱신 성공", newAccessToken));
    }
}
