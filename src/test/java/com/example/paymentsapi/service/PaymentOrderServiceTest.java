package com.example.paymentsapi.service;

import com.example.paymentsapi.web.dto.OrderStatusDto;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentOrderServiceTest {

    @Test
    void orderInfoSave() {
        // Reflection을 이용해 OrderStatusDto 인스턴스 생성
        OrderStatusDto reflectedOrderStatusDto = createOrderStatusDtoUsingReflection();

        // 예상 결과 확인
        assertNotNull(reflectedOrderStatusDto);

        // 추가적인 검증 로직 추가
    }

    private OrderStatusDto createOrderStatusDtoUsingReflection() {
        try {
            // OrderStatusDto 클래스 가져오기
            Class<?> orderStatusDtoClass = Class.forName("com.example.paymentsapi.web.dto.OrderStatusDto");

            // 필요한 생성자 가져오기
            Constructor<?> constructor = orderStatusDtoClass.getDeclaredConstructor(
                    Integer.class, String.class, String.class, Integer.class,
                    Integer.class, String.class, String.class, String.class,
                    LocalDateTime.class, String.class, Integer.class, String.class, Integer.class
            );

            // 객체 생성
            Object instance = constructor.newInstance(
                    1, "testUser", "test@example.com", 100, 123, "success", "200",
                    "testTransactionKey", LocalDateTime.now(), "Credit Card", 30, "1234", 201
            );

            // OrderStatusDto로 캐스팅
            if (instance instanceof OrderStatusDto) {
                return (OrderStatusDto) instance;
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                 | InstantiationException | InvocationTargetException e) {
            e.printStackTrace(); // 예외 처리
        }

        return null;
    }
}
