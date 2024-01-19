package com.HHive.hhive.global.exception.party;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class InvalidPartyTimeException extends CustomException {
    public InvalidPartyTimeException() {
        super(ErrorCode.INVALID_PARTY_TIME_EXCEPTION);
    }
}
