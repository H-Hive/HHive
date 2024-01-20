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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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

    @PostMapping("/emailConfirm")
    public ResponseEntity<CommonResponse<Integer>> mailConfirm(@RequestBody EmailCheckRequestDTO emailCheckRequestDTO) {

        int num = emailService.sendEmail(emailCheckRequestDTO.getEmail());

        return ResponseEntity.ok().body(CommonResponse.of(HttpStatus.CREATED.value(), "인증코드 발급 성공", num));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<UserInfoResponseDTO>> login(
            @RequestBody UserLoginRequestDTO requestDTO, HttpServletResponse response) {

        UserInfoResponseDTO userInfo = userService.login(requestDTO);

        Cookie tokenCookie = jwtUtil.createTokenCookie(requestDTO.getUsername());
        Cookie userInfoCookie = jwtUtil.createUserInfoCookie(userInfo);

        ResponseCookie cookie1 = ResponseCookie.from(userInfoCookie.getName(),userInfoCookie.getValue())
                .sameSite("none")
                .secure(true)
                .path("/")
                .maxAge(3600)
                .build();

        ResponseCookie cookie2 = ResponseCookie.from(tokenCookie.getName(), tokenCookie.getValue())
                .sameSite("none")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie1.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, cookie2.toString());
//        response.addCookie(tokenCookie);
//        response.addCookie(userInfoCookie);

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


    //TODO: 카테고리 선택

    @GetMapping("/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        System.out.println(code);

        // jwt 토큰 반환
        String token = kaKaoService.kakaoLogin(code);

        // 반환된 토큰을 쿠키에 넣음
        Cookie cookie = new Cookie(jwtUtil.JWT_COOKIE_NAME, token.substring(7));
        cookie.setPath("/");

        // + response 객체에 넣음
        response.addCookie(cookie);

        return "redirect:/";
    }
}
