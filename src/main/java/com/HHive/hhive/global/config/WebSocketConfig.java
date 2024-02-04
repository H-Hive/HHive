package com.HHive.hhive.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        //handshake를 위한 설정
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                //웹소켓 지원하지 않는 브라우저를 도움
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        //SimpleBroker로 바로 이동할 경로설정. 스프링에서 제공하는 내장 브로커를 사용하겠다
        registry.enableSimpleBroker("/topic", "/queue");

        //SimpAnnotationMethod 로 가는 경로. 메시지 처리 후 SimpleBroker로 이동할
        registry.setApplicationDestinationPrefixes("/pub");
    }

}
