package com.HHive.hhive.global.exception.party;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;


public class HiveNotFoundException extends CustomException {
    public HiveNotFoundException(ErrorCode errorCode) {
        super(ErrorCode.HIVE_NOT_FOUND_EXCEPTION);
    }
}
