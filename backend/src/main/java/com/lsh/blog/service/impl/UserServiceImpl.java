package com.lsh.blog.service.impl;

import com.lsh.blog.dto.UserDto;
import com.lsh.blog.mapper.UserMapper;
import com.lsh.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserDto get(int userId) {
        UserDto user = userMapper.get(userId);
        return user;
    }
}