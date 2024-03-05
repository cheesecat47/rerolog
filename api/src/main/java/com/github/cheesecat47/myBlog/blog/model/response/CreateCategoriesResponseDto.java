package com.github.cheesecat47.myBlog.blog.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateCategoriesResponseDto {
    @Schema(description = "응답 코드. API 명세서 참고")
    String code;

    @Schema(description = "응답 메시지")
    String message;

    @Schema(description = "게시판 생성 성공 시 반환값 없음")
    Object data = null;
}
