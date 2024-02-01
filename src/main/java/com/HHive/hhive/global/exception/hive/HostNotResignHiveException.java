package com.HHive.hhive.global.exception.hive;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class HostNotResignHiveException extends CustomException {

    public HostNotResignHiveException() { super(ErrorCode.HIVE_HOST_NOT_RESIGN_EXCEPTION);}
}
