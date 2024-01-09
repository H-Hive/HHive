package com.HHive.hhive.global.exception.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorResponse {

    private int statusCode;

    private String message;

    public static ErrorResponse of(HttpStatus statusCode, String message) {

        return ErrorResponse.builder()
                .statusCode(statusCode.value())
                .message(message)
                .build();
    }

}
