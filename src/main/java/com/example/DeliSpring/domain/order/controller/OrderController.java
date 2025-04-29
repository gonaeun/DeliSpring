package com.example.DeliSpring.domain.order.controller;

import com.example.DeliSpring.domain.auth.security.CustomUserDetails;
import com.example.DeliSpring.domain.order.dto.OrderRequestDto;
import com.example.DeliSpring.domain.order.dto.OrderResponseDto;
import com.example.DeliSpring.domain.order.dto.OrderUpdateRequestDto;
import com.example.DeliSpring.domain.order.service.OrderService;
import com.example.DeliSpring.global.aop.OwnerOnly;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> createOrder(  // 주문하기
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody @Valid OrderRequestDto requestDto
    ) {
        orderService.createOrder(userDetails.getUserId(), requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{orderId}")
    @OwnerOnly
    public ResponseEntity<Void> updateOrderStatus(  // 주문 상태 변경 (사장님만 접근 가능)
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long orderId,
        @Valid @RequestBody OrderUpdateRequestDto requestDto
    ) {
        orderService.updateOrderStatus(userDetails.getUserId(), orderId, requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/my")
    public ResponseEntity<List<OrderResponseDto>> getMyOrders(
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<OrderResponseDto> orders = orderService.getMyOrders(userDetails.getUserId());
        return ResponseEntity.ok(orders);  // 리스트형태로 응답 반환
    }

}
