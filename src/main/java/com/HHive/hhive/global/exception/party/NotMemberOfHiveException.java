package com.HHive.hhive.global.exception.party;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class NotMemberOfHiveException extends CustomException {

    public NotMemberOfHiveException() {
        super(ErrorCode.NOT_MEMBER_OF_HIVE_EXCEPTION);
    }
}