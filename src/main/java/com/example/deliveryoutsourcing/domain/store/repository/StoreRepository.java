package com.example.deliveryoutsourcing.domain.store.repository;

import com.example.deliveryoutsourcing.domain.store.entity.Store;
import com.example.deliveryoutsourcing.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {


}