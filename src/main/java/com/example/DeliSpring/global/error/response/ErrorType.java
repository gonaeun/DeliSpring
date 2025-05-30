package com.example.DeliSpring.global.error.response;

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
    REFRESH_TOKEN_NOT_FOUND(1009, HttpStatus.UNAUTHORIZED.value(), "저장된 리프레시 토큰이 존재하지 않습니다."),
    UNAUTHORIZED_USER(1010, HttpStatus.UNAUTHORIZED.value(), "인증된 사용자만 접근할 수 있습니다."),
    FORBIDDEN_ACCESS(1011, HttpStatus.FORBIDDEN.value(), "권한이 없는 사용자입니다."),
    INVALID_PASSWORD(1012, HttpStatus.UNAUTHORIZED.value(), "비밀번호가 일치하지 않습니다."),
    VALIDATION_FAILED(1013, HttpStatus.UNAUTHORIZED.value(), "유효성 검사를 실패하였습니다."),


    /**
     * 2000: user 에러
     */
    USER_NOT_FOUND(2001, HttpStatus.NOT_FOUND.value(), "존재하지 않는 사용자입니다."),
    DUPLICATE_EMAIL(2003, HttpStatus.CONFLICT.value(), "이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(2004, HttpStatus.CONFLICT.value(), "이미 존재하는 닉네임입니다."),
    ALREADY_WITHDRAWN_USER(2005, HttpStatus.CONFLICT.value(), "이미 탈퇴한 회원입니다. 요청을 처리할 수 없습니다."),

    /**
     * 3000: store 에러
     */
    STORE_LIMIT_EXCEEDED(3001, HttpStatus.CONFLICT.value(), "가게 최대 생성 가능 횟수를 초과하였습니다."),
    STORE_NOT_FOUND(3002, HttpStatus.NOT_FOUND.value(), "존재하지 않는 가게입니다."),
    STORE_ALREADY_CLOSED(3003, HttpStatus.CONFLICT.value(), "폐업한 가게입니다"),
    STORE_OWNER_FORBIDDEN(3004, HttpStatus.FORBIDDEN.value(), "가게 수정 권한이 없습니다."),

    /**
     * 4000: menu 에러
     */
    MENU_OWNER_FORBIDDEN(4001, HttpStatus.FORBIDDEN.value(), "메뉴 수정 권한이 없습니다."),
    MENU_NOT_FOUND(4002, HttpStatus.NOT_FOUND.value(), "메뉴를 찾을 수 없습니다."),
    MENU_ALREADY_DELETED(4003, HttpStatus.BAD_REQUEST.value(), "이미 삭제된 메뉴입니다."),


    /**
     * 5000: order 에러
     */
    ORDER_PRICE_LOW(5001, HttpStatus.BAD_REQUEST.value(), "주문 금액이 최소 주문 금액보다 낮습니다."),
    STORE_CLOSED(5002, HttpStatus.BAD_REQUEST.value(), "현재 영업 시간이 아닙니다."),
    ORDER_NOT_FOUND(5003, HttpStatus.NOT_FOUND.value(), "존재하지 않는 주문입니다."),
    ORDER_OWNER_FORBIDDEN(5004, HttpStatus.FORBIDDEN.value(), "주문 수정 권한이 없습니다."),
    ORDER_INVALID_STATUS_CHANGE(5005, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 주문 상태 변경입니다."),
    MENU_NOT_SELECTED(5006, HttpStatus.BAD_REQUEST.value(), "메뉴를 선택해주세요."),

    /**
     * 6000: review 에러
     */
    ORDER_NOT_COMPLETED(6001, HttpStatus.BAD_REQUEST.value(), "주문이 아직 완료되지 않았습니다."),
    REVIEW_ALREADY_EXISTS(6002, HttpStatus.CONFLICT.value(), "이미 작성된 리뷰입니다.");


    private final int code;
    private final int status;
    private final String message;

}
