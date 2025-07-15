package com.mscloud.exception;

import com.mscloud.exception.constant.ErrorCode;

import java.text.MessageFormat;

public class DuplicateRecordException extends CommonException {
    public DuplicateRecordException(String message) {
        super(ErrorCode.PARAMETER_INVALID, message);
    }

    public static DuplicateRecordException of(String message, Object... args) {
        return new DuplicateRecordException(MessageFormat.format(message, args));
    }
}