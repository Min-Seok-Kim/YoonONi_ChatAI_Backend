package com.example.YoonONI_BackEnd.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class BoardVo {
    private String title;
    private String userId;
    private String content;
    private LocalDateTime writeAt;
    private LocalDateTime updatedAt;
}
