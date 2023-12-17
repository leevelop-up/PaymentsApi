package com.example.paymentsapi.controller;

import com.example.paymentsapi.repository.order.Order;
import com.example.paymentsapi.service.PayListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class PayListController {

    private final PayListService payListService;
    @GetMapping("/getlist")
    @ResponseBody
    public Page<Order> getPayList(@PageableDefault(page = 0, size = 10) Pageable pageable,
                                  @RequestParam(required = false) String date,
                                  @RequestParam(required = false) String userId,
                                  @RequestParam(required = false) String orderNo,
                                  @RequestParam(required = false) String resultStatus,
                                  @RequestParam(required = false) String paymentMethod) {
        Specification<Order> spec = Specification.where(null);

        if (date != null && !date.isEmpty()) {
            // 예제로 날짜는 정확한 매칭으로 처리되도록 했습니다. 실제로는 범위 등을 고려해야 할 수 있습니다.
            LocalDate parsedDate = LocalDate.parse(date);
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("orderDate"), parsedDate));
        }

        if (userId != null && !userId.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("userId"), userId));
        }

        if (orderNo != null && !orderNo.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("orderNo"), orderNo));
        }

        if (resultStatus != null && !resultStatus.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("orderStatus"), resultStatus));
        }

        if (paymentMethod != null && !paymentMethod.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("paymentMethod"), paymentMethod));
        }

        return payListService.getPayList(spec, pageable);
    }

}
