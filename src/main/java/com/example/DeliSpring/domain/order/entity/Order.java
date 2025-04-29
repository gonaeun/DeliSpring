package com.example.DeliSpring.domain.order.entity;

import com.example.DeliSpring.domain.menu.entity.Menu;
import com.example.DeliSpring.domain.store.entity.Store;
import com.example.DeliSpring.domain.user.entity.User;
import com.example.DeliSpring.global.common.BaseEntity;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")  // 'order'는 예약어이므로 테이블 이름 변경
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {  // Order엔티티의 Order.status는 현재 상태만 확인

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

}
