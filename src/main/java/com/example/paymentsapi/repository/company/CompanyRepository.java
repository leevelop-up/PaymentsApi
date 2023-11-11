package com.example.paymentsapi.repository.company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    String findBycompanyName(String company);
}
