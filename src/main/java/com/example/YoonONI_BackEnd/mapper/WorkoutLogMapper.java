package com.example.YoonONI_BackEnd.mapper;


import com.example.YoonONI_BackEnd.dto.WorkoutLogDto;
import com.example.YoonONI_BackEnd.vo.WorkoutLogVo;
import com.example.YoonONI_BackEnd.vo.WorkoutSetVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface WorkoutLogMapper {
    void saveLog(WorkoutLogVo workoutLogVo);
    void saveSets(WorkoutSetVo workoutSetVo);
    int selectLogId(WorkoutLogVo workoutLogVo);
    List<WorkoutLogDto> selectLogs(@Param("sDate") LocalDate sDate, @Param("eDate")LocalDate eDate, @Param("userId")String userId);
    List<WorkoutLogDto> selectLog(@Param("workoutDate") String workoutDate, @Param("userId")String userId);
}
