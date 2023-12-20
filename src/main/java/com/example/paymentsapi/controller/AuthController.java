package com.example.paymentsapi.controller;

import com.example.paymentsapi.repository.User.User;
import com.example.paymentsapi.repository.company.Company;
import com.example.paymentsapi.service.AuthService;
import com.example.paymentsapi.web.dto.CommonDto;
import com.example.paymentsapi.web.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/companyCheck")
    public ResponseEntity<ResultDto<Void>> CompanyCheck(Integer CompanyNo){

        CommonDto resultCompanyCheck = authService.companyCheck(CompanyNo);
        ResultDto<Void> result  = ResultDto.in(resultCompanyCheck.getStatus(), resultCompanyCheck.getMessage());
        return ResponseEntity.status(resultCompanyCheck.getHttpStatus()).body(result);
    }


    @PostMapping("/join")
    public ResponseEntity<ResultDto<Void>> JoinMember(@RequestBody User company){
        CommonDto joinMember = authService.join(company);

        return
    }



}
