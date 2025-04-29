package com.example.DeliSpring.domain.user.service;

import com.example.DeliSpring.domain.user.dto.UserResponseDto;

public interface UserService {

    /**
     * 회원 탈퇴 (Soft Delete)
     */
    void withdraw(Long userId, String password);

    /**
     * 내 정보 조회
     */
    UserResponseDto getMyInfo(Long userId);

    /**
     * 비밀번호 변경
     */
    void updatePassword(Long userId, String currentPassword, String newPassword);

    /**
     * 주소 변경
     */
    void updateAddress(Long userId, String address);
}
