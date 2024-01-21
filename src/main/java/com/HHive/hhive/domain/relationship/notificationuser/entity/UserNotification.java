package com.HHive.hhive.domain.relationship.notificationuser.entity;

import com.HHive.hhive.domain.notification.entity.Notification;
import com.HHive.hhive.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserNotification {
    @EmbeddedId
    private UserNotificationPK userNotificationPK;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name ="user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name="notification_id")
    @MapsId("notificationId")
    private Notification notification;

    @Column(name = "status")
    private String status;

    @Builder
    public UserNotification(User user,Notification notification){
        this.user=user;
        this.notification=notification;
        this.userNotificationPK=UserNotificationPK.builder()
                .userId(user.getId())
                .notificationId(notification.getId())
                .build();
        this.status="unread";
    }
    public void changeStatus(){
        this.status="read";
    }

}
