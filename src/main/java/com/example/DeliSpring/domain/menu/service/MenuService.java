package com.example.DeliSpring.domain.menu.service;

import com.example.DeliSpring.domain.menu.dto.MenuRequestDto;

public interface MenuService {

    void createMenu(Long ownerId, Long storeId, MenuRequestDto.Create requestDto); // 메뉴 생성

    void updateMenu(Long ownerId, Long menuId, MenuRequestDto.Update requestDto); // 메뉴 수정

    void deleteMenu(Long ownerId, Long menuId); // 메뉴 삭제
}
