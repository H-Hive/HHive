package com.HHive.hhive.domain.relationship.hiveuser.repository.custom;

import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.user.entity.User;

public interface CustomHiveUserRepository {

    boolean existHiveUserByHiveAndUser(Hive hive, User user);
}
