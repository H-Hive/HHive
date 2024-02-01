package com.HHive.hhive.domain.user.service;

import com.HHive.hhive.domain.user.dto.UpdateUserProfileRequestDTO;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.domain.user.repository.UserRepository;
import com.HHive.hhive.global.exception.user.KakaoUserEmailModificationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Nested
    @DisplayName("프로필 수정")
    class updateProfileTest {

        @Test
        @DisplayName("카카오 로그인 유저의 이메일 수정")
        void updateProfileByKakaoUserFailsWhenEmailIsChanged() {

            // given
            // 카카오 로그인한 사용자를 생성
            User kakaoUser = new User("username", "password", "email@example.com", "description");
            kakaoUser.kakaoIdUpdate(12345L); // 카카오 로그인한 사용자라고 가정하기 위해 카카오 ID를 설정
            kakaoUser.setId(1L); // 사용자 ID 설정

            // 이메일을 변경하는 요청을 생성
            UpdateUserProfileRequestDTO requestDTO = new UpdateUserProfileRequestDTO("newemail@example.com", "new description");

            // userRepository에서 동일한 Id와 카카오 Id를 가진 사용자를 반환하도록 설정
            when(userRepository.findById(1L)).thenReturn(Optional.of(kakaoUser)); // findById() 메서드를 가장
            when(userRepository.findByKakaoId(kakaoUser.getKakaoId())).thenReturn(Optional.of(kakaoUser)); // findByKakaoId() 메서드를 가장

            // 로그인한 사용자를 생성
            User loginUser = new User("username", "password", "email@example.com", "description");
            loginUser.kakaoIdUpdate(12345L); // 카카오 로그인한 사용자라고 가정하기 위해 카카오 ID를 설정
            loginUser.setId(1L); // 사용자 ID 설정

            // then
            // 이메일 수정을 시도하면 KakaoUserEmailModificationException 예외가 발생해야 함
            assertThrows(KakaoUserEmailModificationException.class, () -> {
                userService.updateProfile(1L, requestDTO, loginUser); // 가장된 ID 사용
            });
        }
    }
}
