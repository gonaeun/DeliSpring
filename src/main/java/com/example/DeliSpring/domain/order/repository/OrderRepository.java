package com.example.DeliSpring.domain.order.repository;

import com.example.DeliSpring.domain.order.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long userId);  // 주문 목록 조회(사용자)

}
