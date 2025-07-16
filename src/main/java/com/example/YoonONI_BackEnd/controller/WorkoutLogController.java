package com.example.YoonONI_BackEnd.controller;


import com.example.YoonONI_BackEnd.service.WorkoutLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
public class WorkoutLogController {
    private final WorkoutLogService workoutLogService;
}
