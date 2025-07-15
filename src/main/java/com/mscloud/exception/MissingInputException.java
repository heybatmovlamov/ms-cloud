package com.mscloud.exception;


import com.mscloud.exception.constant.ErrorCode;

import java.text.MessageFormat;

public class MissingInputException extends CommonException {
    public MissingInputException(String message) {
        super(ErrorCode.PARAMETER_INVALID, message);
    }

    public static MissingInputException of(String message, Object... args) {
        return new MissingInputException(MessageFormat.format(message, args));
    }
}