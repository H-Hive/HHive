package com.HHive.hhive.domain.notification.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
@Getter
@Setter
@NoArgsConstructor
public class CustomSseEmitter extends SseEmitter {
    private Long userId;
    private static final Long DEFAULT_TIMEOUT = 600000L;
    public CustomSseEmitter(Long userId) {
        super(DEFAULT_TIMEOUT);
        this.userId = userId;
    }
}
