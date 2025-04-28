package com.example.deliveryoutsourcing.domain.auth.controller;

import com.example.deliveryoutsourcing.domain.auth.dto.AuthRequestDto;
import com.example.deliveryoutsourcing.domain.auth.jwt.JwtToken;
import com.example.deliveryoutsourcing.domain.auth.security.CustomUserDetails;
import com.example.deliveryoutsourcing.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody AuthRequestDto.Signup dto) {
        service.signup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 로그인 (JWT 발급)
     */
    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@Valid @RequestBody AuthRequestDto.Login dto) {  // dto 받아서 로그인 성공시 토큰 생성
        return ResponseEntity.status(HttpStatus.OK).body(service.login(dto));
    }

    /**
     * 로그아웃 (리프레시 토큰 제거)
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal CustomUserDetails userDetails) { //현재 로그인된 유저 정보 추출
        Long userId = userDetails.getUserId(); // UserDetails를 커스텀필터로 사용해서 userId 꺼내쓰기! repository를 두번 안 들려도 됨
        service.logout(userId);   // refresh token 삭제
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 액세스 토큰 재발급
     */
    @PostMapping("/reissue")
    public ResponseEntity<JwtToken> reIssue(@RequestHeader("Authorization") String bearerToken) {  // 헤더에서 Authorization : Bearer Token 추출
        return ResponseEntity.status(HttpStatus.OK).body(service.reIssue(bearerToken));    // AuthService.reIssue에서 RefreshToken으로 AccessToken 재발급

    }
}
