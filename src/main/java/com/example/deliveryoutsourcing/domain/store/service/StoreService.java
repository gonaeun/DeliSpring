package com.example.deliveryoutsourcing.domain.store.service;

import com.example.deliveryoutsourcing.domain.store.dto.StoreRequestDto;
import com.example.deliveryoutsourcing.domain.store.dto.StoreResponseDto;
import java.util.List;

public interface StoreService {

    /**
     * 가게 생성
     */
    void createStore(Long ownerId, StoreRequestDto.Create requestDto);

}
