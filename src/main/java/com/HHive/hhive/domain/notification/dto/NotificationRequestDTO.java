package com.HHive.hhive.domain.notification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class NotificationRequestDTO {

    @NotBlank
    private String message;

    @NotBlank
    private Long Id;

    @NotBlank
    private String SourceType;

}
