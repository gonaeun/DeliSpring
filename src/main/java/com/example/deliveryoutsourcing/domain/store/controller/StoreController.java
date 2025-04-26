package com.example.deliveryoutsourcing.domain.store.controller;

import com.example.deliveryoutsourcing.domain.auth.security.CustomUserDetails;
import com.example.deliveryoutsourcing.domain.store.dto.StoreRequestDto;
import com.example.deliveryoutsourcing.domain.store.dto.StoreResponseDto;
import com.example.deliveryoutsourcing.domain.store.service.StoreService;
import com.example.deliveryoutsourcing.global.aop.OwnerOnly;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    /**
     * 가게 생성 (사장님만 가능)
     */
    @PostMapping
    @OwnerOnly
    public ResponseEntity<Void> createStore(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @Valid @RequestBody StoreRequestDto.Create requestDto
    ) {
        Long ownerId = userDetails.getUserId();
        storeService.createStore(ownerId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 가게 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<StoreResponseDto>> getStores(
        @RequestParam(required = false) String name
    ) {
        List<StoreResponseDto> stores = storeService.getStores(name);
        return ResponseEntity.ok(stores);
    }

    /**
     * 가게 단건 조회
     */
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponseDto> getStore(@PathVariable Long storeId) {
        StoreResponseDto store = storeService.getStore(storeId);
        return ResponseEntity.ok(store);
    }

    /**
     * 가게 수정 (사장님만 가능)
     */
    @PatchMapping("/{storeId}")
    @OwnerOnly
    public ResponseEntity<Void> updateStore(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long storeId,
        @Valid @RequestBody StoreRequestDto.Update requestDto
    ) {
        Long ownerId = userDetails.getUserId();
        storeService.updateStore(ownerId, storeId, requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 가게 폐업 (사장님만 가능)
     */
    @PatchMapping("/{storeId}/close")
    @OwnerOnly
    public ResponseEntity<Void> closeStore(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long storeId,
        @RequestBody StoreRequestDto.Close requestDto
    ) {
        Long ownerId = userDetails.getUserId();
        storeService.closeStore(ownerId, storeId, requestDto.getPassword());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}