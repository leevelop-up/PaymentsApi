package com.example.paymentsapi.controller;

import com.example.paymentsapi.repository.order.Order;
import com.example.paymentsapi.service.PayListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class PayListController {

    private final PayListService payListService;
    @GetMapping("/getlist")
    @ResponseBody
    public Page<Order> getPayList(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {

        return payListService.getPayList(PageRequest.of(page, size));
    }

}
