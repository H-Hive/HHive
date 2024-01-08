package com.HHive.hhive.domain.user.repository;

import com.HHive.hhive.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.is_deleted = :isDeleted AND u.deletedAt < :dateTime")
    List<User> findByIsDeletedAndDeletedAtBefore(@Param("isDeleted") boolean is_deleted, @Param("dateTime") LocalDateTime dateTime);
}
