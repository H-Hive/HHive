package com.HHive.hhive.global.exception.hive;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class ForbiddenAboutHiveException extends CustomException {

    public ForbiddenAboutHiveException() { super(ErrorCode.FORBIDDEN_ABOUT_HIVE_EXCEPTION);}
}
