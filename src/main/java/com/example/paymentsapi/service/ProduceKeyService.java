package com.example.paymentsapi.service;

import com.example.paymentsapi.repository.company.Company;
import com.example.paymentsapi.repository.company.CompanyRepository;
import com.example.paymentsapi.repository.serialkey.Serialkey;
import com.example.paymentsapi.repository.serialkey.SerialkeyRepository;
import com.example.paymentsapi.web.dto.CommonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProduceKeyService {

    private final CompanyRepository companyRepository;
    private final SerialkeyRepository serialkeyRepository;
    public CommonDto insertCompany(String company) {

        //회사명 중복 체크
       String dupCompany=  companyRepository.findBycompanyName(company);

       if(!dupCompany.isEmpty()){
           return CommonDto.builder()
                   .message("회사명이 중복되어있습니다.")
                   .status("fail")
                   .httpStatus(HttpStatus.valueOf("fail"))
                   .data("")
                   .build();

       }
        Company companyInfo = Company.builder()
                .companyName(company)
                .joinDate(LocalDateTime.now())
                .build();

        companyRepository.save(companyInfo);

        return CommonDto.builder()
                .message("성공")
                .status("success")
                .httpStatus(HttpStatus.OK)
                .data("")
                .build();

    }

    public CommonDto insertKey(Integer companyCode) {
        String message = "";
        String status = "";
        Optional<Company> company = companyRepository.findById(companyCode);

        if(company == null){
        //키가 없는경우 키 발급
            String inputString = "ABCDEFGHIJKLMNOPQRSTUVWXYMZ1234567890";

            // 문자열을 문자 리스트로 변환
            List<Character> charList = new ArrayList<>();
            for (char c : inputString.toCharArray()) {
                charList.add(c);
            }
            // 리스트를 랜덤하게 섞음
            Collections.shuffle(charList);
            // 섞인 리스트를 다시 문자열로 변환
            StringBuilder resultStringBuilder = new StringBuilder();
            for (char c : charList) {
                resultStringBuilder.append(c);
            }

            String resultString = resultStringBuilder.toString();

            Serialkey serialkey = Serialkey.builder()
                    .serialKey(resultString)
                    .companyCode(companyCode)
                    .build();
            serialkeyRepository.save(serialkey);
            message = "키발급 성공";
            status = "success";
        }else{
        //해당 코드에 키값이 있는경우 fail
            message = "키발급 실패";
            status = "fail";
        }

        return CommonDto.builder()
                .message(message)
                .status(status)
                .data("")
                .build();
    }
}
