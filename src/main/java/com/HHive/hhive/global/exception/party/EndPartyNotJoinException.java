package com.HHive.hhive.global.exception.party;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class EndPartyNotJoinException extends CustomException {
    public EndPartyNotJoinException() {
        super(ErrorCode.END_PARTY_NOT_JOIN_EXCEPTION);
    }
}
