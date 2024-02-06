package com.HHive.hhive.domain.user.service;

import com.HHive.hhive.domain.user.entity.Token;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.domain.user.repository.TokenRepository;
import com.HHive.hhive.domain.user.repository.UserRepository;
import com.HHive.hhive.global.exception.jwt.ExpiredRefreshTokenException;
import com.HHive.hhive.global.exception.jwt.RefreshTokenNotFoundException;
import com.HHive.hhive.global.exception.jwt.RevokedRefreshTokenException;
import com.HHive.hhive.global.exception.user.UserNotFoundException;
import com.HHive.hhive.global.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(TokenRepository tokenRepository, UserRepository userRepository, JwtUtil jwtUtil) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public String createRefreshToken(Long userId) {

        // DB에서 User 객체를 조회
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        // 토큰 만료시간 7일
        long TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000L;

        // JwtUtil 클래스의 메서드를 호출하여 리프레시 토큰을 생성
        String refreshToken = jwtUtil.createToken(user.getUsername(), TOKEN_VALIDITY);

        Token tokenEntity = Token.builder()
                .refreshToken(refreshToken)
                .user(user)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(tokenEntity);

        return "Bearer " + refreshToken;
    }

    @Transactional
    public String createAccessTokenWithRefreshToken(String refreshToken) {

        System.out.println(refreshToken);

        // 데이터베이스에서 리프레시 토큰 조회
        Token tokenEntity = tokenRepository.findByRefreshToken(refreshToken);

        if (tokenEntity == null) {
            throw new RefreshTokenNotFoundException();
        }

        try {
            // 리프레시 토큰을 검증
            jwtUtil.validationToken(refreshToken);

            // 리프레시 토큰이 사용 중지되었다면 예외를 발생
            if (tokenEntity.isRevoked()) {
                throw new RevokedRefreshTokenException();
            }

            // 액세스 토큰의 유효시간을 1시간으로 설정
            long TOKEN_VALIDITY = 60 * 60 * 1000L;

            // 새로운 액세스 토큰을 생성하고 반환
            return jwtUtil.createToken(tokenEntity.getUser().getUsername(), TOKEN_VALIDITY);

        } catch (ExpiredJwtException e) {
            // 리프레시 토큰이 만료되었다면 예외를 발생
            throw new ExpiredRefreshTokenException();
        }
    }
}