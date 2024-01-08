package com.HHive.hhive.domain.notification.service;

import com.HHive.hhive.domain.notification.dto.NotificationRequestDTO;
import com.HHive.hhive.domain.notification.dto.NotificationResponseDTO;
import com.HHive.hhive.domain.notification.entity.Notification;
import com.HHive.hhive.domain.notification.repository.NotificationRepository;
import com.HHive.hhive.domain.relationship.notificationuser.entity.UserNotification;
import com.HHive.hhive.domain.relationship.notificationuser.repository.UserNotificationRepository;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.domain.user.repository.UserRepository;
import com.HHive.hhive.global.common.CommonResponse;
import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserPartyRepository userPartyRepository;
    private final UserHiveRepository userHiveRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final UserRepository userRepository;


    @Transactional
    public CommonResponse sendNotification(NotificationRequestDTO notificationRequestDTO) {
        String type = notificationRequestDTO.getType();
        Notification notification = Notification.builder()
                .message(notificationRequestDTO.getMessage())
                .build();
        notificationRepository.save(notification);
        if (type.equals("party")) {
            List<UserParty> userPartyList = userPartyRepository.findUsersByPartyId(
                    notificationRequestDTO.getId());
            sendNotificationToUserListParty(userPartyList, notification);
        } else if (type.equals("hive")) {
            List<UserHive> userHiveList = userHiveRepository.findUsersByHiveId(
                    notificationRequestDTO.getId());
            sendNotificationToUserListHive(userHiveList, notification);
        }

        return new CommonResponse(200, "알림전송 성공", notification);
    }

    public List<NotificationResponseDTO> getNotificationsByUserId(Long userId) {
        List<UserNotification> userNotifications = userNotificationRepository.findByUserId(userId);

        if (userNotifications.isEmpty()) {
            // 유저에게 연관된 알림이 없는 경우 처리
            throw new CustomException(ErrorCode.NOT_FOUND_NOTIFICATION_EXCEPTION);
        }

        List<Notification> notifications = userNotifications.stream()
                .map(UserNotification::getNotification)
                .toList();

        if (notifications.isEmpty()) {
            // 알림이 하나도 없는 경우 처리
            throw new CustomException(ErrorCode.NOT_FOUND_NOTIFICATION_EXCEPTION);
        }

        return notifications.stream()
                .map(NotificationResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private void sendNotificationToUserListParty(List<UserParty> userPartyList,
            Notification notification) {
        List<Long> userIdList = userPartyList.stream()
                .map(userParty -> userParty.getUser().getId())
                .toList();

        for (Long userId : userIdList) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
            UserNotification userNotification = UserNotification.builder()
                    .user(user)
                    .notification(notification)
                    .build();
            userNotificationRepository.save(userNotification);
        }
    }

    private void sendNotificationToUserListHive(List<UserHive> userHiveList,
            Notification notification) {
        List<Long> userIdList = userHiveList.stream()
                .map(UserHive::getUser)
                .map(User::getId)
                .toList();

        for (Long userId : userIdList) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
            UserNotification userNotification = UserNotification.builder()
                    .user(user)
                    .notification(notification)
                    .build();
            userNotificationRepository.save(userNotification);
        }

    }
}
