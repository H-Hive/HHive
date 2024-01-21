package com.HHive.hhive.global.exception.notification;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class EmitterNotFoundException extends CustomException {
    public EmitterNotFoundException() {
        super(ErrorCode.NOT_FOUND_EMITTER_EXCEPTION);
    }

}
