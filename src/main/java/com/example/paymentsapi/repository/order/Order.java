package com.example.paymentsapi.repository.order;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="paymentOrder")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="payment_payno")
    private Integer payNo;
    @Column(name="payment_userid")
    private String userid;
    @Column(name="payment_email")
    private String email;
    @Column(name="payment_amount")
    private Integer amount;
    @Column(name="payment_orderno")
    private Integer orderNo;
    @Column(name="payment_status")
    private String resultStatus;
    @Column(name="payment_statuscode")
    private String resultStatusCode;
    @Column(name="payment_transactionkey")
    private String transactionkey;
    @Column(name="payment_orderdate")
    private LocalDateTime orderDate;
    @Column(name="payment_paymentmathod")
    private String paymentMethod;
    @Column(name="payment_payperiod")
    private Integer payPeriod;
    @Column(name="payment_accesscode")
    private String accessCode;
    @Column(name="payment_paymentcode")
    private Integer paymentCode;
















}
