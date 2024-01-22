package com.HHive.hhive.domain.notification.service;

import com.HHive.hhive.domain.hive.repository.HiveRepository;
import com.HHive.hhive.domain.notification.dto.NotificationRequestDTO;
import com.HHive.hhive.domain.notification.dto.NotificationResponseDTO;
import com.HHive.hhive.domain.notification.entity.CustomSseEmitter;
import com.HHive.hhive.domain.notification.entity.Notification;
import com.HHive.hhive.domain.notification.repository.NotificationRepository;
import com.HHive.hhive.domain.party.repository.PartyRepository;
import com.HHive.hhive.domain.relationship.hiveuser.entity.HiveUser;
import com.HHive.hhive.domain.relationship.hiveuser.repository.HiveUserRepository;
import com.HHive.hhive.domain.relationship.notificationuser.entity.UserNotification;
import com.HHive.hhive.domain.relationship.notificationuser.repository.UserNotificationRepository;
import com.HHive.hhive.domain.relationship.partyuser.entity.PartyUser;
import com.HHive.hhive.domain.relationship.partyuser.repository.PartyUserRepository;
import com.HHive.hhive.global.exception.hive.NotFoundHiveException;
import com.HHive.hhive.global.exception.notification.EmitterNotFoundException;
import com.HHive.hhive.global.exception.notification.NotificationNotFoundException;
import com.HHive.hhive.global.exception.party.PartyNotFoundException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
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


    private final List<CustomSseEmitter> emitters = new ArrayList<>();


    public void addSseEmitter(CustomSseEmitter emitter) {
        emitters.add(emitter);
        emitter.onCompletion(() -> {
            emitters.remove(emitter);
        });
        emitter.onTimeout(() -> {
            emitters.remove(emitter);
        });
        System.out.println("SseEmitter가 추가 되었습니다" + emitter.getUserId());
    }

    public void sendNotificationToClients(CustomSseEmitter emitter, Notification notification) {
        try {
            emitter.send(notification);
            System.out.println("알림 전송 성공");
        } catch (Exception e) {
            System.err.println("전송 실패");
        }
    }

    @Transactional
    public List<CustomSseEmitter> sendNotification(NotificationRequestDTO notificationRequestDTO) {

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
        return emitters;
    }


    public CustomSseEmitter createUserEmitter(Long userId) {
        return new CustomSseEmitter(userId);
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
    public void readNotification(Long userId){
        List<UserNotification> userNotifications =userNotificationRepository.findByUserIdAndNotificationId(userId);
        for (UserNotification userNotification : userNotifications) {
            userNotification.changeStatus();
        }
        userNotificationRepository.saveAll(userNotifications);
    }
    public void clear(){
        emitters.clear();
    }

    private void sendNotificationToUserListParty(List<PartyUser> partyUserList,
            Notification notification) {

        for (PartyUser partyUser : partyUserList) {
            Long userId = partyUser.getUser().getId();
            CustomSseEmitter emitter = findSseEmitterByUserId(userId);
            sendNotificationToClients(emitter,notification);
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
            CustomSseEmitter emitter = findSseEmitterByUserId(userId);
            if(emitter.getUserId().equals(userId)) {
                sendNotificationToClients(emitter, notification);
            }
            UserNotification userNotification = UserNotification.builder()
                    .user(hiveUser.getUser())
                    .notification(notification)
                    .build();
            userNotificationRepository.save(userNotification);


        }
    }

    private CustomSseEmitter findSseEmitterByUserId(Long userId) {
        return emitters.stream()
                .filter(emitter -> userId.equals(emitter.getUserId()))
                .findFirst()
                .orElse(createUserEmitter(0L));
    }
}
