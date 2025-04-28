package com.example.deliveryoutsourcing.domain.order.service;

import com.example.deliveryoutsourcing.domain.order.dto.OrderRequestDto;
import com.example.deliveryoutsourcing.domain.order.dto.OrderResponseDto;
import com.example.deliveryoutsourcing.domain.order.dto.OrderUpdateRequestDto;
import java.util.List;

public interface OrderService {
    void createOrder(Long userId, OrderRequestDto requestDto);

    void updateOrderStatus(Long ownerId, Long orderId, OrderUpdateRequestDto requestDto);  // 주문상태변경(사장님)

    List<OrderResponseDto> getMyOrders(Long userId);  // 주문 목록 조회


}
