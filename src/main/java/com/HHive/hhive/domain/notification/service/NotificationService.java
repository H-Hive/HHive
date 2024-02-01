package com.HHive.hhive.domain.notification.service;

import com.HHive.hhive.domain.hive.repository.HiveRepository;
import com.HHive.hhive.domain.notification.dto.NotificationRequestDTO;
import com.HHive.hhive.domain.notification.dto.NotificationResponseDTO;
import com.HHive.hhive.domain.notification.entity.Notification;
import com.HHive.hhive.domain.notification.repository.EmitterRepository;
import com.HHive.hhive.domain.notification.repository.NotificationRepository;
import com.HHive.hhive.domain.party.repository.PartyRepository;
import com.HHive.hhive.domain.relationship.hiveuser.entity.HiveUser;
import com.HHive.hhive.domain.relationship.hiveuser.repository.HiveUserRepository;
import com.HHive.hhive.domain.relationship.notificationuser.entity.UserNotification;
import com.HHive.hhive.domain.relationship.notificationuser.repository.UserNotificationRepository;
import com.HHive.hhive.domain.relationship.partyuser.entity.PartyUser;
import com.HHive.hhive.domain.relationship.partyuser.repository.PartyUserRepository;
import com.HHive.hhive.global.exception.hive.NotFoundHiveException;
import com.HHive.hhive.global.exception.notification.NotificationNotFoundException;
import com.HHive.hhive.global.exception.party.PartyNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Getter
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final PartyUserRepository partyUserRepository;
    private final HiveUserRepository hiveUserRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final PartyRepository partyRepository;
    private final HiveRepository hiveRepository;

    private final EmitterRepository emitterRepository;


    public SseEmitter addSseEmitter(Long userId) {
        SseEmitter emitter = new SseEmitter(600000L);
        emitterRepository.add(userId, emitter);
        emitter.onCompletion(() -> {
            emitterRepository.remove(userId);
        });
        emitter.onTimeout(() -> {
            emitterRepository.remove(userId);
        });
        System.out.println("SseEmitter가 추가 되었습니다");
        return emitter;
    }

    @Transactional
    public NotificationResponseDTO sendNotification(NotificationRequestDTO notificationRequestDTO) {

        String type = notificationRequestDTO.getType();
        Notification notification = Notification.builder()
                .message(notificationRequestDTO.getMessage())
                .build();

        if (type.equals("party")) {
            notification.setGroupName(partyRepository.findById(notificationRequestDTO.getId())
                    .orElseThrow(PartyNotFoundException::new).getTitle());
            notificationRepository.save(notification);
            List<PartyUser> partyUserList = partyUserRepository.findUsersByPartyId(
                    notificationRequestDTO.getId());
            sendNotificationToUserListParty(partyUserList, notification);
        } else if (type.equals("hive")) {
            notification.setGroupName(hiveRepository.findById(notificationRequestDTO.getId())
                    .orElseThrow(NotFoundHiveException::new).getTitle());
            notificationRepository.save(notification);
            List<HiveUser> hiveUserList = hiveUserRepository.findHiveUsersByHiveId(
                    notificationRequestDTO.getId());
            sendNotificationToUserListHive(hiveUserList, notification);
        }
        return NotificationResponseDTO.fromEntity(notification);
    }

    public List<NotificationResponseDTO> getNotificationsByUserId(Long userId) {
        List<Notification> notifications = userNotificationRepository
                .findNotificationsByUserId(userId);

        if (notifications.isEmpty()) {

            throw new NotificationNotFoundException();
        }

        return notifications.stream()
                .map(NotificationResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(NotificationNotFoundException::new);
        userNotificationRepository.deleteByNotificationId(notificationId);
        notificationRepository.deleteById(notificationId);

    }

    public Long showUnreadNotificationCountForUser(Long userId) {

        return userNotificationRepository
                .countUnreadNotificationsByUserId(userId, "unread");
    }

    public void readNotification(Long userId) {
        List<UserNotification> userNotifications = userNotificationRepository.findByUserIdAndNotificationId(
                userId);
        for (UserNotification userNotification : userNotifications) {
            userNotification.changeStatus();
        }
        userNotificationRepository.saveAll(userNotifications);
    }

    private void sendNotificationToUserListParty(List<PartyUser> partyUserList,
            Notification notification) {

        for (PartyUser partyUser : partyUserList) {
            Long userId = partyUser.getUser().getId();
            if (emitterRepository.containsUserId(userId)) {
                sendNotificationToClients(userId, notification);
            }
            UserNotification userNotification = UserNotification.builder()
                    .user(partyUser.getUser())
                    .notification(notification)
                    .build();
            userNotificationRepository.save(userNotification);
        }
    }

    private void sendNotificationToUserListHive(List<HiveUser> hiveUserList,
            Notification notification) {

        for (HiveUser hiveUser : hiveUserList) {
            Long userId = hiveUser.getUser().getId();
            if (emitterRepository.containsUserId(userId)) {
                sendNotificationToClients(userId, notification);
            }
            UserNotification userNotification = UserNotification.builder()
                    .user(hiveUser.getUser())
                    .notification(notification)
                    .build();
            userNotificationRepository.save(userNotification);


        }
    }

    private void sendNotificationToClients(Long userId, Notification notification) {
        SseEmitter emitter = emitterRepository.getEmitter(userId);
        try {
            emitter.send(notification);
            System.out.println("알림 전송 성공");
        } catch (Exception e) {
            System.err.println("전송 실패");
        }
    }
}
