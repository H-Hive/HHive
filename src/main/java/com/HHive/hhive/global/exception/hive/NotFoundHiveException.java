package com.HHive.hhive.global.exception.hive;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class NotFoundHiveException extends CustomException {

    public NotFoundHiveException() { super(ErrorCode.NOT_FOUND_HIVE_EXCEPTION);}
}
