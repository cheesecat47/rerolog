package com.github.cheesecat47.myBlog.user.service;

import com.github.cheesecat47.myBlog.user.model.AuthTokenDto;
import com.github.cheesecat47.myBlog.user.model.UserInfoDto;
import com.github.cheesecat47.myBlog.user.model.request.LoginRequestDto;
import com.github.cheesecat47.myBlog.user.model.request.RefreshRequestDto;

import java.util.Optional;

public interface UserService {
    UserInfoDto getUserInfo(Optional<String> userId) throws Exception;

    AuthTokenDto login(LoginRequestDto params) throws Exception;

    AuthTokenDto refresh(RefreshRequestDto params) throws Exception;
}
