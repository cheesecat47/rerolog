package com.github.cheesecat47.myBlog.blog.model.response;

import com.github.cheesecat47.myBlog.blog.model.BlogInfoDto;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class GetBlogInfoResponse {
    HttpStatus status;
    String message;
    BlogInfoDto data;
}
