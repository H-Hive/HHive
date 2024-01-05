package com.HHive.hhive.global.exception.user;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class PasswordConfirmationException extends CustomException {

    public PasswordConfirmationException() {
        super(ErrorCode.PASSWORD_CONFIRMATION_EXCEPTION);
    }
}
