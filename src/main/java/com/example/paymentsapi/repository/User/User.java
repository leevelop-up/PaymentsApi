package com.example.paymentsapi.repository.User;

import lombok.*;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_no")
    private String userNo;

    @Column(name="user_email")
    private String userId;
    @Column(name="user_name")
    private String userName;
    @Column(name="user_password")
    private String passWord;
    @Column(name="user_role")
    private String userRole;
    @Column(name="user_companycode")
    private Integer companyCode;
    @Column(name="user_joindate")
    private LocalDate joinDate;
    @Column(name="user_authstate")
    private String State;

}

