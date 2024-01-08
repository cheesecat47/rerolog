package com.github.cheesecat47.myBlog.blog.model.response;

import com.github.cheesecat47.myBlog.blog.model.BlogInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetBlogInfoResponse {
    @Schema(description = "응답 코드. API 명세서 참고")
    String code;

    @Schema(description = "응답 메시지")
    String message;

    @Schema(description = "블로그 정보 객체.")
    BlogInfoDto data;
}
