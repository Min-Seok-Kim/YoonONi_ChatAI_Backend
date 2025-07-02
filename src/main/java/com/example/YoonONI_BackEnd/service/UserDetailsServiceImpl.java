package com.example.YoonONI_BackEnd.service;

import com.example.YoonONI_BackEnd.mapper.UserMapper;
import com.example.YoonONI_BackEnd.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserVo user = userMapper.selectUser(userId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with userId: " + userId);
        }
        return UserDetailsImpl.build(user);
    }
}
