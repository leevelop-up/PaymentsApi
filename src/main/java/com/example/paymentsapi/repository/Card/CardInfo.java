package com.example.paymentsapi.repository.Card;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="carddata")
public class CardInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="card_no")
    private Integer companyCode;

    @Column(name="card_krname")
    private String CardNameKR;

    @Column(name="card_egname")
    private String CardNameEN;

}
