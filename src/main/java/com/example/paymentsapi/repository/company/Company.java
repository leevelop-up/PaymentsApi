package com.example.paymentsapi.repository.company;


import lombok.*;

import javax.persistence.*;
import java.lang.annotation.Target;
import java.text.DateFormat;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="companydata")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="cd_companyname")
    private String companyName;

    @Column(name="cd_joindate")
    private LocalDateTime joinDate;

}
