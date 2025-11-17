package com.ditial_wallet.walletservice.exception;

import com.ditial_wallet.walletservice.dtos.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    ResponseEntity<Object> handleConflictException(ConflictException exception) {
        return new ResponseEntity<>(new ApiResponse<>(
                exception.getMessage(),
                null
        ), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<Object> handleNotFoundException(NotFoundException exception) {
        return new ResponseEntity<>(new ApiResponse<>(
                exception.getMessage(),
                null
        ), HttpStatus.NOT_FOUND);
    }
}
