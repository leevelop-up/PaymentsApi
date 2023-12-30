package com.example.paymentsapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Builder
@Getter
@AllArgsConstructor
public class UserDto {

    private String userId;

    private String userName;

    private String userRole;

    private Integer companyCode;

    private LocalDate joinDate;
    private String State;
}
