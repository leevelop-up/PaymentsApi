package com.example.paymentsapi.service;

import com.example.paymentsapi.repository.Card.CardInfo;
import com.example.paymentsapi.repository.Card.CardRepository;
import com.example.paymentsapi.repository.company.Company;
import com.example.paymentsapi.repository.company.CompanyRepository;
import com.example.paymentsapi.repository.serialkey.Serialkey;
import com.example.paymentsapi.repository.serialkey.SerialkeyRepository;
import com.example.paymentsapi.web.dto.CommonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ProduceKeyService {

    private final CompanyRepository companyRepository;
    private final SerialkeyRepository serialkeyRepository;
    private final CardRepository cardRepository;

    private static final String SUCCESS_MESSAGE = "성공";
    private static final String DUPLICATE_COMPANY_MESSAGE = "회사명이 중복되어있습니다.";
    private static final String KEY_ISSUE_SUCCESS_MESSAGE = "키발급 성공";
    private static final String KEY_ISSUE_FAIL_MESSAGE = "키발급 실패";

    public CommonDto insertCompany(String companyName) {
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

        if (!company.isPresent()) {
            String generatedKey = generateSecureRandomKey();
            Serialkey serialkey = Serialkey.builder()
                    .serialKey(generatedKey)
                    .companyCode(companyCode)
                    .build();
            serialkeyRepository.save(serialkey);

            return CommonDto.builder()
                    .message(KEY_ISSUE_SUCCESS_MESSAGE)
                    .status("success")
                    .data("")
                    .build();
        } else {
            return CommonDto.builder()
                    .message(KEY_ISSUE_FAIL_MESSAGE)
                    .status("fail")
                    .data("")
                    .build();
        }
    }

    public CommonDto insertCard(String cardName) {
        System.out.println("Received cardName: " + cardName);
        if (cardName == null) {
            return CommonDto.builder()
                    .message("카드 이름이 없습니다.") // Update the message for null cardName
                    .status("fail")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .data("")
                    .build();
        }

        String existingCompany = cardRepository.findCardInfoBycardNameKR(cardName);
        System.out.println("Received existingCompany: " + existingCompany);
        if (existingCompany != null) {
                return CommonDto.builder()
                        .message("카드 이름이 이미 결제 시스템에 존재합니다.")
                        .status("fail")
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .data("")
                        .build();

        }

        String cardNameEN = CardInfo.getEnglishName(cardName);

        CardInfo newCardName = CardInfo.builder()
                .cardNameEN(cardNameEN)
                .cardNameKR(cardName)
                .build();

        cardRepository.save(newCardName);

        return CommonDto.builder()
                .message(SUCCESS_MESSAGE)
                .status("success")
                .httpStatus(HttpStatus.OK)
                .data(cardName)
                .build();
    }

    private String generateSecureRandomKey() {
        String inputString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        List<Character> charList = new ArrayList<>();

        for (char c : inputString.toCharArray()) {
            charList.add(c);
        }

        Collections.shuffle(charList, new SecureRandom());

        StringBuilder resultStringBuilder = new StringBuilder();
        for (char c : charList) {
            resultStringBuilder.append(c);
        }

        return resultStringBuilder.toString();
    }
}
