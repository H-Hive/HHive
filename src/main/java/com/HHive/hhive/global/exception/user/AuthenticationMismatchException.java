package com.HHive.hhive.global.exception.user;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class AuthenticationMismatchException extends CustomException {

    public AuthenticationMismatchException() {
        super(ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
    }
}
