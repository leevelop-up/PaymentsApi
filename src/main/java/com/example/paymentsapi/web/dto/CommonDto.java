package com.example.paymentsapi.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class CommonDto<Data> {
    private String status;
    private String message;
    private HttpStatus httpStatus;
    private Data data;
}
