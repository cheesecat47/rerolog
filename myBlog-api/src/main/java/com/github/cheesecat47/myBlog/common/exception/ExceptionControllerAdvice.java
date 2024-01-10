package com.github.cheesecat47.myBlog.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

        HashMap<String, Object> result = new HashMap<>();
        result.put("message", e.getMessage());

        ResponseEntity.BodyBuilder bodyBuilder = null;

        if (e instanceof MyBlogCommonException) {
            result.put("data", ((MyBlogCommonException) e).getData());

            String code = ((MyBlogCommonException) e).getCode();
            result.put("code", code);

            switch (code) {
                case ResponseCode.NO_REQUIRED_REQUEST_PARAMETER:
                case ResponseCode.INVALID_REQUEST_PARAMETER:
                    bodyBuilder = ResponseEntity.status(HttpStatus.BAD_REQUEST);
                    break;
                case ResponseCode.UNAUTHORIZED:
                    bodyBuilder = ResponseEntity.status(HttpStatus.UNAUTHORIZED);
                    break;
                case ResponseCode.NO_RESULT:
                case ResponseCode.NO_RESOURCE:
                    bodyBuilder = ResponseEntity.status(HttpStatus.NOT_FOUND);
                    break;
            }
        } else {
            result.put("code", ResponseCode.INTERNAL_SERVER_ERROR);
            result.put("data", e.getCause());
            bodyBuilder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return bodyBuilder.body(result);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<HashMap<String, Object>> handle404(NoHandlerFoundException e) {
        logger.error("Exception 발생 : {}", e.getMessage());

        HashMap<String, Object> result = new HashMap<>() {{
            put("message", e.getMessage());
            put("code", ResponseCode.NO_RESOURCE);
            put("data", e.getCause());
        }};

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

}
