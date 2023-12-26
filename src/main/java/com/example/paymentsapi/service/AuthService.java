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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public static List<Integer> generateRandomNumbers(int count) {
        // 1에서 9까지의 숫자를 담은 리스트 생성
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }

        // 리스트를 섞음
        Collections.shuffle(numbers);

        // count 개수만큼 숫자를 선택
        List<Integer> selectedNumbers = numbers.subList(0, count);

        return selectedNumbers;
    }

    public CommonDto join(User user) {
        String userId= user.getUserId();
        String userName= user.getUserName();
        LocalDateTime now = LocalDateTime.now();
        String StateMsg = "성공";
        List<Integer> randomValues = generateRandomNumbers(5);
        if(userRepository.existsByuserId(userId)){
            StateMsg = "동일한 아이디가 있습니다.";
        }else{
            User userInfo =User.builder()
                    .userId(userId)
                    .companyCode(user.getCompanyCode())
                    .passWord(randomValues.toString())
                    .userName(userName)
                    .userRole("USER")
                    .joinDate(now)
                    .build();
            userRepository.save(userInfo);
            StateMsg = "임시 비밀번호"+randomValues.toString();
        }


        return CommonDto.builder()
                .message(StateMsg)
                .status("success")
                .httpStatus(HttpStatus.OK)
                .data("none")
                .build();

    }
}
