package com.HHive.hhive.domain.hive.service;

import com.HHive.hhive.domain.hive.dto.HiveDTO;
import com.HHive.hhive.domain.hive.dto.HiveDTO.Response;
import com.HHive.hhive.domain.hive.dto.HiveDTO.UpdateHiveRequest;
import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.hive.repository.HiveRepository;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.domain.user.repository.UserRepository;
import com.HHive.hhive.domain.user.service.UserService;
import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HiveService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final HiveRepository hiveRepository;


    public Response createHive(User user, HiveDTO.CreateHiveRequest createHiveRequest) {
        User createBy = userService.getUser(user.getId());
        Hive saveHive = hiveRepository.save(createHiveRequest.toEntity(createBy));

        return HiveDTO.Response.of(saveHive, user);

    }

    @Transactional
    public HiveDTO.Response updateHive(User user, Long hiveId,
            UpdateHiveRequest updateHiveRequest) {
        Hive hive = getHiveAndCheckAuth(user, hiveId);

        hive.update(updateHiveRequest);
        return HiveDTO.Response.of(hive, user);
    }


    public Hive getHiveAndCheckAuth(User user, Long hiveId) {
        Hive hive = findHiveById(hiveId);
        User loginUser = userService.getUser(user.getId());

        return hive;
    }

    public Hive findHiveById(Long hiveId) {
        return hiveRepository.findByIdAndIsDeletedIsFalse(hiveId).orElseThrow(
                () -> new CustomException(ErrorCode.FORBIDDEN_ABOUT_HIVE)
        );
    }
}
