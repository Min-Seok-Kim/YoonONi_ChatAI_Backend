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
public class WorkoutLogVo {
    private String userId;
    private LocalDate workoutDate;
    private String memo;
    private LocalDateTime createdAt;
}
