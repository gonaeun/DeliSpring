package com.example.DeliSpring.domain.store.service;

import com.example.DeliSpring.domain.store.dto.StoreRequestDto;
import com.example.DeliSpring.domain.store.dto.StoreResponseDto;
import java.util.List;

public interface StoreService {

    /**
     * 가게 생성
     */
    void createStore(Long ownerId, StoreRequestDto.Create requestDto);

    /**
     * 가게 목록 조회 (이름 검색 가능)
     */
    List<StoreResponseDto> getStores(String name);

    /**
     * 가게 단건 조회 (메뉴 포함)
     */
    StoreResponseDto getStore(Long storeId);

    /**
     * 가게 정보 수정
     */
    void updateStore(Long ownerId, Long storeId, StoreRequestDto.Update requestDto);

    /**
     * 가게 폐업
     */
    void closeStore(Long ownerId, Long storeId, String password);
}