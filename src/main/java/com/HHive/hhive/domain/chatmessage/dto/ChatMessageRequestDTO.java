package com.HHive.hhive.domain.chatmessage.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ChatMessageRequestDTO {

    private Long hiveId;

    private String username;

    @Size(min = 1, max = 500)
    private String message;
}
