package com.example.DeliSpring.domain.order.dto;

import com.example.DeliSpring.global.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponseDto {
    private String orderMenu;
    private Integer orderPrice;
    private OrderStatus orderStatus;
}
