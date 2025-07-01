package com.example.YoonONI_BackEnd.service;


import com.example.YoonONI_BackEnd.dto.UserRequestDto;
import com.example.YoonONI_BackEnd.mapper.UserMapper;
import com.example.YoonONI_BackEnd.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public ResponseEntity<?> signUp(UserRequestDto userRequestDto) {
        UserVo user = UserVo.builder()
                .userId(userRequestDto.getUserId())
                .password(userRequestDto.getPassword())
                .email(userRequestDto.getEmail())
                .gender(userRequestDto.getGender())
                .birthDate(userRequestDto.getBirthDate())
                .heightCm(userRequestDto.getHeightCm())
                .weightKg(userRequestDto.getWeightKg())
                .build();
        userMapper.insertUser(user);
        return ResponseEntity.ok().body("성공");
    }
}
