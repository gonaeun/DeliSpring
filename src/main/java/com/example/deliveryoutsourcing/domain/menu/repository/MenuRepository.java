package com.example.deliveryoutsourcing.domain.menu.repository;

import com.example.deliveryoutsourcing.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
