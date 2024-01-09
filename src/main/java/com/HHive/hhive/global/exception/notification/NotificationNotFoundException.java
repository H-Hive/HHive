package com.HHive.hhive.global.exception.notification;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class NotificationNotFoundException extends CustomException {
    public NotificationNotFoundException() {
        super(ErrorCode.NOT_FOUND_NOTIFICATION_EXCEPTION);
    }

}
