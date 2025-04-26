package com.example.deliveryoutsourcing.global.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 사장님만 접근 가능한 어노테이션
 */
@Target(ElementType.METHOD) // @Target : 어디에 정의할수있는지 >> 메서드!
@Retention(RetentionPolicy.RUNTIME)  // @Retention : 언제까지 정보가 유지될지 >> 런타임중에!
public @interface OwnerOnly {  // OwnerOnly 라는 커스텀 어노테이션 생성
}
