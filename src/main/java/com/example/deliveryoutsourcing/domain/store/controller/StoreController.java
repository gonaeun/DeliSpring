package com.example.deliveryoutsourcing.domain.store.controller;

import com.example.deliveryoutsourcing.domain.auth.security.CustomUserDetails;
import com.example.deliveryoutsourcing.domain.store.dto.StoreRequestDto;
import com.example.deliveryoutsourcing.domain.store.dto.StoreResponseDto;
import com.example.deliveryoutsourcing.domain.store.service.StoreService;
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
    public ResponseEntity<Void> createStore(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @Valid @RequestBody StoreRequestDto.Create requestDto
    ) {
        Long ownerId = userDetails.getUserId();
        storeService.createStore(ownerId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
