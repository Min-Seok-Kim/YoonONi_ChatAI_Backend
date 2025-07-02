package com.example.YoonONI_BackEnd.service;


import com.example.YoonONI_BackEnd.config.JwtTokenProvider;
import com.example.YoonONI_BackEnd.dto.JwtResponseDto;
import com.example.YoonONI_BackEnd.dto.LoginRequestDto;
import com.example.YoonONI_BackEnd.dto.UserRequestDto;
import com.example.YoonONI_BackEnd.mapper.UserMapper;
import com.example.YoonONI_BackEnd.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    private final PasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<?> signUp(UserRequestDto userRequestDto) {
        if(userMapper.existsByUserId(userRequestDto.getUserId())){
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        userRequestDto.setPassword(encoder.encode(userRequestDto.getPassword()));

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

    public ResponseEntity<?> login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUserId(), loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponseDto(jwt, userDetails.getUsername()));
    }
}
