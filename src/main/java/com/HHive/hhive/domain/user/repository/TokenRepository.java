package com.HHive.hhive.domain.user.repository;

import com.HHive.hhive.domain.user.entity.Token;
import com.HHive.hhive.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findByUser(User user);

    Optional<Token> findByToken(String token);
}
