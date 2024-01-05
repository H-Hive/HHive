package com.HHive.hhive.global.exception.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {

        log.error(e.getMessage());

        HttpStatus httpStatus = HttpStatus.valueOf(e.getStatusCode());
        ErrorResponse errorResponse = ErrorResponse.of(httpStatus, e.getMessage());

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
