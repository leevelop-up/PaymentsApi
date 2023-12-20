package com.example.paymentsapi.service;

import com.example.paymentsapi.repository.User.User;
import com.example.paymentsapi.repository.User.UserRepository;
import com.example.paymentsapi.repository.company.Company;
import com.example.paymentsapi.repository.company.CompanyRepository;
import com.example.paymentsapi.web.dto.CommonDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    public CommonDto companyCheck(Integer companyNo) {

        Optional<Company> companyChecking = companyRepository.findBycompanyCode(companyNo);
        //해당 값이 있으면 성공 없으면 실패(실패시 회사 명 입력 폼으로
        if (companyChecking.isPresent()) {
            return CommonDto.builder()
                    .message(companyChecking.get().getCompanyName())
                    .status("success")
                    .httpStatus(HttpStatus.OK)
                    .data("success")
                    .build();
        }else{
            return CommonDto.builder()
                    .message("none")
                    .status("fail")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .data("none")
                    .build();
        }
    }


    public CommonDto join(User user) {
        String userId= user.getUserId();
        String userName= user.getUserName();
        LocalDateTime now = LocalDateTime.now();
        String StateMsg = "성공";

        if(userRepository.existsByuserId(userId)){
            StateMsg = "동일한 아이디가 있습니다.";
        }else{
            User userInfo =User.builder()
                    .userId(userId)
                    .companyCode(user.getCompanyCode())
                    .userName(userName)
                    .userRole("USER")
                    .joinDate(now)
                    .build();
            userRepository.save(userInfo);
        }


        return CommonDto.builder()
                .message(StateMsg)
                .status("success")
                .httpStatus(HttpStatus.OK)
                .data("none")
                .build();

    }
}
