package com.example.deliveryoutsourcing.domain.order.dto;

import com.example.deliveryoutsourcing.global.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderUpdateRequestDto {

    @NotNull
    private OrderStatus statusAfter; // 변경하고 싶은 상태
}
