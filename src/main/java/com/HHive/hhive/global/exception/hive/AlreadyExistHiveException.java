package com.HHive.hhive.global.exception.hive;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class AlreadyExistHiveException extends CustomException {

    public AlreadyExistHiveException() {
        super(ErrorCode.ALREADY_EXIST_HIVE_EXCEPTION);}
}
