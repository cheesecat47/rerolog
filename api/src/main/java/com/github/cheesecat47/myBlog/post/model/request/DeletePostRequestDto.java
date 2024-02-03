package com.github.cheesecat47.myBlog.post.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DeletePostRequestDto {
    @Schema(description = "액세스 토큰", hidden = true)
    String accessToken;

    @Schema(description = "유저 아이디. DB의 `id_str` 값")
    String userId;

    @Schema(description = "글 아이디", hidden = true)
    String postId;

    @Schema(description = "삭제할 글 제목", hidden = true)
    String postTitle;
}
