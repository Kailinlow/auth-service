package com.kltn.auth_service.component.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUserId(String id);

    List<Order> findByStatus(String status);
}
