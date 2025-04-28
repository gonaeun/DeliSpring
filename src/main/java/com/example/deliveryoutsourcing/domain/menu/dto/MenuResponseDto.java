package com.example.deliveryoutsourcing.domain.menu.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuResponseDto {
    private Long menuId;
    private String name;
    private Integer price;
}
