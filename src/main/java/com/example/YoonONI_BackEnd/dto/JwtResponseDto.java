package com.example.YoonONI_BackEnd.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class JwtResponseDto {
    private String token;
    private String type = "Bearer";
    private String userId;

    public JwtResponseDto(String token, String userId) {
        this.token = token;
        this.userId = userId;
    }
}
