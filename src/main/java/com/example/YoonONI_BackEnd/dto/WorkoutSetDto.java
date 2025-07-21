package com.example.YoonONI_BackEnd.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutSetDto {
    private String exerciseName;
    private String targetMuscle;
    private int setNumber;
    private int reps;
    private double weight;
}
