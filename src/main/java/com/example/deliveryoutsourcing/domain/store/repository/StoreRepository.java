package com.example.deliveryoutsourcing.domain.store.repository;

import com.example.deliveryoutsourcing.domain.store.entity.Store;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

//    List<Store> findAllByOwnerId(Long ownerId);  // 특정 사장님의 모든 가게 목록 조회

    Long countByOwnerIdAndIsClosedFalse(Long ownerId); // 사장님이 현재 운영하는 가게 목록 조회(최대 3개 제한 확인용)

    List<Store> findAllByNameContainingIgnoreCase(String name); // 가게 이름으로 검색 (대소문자 구분없음)

    Optional<Store> findByIdAndIsClosedFalse(Long storeId);  // 가게 단건 조회 (폐업한 가게는 조회안됨)
}
