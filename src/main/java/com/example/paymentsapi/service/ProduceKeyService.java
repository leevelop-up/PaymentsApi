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
import java.util.*;

@RequiredArgsConstructor
@Service
public class ProduceKeyService {

    private final CompanyRepository companyRepository;
    private final SerialkeyRepository serialkeyRepository;

    private static final String SUCCESS_MESSAGE = "성공";
    private static final String DUPLICATE_COMPANY_MESSAGE = "회사명이 중복되어있습니다.";
    private static final String KEY_ISSUE_SUCCESS_MESSAGE = "키발급 성공";
    private static final String KEY_ISSUE_FAIL_MESSAGE = "키발급 실패";

    public CommonDto insertCompany(String companyName) {
        // 회사명 중복 체크
        Optional<Company> existingCompany = companyRepository.findByCompanyName(companyName);

        if (existingCompany.isPresent()) {
            return CommonDto.builder()
                    .message(DUPLICATE_COMPANY_MESSAGE)
                    .status("fail")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .data("")
                    .build();
        }

        Company newCompany = Company.builder()
                .companyName(companyName)
                .joinDate(LocalDateTime.now())
                .build();

        companyRepository.save(newCompany);

        return CommonDto.builder()
                .message(SUCCESS_MESSAGE)
                .status("success")
                .httpStatus(HttpStatus.OK)
                .data("")
                .build();
    }

    public CommonDto insertKey(Integer companyCode) {
        Optional<Company> company = companyRepository.findById(companyCode);

        String message;
        String status;

        if (!company.isPresent()) {
            // 키 발급
            String generatedKey = generateRandomKey();
            Serialkey serialkey = Serialkey.builder()
                    .serialKey(generatedKey)
                    .companyCode(companyCode)
                    .build();
            serialkeyRepository.save(serialkey);

            message = KEY_ISSUE_SUCCESS_MESSAGE;
            status = "success";
        } else {
            // 해당 코드에 키값이 있는 경우 fail
            message = KEY_ISSUE_FAIL_MESSAGE;
            status = "fail";
        }

        return CommonDto.builder()
                .message(message)
                .status(status)
                .data("")
                .build();
    }

    private String generateRandomKey() {
        String inputString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        List<Character> charList = new ArrayList<>();

        for (char c : inputString.toCharArray()) {
            charList.add(c);
        }

        Collections.shuffle(charList);

        StringBuilder resultStringBuilder = new StringBuilder();
        for (char c : charList) {
            resultStringBuilder.append(c);
        }

        return resultStringBuilder.toString();
    }
}
