package com.example.deliveryoutsourcing.domain.order.dto;

import com.example.deliveryoutsourcing.global.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponseDto {
    private String orderMenu;
    private Integer orderPrice;
    private OrderStatus orderStatus;
}
