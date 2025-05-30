package com.example.DeliSpring.domain.user.service;

import com.example.DeliSpring.config.PasswordEncoder;
import com.example.DeliSpring.domain.user.dto.UserResponseDto;
import com.example.DeliSpring.domain.user.entity.User;
import com.example.DeliSpring.domain.user.repository.UserRepository;
import com.example.DeliSpring.global.error.ApiException;
import com.example.DeliSpring.global.error.response.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void withdraw(Long userId, String password) {
        User user = userRepository.findById(userId)  // userId로 사용자 조회
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

        if(user.isDeleted()) {
            throw new ApiException(ErrorType.ALREADY_WITHDRAWN_USER);
        }

        if(!passwordEncoder.matches(password, user.getPassword())) {  // 비밀번호 일치 여부
            throw new ApiException(ErrorType.INVALID_PASSWORD);
        }

        user.withdraw();  // soft delete
    }

    @Override
    public UserResponseDto getMyInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

        if(user.isDeleted()) {
            throw new ApiException(ErrorType.ALREADY_WITHDRAWN_USER);
        }

        return UserResponseDto.builder()
            .id(user.getId())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .address(user.getAddress())
            .role(user.getRole())
            .build();
    }

    @Override
    public void updatePassword(Long userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

        if (user.isDeleted()) {
            throw new ApiException(ErrorType.ALREADY_WITHDRAWN_USER);
        }

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new ApiException(ErrorType.INVALID_PASSWORD);
        }

        user.updatePassword(passwordEncoder.encode(newPassword)); // 새 비밀번호로 암호화 저장
    }


    @Override
    public void updateAddress(Long userId, String address) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));

        if (user.isDeleted()) {
            throw new ApiException(ErrorType.ALREADY_WITHDRAWN_USER);
        }

        user.updateAddress(address);
    }

}
