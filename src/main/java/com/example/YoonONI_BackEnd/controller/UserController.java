package com.example.YoonONI_BackEnd.controller;


import com.example.YoonONI_BackEnd.dto.LoginRequestDto;
import com.example.YoonONI_BackEnd.dto.UserRequestDto;
import com.example.YoonONI_BackEnd.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserRequestDto userRequestDto) {

        return userService.signUp(userRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        userService.login(loginRequestDto);
        return ResponseEntity.ok().body("로그인 성공");
    }
}
