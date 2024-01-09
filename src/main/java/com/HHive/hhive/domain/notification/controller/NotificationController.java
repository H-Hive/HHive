package com.HHive.hhive.domain.notification.controller;

import com.HHive.hhive.domain.notification.dto.NotificationRequestDTO;
import com.HHive.hhive.domain.notification.dto.NotificationResponseDTO;
import com.HHive.hhive.domain.notification.service.NotificationService;
import com.HHive.hhive.global.common.CommonResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<CommonResponse> sendNotification(
            @RequestBody NotificationRequestDTO notificationRequestDTO) {
        CommonResponse response = notificationService.sendNotification(notificationRequestDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByUserId(
            @PathVariable(name = "userId") Long userId
    ) {
        List<NotificationResponseDTO> notifications = notificationService.getNotificationsByUserId(
                userId);
        return ResponseEntity.ok(notifications);
    }

    @DeleteMapping("/{notificationId")
    public ResponseEntity<CommonResponse> deleteNotification(
            @PathVariable(name = "notificationId") Long notificationId
    ) {
        CommonResponse response = notificationService.deleteNotification(notificationId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


}

