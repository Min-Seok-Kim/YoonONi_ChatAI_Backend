package com.example.YoonONI_BackEnd.vo;


import lombok.*;

import java.security.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    private String userId;
    private String password;
    private String email;
    private String gender;
    private LocalDate birthDate;
    private Float heightCm;
    private Float weightKg;
}
