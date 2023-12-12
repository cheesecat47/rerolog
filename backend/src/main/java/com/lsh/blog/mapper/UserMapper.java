package com.lsh.blog.mapper;

import com.lsh.blog.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDto get(int userId);
}
