package com.HHive.hhive.global.exception.party;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class PartyHostNotResignException extends CustomException {

    public PartyHostNotResignException() {
        super(ErrorCode.PARTY_HOST_NOT_RESIGN_EXCEPTION);
    }
}
