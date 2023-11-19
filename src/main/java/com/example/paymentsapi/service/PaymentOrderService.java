package com.example.paymentsapi.service;

import com.example.paymentsapi.controller.PaymentOrderController;
import com.example.paymentsapi.repository.Card.CardRepository;
import com.example.paymentsapi.repository.order.Order;
import com.example.paymentsapi.repository.order.OrderRepository;
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

    public boolean isEmailValid(String email) {
        // 이메일 형식을 확인하는 정규표현식
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public List<Order> OrderInfoSave(OrderStatusDto orderStatusDto) {

        Integer Period = orderStatusDto.getPayPeriod(); //할부 기간
        String StateCode = "200";
        String StateMsg = "결제성공";
        String Transactionkey = "";
        String Email = orderStatusDto.getEmail();
        String CardName = orderStatusDto.getPaymentMethod();
        if(Period == null){
            Period = 0;
        }
        /*
        * 실제 결제 시에는 해당 부분에서 카드사와 연결하여 해당 금액 및 정보를 비교하고 리턴값을 받음
        * 하지만 개인 프로젝트 상에서 카드사 연결이 불가하여 임의 디폴드값을 부여하고 메세지 노출
        *
        * */
        
        if (!isEmailValid(Email)) {
            //INVALID_EMAIL
            StateCode = "4003";
            StateMsg = "이메일 주소 형식에 맞지 않습니다.";
        }
        if(orderStatusDto.getAmount() <= 0){
            //BELOW_ZERO_AMOUNT
            StateCode = "4002";
            StateMsg = "금액은 0보다 커야 합니다.";
        }

        Boolean ChkOrderNo = orderRepository.findByOrderNo(orderStatusDto.getOrderNo());
        if(ChkOrderNo != null){
            //DUPLICATED_ORDER_ID
            StateCode = "4091";
            StateMsg = "이미 승인 및 취소가 진행된 중복된 주문번호 입니다. 다른 주문번호로 진행해주세요.";
        }

        if(cardRepository.findByCardNameEN(orderStatusDto.getPaymentMethod()) == "" || orderStatusDto.getPaymentMethod() == ""){
            //INVALID_CARD_COMPANY
            StateCode = "4001";
            StateMsg = "유효하지 않은 카드사입니다.";
        }

        //결제 실패저장은 결제 카드관련된 실패일 경우에만 저장(한도초과, 정지카드 등)
        //결제 정보 등록 및 리턴값 부여
        Order orderInfo = Order.builder()
                .userid(orderStatusDto.getUserid()) // 구매자 아이디
                .email(Email) //구매자 이메일
                .amount(orderStatusDto.getAmount()) //구매금액
                .orderNo(orderStatusDto.getOrderNo()) //주문서번호(고유번호)
                .resultStatus(StateMsg) // 결제 상태 내용
                .resultStatusCode(StateCode) // 결제 상태 코드
                .transactionkey(Transactionkey) //결제 고유 식별자
                .orderDate(LocalDateTime.now()) //결제 시간
                .paymentMethod(CardName) // 카드사명
                .paymentCode(orderStatusDto.getPaymentCode()) //카드사 코드
                .payPeriod(Period) //할부 기간
                .build();

        Order savedOrder = orderRepository.save(orderInfo);

        // 리스트가 필요한 경우, 저장된 주문을 포함한 리스트를 만들 수 있습니다.
        List<Order> searchResult = Collections.singletonList(savedOrder);

        return searchResult;
    }
}
