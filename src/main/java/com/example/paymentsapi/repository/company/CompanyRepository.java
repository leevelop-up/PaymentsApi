package com.example.paymentsapi.repository.company;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Optional<Company> findBycompanyName(String companyName);

    Optional<Company> findBycompanyCode(Integer companyNo);
}
