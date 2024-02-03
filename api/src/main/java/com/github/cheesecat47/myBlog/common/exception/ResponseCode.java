package com.github.cheesecat47.myBlog.common.exception;

/**
 * 응답 코드. API 명세서 참고
 */
public interface ResponseCode {
    String NORMAL_SERVICE = "00";
    String NO_REQUIRED_REQUEST_PARAMETER = "10";
    String INVALID_REQUEST_PARAMETER = "11";
    String UNAUTHORIZED = "12";
    String NO_RESULT = "13";
    String NO_RESOURCE = "14";
    String INTERNAL_SERVER_ERROR = "20";
    String SQL_ERROR = "21";
}
