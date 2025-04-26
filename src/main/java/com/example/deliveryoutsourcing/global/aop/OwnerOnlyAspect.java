package com.example.deliveryoutsourcing.global.aop;

import com.example.deliveryoutsourcing.domain.auth.security.CustomUserDetails;
import com.example.deliveryoutsourcing.global.error.ApiException;
import com.example.deliveryoutsourcing.global.error.response.ErrorType;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @OwnerOnly가 붙어있으면, 사장님인지 아닌지 권한 체크해서 예외 터뜨림
 */
@Aspect  // aop 수행
@Component
@RequiredArgsConstructor
public class OwnerOnlyAspect {

    @Before("@annotation(com.example.deliveryoutsourcing.global.aop.OwnerOnly)")
    public void checkOwnerRole() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();  // principal은 현재 로그인한 사용자 정보

        // fix: 상황에 따라 principal의 타입이 다르므로 instanceof를 통해 타입 검사 필요
        // 정상 사용자 >> principal = CustomUserDetails객체 (유저클래스)
        // 비로그인 또는 인증안됨 >> principal = anonymousUser (문자열)
        if (!(principal instanceof CustomUserDetails)) {   // principal이 예상과 다르면 에러 (다른 role, 비정상 로그인)
            throw new ApiException(ErrorType.UNAUTHORIZED_USER);
        }

        CustomUserDetails userDetails = (CustomUserDetails) principal;  // principal이 올바르게 들어왔다면 다운캐스팅해서 로그인한 유저 정보 접근

        if (!userDetails.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_OWNER"))) {  // 로그인한 유저가 가진 권한 중에 owner 권한이 있는지 찾기
            throw new ApiException(ErrorType.FORBIDDEN_ACCESS);
        }
    }
}
