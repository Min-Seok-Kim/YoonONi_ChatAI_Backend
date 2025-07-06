package com.example.YoonONI_BackEnd.service;


import com.example.YoonONI_BackEnd.config.security.JwtTokenProvider;
import com.example.YoonONI_BackEnd.config.RequestDataSet;
import com.example.YoonONI_BackEnd.dto.JwtResponseDto;
import com.example.YoonONI_BackEnd.dto.LoginRequestDto;
import com.example.YoonONI_BackEnd.mapper.UserMapper;
import com.example.YoonONI_BackEnd.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    private final PasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<?> signUp(RequestDataSet requestDataSet) {
        String userId = requestDataSet.inGetString("userId");
        String password = requestDataSet.inGetString("password");
        String email = requestDataSet.inGetString("email");
        String gender = requestDataSet.inGetString("gender");
        String birthDateStr = requestDataSet.inGetString("birthDate"); // yyyy-MM-dd 형식 가정

        Float heightCm = null;
        try {
            String heightStr = requestDataSet.inGetString("heightCm");
            if (StringUtils.isNotBlank(heightStr)) {
                heightCm = Float.parseFloat(heightStr);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: 키 값은 숫자여야 합니다.");
        }

        Float weightKg = null;
        try {
            String weightStr = requestDataSet.inGetString("weightKg");
            if (StringUtils.isNotBlank(weightStr)) {
                weightKg = Float.parseFloat(weightStr);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: 몸무게 값은 숫자여야 합니다.");
        }



        if(userMapper.existsByUserId(userId)){
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }


        if (StringUtils.isAnyBlank(userId, password, email)) {
            return ResponseEntity.badRequest().body("필수 값 누락");
        }

        String encodedPassword = encoder.encode(password);

        LocalDate birthDate;
        try {
            birthDate = LocalDate.parse(birthDateStr);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: 잘못된 생년월일 형식입니다 (예: 1995-05-15)");
        }


        UserVo user = UserVo.builder()
                .userId(userId)
                .password(encodedPassword)
                .email(email)
                .gender(gender)
                .birthDate(birthDate)
                .heightCm(heightCm)
                .weightKg(weightKg)
                .build();
        userMapper.insertUser(user);
        return ResponseEntity.ok().body("성공");
    }

    public ResponseEntity<?> login(RequestDataSet requestDataSet) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDataSet.inGetString("userId"), requestDataSet.inGetString("password")));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponseDto(jwt, userDetails.getUsername()));
    }
}
