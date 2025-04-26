package com.example.deliveryoutsourcing.domain.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class StoreRequestDto {

    @Getter
    @Setter
    public static class Create {

        @NotBlank(message = "가게 이름은 필수입니다.")
        private String name;

        @NotBlank(message = "오픈 시간은 필수입니다.")
        private String openTime;

        @NotBlank(message = "마감 시간은 필수입니다.")
        private String closeTime;

        @NotNull(message = "최소 주문 금액은 필수입니다.")
        private Integer minOrderPrice;
    }



}
