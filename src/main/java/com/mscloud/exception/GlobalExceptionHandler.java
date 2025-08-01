package com.mscloud.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<Map<String, Object>> handleCommonException(CommonException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", ex.getErrorCode());
        body.put("message", ex.getMessage());

        return ResponseEntity.ok(body); // ❗ 200 OK ilə qaytarırıq, amma içində status -1 göstəririk
    }
}
