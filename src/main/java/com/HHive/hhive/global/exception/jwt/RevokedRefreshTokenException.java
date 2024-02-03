package com.HHive.hhive.global.exception.jwt;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class RevokedRefreshTokenException extends CustomException {

    public RevokedRefreshTokenException() {
        super(ErrorCode.REVOKED_REFRESH_TOKEN_EXCEPTION);
    }
}
