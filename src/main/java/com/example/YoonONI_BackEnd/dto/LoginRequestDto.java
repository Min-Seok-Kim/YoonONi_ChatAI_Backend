package com.example.YoonONI_BackEnd.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginRequestDto {
    private String userId;
    private String password;
}
