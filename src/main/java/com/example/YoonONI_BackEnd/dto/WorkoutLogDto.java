package com.example.YoonONI_BackEnd.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class WorkoutLogDto {
    private LocalDate workoutDate;
    private String memo;
    private List<WorkoutSetDto> sets;
}
