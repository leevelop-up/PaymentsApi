package com.example.paymentsapi.service;

import com.example.paymentsapi.controller.PaymentOrderController;
import com.example.paymentsapi.repository.order.Order;
import com.example.paymentsapi.repository.order.OrderRepository;
import com.example.paymentsapi.web.dto.OrderStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentOrderService {
    private final OrderRepository orderRepository;


    public List<Order> OrderInfoSave(OrderStatusDto orderStatusDto) {

        Integer Period = orderStatusDto.getPayPeriod();
        if(Period == null){
            Period = 0;
        }

        //BELOW_ZERO_AMOUNT	금액은 0보다 커야 합니다.
        //DUPLICATED_ORDER_ID	이미 승인 및 취소가 진행된 중복된 주문번호 입니다. 다른 주문번호로 진행해주세요.
        //INVALID_BANK	유효하지 않은 은행입니다.
        //INVALID_CARD_COMPANY	유효하지 않은 카드사입니다.
        //INVALID_SUCCESS_URL	successUrl 값은 필수 값입니다.
        //INVALID_EMAIL	이메일 주소 형식에 맞지 않습니다.
        //INVALID_DATE	날짌 데이터가 잘못 되었습니다.
        //INCORRECT_FAIL_URL_FORMAT	잘못된 failUrl 입니다.
        //INCORRECT_SUCCESS_URL_FORMAT	잘못된 successUrl 입니다.



        //결제 실패저장은 결제 카드관련된 실패일 경우에만 저장(한도초과, 정지카드 등)
        //결제 정보 등록 및 리턴값 부여
        Order orderInfo = Order.builder()
                .userid(orderStatusDto.getUserid())
                .email(orderStatusDto.getEmail())
                .amount(orderStatusDto.getAmount())
                .orderNo(orderStatusDto.getOrderNo())
                .resultStatus("결제 성공")
                .resultStatusCode("200")
                .transactionkey(orderStatusDto.getTransactionkey())
                .orderDate(LocalDateTime.now())
                .accessCode("1234")
                .paymentMethod("Credit Card")
                .paymentCode(201)
                .payPeriod(Period)
                .build();

        Order savedOrder = orderRepository.save(orderInfo);

        // 리스트가 필요한 경우, 저장된 주문을 포함한 리스트를 만들 수 있습니다.
        List<Order> searchResult = Collections.singletonList(savedOrder);
        return searchResult;
    }
}
