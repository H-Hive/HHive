package com.HHive.hhive.domain.chatmessage.dto;

import com.HHive.hhive.domain.chatmessage.entity.ChatMessage;
import com.HHive.hhive.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatMessageResponseDTO {

    private String message;

    private Long senderId;

    private String senderName;

    private LocalDateTime createdAt;

    public static ChatMessageResponseDTO from(ChatMessage chatMessage) {

        return ChatMessageResponseDTO.builder()
                .message(chatMessage.getMessage())
                .senderId(chatMessage.getSenderId())
                .senderName(chatMessage.getSenderName())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
