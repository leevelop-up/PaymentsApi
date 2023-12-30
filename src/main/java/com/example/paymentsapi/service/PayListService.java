package com.example.paymentsapi.service;

import com.example.paymentsapi.repository.User.User;
import com.example.paymentsapi.repository.User.UserRepository;
import com.example.paymentsapi.repository.order.Order;
import com.example.paymentsapi.repository.order.OrderRepository;
import com.example.paymentsapi.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PayListService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    public Page<Order> getPayList(Specification<Order> spec, Pageable pageable) {

        return orderRepository.findAll(spec,pageable);
    }


    public Page<UserDto> getUserPage(Specification<User> spec, Pageable pageable) {
        Page<User> userPage = userRepository.findAll(spec, pageable);
        List<UserDto> userDtoList = userPage.getContent().stream()
                .map(user -> new UserDto(
                        user.getUserId(),
                        user.getUserName(),
                        user.getUserRole(),
                        user.getCompanyCode(),
                        user.getJoinDate(),
                        user.getState()
                        // password 필드는 제외
                ))
                .collect(Collectors.toList());

        return new PageImpl<>(userDtoList, pageable, userPage.getTotalElements());
    }




}
