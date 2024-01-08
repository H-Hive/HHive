package com.HHive.hhive.global.exception.jwt;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class InvalidJwtSignatureException extends CustomException {

    public InvalidJwtSignatureException(Throwable cause) {
        super(ErrorCode.INVALID_JWT_SIGNATURE_EXCEPTION, cause);
    }
}
