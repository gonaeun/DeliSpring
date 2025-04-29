package com.example.DeliSpring.domain.store.dto;

import com.example.DeliSpring.domain.menu.dto.MenuResponseDto;
import java.time.LocalTime;
import java.util.List;
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
    private boolean isClosed; // 폐업여부
    private List<MenuResponseDto> menus; // 단건조회시 메뉴 조회
}
