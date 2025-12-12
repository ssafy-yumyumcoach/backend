package com.yumyumcoach.global.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/*
전역 예외
 */

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    // JWT: 401 에러
    @ExceptionHandler({
            ExpiredJwtException.class,
            SignatureException.class,
            MalformedJwtException.class,
            UnsupportedJwtException.class,

    })
    public ResponseEntity<?> handleJwtUnauthorized(Exception e) {
        return createErrorResponse("INVALID_TOKEN", e.getMessage(), 401);
    }

    // JWT: 400 에러
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        return createErrorResponse("INVALID_TOKEN", e.getMessage(), 400);
    }

    // JWT: 잘못된 이메일/비밀번호 입력 에러
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> InvalidCredentialsException(InvalidCredentialsException e) {
        return createErrorResponse("AUTH_INVALID_CREDENTIALS", e.getMessage(), 401);
    }

    // 공통 에러 응답
    private ResponseEntity<?> createErrorResponse(String code, String message, int status) {
        return ResponseEntity
                .status(status)
                .body(Map.of("status", status, "code", code, "message", message));
    }
}
