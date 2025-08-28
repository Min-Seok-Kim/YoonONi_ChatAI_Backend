package com.example.YoonONI_BackEnd.service;


import com.example.YoonONI_BackEnd.config.RequestDataSet;
import com.example.YoonONI_BackEnd.mapper.MyPageMapper;
import com.example.YoonONI_BackEnd.vo.MyPageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MyPageMapper myPageMapper;

    public ResponseEntity<?> selectMyPage(RequestDataSet requestDataSet) {
        String userId = requestDataSet.inGetString("userId");

        MyPageVo result = myPageMapper.selectMyPage(userId);

        return ResponseEntity.ok().body(result);
    }
}
