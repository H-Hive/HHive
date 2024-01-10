package com.HHive.hhive.global.exception.hiveuser;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class HiveUserNotFoundException extends CustomException {

    public HiveUserNotFoundException() {
        super(ErrorCode.HIVE_USER_NOT_FOUND_EXCEPTION);
    }

}
