package com.HHive.hhive.global.exception.notification;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class EmitterSendErrorException extends CustomException {
    public EmitterSendErrorException() {
        super(ErrorCode.EMITTER_SEND_ERROR_EXCEPTION);
    }
}
