package com.HHive.hhive.domain.user.repository;

import com.HHive.hhive.domain.user.entity.Token;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.global.exception.common.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findByUser(User user);


    Token findByRefreshToken(String token);

    @Modifying
    @Transactional
    @Query("update Token t set t.expired = true, t.revoked = true where t.refreshToken = :token")
    void setExpiredAndRevoked(String token);
}
