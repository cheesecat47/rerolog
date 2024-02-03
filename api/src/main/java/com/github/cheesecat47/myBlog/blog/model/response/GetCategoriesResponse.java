package com.github.cheesecat47.myBlog.blog.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class GetCategoriesResponse {
    @Schema(description = "응답 코드. API 명세서 참고")
    String code;

    @Schema(description = "응답 메시지")
    String message;

    @Schema(description = "게시판 정보 객체 배열. 만약 조회 조건은 문제가 없지만 해당하는 게시판이 없다면 data는 길이가 0인 배열 반환.")
    List<CategoryDto> data;
}
