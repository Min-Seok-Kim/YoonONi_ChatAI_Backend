package com.example.YoonONI_BackEnd.controller;


import com.example.YoonONI_BackEnd.config.RequestDataSet;
import com.example.YoonONI_BackEnd.service.WorkoutLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
public class WorkoutLogController {
    private final WorkoutLogService workoutLogService;

    @PostMapping("/save")
    public ResponseEntity<?> save(RequestDataSet requestDataSet) {
        return workoutLogService.logSave(requestDataSet);
    }
}
