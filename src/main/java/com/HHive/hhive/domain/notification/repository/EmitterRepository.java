package com.HHive.hhive.domain.notification.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class EmitterRepository {

    public final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void add(Long userId, SseEmitter emitter) {
        emitters.put(userId, emitter);
    }

    public void remove(Long userId) {
        emitters.remove(userId);
    }

    public SseEmitter getEmitter(Long userId) {
        return emitters.get(userId);
    }

    public boolean containsUserId(Long userId) {
        return emitters.containsKey(userId);
    }
}
