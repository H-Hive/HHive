package com.HHive.hhive.global.exception.user;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class KakaoUserEmailModificationException extends CustomException {

    public KakaoUserEmailModificationException() {
        super(ErrorCode.KAKAO_USER_EMAIL_MODIFICATION_EXCEPTION);
    }
}
