package com.HHive.hhive.global.exception.party;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;


public class UnauthorizedAccessException extends CustomException {
    public UnauthorizedAccessException(ErrorCode errorCode) {
        super(ErrorCode.UNAUTHORIZED_ACCESS_EXCEPTION);
    }
}
