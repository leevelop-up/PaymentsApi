package com.example.paymentsapi.repository.Card;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
    private String cardNameKR;

    @Column(name="card_egname")
    private String cardNameEN;
    private static final Map<String, String> cardNameMapping = new HashMap<>();

    static {
        cardNameMapping.put("하나", "Hana");
        cardNameMapping.put("현대", "Hyundai");
        cardNameMapping.put("국민", "KB");
        cardNameMapping.put("신한", "Shinhan");
        cardNameMapping.put("삼성", "Samsung");
        cardNameMapping.put("카카오페이", "Kakaopay");
        cardNameMapping.put("네이버페이", "NaverPay");
        cardNameMapping.put("우리", "Woori");

    }

    public static String getEnglishName(String koreanName) {
        return cardNameMapping.getOrDefault(koreanName, "Unknown");
    }
}
