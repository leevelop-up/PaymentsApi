package com.example.paymentsapi.repository.User;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
public class Login {
    //        @NotEmpty(message = "이메일은 필수 입력값입니다.")
//        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String userid;

    //@NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(userid, password);
    }
}
