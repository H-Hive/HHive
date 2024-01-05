package com.HHive.hhive.domain.user.repository;

import com.HHive.hhive.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
