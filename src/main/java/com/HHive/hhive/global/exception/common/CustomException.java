package com.HHive.hhive.global.exception.common;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final int statusCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.statusCode = errorCode.getStatusCode();
    }

    public CustomException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.statusCode = errorCode.getStatusCode();
    }
}
