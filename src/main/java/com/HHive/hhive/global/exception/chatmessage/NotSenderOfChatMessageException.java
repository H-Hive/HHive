package com.HHive.hhive.global.exception.chatmessage;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class NotSenderOfChatMessageException extends CustomException {

    public NotSenderOfChatMessageException() {
        super(ErrorCode.NOT_SENDER_OF_CHATMESSAGE_EXCEPTION);
    }
}
