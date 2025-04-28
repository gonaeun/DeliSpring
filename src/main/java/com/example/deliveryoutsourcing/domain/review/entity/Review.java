package com.example.deliveryoutsourcing.domain.review.entity;

import com.example.deliveryoutsourcing.global.common.BaseEntity;
import com.example.deliveryoutsourcing.domain.order.entity.Order;
import com.example.deliveryoutsourcing.domain.store.entity.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // 관련 엔티티를 "필요할때 조회"하도록 지연로딩 사용 (리뷰 정보 먼저 가져오고 store,order은 직접 조회하면 추가쿼리 사용하도록)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private Integer rating;
    // int와 달리 Integer은 null 가능 >> 리뷰 작성 전일때 0점인지 null값인지 구분 가능

    @Column(nullable = false, length = 255)
    private String comment;

    public Review(Store store, Order order, int rating, String comment) {
        this.store = store;
        this.order = order;
        this.rating = rating;
        this.comment = comment;
    }
}
