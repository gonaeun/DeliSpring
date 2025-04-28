package com.example.deliveryoutsourcing.domain.order.service;

import com.example.deliveryoutsourcing.domain.order.dto.OrderRequestDto;
import com.example.deliveryoutsourcing.domain.order.dto.OrderUpdateRequestDto;

public interface OrderService {
    void createOrder(Long userId, OrderRequestDto requestDto);

    void updateOrderStatus(Long ownerId, Long orderId, OrderUpdateRequestDto requestDto);

}
