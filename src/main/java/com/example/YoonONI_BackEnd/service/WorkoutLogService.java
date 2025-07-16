package com.example.YoonONI_BackEnd.service;


import com.example.YoonONI_BackEnd.mapper.WorkoutLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkoutLogService {
    private final WorkoutLogMapper workoutLogMapper;
}
