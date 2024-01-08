package com.github.cheesecat47.myBlog.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class MyBlogCommonException extends Exception {

    private Map<String, Object> data;
    private String code;

    public MyBlogCommonException(String code, String message, Map<String, Object> data) {
        super(message);
        this.setData(data);
        this.setCode(code);
    }
}
