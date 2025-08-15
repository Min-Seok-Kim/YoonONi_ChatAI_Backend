package com.example.YoonONI_BackEnd.vo;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class GoalVo {
    private String userId;
    private int year;
    private int week;
    private int weeklyGoal;
}
