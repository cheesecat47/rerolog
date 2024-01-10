package com.github.cheesecat47.myBlog.post.model.response;

import com.github.cheesecat47.myBlog.post.model.PostDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetPostByIdResponse {
    @Schema(description = "응답 코드. API 명세서 참고")
    String code;

    @Schema(description = "응답 메시지")
    String message;

    @Schema(description = "글 정보 객체")
    PostDto data;
}
