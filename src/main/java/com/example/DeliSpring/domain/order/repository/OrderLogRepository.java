package com.example.DeliSpring.domain.order.repository;

import com.example.DeliSpring.domain.order.entity.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLogRepository extends JpaRepository<OrderLog, Long> {

}
