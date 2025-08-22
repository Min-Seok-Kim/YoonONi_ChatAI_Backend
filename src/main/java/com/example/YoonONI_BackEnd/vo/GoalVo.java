package com.example.YoonONI_BackEnd.vo;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
public class GoalVo {
    private String userId;
    private LocalDate weekStartDate;
    private LocalDate weekEndDate;
    private int goalCount;
}
