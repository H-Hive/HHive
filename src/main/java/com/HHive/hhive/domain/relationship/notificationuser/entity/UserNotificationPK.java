package com.HHive.hhive.domain.relationship.notificationuser.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserNotificationPK implements Serializable {


    @JoinColumn(name = "user_id")
    private Long userId;

    @JoinColumn(name = "notification_id")
    private Long notificationId;

    @Builder
    public UserNotificationPK(Long userId, Long notificationId) {
        this.userId = userId;
        this.notificationId = notificationId;
    }
}
