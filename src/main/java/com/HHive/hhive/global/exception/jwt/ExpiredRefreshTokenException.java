package com.HHive.hhive.global.exception.jwt;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class ExpiredRefreshTokenException extends CustomException {

    public ExpiredRefreshTokenException() {
        super(ErrorCode.EXPIRED_REFRESH_TOKEN_EXCEPTION);
    }
}
