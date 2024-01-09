package com.HHive.hhive.global.exception.party;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class PartyNotFoundException extends CustomException {
    public PartyNotFoundException() {
        super(ErrorCode.PARTY_NOT_FOUND_EXCEPTION);
    }
}
