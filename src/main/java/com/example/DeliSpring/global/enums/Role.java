package com.example.DeliSpring.global.enums;

import lombok.Getter;

@Getter
public enum Role {

    USER("ROLE_USER"),
    OWNER("ROLE_OWNER");

    private final String key;

    Role(String key) {
        this.key = key;
    }

    /**
     * Spring Security의 권한 표현 방식과 동일한 문자열을 반환 ("ROLE_OWNER", "ROLE_USER")
     */
    public String getKey() {
        return key;
    }
}
