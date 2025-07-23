package com.example.YoonONI_BackEnd.controller;


import com.example.YoonONI_BackEnd.config.RequestDataSet;
import com.example.YoonONI_BackEnd.service.WorkoutLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/select/all")
    public ResponseEntity<?> select(RequestDataSet requestDataSet) {
        return workoutLogService.selectLogs(requestDataSet);
    }

    @GetMapping("/select")
    public ResponseEntity<?> selectAll(RequestDataSet requestDataSet) {
        return workoutLogService.selectLog(requestDataSet);
    }
}
