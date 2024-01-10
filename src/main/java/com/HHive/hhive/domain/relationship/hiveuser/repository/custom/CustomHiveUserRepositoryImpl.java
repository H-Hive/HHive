package com.HHive.hhive.domain.relationship.hiveuser.repository.custom;

import static com.HHive.hhive.domain.relationship.hiveuser.entity.QHiveUser.hiveUser;

import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.relationship.hiveuser.entity.QHiveUser;
import com.HHive.hhive.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomHiveUserRepositoryImpl implements
        CustomHiveUserRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final EntityManager em;

    @Override
    public boolean existHiveUserByHiveAndUser(Hive hive, User user) {

        return jpaQueryFactory
                .from(hiveUser)
                .where(hiveUser.hive.eq(hive),
                        hiveUser.user.eq(user))
                .fetchFirst() != null;
    }
}
