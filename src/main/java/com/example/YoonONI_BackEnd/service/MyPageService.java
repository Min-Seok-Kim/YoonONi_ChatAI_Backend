package com.example.YoonONI_BackEnd.service;


import com.example.YoonONI_BackEnd.config.RequestDataSet;
import com.example.YoonONI_BackEnd.mapper.MyPageMapper;
import com.example.YoonONI_BackEnd.vo.MyPagePwdVo;
import com.example.YoonONI_BackEnd.vo.MyPageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MyPageMapper myPageMapper;

    private final PasswordEncoder encoder;

    public ResponseEntity<?> selectMyPage(RequestDataSet requestDataSet) {
        String userId = requestDataSet.inGetString("userId");

        MyPageVo result = myPageMapper.selectMyPage(userId);
        return ResponseEntity.ok().body(result);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> changePassword(RequestDataSet requestDataSet) {
        String pwd = requestDataSet.inGetString("password");
        String userId = requestDataSet.inGetString("userId");

        String encodedPassword = encoder.encode(pwd);

        MyPagePwdVo myPagePwdVo = MyPagePwdVo.builder()
                .password(pwd)
                .newPassword(encodedPassword)
                .userId(userId)
                .build();

        myPageMapper.changePassword(myPagePwdVo);
        return ResponseEntity.ok().body("비밀번호가 변경되었습니다.");
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteId(RequestDataSet requestDataSet) {
        String userId = requestDataSet.inGetString("userId");

        myPageMapper.deleteId(userId);
        return ResponseEntity.ok().body("탈퇴되었습니다.");
    }
}
