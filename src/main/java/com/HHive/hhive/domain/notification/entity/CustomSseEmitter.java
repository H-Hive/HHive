package com.HHive.hhive.domain.notification.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
@Getter
@Setter
public class CustomSseEmitter extends SseEmitter {
    private Long userId;
    public CustomSseEmitter(Long userId) {
        this.userId = userId;
    }
}
