package com.HHive.hhive.global.exception.user;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException() {
        super(ErrorCode.NOT_FOUND_USER_EXCEPTION);
    }
}
