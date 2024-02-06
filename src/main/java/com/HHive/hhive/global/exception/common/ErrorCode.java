package com.HHive.hhive.global.exception.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //Jwt
    INVALID_JWT_SIGNATURE_EXCEPTION(401, "유효하지 않는 JWT 서명 입니다."),
    EXPIRED_JWT_TOKEN_EXCEPTION(401, "만료된 JWT token 입니다."),
    UNSUPPORTED_JWT_TOKEN_EXCEPTION(401, "지원되지 않는 JWT 토큰 입니다."),
    INVALID_JWT_TOKEN_EXCEPTION(401, "잘못된 JWT 토큰 입니다."),
    REFRESH_TOKEN_NOT_FOUND_EXCEPTION(404, "Refresh Token을 찾을 수 없습니다."),
    EXPIRED_REFRESH_TOKEN_EXCEPTION(401, "만료된 Refresh Token 입니다."),
    REVOKED_REFRESH_TOKEN_EXCEPTION(401, "사용이 중지된 Refresh Token 입니다."),

    //User
    PASSWORD_MISMATCH_EXCEPTION(401, "비밀번호가 일치하지 않습니다."),
    ALREADY_EXIST_USERNAME_EXCEPTION(401, "중복된 유저네임입니다."),
    ALREADY_EXIST_KAKAOID_EXCEPTION(401, "중복된 카카오ID 입니다."),
    ALREADY_EXIST_EMAIL_EXCEPTION(401, "중복된 이메일입니다."),
    AUTHENTICATION_MISMATCH_EXCEPTION(401, "권한이 없습니다."),
    PASSWORD_CONFIRMATION_EXCEPTION(401, "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    NOT_FOUND_USER_EXCEPTION(400, "해당 유저가 존재하지 않습니다"),
    INVALID_EMAIL_VERIFICATION_CODE_EXCEPTION(400, "입력하신 이메일 인증 코드가 유효하지 않습니다"),
    KAKAO_USER_EMAIL_MODIFICATION_EXCEPTION(400, "카카오 로그인한 유저는 이메일을 수정할 수 없습니다."),

    //Hive
    ALREADY_EXIST_HIVE_EXCEPTION(401, "중복된 타이틀입니다."),
    FORBIDDEN_ABOUT_HIVE_EXCEPTION(403, "해당 하이브에 대한 권한이 없습니다."),
    NOT_FOUND_HIVE_EXCEPTION(404, "해당 하이브를 찾을 수 없습니다."),
    HIVE_HOST_NOT_RESIGN_EXCEPTION(403,"호스트는 하이브 탈퇴가 불가능합니다."),

    //Party
    PARTY_NOT_FOUND_EXCEPTION(404, "해당 파티를 찾을 수 없습니다."),
    UNAUTHORIZED_ACCESS_EXCEPTION(403,"해당 파티에 대한 권한이 없습니다."),
    NOT_MEMBER_OF_HIVE_EXCEPTION(403,"하이브에 가입 되어 있지 않습니다."),
    PARTY_HOST_NOT_RESIGN_EXCEPTION(403,"리더는 파티 탈퇴가 불가능합니다."),
    PARTY_HOST_NOT_JOIN_EXCEPTION(403,"리더는 파티 가입이 불가능합니다."),
    END_PARTY_NOT_JOIN_EXCEPTION(403,"가입 기간이 끝난 파티 입니다."),
    ALREADY_JOIN_EXCEPTION(403,"이미 가입 되어 있습니다."),
    PARTY_NOT_RESIGN_EXCEPTION(403,"탈퇴할 파티가 없습니다."),
    INVALID_PARTY_TIME_EXCEPTION(403,"설정 하려는 약속 시간이 현재 보다 과거 입니다."),

    //Notification
    NOT_FOUND_NOTIFICATION_EXCEPTION(401,"알림이 존재하지 않습니다"),
    NOT_FOUND_EMITTER_EXCEPTION(401,"구독된 알림이 없습니다"),
    EMITTER_SEND_ERROR_EXCEPTION(500,"알림 전송 실패"),

    //HiveUser
    HIVE_USER_NOT_FOUND_EXCEPTION(403, "요청하신 하이브에 가입하지 않은 사용자입니다."),

    //ChatMessage
    NOT_FOUND_CHATMESSAGE_EXCEPTION(404, "해당 메시지를 찾을 수 없습니다."),
    NOT_SENDER_OF_CHATMESSAGE_EXCEPTION(401, "메시지 작성자만 삭제가 가능합니다");

    private final int statusCode;

    private final String message;

    ErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
