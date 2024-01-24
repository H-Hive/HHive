package com.HHive.hhive.domain.notification.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomSseEmitter extends SseEmitter {
    private Long userId;

}
