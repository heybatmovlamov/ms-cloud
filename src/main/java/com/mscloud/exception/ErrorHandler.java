package com.mscloud.exception;

import com.mscloud.exception.model.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@Slf4j
@Component
@RequiredArgsConstructor
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MissingInputException.class)
    public ErrorResponse handleMissingInputException(MissingInputException ex) {
        this.addErrorLog(BAD_REQUEST, ex.getErrorCode(), ex.getMessage(), "MissingInputException");
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(DuplicateRecordException.class)
    public ErrorResponse handleDuplicateRecordException(DuplicateRecordException ex) {
        this.addErrorLog(CONFLICT, ex.getErrorCode(), ex.getMessage(), "DuplicateRecordException");
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    protected void addErrorLog(HttpStatus httpStatus, String errorCode, String errorMessage, String exceptionType) {
        log.error("HttpStatus: {} | Code: {} | Type: {} | Message: {}", httpStatus, errorCode,
                exceptionType, errorMessage);
    }
}