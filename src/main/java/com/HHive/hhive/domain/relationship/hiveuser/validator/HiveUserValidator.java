package com.HHive.hhive.domain.relationship.hiveuser.validator;

import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.relationship.hiveuser.repository.HiveUserRepository;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.global.exception.hiveuser.HiveUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HiveUserValidator {

    private final HiveUserRepository hiveUserRepository;

    public void validateHiveUser(Hive hive, User user) {
        if(!hiveUserRepository.existHiveUserByHiveAndUser(hive, user)) {
            throw new HiveUserNotFoundException();
        }
    }
}
