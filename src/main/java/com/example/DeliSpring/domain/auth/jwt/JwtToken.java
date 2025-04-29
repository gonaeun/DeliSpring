package com.example.DeliSpring.domain.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class JwtToken {

    private String grantType;  // Bearer
    private String accessToken;
    private String refreshToken;
}
