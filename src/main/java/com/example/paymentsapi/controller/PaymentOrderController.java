package com.example.paymentsapi.controller;


import com.example.paymentsapi.repository.order.Order;
import com.example.paymentsapi.service.PaymentOrderService;
import com.example.paymentsapi.web.dto.OrderStatusDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class PaymentOrderController {
    private final PaymentOrderService paymentOrderService;
    @ApiOperation(value="카드결제 진행")
    @PostMapping(value = "/ing", consumes = "application/json")
    public List<Order> OrderPayment(@RequestBody OrderStatusDto orderStatusDto){

        List<Order> resultData =  paymentOrderService.OrderInfoSave(orderStatusDto);
        return resultData;
    }

}
