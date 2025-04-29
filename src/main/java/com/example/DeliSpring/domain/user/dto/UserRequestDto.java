package com.example.DeliSpring.domain.user.dto;

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

    @Getter
    public static class UpdatePassword {

        @NotBlank(message = "현재 비밀번호를 입력해주세요.")
        private String currentPassword;

        @NotBlank(message = "새롭게 설정할 비밀번호를 입력해주세요")
        private String newPassword;
    }


}
