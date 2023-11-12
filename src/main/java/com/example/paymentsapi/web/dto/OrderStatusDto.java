package com.example.paymentsapi.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
@Getter
@Setter
@Builder
public class OrderStatusDto {
    private Integer payNo;
    private String userid;
    private String email;
    private Integer amount;
    private Integer orderNo;
    private String resultStatus;
    private String resultStatusCode;
    private String transactionkey;
    private LocalDateTime orderDate;
    private String paymentMethod;
    private Integer payPeriod;
    private String accessCode;
    private Integer paymentCode;

}
