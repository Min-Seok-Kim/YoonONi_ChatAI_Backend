package com.example.YoonONI_BackEnd.mapper;


import com.example.YoonONI_BackEnd.vo.GoalVo;
import com.example.YoonONI_BackEnd.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void insertUser(UserVo user);
    UserVo selectUser(String userId);
    boolean existsByUserId(String userId);
    void saveGoal(GoalVo goal);
}