package com.example.DeliSpring.domain.store.entity;

import com.example.DeliSpring.domain.menu.entity.Menu;
import com.example.DeliSpring.domain.user.entity.User;
import com.example.DeliSpring.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //FK
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private LocalTime openTime;

    @Column(nullable = false)
    private LocalTime closeTime;

    @Column(name = "min_order_price", nullable = false)
    private int minOrderPrice;

    @Column(name = "is_closed")
    private boolean isStoreClosed = false; // 폐업여부

    @OneToMany(mappedBy = "store")
    private List<Menu> menus = new ArrayList<>();

    // 폐업 처리
    public void close() {
        this.isStoreClosed = true;
    }

    // 가게 정보 수정
    public void update(String name, LocalTime openTime, LocalTime closeTime, Integer minOrderPrice) {
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderPrice = minOrderPrice;
    }

}
