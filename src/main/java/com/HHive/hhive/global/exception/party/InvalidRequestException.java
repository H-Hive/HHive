package com.HHive.hhive.global.exception.party;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class InvalidRequestException extends CustomException {
    public InvalidRequestException(ErrorCode errorCode) {
        super(ErrorCode.INVALID_REQUEST_EXCEPTION);
    }
}
