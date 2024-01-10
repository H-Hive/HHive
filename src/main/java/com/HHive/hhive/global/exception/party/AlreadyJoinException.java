package com.HHive.hhive.global.exception.party;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class AlreadyJoinException extends CustomException {
    public AlreadyJoinException() {
        super(ErrorCode.ALREADY_JOIN_EXCEPTION);
    }
}
