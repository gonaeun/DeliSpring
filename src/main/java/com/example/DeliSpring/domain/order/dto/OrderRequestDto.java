package com.example.DeliSpring.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderRequestDto {

    @NotNull
    private Long menuId;   // 각 주문당 메뉴 하나만 주문받도록 Long타입으로 설정함
}


