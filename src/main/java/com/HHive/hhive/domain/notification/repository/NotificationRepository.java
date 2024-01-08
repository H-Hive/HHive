package com.HHive.hhive.domain.notification.repository;

import com.HHive.hhive.domain.notification.entity.Notification;
import com.HHive.hhive.domain.relationship.notificationuser.entity.UserNotification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

}
