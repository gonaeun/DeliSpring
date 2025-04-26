package com.example.deliveryoutsourcing.domain.store.dto;

import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreResponseDto {

    private Long storeId;
    private String name;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Integer minOrderPrice;
    private boolean isClosed;
}
