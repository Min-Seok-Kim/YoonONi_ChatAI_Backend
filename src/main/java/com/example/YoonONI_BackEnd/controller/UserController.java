package com.example.YoonONI_BackEnd.controller;


import com.example.YoonONI_BackEnd.config.RequestDataSet;
import com.example.YoonONI_BackEnd.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<?> signUp(RequestDataSet requestDataSet) {

        return userService.signUp(requestDataSet);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(RequestDataSet requestDataSet) {

        return userService.login(requestDataSet);
    }
}
