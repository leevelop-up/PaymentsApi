package com.example.paymentsapi.repository.Card;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardInfo, Integer> {

    String findByCardNameEN(String paymentMethod);
}
