package com.example.paymentsapi.repository.serialkey;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerialkeyRepository extends JpaRepository<Serialkey,Integer> {

    List<Serialkey> findBycompanyCode(Integer companyCode);

    Serialkey findByserialKey(String transactionkey);
}
