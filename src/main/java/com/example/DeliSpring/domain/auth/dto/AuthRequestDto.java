package com.example.DeliSpring.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.example.DeliSpring.global.enums.Role;

public class AuthRequestDto {

    @Getter
    public static class Signup {

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이어야 합니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "비밀번호는 영문자, 숫자, 특수문자를 포함해 8자 이상이어야 합니다."
        )
        private String password;

        @NotBlank(message = "닉네임은 필수입니다.")
        @Size(max = 8, message = "닉네임은 8자 이내로 입력해주세요.")
        private String nickname;

        @NotNull(message = "권한은 필수입니다.")
        private Role role;  // enums라서 role객체
    }

    @Getter
    @AllArgsConstructor
    public static class Login {

        @Email(message = "이메일 형식이어야 합니다.")
        @NotBlank
        private String email;

        @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
        )
        @NotBlank
        private String password;
    }
}
