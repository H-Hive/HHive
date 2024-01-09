package com.HHive.hhive.domain.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class NotificationRequestDTO {

    @NotBlank
    private String message;

    @NotNull
    private Long id;

    @NotBlank
    private String type;

}
