package com.example.YoonONI_BackEnd.mapper;


import com.example.YoonONI_BackEnd.vo.WorkoutLogVo;
import com.example.YoonONI_BackEnd.vo.WorkoutSetVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkoutLogMapper {
    void saveLog(WorkoutLogVo workoutLogVo);
    void saveSets(WorkoutSetVo workoutSetVo);
    int selectLogId(WorkoutLogVo workoutLogVo);
}
