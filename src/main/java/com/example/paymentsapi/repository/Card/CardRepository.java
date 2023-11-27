package com.example.paymentsapi.repository.Card;

import com.example.paymentsapi.repository.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<CardInfo, Integer> {

    String findCardInfoBycardNameEN(String paymentMethod);

    String findCardInfoBycardNameKR(String cardName);
}
