package com.example.paymentsapi.repository.serialkey;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="serialkey")
public class Serialkey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="sk_companycode")
    private Integer companyCode;

    @Column(name="sk_serialkey")
    private String serialKey;





}
