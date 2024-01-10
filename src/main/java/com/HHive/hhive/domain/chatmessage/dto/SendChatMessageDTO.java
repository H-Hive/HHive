package com.HHive.hhive.domain.chatmessage.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SendChatMessageDTO {

    @Size(min = 1, max = 500)
    private String message;
}
