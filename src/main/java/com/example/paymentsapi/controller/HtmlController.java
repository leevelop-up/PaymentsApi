package com.example.paymentsapi.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlController {
    @ApiOperation("테스트메인페이지")
    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/main")
    public String adminmain(){
        return "adminmain";
    }


}
