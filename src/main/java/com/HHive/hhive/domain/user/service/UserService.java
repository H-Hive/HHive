package com.HHive.hhive.domain.user.service;

import com.HHive.hhive.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup() {}

    @Transactional
    public void updateProfile() {}

    @Transactional
    public void updatePassword() {}
}
