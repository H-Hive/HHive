package com.HHive.hhive.global.exception.jwt;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class ExpiredJwtTokenException extends CustomException {

    public ExpiredJwtTokenException(Throwable cause) {
        super(ErrorCode.EXPIRED_JWT_TOKEN_EXCEPTION, cause);
    }
}
