package com.HHive.hhive.global.exception.jwt;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class UnsupportedJwtTokenException extends CustomException {

    public UnsupportedJwtTokenException(Throwable cause) {
        super(ErrorCode.UNSUPPORTED_JWT_TOKEN_EXCEPTION, cause);
    }
}
