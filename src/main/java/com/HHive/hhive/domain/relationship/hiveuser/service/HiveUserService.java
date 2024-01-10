package com.HHive.hhive.domain.relationship.hiveuser.service;

import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.relationship.hiveuser.entity.HiveUser;
import com.HHive.hhive.domain.relationship.hiveuser.repository.HiveUserRepository;
import com.HHive.hhive.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HiveUserService {

    private final HiveUserRepository hiveUserRepository;

    public boolean isExistedUser(User user, Hive hive) {
        return hiveUserRepository.existsByUser_IdAndHive_IdAndIsDeletedIsFasle(user.getId(), hive.getId());
    }

    public void saveHiveUser(Hive saveHive, User newUser) {
        HiveUser hiveUser = HiveUser.builder().user(newUser).hive(saveHive).build();
        hiveUserRepository.save(hiveUser);
    }
}
