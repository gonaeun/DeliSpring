package com.example.DeliSpring.domain.order.dto;

import com.example.DeliSpring.global.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderUpdateRequestDto {

    @NotNull
    private OrderStatus statusAfter; // 변경하고 싶은 상태
}
