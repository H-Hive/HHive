package com.HHive.hhive.global.exception.party;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class PartyNotResignException extends CustomException {
    public PartyNotResignException() {
        super(ErrorCode.PARTY_NOT_RESIGN_EXCEPTION);
    }
}
