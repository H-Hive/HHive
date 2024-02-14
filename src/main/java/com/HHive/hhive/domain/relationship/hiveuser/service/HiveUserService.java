package com.HHive.hhive.domain.relationship.hiveuser.service;

import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.relationship.hiveuser.entity.HiveUser;
import com.HHive.hhive.domain.relationship.hiveuser.repository.HiveUserRepository;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.global.exception.hive.NotFoundHiveException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HiveUserService {

    private final HiveUserRepository hiveUserRepository;

    public boolean isExistedUser(User user, Hive hive) {

        return hiveUserRepository.existsByUser_IdAndHive_IdAndIsDeletedIsFalse(user.getId(),
                hive.getId());
    }

    public void saveHiveUser(Hive savedHive, User newUser) {

        HiveUser hiveUser = HiveUser.builder()
                .user(newUser)
                .hive(savedHive)
                .build();

        hiveUserRepository.save(hiveUser);
    }

    public List<User> findAllByHiveUsersInHive(Hive hive) {

        return hiveUserRepository.findUsersByHiveId(hive.getId());
    }

    public User searchHiveUser(Hive hive, User searchHiveUser) {

        return hiveUserRepository.findUserByHiveIdAndUsername(hive.getId(),
                searchHiveUser.getUsername())
                .orElseThrow(NotFoundHiveException::new);
    }

    public List<Hive> findAllHivesByHiveUser(User user) {

        return hiveUserRepository.findAllHiveByHiveUser(user.getId());
    }
}
