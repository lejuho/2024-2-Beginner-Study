package com.example.todo_api.common;

import com.example.todo_api.common.dto.ErrorResponse;
import com.example.todo_api.common.exception.BadRequestException;
import com.example.todo_api.common.exception.InternalServerErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknownException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    //DTO 에서 유효성 검사가 실패했을 때 대신 응답하는 핸들러
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse errorResponse = new ErrorResponse(message);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
