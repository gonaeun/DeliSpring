package com.example.deliveryoutsourcing.domain.user.entity;

import com.example.deliveryoutsourcing.domain.store.entity.Store;
import com.example.deliveryoutsourcing.global.common.BaseEntity;
import com.example.deliveryoutsourcing.global.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 8)
    private String nickname;

    @Enumerated(EnumType.STRING) // db에 enum이 숫자로 저장되지 않도록 string으로 지정
    @Column(nullable = false)
    private Role role;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    // 연관관계 설정
    @OneToMany(mappedBy = "owner")
    private List<Store> stores = new ArrayList<>();

    @Column
    private String refreshToken;

    public void updateToken(String refreshToken) { // 로그아웃
        this.refreshToken = refreshToken;
    }

    public void withdraw() {
        this.isDeleted = true;
    }
}

