package com.example.YoonONI_BackEnd.service;


import com.example.YoonONI_BackEnd.config.RequestDataSet;
import com.example.YoonONI_BackEnd.config.util.DateUtil;
import com.example.YoonONI_BackEnd.dto.WorkoutLogDto;
import com.example.YoonONI_BackEnd.mapper.WorkoutLogMapper;
import com.example.YoonONI_BackEnd.vo.WorkoutLogVo;
import com.example.YoonONI_BackEnd.vo.WorkoutSetVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WorkoutLogService {
    private final WorkoutLogMapper workoutLogMapper;

    public ResponseEntity<?> logSave(RequestDataSet requestDataSet) {
        String workoutDate = requestDataSet.inGetString("workoutDate");
        String memo = requestDataSet.inGetString("memo");

        WorkoutLogVo workoutLogVo = WorkoutLogVo.builder()
                .workoutDate(DateUtil.getStringToDate(workoutDate))
                .memo(memo)
                .userId(requestDataSet.inGetString("userId"))
                .build();

        workoutLogMapper.saveLog(workoutLogVo);
        int logId = workoutLogMapper.selectLogId(workoutLogVo);

        List<Map<String, Object>> sets = (List<Map<String, Object>>) requestDataSet.getInput().get("sets");

        for (Map<String, Object> set : sets) {
            String exerciseName = set.get("exerciseName").toString();
            String targetMuscle = set.get("targetMuscle").toString();
            int setNumber = Integer.parseInt(set.get("setNumber").toString());
            int reps = Integer.parseInt(set.get("reps").toString());
            BigDecimal weight = new BigDecimal (set.get("weight").toString());

            WorkoutSetVo workoutSetVo = WorkoutSetVo.builder()
                    .workoutLogId(logId)
                    .exerciseName(exerciseName)
                    .reps(reps)
                    .setNumber(setNumber)
                    .targetMuscle(targetMuscle)
                    .weight(weight)
                    .build();
            workoutLogMapper.saveSets(workoutSetVo);
        }

        return ResponseEntity.ok().body("저장 성공");
    }

    public ResponseEntity<?> selectLogs(RequestDataSet requestDataSet) {
        Map<String, LocalDate> currentMonthRange = DateUtil.getCurrentMonthRange();

        LocalDate sDate = currentMonthRange.get("startDate");
        LocalDate eDate = currentMonthRange.get("endDate");

        String userId = requestDataSet.inGetString("userId");

        List<WorkoutLogDto> logs = workoutLogMapper.selectLogs(sDate, eDate, userId);

        System.out.println(logs);

        return ResponseEntity.ok().body(logs);
    }

    public ResponseEntity<?> selectLog(RequestDataSet requestDataSet) {
        String workoutDate = requestDataSet.inGetString("workout_date");
        String userId = requestDataSet.inGetString("userId");

        List<WorkoutLogDto> log = workoutLogMapper.selectLog(workoutDate, userId);
        return ResponseEntity.ok().body(log);
    }

    public ResponseEntity<?> countLog(RequestDataSet requestDataSet) {
        String userId = requestDataSet.inGetString("userId");

        int logs = workoutLogMapper.countLog(userId);

        return ResponseEntity.ok().body(logs);
    }
}
