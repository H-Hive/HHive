package com.HHive.hhive.domain.relationship.notificationuser.repository;

import com.HHive.hhive.domain.notification.entity.Notification;
import com.HHive.hhive.domain.relationship.notificationuser.entity.UserNotification;
import com.HHive.hhive.domain.relationship.notificationuser.entity.UserNotificationPK;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, UserNotificationPK> {

    List<UserNotification> findByUserId(Long userId);

    @Query("SELECT un.notification FROM UserNotification un WHERE un.user.id = :userId AND un.notification.status = :status")
    List<Notification> findNotificationsByUserIdAndStatus(@Param("userId") Long userId,
            @Param("status") String status);

    @Query("SELECT COUNT(un.notification) FROM UserNotification un WHERE un.user.id = :userId AND un.notification.status = :status")
    Long countUnreadNotificationsByUserId(@Param("userId") Long userId,
            @Param("status") String status);

    @Modifying
    @Query("DELETE FROM UserNotification un WHERE un.notification.id = :notificationId")
    void deleteByNotificationId(@Param("notificationId") Long notificationId);

}

