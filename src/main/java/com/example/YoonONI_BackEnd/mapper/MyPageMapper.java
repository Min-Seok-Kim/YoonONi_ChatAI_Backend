package com.example.YoonONI_BackEnd.mapper;


import com.example.YoonONI_BackEnd.vo.MyPagePwdVo;
import com.example.YoonONI_BackEnd.vo.MyPageVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {
    MyPageVo selectMyPage(String userId);

    void changePassword(MyPagePwdVo myPagePwdVo);

    void deleteId(String userId);
}
