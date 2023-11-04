package com.example.paymentsapi.service;

import com.example.paymentsapi.repository.User.User;
import com.example.paymentsapi.repository.User.UserRepository;
import com.example.paymentsapi.repository.userDetails.CustomerUserDetails;
import com.example.paymentsapi.service.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

class CustomUserDetailServiceTest {


    @Test
    void  loadUserByUsername() {
        String email = "asdf@naver.com";


        System.out.println(email);
    }
}