package com.example.paymentsapi.controller;

import com.example.paymentsapi.repository.User.Login;
import com.example.paymentsapi.repository.User.User;
import com.example.paymentsapi.service.AuthService;
import com.example.paymentsapi.service.exception.NotAcceptException;
import com.example.paymentsapi.web.dto.CommonDto;
import com.example.paymentsapi.web.dto.ResultDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/companyCheck", consumes = "application/json")
    public ResponseEntity<ResultDto<Void>> CompanyCheck(@RequestBody Integer CompanyNo){
        System.out.println("회사번호"+CompanyNo);
        CommonDto resultCompanyCheck = authService.companyCheck(CompanyNo);
        ResultDto<Void> result  = ResultDto.in(resultCompanyCheck.getStatus(), resultCompanyCheck.getMessage());
        return ResponseEntity.status(resultCompanyCheck.getHttpStatus()).body(result);
    }


    @ApiOperation("회원 가입")
    @PostMapping("/join")
    public ResponseEntity<ResultDto<Void>> JoinMember(@RequestBody User user){
        System.out.println(user.getUserId());
        CommonDto joinMember = authService.join(user);
        ResultDto<Void> result  = ResultDto.in(joinMember.getStatus(), joinMember.getMessage());
        return ResponseEntity.status(joinMember.getHttpStatus()).body(result);
    }

    @ApiOperation("로그인")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login, HttpServletResponse httpServletResponse) throws NotAcceptException {

        //String token = authService.login(loginRequest);
        //httpServletResponse.setHeader("X-AUTH-TOKEN",token);
        System.out.println(login.getUserid());
        return authService.login(login,httpServletResponse);
    }



}
