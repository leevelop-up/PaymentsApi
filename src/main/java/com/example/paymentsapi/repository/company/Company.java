package com.example.paymentsapi.repository.company;


import com.example.paymentsapi.repository.Card.CardInfo;
import lombok.*;

import javax.persistence.*;
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
    @Column(name="cd_companyno")
    private Integer companyCode;

    @Column(name="cd_companyname")
    private String companyName;

    @Column(name="cd_joindate")
    private LocalDateTime joinDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_no", referencedColumnName = "card_no")
    private CardInfo cardInfo;

}
