package com.HHive.hhive.domain.relationship.notificationuser.repository;

import com.HHive.hhive.domain.relationship.notificationuser.entity.UserNotification;
import com.HHive.hhive.domain.relationship.notificationuser.entity.UserNotificationPK;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, UserNotificationPK> {

    List<UserNotification> findByUserId(Long userId);
    List<UserNotification> findByUserIdAndNotificationStatus(Long userId, String status);
}

