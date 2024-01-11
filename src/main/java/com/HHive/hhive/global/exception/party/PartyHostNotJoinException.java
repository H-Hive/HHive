package com.HHive.hhive.global.exception.party;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class PartyHostNotJoinException extends CustomException {
    public PartyHostNotJoinException() {
        super(ErrorCode.PARTY_HOST_NOT_JOIN_EXCEPTION);
    }
}
