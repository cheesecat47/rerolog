package com.github.cheesecat47.myBlog.blog.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class GetCategoriesResponse {
    HttpStatus status;

    String message;

    @Schema(description = "만약 조회 조건은 문제가 없지만 해당하는 게시판이 없다면 data는 길이가 0인 배열 반환.")
    List<String> data;
}
