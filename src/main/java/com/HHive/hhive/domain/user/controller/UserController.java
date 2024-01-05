package com.HHive.hhive.domain.user.controller;

import com.HHive.hhive.domain.user.service.UserService;
import com.HHive.hhive.global.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup() {}

    @PostMapping("/login")
    public ResponseEntity<?> login() {}

    @GetMapping("/profile/{user_id}")
    public ResponseEntity<?> getProfile() {}

    @PatchMapping("/profile/{user_id}")
    public ResponseEntity<?> updateProfile() {}

    @PatchMapping("/profile/{user_id}/password")
    public ResponseEntity<?> updatePassword() {}

    //TODO: 카테고리 선택

    //TODO: 카카오 로그인
}
