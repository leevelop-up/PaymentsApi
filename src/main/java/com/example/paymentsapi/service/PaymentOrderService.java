package com.example.paymentsapi.service;

import com.example.paymentsapi.controller.PaymentOrderController;
import com.example.paymentsapi.repository.Card.CardInfo;
import com.example.paymentsapi.repository.Card.CardRepository;
import com.example.paymentsapi.repository.order.Order;
import com.example.paymentsapi.repository.order.OrderRepository;
import com.example.paymentsapi.repository.serialkey.Serialkey;
import com.example.paymentsapi.repository.serialkey.SerialkeyRepository;
import com.example.paymentsapi.web.dto.OrderStatusDto;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class PaymentOrderService {
    private final OrderRepository orderRepository;
    private final CardRepository cardRepository;
    private final SerialkeyRepository serialkeyRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());
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
        Set<String> uniqueOrderNumbers = new HashSet<>();
        boolean hasDuplicates = false;
        for (Order order : ChkOrderNo) {
            Integer orderNumber = order.getOrderNo(); // Replace with the actual method to get the order number
            if (!uniqueOrderNumbers.add(String.valueOf(orderNumber))) {
                hasDuplicates = true;
                System.out.println("Duplicate order number found: " + orderNumber);
            }
        }
        if (!hasDuplicates || orderStatusDto.getOrderNo() == null) {
            System.out.println("No duplicate order numbers found.");
            hasDuplicates = false;
        }
        if (Period == null) {
            Period = 0;
        }
        if(InputKey == null){
            StateCode = INVALID_SerialKey_CODE;
            StateMsg = INVALID_SerialKey_MSG;
            accessCode = 401;
        }else if (!isEmailValid(Email)) {
            StateCode = INVALID_EMAIL_CODE;
            StateMsg = INVALID_EMAIL_MSG;
            accessCode = 405;
        }else if(orderStatusDto.getAmount() != null) {
            if (orderStatusDto.getAmount() <= 0) {
                StateCode = BELOW_ZERO_AMOUNT_CODE;
                StateMsg = BELOW_ZERO_AMOUNT_MSG;
                accessCode = 402;
            }
        }else if (hasDuplicates == false || orderStatusDto.getOrderNo() == null) {
            StateCode = DUPLICATED_ORDER_ID_CODE;
            StateMsg = DUPLICATED_ORDER_ID_MSG;
            accessCode = 403;
        }else if (cardInfoList == null || cardInfoList.isEmpty() || orderStatusDto.getPaymentMethod().isEmpty()) {
            StateCode = INVALID_CARD_COMPANY_CODE;
            StateMsg = INVALID_CARD_COMPANY_MSG;
            accessCode = 404;
        }

        if(accessCode != 200){
            log.error(accessCode+"/"+StateMsg+"/"+orderStatusDto.getUserid()+"/"+orderStatusDto.getOrderNo());
        }
        Order orderInfo = Order.builder()
                .userid(orderStatusDto.getUserid())
                .email(Email)
                .amount(orderStatusDto.getAmount())
                .orderNo(orderStatusDto.getOrderNo())
                .resultStatus(StateMsg)
                .resultStatusCode(StateCode)
                .transactionkey(Transactionkey)
                .orderDate(LocalDate.now())
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
