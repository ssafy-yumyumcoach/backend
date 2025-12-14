package com.yumyumcoach.global.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException e) {
        ErrorCode ec = e.getErrorCode();
        return ResponseEntity.status(ec.getHttpStatus()).body(ErrorResponse.of(ec, e.getMessage()));
    }

    // @Valid 실패 -> 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValid(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.from(ErrorCode.INVALID_REQUEST));
    }

    // JSON 바디가 깨짐/파싱 실패 -> 400
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotReadable(Exception e) {
        return ResponseEntity.badRequest().body(ErrorResponse.from(ErrorCode.INVALID_REQUEST));
    }

    // path/query 타입 미스매치 -> 400
    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(Exception e) {
        return ResponseEntity.badRequest().body(ErrorResponse.from(ErrorCode.INVALID_REQUEST));
    }

    // 나머지 -> 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknown(Exception e) {
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}

