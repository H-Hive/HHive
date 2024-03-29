package com.HHive.hhive.domain.user.service;

import com.HHive.hhive.domain.category.data.MajorCategory;
import com.HHive.hhive.domain.category.data.SubCategory;
import com.HHive.hhive.domain.hive.dto.HiveResponseDTO;
import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.relationship.hiveuser.service.HiveUserService;
import com.HHive.hhive.domain.user.dto.*;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.domain.user.repository.UserRepository;
import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;
import com.HHive.hhive.global.exception.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final HiveUserService hiveUserService;

    private final EmailService emailService;

    @Transactional
    public void signup(UserSignupRequestDTO requestDTO) {
        String username = requestDTO.getUsername();
        String password = requestDTO.getPassword();
        String checkPassword = requestDTO.getCheckPassword();
        String email = requestDTO.getEmail();
        String description = requestDTO.getDescription();

        // 비밀번호 != 비밀번호 확인
        if (!Objects.equals(password, checkPassword)) {
            throw new PasswordConfirmationException();
        }

        String encodePassword = passwordEncoder.encode(password);

        // 유저네임 중복확인
        if (userRepository.findByUsername(username).isPresent()) {
            throw new AlreadyExistUsernameException();
        }

        // 이메일 중복확인
        if (userRepository.findByEmail(email).isPresent()) {
            throw new AlreadyExistEmailException();
        }

        User user = new User(username, encodePassword, email, description);
        userRepository.save(user);
    }

    public UserInfoResponseDTO login(UserLoginRequestDTO requestDTO) {
        String username = requestDTO.getUsername();
        String password = requestDTO.getPassword();

        // 저장된 회원이 없을 때
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserNotFoundException();
        }

        // 로그인 성공시 UserInfoResponseDTO로 변환하여 반환
        return new UserInfoResponseDTO(user);
    }

    public UserInfoResponseDTO kakaoLogin(String username) {

        // username으로 User 객체를 찾음
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        // User 객체를 UserInfoResponseDTO로 변환
        UserInfoResponseDTO userInfo = new UserInfoResponseDTO(user);

        return userInfo;
    }

    public UserInfoResponseDTO getProfile(Long userId) {
        User user = getUser(userId);
        return new UserInfoResponseDTO(user);
    }

    @Transactional
    public void updateProfile(Long userId, UpdateUserProfileRequestDTO requestDTO, User loginUser) {

        User user = getUser(userId);

        if (!loginUser.getId().equals(user.getId())) {
            throw new AuthenticationMismatchException();
        }

        // 카카오 로그인한 유저인 경우 이메일 수정을 허용하지 않음
        if (userRepository.findByKakaoId(user.getKakaoId()).isPresent() && requestDTO.getEmail() != null) {
            throw new KakaoUserEmailModificationException();
        }

        user.updateProfile(requestDTO);
    }

    @Transactional
    public void updatePassword(Long userId, UpdateUserPasswordRequestDTO requestDTO, User loginUser) {
        String password = requestDTO.getPassword();
        String updatePassword = requestDTO.getUpdatePassword();
        String checkUpdatePassword = requestDTO.getCheckUpdatePassword();

        User user = getUser(userId);

        if (!loginUser.getUsername().equals(user.getUsername())) {
            throw new AuthenticationMismatchException();
        }

        if (!updatePassword.equals(checkUpdatePassword)) {
            throw new PasswordConfirmationException();
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordMismatchException();
        } else {
            updatePassword = passwordEncoder.encode(updatePassword);
            user.setPassword(updatePassword);
        }

        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long user_id, User loginUser) {
        User userToDelete = getUser(user_id);

        // 로그인 한 유저 == 삭제할 유저 확인
        if (!loginUser.getUsername().equals(userToDelete.getUsername())) {
            throw new AuthenticationMismatchException();
        }

        // 삭제 -> Soft delete로 구현
        userToDelete.updateDeletedAt();

        userRepository.save(userToDelete);
    }


//    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void processPendingDeletions() {
        List<User> userToDelete = userRepository.findByIsDeletedAndDeletedAtBefore(true, LocalDateTime.now().minusMinutes(1));

        userRepository.deleteAll(userToDelete);
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmailAndIs_deletedFalse(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsernameAndIs_deletedFalse(username)
                .orElseThrow(UserNotFoundException::new);
    }

    public List<HiveResponseDTO> getMyHives(Long userId) {
        User user = getUser(userId);

        List<Hive> myHives = hiveUserService.findAllHivesByHiveUser(user);
        return myHives.stream().map(HiveResponseDTO::of).toList();

    }

    // 카테고리 설정
    @Transactional
    public UserCategoryResponseDTO setCategory(Long userId, HobbyCategoryRequestDTO requestDTO, User loginUser) {

        if (!userId.equals(loginUser.getId())) {
            throw new CustomException(ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
        user.setMajorCategory(MajorCategory.findByTitle(requestDTO.getMajorCategory()));
        user.setSubCategory(SubCategory.findByTitle(requestDTO.getSubCategory()));
        userRepository.save(user);

        return new UserCategoryResponseDTO(user.getMajorCategory(), user.getSubCategory());
    }

    // 이메일 인증 요청
    @Transactional
    public void requestEmailVerification(Long userId, int verificationCode) {
        User user = getUser(userId);

        // User 엔터티의 email_verification_code 필드를 업데이트
        user.setEmailVerificationCode(String.valueOf(verificationCode));
    }

    // 이메일 인증 코드 검증
    @Transactional
    public void verifyEmail(Long userId, String inputCode) {
        User user = getUser(userId);

        // 입력한 코드와 User 엔터티의 email_verification_code를 비교
        if (!inputCode.equals(user.getEmailVerificationCode())) {
            throw new InvalidEmailVerificationCodeException();
        }

        // 코드가 일치하면 email_verified 값을 true로 업데이트
        user.setEmailVerified(true);
    }

    // email_verification_code 필드 업데이트
    @Transactional
    public void updateEmailVerificationCode(Long userId, String code) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        user.setEmailVerificationCode(code);
    }
}
