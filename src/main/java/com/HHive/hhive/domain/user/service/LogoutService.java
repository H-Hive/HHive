package com.HHive.hhive.domain.user.service;

import com.HHive.hhive.domain.user.entity.Token;
import com.HHive.hhive.domain.user.repository.TokenRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final EntityManager entityManager;

    public LogoutService(TokenRepository tokenRepository, EntityManager entityManager) {
        this.tokenRepository = tokenRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        final String authHeader = request.getHeader("Refresh-Token");
        final String refreshToken;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);

        Token storedToken = tokenRepository.findByRefreshToken(refreshToken);

        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);

            tokenRepository.setExpiredAndRevoked(refreshToken);
        }
    }
}
