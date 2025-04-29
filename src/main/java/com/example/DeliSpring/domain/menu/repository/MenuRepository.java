package com.example.DeliSpring.domain.menu.repository;

import com.example.DeliSpring.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
