package com.github.cheesecat47.myBlog.post.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "댓글 정보 객체.")
public class CommentDto {
    @Schema(description = "댓글 아이디.")
    int commentId;

    @Schema(description = "댓글 작성자 아이디.")
    String userId;

    @Schema(description = "댓글 본문.")
    String content;

    @Schema(description = "댓글 작성일. ISO8601 형식. UTC 기준.")
    String createdAt;
}
