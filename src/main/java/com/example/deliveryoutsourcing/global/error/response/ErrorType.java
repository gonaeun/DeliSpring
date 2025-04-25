package com.example.deliveryoutsourcing.global.error.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorType implements ExceptionStatus{

    /**
     * 1000: auth 에러
     */
    TOKEN_INVALID(1001, HttpStatus.UNAUTHORIZED.value(), "잘못된 토큰입니다."),
    TOKEN_EXPIRED(1002, HttpStatus.UNAUTHORIZED.value(), "만료된 토큰입니다."),
    TOKEN_VALIDATION_FAILED(1003, HttpStatus.UNAUTHORIZED.value(), "토큰 유효성 에러"),
    TOKEN_UNSUPPORTED(1004, HttpStatus.UNAUTHORIZED.value(), "지원하지 않는 토큰"),
    INVALID_JWT_SIGNATURE(1005, HttpStatus.UNAUTHORIZED.value(), "잘못된 JWT 서명입니다."),
    INVALID_JWT_TOKEN(1006, HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 JWT 토큰입니다."),
    REFRESH_TOKEN_MISMATCH(1007, HttpStatus.UNAUTHORIZED.value(), "리프레시 토큰이 일치하지 않습니다."),
    REFRESH_TOKEN_NOT_PROVIDED(1008, HttpStatus.UNAUTHORIZED.value(), "리프레시 토큰이 제공되지 않았습니다."),
    REFRESH_TOKEN_NOT_FOUND(1009, HttpStatus.UNAUTHORIZED.value(), "저장된 리프레시 토큰이 존재하지 않습니다."),
    UNAUTHORIZED_USER(1010, HttpStatus.UNAUTHORIZED.value(), "인증된 사용자만 접근할 수 있습니다."),
    INVALID_PASSWORD(1101, HttpStatus.UNAUTHORIZED.value(), "비밀번호가 일치하지 않습니다."),
    LOGIN_FAILED(1101, HttpStatus.UNAUTHORIZED.value(), "로그인에 실패했습니다."),


    /**
     * 2000: user 에러
     */
    USER_NOT_FOUND(2001, HttpStatus.NOT_FOUND.value(), "존재하지 않는 사용자입니다."),
    EMAIL_NOT_FOUND(2002, HttpStatus.NOT_FOUND.value(), "존재하지 않는 이메일입니다."),
    DUPLICATE_EMAIL(2003, HttpStatus.CONFLICT.value(), "이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(2004, HttpStatus.CONFLICT.value(), "이미 존재하는 닉네임입니다."),
    FORBIDDEN_PROFILE(2601, HttpStatus.FORBIDDEN.value(), "이 사용자는 프로필 비공개 설정 상태이며, 친구가 아닌 경우 정보 열람이 제한됩니다."),
    DISABLE_USER(2602, HttpStatus.FORBIDDEN.value(), "비활성화 계정입니다. 서비스 이용을 원하시면 문의하세요."),
    ALREADY_DEACTIVATED_ACCOUNT(2603, HttpStatus.CONFLICT.value(), "이미 비활성화 계정입니다. 요청을 처리할 수 없습니다.");

    /**
     * 3000: store 에러
     */

    /**
     * 4000: menu 에러
     */

    /**
     * 5000: order 에러
     */

    /**
     * 6000: review 에러
     */

    private final int code;
    private final int status;
    private final String message;

}
