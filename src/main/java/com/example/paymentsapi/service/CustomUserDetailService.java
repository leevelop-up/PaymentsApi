package com.example.paymentsapi.service;

import com.example.paymentsapi.repository.User.User;
import com.example.paymentsapi.repository.User.UserRepository;
import com.example.paymentsapi.repository.company.Company;
import com.example.paymentsapi.repository.company.CompanyRepository;
import com.example.paymentsapi.repository.userDetails.CustomerUserDetails;
import com.example.paymentsapi.service.exception.NotFoundException;
import com.example.paymentsapi.web.dto.CompanyDto;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Primary
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByUserId(email).orElseThrow(()-> new NotFoundException("email에 해당하는 회원이 없습니다."));
        CustomerUserDetails customerUserDetails = CustomerUserDetails.builder()
                .userid(user.getUserId())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("role")))
                .password(user.getPassWord()).build();

        return customerUserDetails;
    }



    public CompanyDto getCompanyDtoByCode(Integer companyCode) {
        Company company = companyRepository.findByCompanyCode(companyCode);
        return new CompanyDto(
                company.getCompanyCode(),
                company.getCompanyName(),
                company.getJoinDate()
        );
    }
}
