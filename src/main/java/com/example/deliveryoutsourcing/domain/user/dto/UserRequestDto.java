package com.example.deliveryoutsourcing.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class UserRequestDto {

    @Getter
    public static class Withdraw {
        @NotBlank
        private String password;
    }

    @Getter
    public static class UpdateAddress {
        @NotBlank
        private String address;
    }


}
