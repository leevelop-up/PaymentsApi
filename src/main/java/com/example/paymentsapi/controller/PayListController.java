package com.example.paymentsapi.controller;

import com.example.paymentsapi.repository.User.User;
import com.example.paymentsapi.repository.order.Order;
import com.example.paymentsapi.service.PayListService;
import com.example.paymentsapi.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

    @GetMapping("/memberlist")
    @ResponseBody
    public Page<UserDto> getUserList(@PageableDefault(page = 0, size = 10) Pageable pageable,
                                     @RequestParam(required = false) String userEmail,
                                     @RequestParam(required = false) String userName,
                                     @RequestParam(required = false) String userRole,
                                     @RequestParam(required = false) Integer companyCode,
                                     @RequestParam(required = false) String userAuthState) {
        Specification<User> spec = Specification.where(null);

        if (userEmail != null && !userEmail.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("userId"), userEmail));
        }

        if (userName != null && !userName.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("userName"), userName));
        }

        if (userRole != null && !userRole.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("userRole"), userRole));
        }

        if (companyCode != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("companyCode"), companyCode));
        }

        if (userAuthState != null && !userAuthState.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("state"), userAuthState));
        }

        return payListService.getUserPage(spec, pageable);
    }


}
