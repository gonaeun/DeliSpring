package com.example.deliveryoutsourcing.domain.order.repository;

import com.example.deliveryoutsourcing.domain.order.entity.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLogRepository extends JpaRepository<OrderLog, Long> {

}
