package com.github.cheesecat47.myBlog.user.service;

import com.github.cheesecat47.myBlog.common.exception.WrongRequestParameterException;
import com.github.cheesecat47.myBlog.user.model.UserInfoDto;

import java.sql.SQLException;
import java.util.Optional;

public interface UserService {
    UserInfoDto getUserInfo(Optional<String> idStr) throws SQLException, WrongRequestParameterException;
}
