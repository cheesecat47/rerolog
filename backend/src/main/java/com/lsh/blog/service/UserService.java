package com.lsh.blog.service;

import com.lsh.blog.dto.UserDto;

public interface UserService {
    UserDto get(int userId) throws Exception;
}
