package com.example.DeliSpring.domain.menu.controller;

import com.example.DeliSpring.domain.auth.security.CustomUserDetails;
import com.example.DeliSpring.domain.menu.dto.MenuRequestDto;
import com.example.DeliSpring.domain.menu.service.MenuService;
import com.example.DeliSpring.global.aop.OwnerOnly;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores/{storeId}/menus")
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    @OwnerOnly
    public ResponseEntity<Void> createMenu(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long storeId,
        @Valid @RequestBody MenuRequestDto.Create requestDto
    ) {
        menuService.createMenu(userDetails.getUserId(), storeId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/menus/{menuId}")  //storeId가 없어도 ownerId, menuId로 비즈니스 로직 충분히 검증 가능
    @OwnerOnly
    public ResponseEntity<Void> updateMenu(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long menuId,
        @Valid @RequestBody MenuRequestDto.Update requestDto
    ) {
        menuService.updateMenu(userDetails.getUserId(), menuId, requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/menus/{menuId}")
    @OwnerOnly
    public ResponseEntity<Void> deleteMenu(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long menuId
    ) {
        menuService.deleteMenu(userDetails.getUserId(), menuId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
