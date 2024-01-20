package com.HHive.hhive.global.jwt;

import com.HHive.hhive.domain.user.dto.UserInfoResponseDTO;
import com.HHive.hhive.global.exception.jwt.ExpiredJwtTokenException;
import com.HHive.hhive.global.exception.jwt.InvalidJwtSignatureException;
import com.HHive.hhive.global.exception.jwt.InvalidJwtTokenException;
import com.HHive.hhive.global.exception.jwt.UnsupportedJwtTokenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class JwtUtil {

    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";

    // 쿠키 이름
    public static final String USER_INFO_COOKIE_NAME = "userinfo";

    public static final String JWT_COOKIE_NAME = "token";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String resolveToken(HttpServletRequest request) {

//        Cookie[] cookies = request.getCookies();
//
//        if (cookies == null) {
//            return null;
//        }
//
//        String bearerToken = Arrays.stream(cookies)
//                .filter(cookie -> cookie.getName().equals(JWT_COOKIE_NAME))
//                .findFirst()
//                .map(Cookie::getValue)
//                .orElse(null);
//
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
//            return bearerToken.substring(7);
//        }

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public boolean validationToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException exception) {
            throw new InvalidJwtSignatureException(exception);
        } catch (ExpiredJwtException exception) {
            throw new ExpiredJwtTokenException(exception);
        } catch (UnsupportedJwtException exception) {
            throw new UnsupportedJwtTokenException(exception);
        } catch (IllegalArgumentException exception) {
            throw new InvalidJwtTokenException(exception);
        }
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

//    public Cookie createTokenCookie(String username) {
//
//        long EXPIRED_TIME = 60 * 60;
//
//        String jwtToken = createToken(username);
//
//        Cookie cookie = new Cookie(JWT_COOKIE_NAME, jwtToken);
//        cookie.setPath("/api");
//        cookie.setMaxAge((int) EXPIRED_TIME);
//        cookie.setHttpOnly(true);
//
//        return cookie;
//    }
//
//    public Cookie createUserInfoCookie(UserInfoResponseDTO responseDTO) {
//
//        long EXPIRED_TIME = 60 * 60;
//
//        String responseToString = null;
//
//        try {
//            responseToString = objectMapper.writeValueAsString(responseDTO);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        String encodedUserInfo = Base64.getEncoder().encodeToString(responseToString.getBytes());
//
//        Cookie cookie = new Cookie(USER_INFO_COOKIE_NAME, encodedUserInfo);
//        cookie.setPath("/");
//        cookie.setMaxAge((int) EXPIRED_TIME);
//
//        return cookie;
//    }

    public String createToken(String username) {
        Date date = new Date();

        // 토큰 만료시간 60분
        long TOKEN_TIME = 60 * 60 * 1000;
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }
}
