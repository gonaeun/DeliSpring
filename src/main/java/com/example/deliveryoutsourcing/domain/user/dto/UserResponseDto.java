package com.example.deliveryoutsourcing.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import com.example.deliveryoutsourcing.global.enums.Role;

@Getter
@Builder
public class UserResponseDto {

    private Long id;
    private String email;
    private String nickname;
    private String address;
    private Role role;
}
