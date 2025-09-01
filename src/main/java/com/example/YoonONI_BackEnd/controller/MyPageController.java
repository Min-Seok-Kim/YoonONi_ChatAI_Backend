package com.example.YoonONI_BackEnd.controller;


import com.example.YoonONI_BackEnd.config.RequestDataSet;
import com.example.YoonONI_BackEnd.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping("/select")
    public ResponseEntity<?> select(RequestDataSet requestDataSet) {
        return myPageService.selectMyPage(requestDataSet);
    }

    @PutMapping("/change/password")
    public ResponseEntity<?> modify(RequestDataSet requestDataSet) {
        return myPageService.changePassword(requestDataSet);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(RequestDataSet requestDataSet) {
        return myPageService.deleteId(requestDataSet);
    }
}
