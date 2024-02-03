package com.github.cheesecat47.myBlog.post.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreatePostResponseDto {
    @Schema(description = "응답 메시지")
    String message;

    @Schema(description = "응답 코드. API 명세서 참고")
    String code;

    Object data;
}
