package com.example.YoonONI_BackEnd.vo;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
public class MyPageVo {
    private String userId;
    private String email;
    private LocalDate birthDate;
    private Float heightCm;
    private Float weightKg;
}
