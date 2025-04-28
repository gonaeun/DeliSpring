package com.example.deliveryoutsourcing.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderRequestDto {

    @NotNull
    private Long menuId;
}


