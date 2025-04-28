package com.example.deliveryoutsourcing.domain.menu.entity;

import com.example.deliveryoutsourcing.domain.store.entity.Store;
import com.example.deliveryoutsourcing.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(name = "is_deleted")
    private boolean isMenuDeleted = false;

    public Menu(Store store, String name, int price) {
        this.store = store;
        this.name = name;
        this.price = price;
        this.isMenuDeleted = false;
    }

    public void update(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public void delete() {
        this.isMenuDeleted = true;
    }


}
