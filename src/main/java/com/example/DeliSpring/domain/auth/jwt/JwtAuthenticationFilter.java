package com.example.DeliSpring.domain.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean { // 요청마다 jwt 토큰이 유효한지 검사해서 SecurityContext에 인증정보 등록

    private final JwtProvider jwtTokenProvider;
    private static final String[] WHITELIST = {"/", "/users/signup", "/auth/login", "/auth/reissue"}; // 화이트리스트: 토큰 없이도 접근 허용

    /**
     * 1. Request Header에서 JWT 토큰 추출
     * 2. validateToken으로 토큰 유효성 검사
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) // doFilter: 모든 요청이 이 필터 통과함. jwt 검증여부 판단
        throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();  // uri 추출

        // 화이트 리스트인지 체크하는 로직 호출
        if (isLoginCheck(requestURI)) {
            chain.doFilter(request, response);  // 화이트리스트에 포함되어 있다면, 토큰 체크 없이 다음 필터로 넘어가도록
            return;
        }

        // 토큰 검증 로직
        String token = resolveToken((HttpServletRequest) request);  // 헤더에서 토큰 꺼냄 (Authorization: Bearer {token} 형태에서 추출)

        if (token != null && jwtTokenProvider.validateToken(token)) {  // 토큰이 존재하고 유효하면
            Authentication authentication = jwtTokenProvider.getAuthentication(token); // jwtProvider에서 CustomUserDetails를 담고있는 인증객체(Authentication) 생성 = 토큰을 파싱해서 CustomUserDetails를 만들고, 그걸 Authentication 객체에 담아서 리턴
            SecurityContextHolder.getContext().setAuthentication(authentication);  // SecurityContextHolder에 등록하여 인증된 사용자로 인식하도록.
        }
        chain.doFilter(request, response);
    }

    /**
     * Request Header에서 토큰 정보 추출
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");  // 헤더에서 토큰만 추출
        if (StringUtils.hasText(bearerToken)) {
            return bearerToken.substring(7);  // "Bearer "를 제거한 실제 토큰 값
        }
        return null;
    }

    /**
     * 화이트 리스트는 인증 체크 x
     */
    private boolean isLoginCheck(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITELIST, requestURI);
    }
}
