package com.example.YoonONI_BackEnd.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;


@Getter
@Setter
@ToString
@Builder
public class WorkoutSetVo {
    private long workoutLogId;
    private String exerciseName;
    private String targetMuscle;
    private int setNumber;
    private int reps;
    private BigDecimal weight;
}
