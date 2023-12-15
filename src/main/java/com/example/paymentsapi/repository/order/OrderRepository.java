package com.example.paymentsapi.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByOrderNo(Integer orderNo);
}
