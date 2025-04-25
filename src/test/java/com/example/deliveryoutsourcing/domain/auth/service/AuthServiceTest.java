package com.example.deliveryoutsourcing.domain.auth.service;

import com.example.deliveryoutsourcing.domain.user.entity.User;
import com.example.deliveryoutsourcing.domain.user.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void logout_성공시_refreshToken이_null값이_되는지() {
        // given
        Long userId = 1L;
        User mockUser = new User(); // 가짜 user 객체 생성

        // when
        authService.logout(userId);

        // then

    }
}