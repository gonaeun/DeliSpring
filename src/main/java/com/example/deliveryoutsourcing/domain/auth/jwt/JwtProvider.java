package com.example.deliveryoutsourcing.domain.auth.jwt;

import com.example.deliveryoutsourcing.domain.auth.security.CustomUserDetails;
import com.example.deliveryoutsourcing.domain.auth.service.AuthService;
import com.example.deliveryoutsourcing.global.error.ApiException;
import com.example.deliveryoutsourcing.global.error.response.ErrorType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Value;



@Slf4j
@Component
public class JwtProvider {

    private final SecretKey key;
    private final AuthService authService;

    /**
     * 생성자를 통한 JWT 서명용 Key 초기화
     * application.property에서 secret 값 가져와서 key에 저장
     */
    public JwtProvider(@Value("${jwt.secret}") String secretKey,AuthService authService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.authService = authService;
    }

    /**
     * 로그인 성공: AccessToken과 RefreshToken을 생성
     */
    public JwtToken generateToken(Long userId) {
        long now = (new Date()).getTime();
        Date issuedAt = new Date(); // 토큰 발급 시간

        Map<String, Object> headers = new HashMap<>();  // jwt의 헤더 정의
        headers.put("alg", "HS256"); // 알고리즘
        headers.put("typ", "JWT");  // 토큰 타입

        // Access Token 생성
        String accessToken = Jwts.builder()
            .setSubject(String.valueOf(userId))              // 토큰 주체: userId
            .claim("auth", "ROLE_USER")                // 사용자 권한 정보
            .claim("iss", "off")                       // 발급자 정보(issuer)
            .setExpiration(new Date(now + 3600000))          // 만료 시간: 60분
            .setIssuedAt(issuedAt)                           // 발급 시각
            .signWith(key, SignatureAlgorithm.HS256)         // 서명 키/알고리즘
            .compact();                                      // JWT 문자열로 최종 생성

        // Refresh Token 검사 및 생성
        String refreshToken = authService.getRefreshToken(userId);  // db에서 유저의 기존 리프레시토큰 꺼내옴
        if (refreshToken == null || isRefreshTokenExpired(refreshToken)) {  // refresh토큰이 없거나 만료된 경우에 새로 발급
            refreshToken = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("iss", "off")
                .claim("add", "ref")  // refresh 토큰임을 구분하는 추가정보
                .setExpiration(new Date(now + 604800000))  // 만료 : 7일
                .setIssuedAt(issuedAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
            authService.saveToken(userId, refreshToken);
        }

        // 클라이언트에 넘길 토큰 객체 구성
        return JwtToken.builder()
            .grantType("Bearer")  // 토큰타입지정 : Bearer
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    /**
     * 인증 객체 생성 : JWT를 복호화하여 토큰에 들어있는 정보를 꺼내 Authentication 객체를 생성
     * JwtAuthenticationFilter에서 해당 메서드 호출
     */
    public Authentication getAuthentication(String token) {  // jwt 토큰을 spring security에서 사용할 authentication 객체로 변환해주는 메서드
        Claims claims = parseClaims(token);  // 토큰 안에 들어있는 payload(email,nick...)를 꺼냄

        if (claims.get("auth") == null) { // role(USER/OWNER)이 없으면 예외 발생
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Long userId = Long.valueOf(claims.getSubject());
        String email = claims.get("email", String.class);

        String authString = claims.get("auth", String.class);  // Role
        List<GrantedAuthority> authorities = Arrays.stream(authString.split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        CustomUserDetails userDetails = new CustomUserDetails(userId, email, "", authorities); // 로그인하는거 아니라서 비번 검증 안하니까 pw는 빈값
        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);  // 이렇게 Authentication 객체 반환하면 Spring Security가 인증 됐다고 판단함
    }

    /**
     * 유효한 토큰인지 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {  // 서명이 위조되었거나, jwt 구조 잘못됨
            throw new ApiException(ErrorType.TOKEN_INVALID);
        } catch (ExpiredJwtException e) {  // 토큰 만료됨
            throw new ApiException(ErrorType.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException | IllegalArgumentException e) {  // 지원하지 않는 형식이거나, 잘못된 값
            throw new ApiException(ErrorType.TOKEN_UNSUPPORTED);
        } catch (Exception e) {  // 그밖의 오류들
            throw new ApiException(ErrorType.TOKEN_VALIDATION_FAILED);
        }
    }

    /**
     * DB에 저장된 RefreshToken과 비교하여 AccessToken을 재발급
     */
    public JwtToken refreshToken(String refreshToken) {
        try {long userId = extractUserId(refreshToken);

            String savedRefreshToken =authService.getRefreshToken(userId); // 토큰에서 userId 꺼내서 db에 저장된 토큰과 비교

            if (!refreshToken.equals(savedRefreshToken)) { // 두 토큰이 다를 경우
                log.warn("Refresh Token mismatch for userId={}", userId);
                throw new ApiException(ErrorType.REFRESH_TOKEN_MISMATCH);
            }

            JwtToken newToken = generateToken(userId);  // 같다면, AccessToken 새로 발급

            return newToken;

        } catch (NullPointerException e) {
            log.warn("does not exist Token");
            throw new ApiException(ErrorType.REFRESH_TOKEN_NOT_PROVIDED);
        } catch (NoSuchElementException e) {
            log.warn("no such Token value");
            throw new ApiException(ErrorType.REFRESH_TOKEN_NOT_FOUND);
        }
    }

    /**
     * refresh token이 만료되었는지 확인
     */
    public boolean isRefreshTokenExpired(String refreshToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken);
            return false; // 유효하면 false
        } catch (ExpiredJwtException e) {
            return true;  // 만료되었다면 true
        } catch (Exception e) {  // 그밖의 경우는 예외 던지기
            throw new ApiException(ErrorType.TOKEN_VALIDATION_FAILED);
        }
    }

    /**
     * 토큰을 파싱해서 userId를 꺼냄
     */
    private Long extractUserId(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
            return Long.valueOf(claims.getSubject());
        } catch (SignatureException e) {
            throw new ApiException(ErrorType.INVALID_JWT_SIGNATURE);
        } catch (Exception e) {
            throw new ApiException(ErrorType.INVALID_JWT_TOKEN);
        }
    }

    /**
     * JWT 토큰을 파싱해서 payload(클레임) 꺼냄
     */
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken)
                .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}

