package com.github.cheesecat47.myBlog.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> exceptionHandler(Exception e) {
        logger.error("Exception 발생 : {}", e.getMessage());

        HashMap<String, Object> result = new HashMap<>() {{
            put("message", e.getMessage());
            if (e instanceof MyBlogCommonException) {
                put("status", ((MyBlogCommonException) e).getStatus());
                put("data", ((MyBlogCommonException) e).getData());
            } else {
                put("status", HttpStatus.INTERNAL_SERVER_ERROR);
                put("data", e.getCause());
            }
        }};

        return ResponseEntity.status((HttpStatusCode) result.get("status")).body(result);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<HashMap<String, Object>> handle404(NoHandlerFoundException e) {
        logger.error("Exception 발생 : {}", e.getMessage());

        HashMap<String, Object> result = new HashMap<>() {{
            put("message", e.getMessage());
            put("data", e.getCause());
        }};

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

}
