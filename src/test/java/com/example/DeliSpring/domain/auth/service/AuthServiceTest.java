package com.example.DeliSpring.domain.auth.service;

import com.example.DeliSpring.domain.auth.jwt.JwtProvider;
import com.example.DeliSpring.domain.auth.jwt.JwtToken;
import com.example.DeliSpring.domain.user.entity.User;
import com.example.DeliSpring.domain.user.repository.UserRepository;
import com.example.DeliSpring.global.enums.Role;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void logout_성공시_refreshToken이_null값이_되는지() {
        // given
        Long userId = 1L;
        User mockUser = new User(); // 가짜 user 객체 생성
        mockUser.updateToken("refreshToken");

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser)); // db가 없이도 테스트가 돌아가도록 직접 만든 mockUser객체가 리턴되도록 함

        // when
        authService.logout(userId);

        // then
        Assertions.assertNull(mockUser.getRefreshToken());  // mockUser의 refreshToken이 null이 되었는지
    }

    @Test
    void refreshToken이_유효할때_accessToken_재발급이_잘_되는지() {
        //given : 가상의 토큰과 아이디 준비
        String refreshToken = "validRefreshToken";
        Long userId = 1L;
        String accessToken = "newAccessToken";

        Mockito.when(jwtProvider.extractUserId(refreshToken)).thenReturn(userId); // refreshToken을 파싱했을 때, userId를 1l로 반환하도록
        Mockito.when(userRepository.findById(userId))
            .thenReturn(Optional.of(User.builder().refreshToken(refreshToken).build())); // userId가 호출되면 refreshToken을 가지고 있는 가짜 user객체를 반환하도록
        Mockito.when(jwtProvider.generateAccessToken(userId, Role.USER)).thenReturn(accessToken); // userId로 새로운 AccessToken 발급하는 메서드 호출되면, "accessToken"라는 문자열 반환

        //when : 토큰 재발급 메서드 실행
        JwtToken token = authService.reIssue("Bearer " + refreshToken);

        //then : accessToken이 일치하는지, refreshToken이 일치하는지 검증
        Assertions.assertEquals(accessToken, token.getAccessToken());
        Assertions.assertEquals(refreshToken, token.getRefreshToken());

    }
}