package com.github.cheesecat47.myBlog.user.model.mapper;

import com.github.cheesecat47.myBlog.user.model.UserInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface UserMapper {
    UserInfoDto getUserInfo(String userId) throws SQLException;
}
