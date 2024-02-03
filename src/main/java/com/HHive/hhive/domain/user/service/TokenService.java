package com.HHive.hhive.domain.user.service;

import com.HHive.hhive.domain.user.entity.Token;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.domain.user.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public List<Token> getTokensByUser(User user) {
        return tokenRepository.findByUser(user);
    }
}
