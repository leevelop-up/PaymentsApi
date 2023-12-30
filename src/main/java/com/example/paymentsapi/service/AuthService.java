package com.example.paymentsapi.service;

import com.example.paymentsapi.config.JwtTokenProvider;
import com.example.paymentsapi.repository.User.Login;
import com.example.paymentsapi.repository.User.User;
import com.example.paymentsapi.repository.User.UserRepository;
import com.example.paymentsapi.repository.company.Company;
import com.example.paymentsapi.repository.company.CompanyRepository;
import com.example.paymentsapi.service.exception.NotAcceptException;
import com.example.paymentsapi.service.exception.NotFoundException;
import com.example.paymentsapi.web.dto.CommonDto;
import com.example.paymentsapi.web.dto.Response;
import com.example.paymentsapi.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final Response response;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;
    private static final String BEARER_TYPE = "Bearer ";

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
        } else {
            return CommonDto.builder()
                    .message("none")
                    .status("fail")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .data("none")
                    .build();
        }
    }

    public static String generateRandomNumbers(int count) {
        // 1에서 9까지의 숫자를 담은 리스트 생성
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }

        // 리스트를 섞음
        Collections.shuffle(numbers);

        // count 개수만큼 숫자를 선택
        List<Integer> selectedNumbers = numbers.subList(0, count);


        StringBuilder result = new StringBuilder();
        for (Integer number : selectedNumbers) {
            result.append(number);
        }
        return result.toString();
    }

    public CommonDto join(User user) {
        String userId = user.getUserId();
        String userName = user.getUserName();
        LocalDate now = LocalDate.now();
        String StateMsg = "성공";
        String Status ="success";
        String randomValues = generateRandomNumbers(5);

        if (userRepository.existsByuserId(userId)) {
            Status = "fail";
            StateMsg = "동일한 아이디가 있습니다.";
        } else {
            User userInfo = User.builder()
                    .userId(userId)
                    .companyCode(user.getCompanyCode())
                    .passWord(randomValues)
                    .userName(userName)
                    .State("N")
                    .userRole("USER")
                    .joinDate(now)
                    .build();
            userRepository.save(userInfo);
            StateMsg = "임시 비밀번호"+randomValues;
            Status = "success";
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(userId);
            simpleMailMessage.setSubject("임시비밀번호 발급");
            simpleMailMessage.setFrom("merong0075@gmail.com");
            simpleMailMessage.setText("임시비밀번호");
            javaMailSender.send(simpleMailMessage);
        }


        return CommonDto.builder()
                .message(StateMsg)
                .status(Status)
                .httpStatus(HttpStatus.OK)
                .data("none")
                .build();
    }

    public ResponseEntity<?> login(Login login, HttpServletResponse httpServletResponse) throws NotAcceptException {
        String userid = login.getUserid();
        String password = login.getPassword();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userid,password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            userRepository.findByUserId(userid)
                    .orElseThrow(()->new NotFoundException("user가 없습니다."));

            //TODO:토큰 비교후 아이디 추출 하는 함수 하나로 만들기
            UserResponseDto.TokenInfo tokenInfo = jwtTokenProvider.createToken(authentication);
            httpServletResponse.addHeader("Authorization","Bearer "+tokenInfo.getAccessToken());

//            redisTemplate.opsForValue()
//                    .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
            return response.success(tokenInfo, "로그인에 성공했습니다.", HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            throw new NotAcceptException("로그인에 실패했습니다.");
        }
    }
}