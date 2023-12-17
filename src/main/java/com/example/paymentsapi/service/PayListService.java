package com.example.paymentsapi.service;

import com.example.paymentsapi.repository.order.Order;
import com.example.paymentsapi.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PayListService {
    private final OrderRepository orderRepository;
    public Page<Order> getPayList(Specification<Order> spec, Pageable pageable) {

        return orderRepository.findAll(spec,pageable);
    }
}
