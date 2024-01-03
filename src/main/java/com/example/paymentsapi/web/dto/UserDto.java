package com.example.paymentsapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@Data
@AllArgsConstructor
public class UserDto {

    private String userId;

    private String userName;

    private String userRole;

    private Integer companyCode;

    private LocalDate joinDate;
    private String State;

    private CompanyDto companyDto;  // 기존에 추가한 CompanyDto 필드
}
