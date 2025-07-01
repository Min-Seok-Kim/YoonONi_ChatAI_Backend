package com.example.YoonONI_BackEnd.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserRequestDto {
    private String userId;
    private String password;
    private String email;
    private String gender;
    private LocalDate birthDate;
    private Float heightCm;
    private Float weightKg;
}
