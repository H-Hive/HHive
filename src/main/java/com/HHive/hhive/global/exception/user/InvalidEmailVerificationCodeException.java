package com.HHive.hhive.global.exception.user;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class InvalidEmailVerificationCodeException extends CustomException {

    public InvalidEmailVerificationCodeException() {
        super(ErrorCode.INVALID_EMAIL_VERIFICATION_CODE_EXCEPTION);
    }
}
