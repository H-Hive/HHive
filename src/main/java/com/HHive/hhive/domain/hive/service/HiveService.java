package com.HHive.hhive.domain.hive.service;

import com.HHive.hhive.domain.category.data.MajorCategory;
import com.HHive.hhive.domain.category.data.SubCategory;
import com.HHive.hhive.domain.hive.dto.CreateHiveRequestDTO;
import com.HHive.hhive.domain.hive.dto.HiveResponseDTO;
import com.HHive.hhive.domain.hive.dto.UpdateHiveRequestDTO;
import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.hive.repository.HiveRepository;
import com.HHive.hhive.domain.relationship.hiveuser.dto.HiveUserInviteRequestDTO;
import com.HHive.hhive.domain.relationship.hiveuser.repository.HiveUserRepository;
import com.HHive.hhive.domain.relationship.hiveuser.service.HiveUserService;
import com.HHive.hhive.domain.user.dto.UserInfoResponseDTO;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.domain.user.repository.UserRepository;
import com.HHive.hhive.domain.user.service.UserService;
import com.HHive.hhive.global.exception.hive.AlreadyExistHiveException;
import com.HHive.hhive.global.exception.hive.NotFoundHiveException;
import com.HHive.hhive.global.exception.user.AlreadyExistEmailException;
import com.HHive.hhive.global.exception.user.NotFoundUserException;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HiveService {

    private final UserService userService;
    private final HiveUserService hiveUserService;
    private final UserRepository userRepository;
    private final HiveRepository hiveRepository;
    private final HiveUserRepository hiveUserRepository;


    public HiveResponseDTO createHive(User user, CreateHiveRequestDTO createHiveRequestDTO) {
        User createBy = userService.getUser(user.getId());
        if (hiveRepository.findByTitle(createHiveRequestDTO.getTitle()).isPresent()) {
            throw new AlreadyExistHiveException();
        }
        Hive saveHive = hiveRepository.save(createHiveRequestDTO.toEntity(createBy));
        hiveUserService.saveHiveUser(saveHive, createBy);

        return HiveResponseDTO.of(saveHive);

    }

    @Transactional
    public HiveResponseDTO updateHive(User user, Long hiveId,
            @Valid UpdateHiveRequestDTO updateHiveRequest) {
        Hive hive = getHiveAndCheckAuth(user, hiveId);

        hive.update(updateHiveRequest);
        return HiveResponseDTO.of(hive);
    }

    public HiveResponseDTO getHive(User user, Long hiveId) {
        Hive hive = getHiveAndCheckAuth(user, hiveId);
        return HiveResponseDTO.of(hive);
    }

    public List<HiveResponseDTO> getHives() {
        List<Hive> hives = hiveRepository.findAllHiveNotDeleted();

        return hives.stream().map(HiveResponseDTO::of).toList();
    }

    public List<HiveResponseDTO> getHivesByCategory(MajorCategory majorCategory,
            SubCategory subCategory) {
        List<Hive> hives = hiveRepository.findAllByMajorCategoryAndSubCategoryContaining(
                majorCategory, subCategory);

        if (hives.isEmpty()) {
            throw new NotFoundHiveException();
        }

        return hives.stream().map(HiveResponseDTO::of).toList();
    }

    @Transactional
    public void deleteHive(User user, Long hiveId) {
        Hive hive = getHiveAndCheckAuth(user, hiveId);
        hive.deleteHive();

    }

    public void inviteNewUser(User user, Long boardId, HiveUserInviteRequestDTO requestDTO) {
        Hive hive = getHiveAndCheckAuth(user, boardId);
        User requestUser = userService.findUserByEmail(requestDTO.getEmail());

        if (hiveUserService.isExistedUser(requestUser, hive)) {
            throw new AlreadyExistEmailException();
        }
        hiveUserService.saveHiveUser(hive, requestUser);
    }

    public List<UserInfoResponseDTO> searchUsersInHive(User user, Long hiveId) {
        Hive hive = getHiveAndCheckAuth(user, hiveId);

        List<User> hiveUsers = hiveUserService.findAllByHiveUsersInHive(hive);
        return hiveUsers.stream().map(UserInfoResponseDTO::new).toList();
    }

    public UserInfoResponseDTO searchUserInHive(User user, Long hiveId, String username) {
        Hive hive = getHiveAndCheckAuth(user, hiveId);
        User searchHiveUser = userService.findUserByUsername(username);

        User hiveUser = hiveUserService.searchHiveUser(hive, searchHiveUser);
        return new UserInfoResponseDTO(hiveUser);
    }

    @Transactional
    public void deleteHiveUser(User user, Long hiveId, String username) {
        Hive hive = getHiveAndCheckAuth(user, hiveId);
        User hiveUser = userService.findUserByUsername(username);

        if (!hiveUserService.isExistedUser(hiveUser, hive)) {
            throw new NotFoundUserException();
        }
        hiveUserRepository.deleteHiveUserByHiveIdAndUserId(hiveId, hiveUser.getId());
    }

    public Hive getHiveAndCheckAuth(User user, Long hiveId) {
        Hive hive = findHiveById(hiveId);
        User loginUser = userService.getUser(user.getId());

        return hive;
    }

    public Hive findHiveById(Long hiveId) {
        return hiveRepository.findByIdAndIsDeletedIsFalse(hiveId).orElseThrow(
                NotFoundHiveException::new);
    }


}