package com.HHive.hhive.global.exception.user;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class AlreadyExistUsernameException extends CustomException {

    public AlreadyExistUsernameException() {
        super(ErrorCode.ALREADY_EXIST_USERNAME_EXCEPTION);
    }
}
