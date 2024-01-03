package com.example.paymentsapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class CompanyDto {
    private Integer companyCode;
    private String companyName;
    private LocalDateTime joinDate;
}
