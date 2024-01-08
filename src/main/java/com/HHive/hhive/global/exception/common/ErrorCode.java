package com.HHive.hhive.global.exception.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //Jwt
    INVALID_JWT_SIGNATURE_EXCEPTION(401, "유효하지 않는 JWT 서명 입니다."),
    EXPIRED_JWT_TOKEN_EXCEPTION(401, "만료된 JWT token 입니다."),
    UNSUPPORTED_JWT_TOKEN_EXCEPTION(401, "지원되지 않는 JWT 토큰 입니다."),
    INVALID_JWT_TOKEN_EXCEPTION(401, "잘못된 JWT 토큰 입니다."),

    //User
    PASSWORD_MISMATCH_EXCEPTION(401, "비밀번호가 일치하지 않습니다."),
    ALREADY_EXIST_USERNAME_EXCEPTION(401, "중복된 유저네임입니다."),
    ALREADY_EXIST_EMAIL_EXCEPTION(401, "중복된 이메일입니다."),
    AUTHENTICATION_MISMATCH_EXCEPTION(401, "권한이 없습니다."),
    PASSWORD_CONFIRMATION_EXCEPTION(401, "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    NOT_FOUND_USER_EXCEPTION(400, "해당 유저가 존재하지 않습니다"),

    //Hive
    FORBIDDEN_ABOUT_HIVE(403, "해당 하이브에 대한 권한이 없습니다."),
    NOT_FOUND_HIVE(404, "해당 하이브를 찾을 수 없습니다.");


    //Party

    //Notification

    private final int statusCode;

    private final String message;

    ErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
