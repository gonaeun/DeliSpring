package com.example.deliveryoutsourcing.domain.user.controller;

import com.example.deliveryoutsourcing.domain.user.dto.UserRequestDto;
import com.example.deliveryoutsourcing.domain.user.dto.UserResponseDto;
import com.example.deliveryoutsourcing.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * 프로필 조회
     */

    /**
     * 프로필 수정
     */
}
