package com.github.cheesecat47.myBlog.user.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
public class GetUserInfoResponse {
    HttpStatus status;
    String message;
    Map<String, Object> error;
    UserInfo data;
}
