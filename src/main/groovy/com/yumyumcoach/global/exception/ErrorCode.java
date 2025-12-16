package com.yumyumcoach.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // ===== COMMON =====
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "요청 값이 올바르지 않습니다."),

    // ===== AUTH =====
    AUTH_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "액세스 토큰이 유효하지 않습니다."),
    AUTH_INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."),

    // ===== COMMUNITY =====
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다."),
    POST_FORBIDDEN(HttpStatus.FORBIDDEN, "해당 게시글에 대한 권한이 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),
    COMMENT_FORBIDDEN(HttpStatus.FORBIDDEN, "해당 댓글에 대한 권한이 없습니다."),
    LIKE_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 좋아요를 누른 게시글입니다."),
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글에 대해 눌러둔 좋아요가 없습니다."),

    // ===== CHALLENGE =====
    CHALLENGE_INVALID_MONTH_PARAM(HttpStatus.BAD_REQUEST,
            "조회 월 형식이 올바르지 않습니다."),
    CHALLENGE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 챌린지를 찾을 수 없습니다."),
    CHALLENGE_ALREADY_JOINED(HttpStatus.CONFLICT, "이미 참여 중인 챌린지입니다."),
    CHALLENGE_JOIN_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "현재는 해당 챌린지에 참여할 수 없습니다."),
    CHALLENGE_JOIN_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 챌린지에 대한 참여 이력을 찾을 수 없습니다."),
    CHALLENGE_LEAVE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "현재는 해당 챌린지에서 나갈 수 없습니다."),
    CHALLENGE_ALREADY_LEFT(HttpStatus.CONFLICT, "이미 나간 챌린지입니다."),

    // ===== EXERCISE =====
    EXERCISE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 운동을 찾을 수 없습니다."),
    EXERCISE_RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 운동 기록을 찾을 수 없습니다."),
    EXERCISE_RECORD_FORBIDDEN(HttpStatus.FORBIDDEN, "해당 운동 기록에 대한 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

