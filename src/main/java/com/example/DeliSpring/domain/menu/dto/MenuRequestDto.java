package com.example.DeliSpring.domain.menu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MenuRequestDto {

    // 메뉴 생성
    @Getter
    public static class Create {
        @NotBlank(message = "메뉴 이름은 필수입니다.")
        private String name;

        @NotNull(message = "메뉴 가격은 필수입니다.")
        private Integer price;
    }

    // 메뉴 수정
    @Getter
    public static class Update {
        @NotBlank(message = "메뉴 이름은 필수입니다.")
        private String name;

        @NotNull(message = "메뉴 가격은 필수입니다.")
        private Integer price;
    }
}
