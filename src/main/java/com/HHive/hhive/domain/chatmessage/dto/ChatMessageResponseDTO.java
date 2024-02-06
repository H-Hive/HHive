package com.HHive.hhive.domain.chatmessage.dto;

import com.HHive.hhive.domain.chatmessage.entity.ChatMessage;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatMessageResponseDTO {

    private String message;

    private String username;

    @JsonFormat(pattern = "MM월 dd일 / HH시 mm분")
    private LocalDateTime createdAt;

    public static ChatMessageResponseDTO from(ChatMessage chatMessage) {

        return ChatMessageResponseDTO.builder()
                .message(chatMessage.getMessage())
                .username(chatMessage.getSenderName())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
