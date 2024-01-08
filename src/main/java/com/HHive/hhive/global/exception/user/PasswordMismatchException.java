package com.HHive.hhive.global.exception.user;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class PasswordMismatchException extends CustomException {

    public PasswordMismatchException() {
        super(ErrorCode.PASSWORD_MISMATCH_EXCEPTION);
    }
}
