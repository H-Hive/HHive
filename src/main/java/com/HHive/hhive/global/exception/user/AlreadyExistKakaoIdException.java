package com.HHive.hhive.global.exception.user;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class AlreadyExistKakaoIdException extends CustomException {

    public AlreadyExistKakaoIdException() {
        super(ErrorCode.ALREADY_EXIST_KAKAOID_EXCEPTION);
    }
}
