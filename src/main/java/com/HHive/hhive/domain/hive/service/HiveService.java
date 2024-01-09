package com.HHive.hhive.domain.hive.service;

import com.HHive.hhive.domain.hive.dto.HiveCreateRequestDTO;
import com.HHive.hhive.domain.hive.dto.HiveResponseDTO;
import com.HHive.hhive.domain.hive.dto.HiveUpdateRequestDTO;
import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.hive.repository.HiveRepository;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.domain.user.repository.UserRepository;
import com.HHive.hhive.domain.user.service.UserService;
import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HiveService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final HiveRepository hiveRepository;


    public HiveResponseDTO createHive(User user, HiveCreateRequestDTO hiveCreateRequestDTO) {
        User createBy = userService.getUser(user.getId());
        Hive saveHive = hiveRepository.save(hiveCreateRequestDTO.toEntity(createBy));

        return HiveResponseDTO.of(saveHive);

    }

    @Transactional
    public HiveResponseDTO updateHive(User user, Long hiveId,
            @Valid HiveUpdateRequestDTO updateHiveRequest) {
        Hive hive = getHiveAndCheckAuth(user, hiveId);

        hive.update(updateHiveRequest);
        return HiveResponseDTO.of(hive);
    }

    public HiveResponseDTO getHive(User user, Long hiveId) {
        Hive hive = getHiveAndCheckAuth(user, hiveId);
        return HiveResponseDTO.of(hive);
    }


    public Hive getHiveAndCheckAuth(User user, Long hiveId) {
        Hive hive = findHiveById(hiveId);
        User loginUser = userService.getUser(user.getId());

        return hive;
    }

    public Hive findHiveById(Long hiveId) {
        return hiveRepository.findByIdAndIsDeletedIsFalse(hiveId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_HIVE_EXCEPTION));
    }
}