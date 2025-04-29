package com.example.DeliSpring.domain.user.controller;

import com.example.DeliSpring.domain.auth.security.CustomUserDetails;
import com.example.DeliSpring.domain.user.dto.UserRequestDto;
import com.example.DeliSpring.domain.user.dto.UserResponseDto;
import com.example.DeliSpring.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 회원 탈퇴
     */
    @PatchMapping("/{userId}/withdraw")
    public ResponseEntity<Void> withdraw(
            @PathVariable Long userId,
            @Valid @RequestBody UserRequestDto.Withdraw requestDto
    ) {
        userService.withdraw(userId, requestDto.getPassword());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    /**
     * 내 정보 조회
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyInfo(
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUserId();
        UserResponseDto responseDto = userService.getMyInfo(userId);
        return ResponseEntity.ok(responseDto);
    }


    /**
     * 프로필 수정 : 주소 변경
     */
    @PatchMapping("/{userId}/address")
    public ResponseEntity<Void> updateAddress(
        @PathVariable Long userId,
        @Valid @RequestBody UserRequestDto.UpdateAddress requestDto
    ) {
        userService.updateAddress(userId, requestDto.getAddress());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 비밀번호 변경
     */
    @PatchMapping("/{userId}/password")
    public ResponseEntity<Void> updatePassword(
        @PathVariable Long userId,
        @Valid @RequestBody UserRequestDto.UpdatePassword requestDto
    ) {
        userService.updatePassword(userId, requestDto.getCurrentPassword(), requestDto.getNewPassword());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
