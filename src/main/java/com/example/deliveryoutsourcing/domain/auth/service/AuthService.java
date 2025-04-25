package com.example.deliveryoutsourcing.domain.auth.service;

import com.example.deliveryoutsourcing.domain.auth.dto.AuthRequestDto;
import com.example.deliveryoutsourcing.domain.auth.jwt.JwtProvider;
import com.example.deliveryoutsourcing.domain.auth.jwt.JwtToken;
import com.example.deliveryoutsourcing.domain.user.entity.User;
import com.example.deliveryoutsourcing.domain.user.repository.UserRepository;
import com.example.deliveryoutsourcing.global.error.ApiException;
import com.example.deliveryoutsourcing.global.error.response.ErrorType;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.deliveryoutsourcing.global.enums.Role;
import com.example.deliveryoutsourcing.config.PasswordEncoder;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {  // 인증 로직 수행

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    @Transactional
    public void signup(AuthRequestDto.Signup dto) {
        if (userRepository.existsByEmail(dto.getEmail())) { // 이메일 중복 확인
            throw new ApiException(ErrorType.DUPLICATE_EMAIL);
        }

        if (userRepository.existsByNickname(dto.getNickname())) { // 닉네임 중복 확인
            throw new ApiException(ErrorType.DUPLICATE_NICKNAME);
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());  // 비밀번호 인코딩

        User user = User.builder() // User 객체 생성
            .email(dto.getEmail())
            .password(encodedPassword)
            .nickname(dto.getNickname())
            .role(dto.getRole()) // role 객체형태로 그대로 받아옴
            .build();

        userRepository.save(user);  // DB에 저장
    }

    /**
     * 로그인 >> 이메일/비밀번호 검증, 토큰 생성
     * 이메일을 통해 유저 객체를 가져옴 비밀번호가 맞는지 확인
     * accessToken과 refreshToken을 반환
     */
    public JwtToken login(AuthRequestDto.Login dto) {
        User findUser = userRepository.findByEmail(dto.getEmail())  // db의 이메일과 일치하는지
            .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));
        if (!passwordEncoder.matches(dto.getPassword(), findUser.getPassword())) {  // 비밀번호 일치하는지
            throw new ApiException(ErrorType.INVALID_PASSWORD);
        }
        return jwtProvider.generateToken(findUser.getId());  // 유저의 id로 jwt토큰 생성 (generateToken()은 accessToken, refreshToken 한쌍 만들어냄)
    }

    /**
     * 로그아웃 >> DB에 저장된 refreshToken을 삭제
     */
    @Transactional
    public void logout(Long userId) {
        User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));
        user.updateToken(null);  // refresh token DB에서 삭제
    }

    /**
     * accessToken을 재발급
     */
    public JwtToken reIssue(String bearerToken) {
        String refreshToken = bearerToken.replace("Bearer ", ""); // 실제 토큰 문자열만 추출
        return jwtProvider.refreshToken(refreshToken);  // jwtProvider에 넘김
    }

    /**
     * refreshToken 가져오기
     */
    public String getRefreshToken(long userId) {

        User findUser = userRepository.findById(userId)
            .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));
        return findUser.getRefreshToken();
    }

    /**
     * refreshToken 저장
     */
    @Transactional
    public void saveToken(long userId, String refreshToken) {
        User findUser = userRepository.findById(userId)
            .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));
        findUser.updateToken(refreshToken);
        userRepository.save(findUser);
    }
}
