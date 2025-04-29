package com.example.DeliSpring.domain.order.entity;

import com.example.DeliSpring.domain.store.entity.Store;
import com.example.DeliSpring.global.enums.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class OrderLog {  // 주문 상태 변경 로그를 저장하는 용도 >> 나중에 고객이 주문 히스토리를 확인하도록 하기 위함

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Enumerated(EnumType.STRING)
    private OrderStatus statusBefore;

    @Enumerated(EnumType.STRING)
    private OrderStatus statusAfter;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    @Builder
    public OrderLog(Order order, Store store, OrderStatus statusBefore, OrderStatus statusAfter) {
        this.order = order;
        this.store = store;
        this.statusBefore = statusBefore;
        this.statusAfter = statusAfter;
        this.timestamp = LocalDateTime.now();
    }
}
