package com.github.cheesecat47.myBlog.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
public class MyBlogCommonException extends Exception {

    private Map<String, Object> data;
    private HttpStatus status;

    public MyBlogCommonException(HttpStatus status, String message, Map<String, Object> data) {
        super(message);
        this.setData(data);
        this.setStatus(status);
    }
}
