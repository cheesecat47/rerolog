package com.github.cheesecat47.myBlog.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
public class WrongRequestParameterException extends Exception {

    private Map<String, Object> param;
    private HttpStatus status;

    public WrongRequestParameterException(HttpStatus status, String message, Map<String, Object> param) {
        super(message);
        this.setParam(param);
        this.setStatus(status);
    }
}
