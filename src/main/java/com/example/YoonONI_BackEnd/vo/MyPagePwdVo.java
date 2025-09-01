package com.example.YoonONI_BackEnd.vo;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class MyPagePwdVo {
    public String password;
    public String newPassword;
    public String userId;
}
