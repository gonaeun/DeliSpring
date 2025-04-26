package com.example.deliveryoutsourcing.domain.user.service;

import com.example.deliveryoutsourcing.domain.user.dto.UserResponseDto;

public interface UserService {

    /**
     * 회원 탈퇴 (Soft Delete)
     * @param userId  - 탈퇴할 유저의 ID
     * @param password - 검증용 비밀번호
     */
    void withdraw(Long userId, String password);

    /**
     * 내 정보 조회
     * @param userId - 조회할 유저 ID
     * return UserResponseDto
     */
    UserResponseDto getMyInfo(Long userId);

    /**
     * 비밀번호 변경
     * @param userId - 유저 ID
     * @param currentPassword - 현재 비밀번호
     * @param newPassword - 새 비밀번호
     */
    void updatePassword(Long userId, String currentPassword, String newPassword);

    /**
     * 주소 변경
     * @param userId - 유저 ID
     * @param address - 새로운 주소
     */
    void updateAddress(Long userId, String address);
}
