package com.example.deliveryoutsourcing.domain.menu.service;

import com.example.deliveryoutsourcing.domain.menu.dto.MenuRequestDto;
import com.example.deliveryoutsourcing.domain.menu.entity.Menu;
import com.example.deliveryoutsourcing.domain.menu.repository.MenuRepository;
import com.example.deliveryoutsourcing.domain.store.entity.Store;
import com.example.deliveryoutsourcing.domain.store.repository.StoreRepository;
import com.example.deliveryoutsourcing.global.error.ApiException;
import com.example.deliveryoutsourcing.global.error.response.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Override
    public void createMenu(Long ownerId, Long storeId, MenuRequestDto.Create requestDto) {
        Store store = storeRepository.findById(storeId)// db에서 가게 id 찾기
            .orElseThrow(() -> new ApiException(ErrorType.STORE_NOT_FOUND));

        if (!store.getOwner().getId().equals(ownerId)) {  // 가게 주인이 일치해야 메뉴 생성 가능
            throw new ApiException(ErrorType.MENU_OWNER_FORBIDDEN);
        }

        Menu menu = new Menu(store, requestDto.getName(), requestDto.getPrice()); // 메뉴 생성
        menuRepository.save(menu); // db에 저장
    }

    @Override
    @Transactional
    public void updateMenu(Long ownerId, Long menuId, MenuRequestDto.Update requestDto) {
        Menu menu = menuRepository.findById(menuId)  // 수정할 메뉴id 찾아보자
            .orElseThrow(() -> new ApiException(ErrorType.MENU_NOT_FOUND));

        if (menu.isMenuDeleted()) {  // 이미 삭제된(soft delete)된 메뉴는 수정 불가
            throw new ApiException(ErrorType.MENU_ALREADY_DELETED);
        }

        if (!menu.getStore().getOwner().getId().equals(ownerId)) {  // 사장 id가 일치해야해
            throw new ApiException(ErrorType.MENU_OWNER_FORBIDDEN);
        }

        menu.update(requestDto.getName(), requestDto.getPrice());
    }

    @Override
    @Transactional
    public void deleteMenu(Long ownerId, Long menuId) {
        Menu menu = menuRepository.findById(menuId)  // 삭제할 메뉴id 찾기
            .orElseThrow(() -> new ApiException(ErrorType.MENU_NOT_FOUND));

        if (menu.isMenuDeleted()) {  // 이미 삭제된 메뉴인지 체크
            throw new ApiException(ErrorType.MENU_ALREADY_DELETED);
        }

        if (!menu.getStore().getOwner().getId().equals(ownerId)) {  // 사장id와 일치하는지 체크
            throw new ApiException(ErrorType.MENU_OWNER_FORBIDDEN);
        }

        menu.delete(); // Soft delete
    }

}
