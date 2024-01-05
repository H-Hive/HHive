package com.HHive.hhive.global.exception.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //User
    NOT_FOUND_USER_EXCEPTION(400, "해당 유저가 존재하지 않습니다");

    //Hive

    //Party

    //Notification

    private final int statusCode;

    private final String message;

    ErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
