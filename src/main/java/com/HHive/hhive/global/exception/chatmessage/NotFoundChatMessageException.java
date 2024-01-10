package com.HHive.hhive.global.exception.chatmessage;

import com.HHive.hhive.global.exception.common.CustomException;
import com.HHive.hhive.global.exception.common.ErrorCode;

public class NotFoundChatMessageException extends CustomException {

    public NotFoundChatMessageException() {
        super(ErrorCode.NOT_FOUND_CHATMESSAGE_EXCEPTION);
    }

}
