package com.HHive.hhive.global.exception.user;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class NotFoundUserException extends CustomException {

    public NotFoundUserException() {
        super(ErrorCode.NOT_FOUND_USER_EXCEPTION);
    }
}
