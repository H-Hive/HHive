package com.HHive.hhive.global.exception.user;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class AlreadyExistEmailException extends CustomException {

    public AlreadyExistEmailException() {
        super(ErrorCode.ALREADY_EXIST_EMAIL_EXCEPTION);
    }
}
