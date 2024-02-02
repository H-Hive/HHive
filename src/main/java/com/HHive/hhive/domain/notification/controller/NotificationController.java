package com.HHive.hhive.domain.notification.controller;

import com.HHive.hhive.domain.notification.dto.NotificationRequestDTO;
import com.HHive.hhive.domain.notification.dto.NotificationResponseDTO;
import com.HHive.hhive.domain.notification.service.NotificationService;
import com.HHive.hhive.domain.user.UserDetailsImpl;
import com.HHive.hhive.global.common.CommonResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<CommonResponse> sendNotification(
            @RequestBody NotificationRequestDTO notificationRequestDTO) {
        NotificationResponseDTO responseDTO = notificationService.sendNotification(
                notificationRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of("메시지 전송 성공", responseDTO));
    }


    @GetMapping(value = "/{userId}/get", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> handleNotifications(@PathVariable(name = "userId") Long userId) {
        SseEmitter emitter = notificationService.addSseEmitter(userId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Accel-Buffering", "no");

        return ResponseEntity.ok()
                .headers(headers)
                .body(emitter);
    }

    @GetMapping("/getAll")
    public ResponseEntity<CommonResponse> getNotificationsByUserId(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<NotificationResponseDTO> notifications = notificationService.getNotificationsByUserId(
                userDetails.getUser().getId());
        return ResponseEntity.ok().body(CommonResponse.of("알림 출력 성공", notifications));
    }

    @GetMapping("/count")
    public ResponseEntity<CommonResponse> showUnreadNotificationCountForUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long notificationCount = notificationService.showUnreadNotificationCountForUser(
                userDetails.getUser()
                        .getId());
        return ResponseEntity.ok().body(CommonResponse.of("알림 출력 성공", notificationCount));
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<CommonResponse> deleteNotification(
            @PathVariable(name = "notificationId") Long notificationId
    ) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok().body(CommonResponse.of("알림 개수 조회 완료", null));
    }

    @GetMapping("/read")
    public ResponseEntity<CommonResponse> readNotification(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        notificationService.readNotification(userDetails.getUser().getId());
        return ResponseEntity.ok().body(CommonResponse.of("알림 열람 완료", null));
    }

}

