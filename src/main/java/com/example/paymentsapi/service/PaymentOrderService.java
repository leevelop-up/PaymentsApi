package com.example.paymentsapi.service;

import com.example.paymentsapi.controller.PaymentOrderController;
import com.example.paymentsapi.repository.Card.CardInfo;
import com.example.paymentsapi.repository.Card.CardRepository;
import com.example.paymentsapi.repository.order.Order;
import com.example.paymentsapi.repository.order.OrderRepository;
import com.example.paymentsapi.repository.serialkey.Serialkey;
import com.example.paymentsapi.repository.serialkey.SerialkeyRepository;
import com.example.paymentsapi.web.dto.OrderStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class PaymentOrderService {
    private final OrderRepository orderRepository;
    private final CardRepository cardRepository;
    private final SerialkeyRepository serialkeyRepository;
    private static final String SUCCESS_CODE = "200";
    private static final String INVALID_EMAIL_CODE = "4003";
    private static final String BELOW_ZERO_AMOUNT_CODE = "4002";
    private static final String DUPLICATED_ORDER_ID_CODE = "4091";
    private static final String INVALID_CARD_COMPANY_CODE = "4001";
    private static final String INVALID_SerialKey_CODE = "4004";


    private static final String SUCCESS_MSG = "결제성공";
    private static final String INVALID_EMAIL_MSG = "이메일 주소 형식에 맞지 않습니다.";
    private static final String BELOW_ZERO_AMOUNT_MSG = "금액은 0보다 커야 합니다.";
    private static final String DUPLICATED_ORDER_ID_MSG = "이미 승인 및 취소가 진행된 중복된 주문번호 입니다. 다른 주문번호로 진행해주세요.";
    private static final String INVALID_CARD_COMPANY_MSG = "유효하지 않은 카드사입니다.";
    private static final String INVALID_SerialKey_MSG = "API키가 유효하지 않습니다.";

    public boolean isEmailValid(String email) {

        if (email == null) {
            // null인 경우 유효하지 않다고 처리
            return false;
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public List<Order> OrderInfoSave(OrderStatusDto orderStatusDto) {

        System.out.println(orderStatusDto);
        Integer Period = orderStatusDto.getPayPeriod();
        String StateCode = SUCCESS_CODE;
        String StateMsg = SUCCESS_MSG;
        String Transactionkey = orderStatusDto.getTransactionkey();
        Integer accessCode = 200;
        String Email = orderStatusDto.getEmail();
        String CardName = orderStatusDto.getPaymentMethod();


        Serialkey InputKey = serialkeyRepository.findByserialKey(Transactionkey);
        //주문서 번호가 동일한지 체크
        List<Order> ChkOrderNo = orderRepository.findByOrderNo(orderStatusDto.getOrderNo());
        String cardInfoList = cardRepository.findCardInfoBycardNameKR(orderStatusDto.getPaymentMethod());

        if(InputKey == null){
            StateCode = INVALID_SerialKey_CODE;
            StateMsg = INVALID_SerialKey_MSG;
            accessCode = 401;
        }else if (Period == null) {
            Period = 0;
        }else if (!isEmailValid(Email)) {
            StateCode = INVALID_EMAIL_CODE;
            StateMsg = INVALID_EMAIL_MSG;
            accessCode = 401;
        }else if(orderStatusDto.getAmount() != null) {
            if (orderStatusDto.getAmount() <= 0) {
                StateCode = BELOW_ZERO_AMOUNT_CODE;
                StateMsg = BELOW_ZERO_AMOUNT_MSG;
                accessCode = 402;
            }
        }else if (ChkOrderNo != null) {
            StateCode = DUPLICATED_ORDER_ID_CODE;
            StateMsg = DUPLICATED_ORDER_ID_MSG;
            accessCode = 403;
        }else if (cardInfoList == null || cardInfoList.isEmpty() || orderStatusDto.getPaymentMethod().isEmpty()) {
            StateCode = INVALID_CARD_COMPANY_CODE;
            StateMsg = INVALID_CARD_COMPANY_MSG;
            accessCode = 404;
        }

        Order orderInfo = Order.builder()
                .userid(orderStatusDto.getUserid())
                .email(Email)
                .amount(orderStatusDto.getAmount())
                .orderNo(orderStatusDto.getOrderNo())
                .resultStatus(StateMsg)
                .resultStatusCode(StateCode)
                .transactionkey(Transactionkey)
                .orderDate(LocalDateTime.now())
                .paymentMethod(CardName)
                .paymentCode(orderStatusDto.getPaymentCode())
                .payPeriod(Period)
                .accessCode(String.valueOf(accessCode))
                .build();

        Order savedOrder = orderRepository.save(orderInfo);

        List<Order> searchResult = Collections.singletonList(savedOrder);

        return searchResult;
    }
}
