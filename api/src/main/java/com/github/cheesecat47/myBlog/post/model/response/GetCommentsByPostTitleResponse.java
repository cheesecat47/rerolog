package com.github.cheesecat47.myBlog.post.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Data
public class GetCommentsByPostTitleResponse {
    @Schema(description = "응답 코드. API 명세서 참고")
    String code;

    @Schema(description = "응답 메시지")
    String message;

    @Schema(description = "댓글 배열과 댓글 수 객체. 만약 해당 글에 달린 댓글이 없다면 data는 길이가 0인 배열.")
    Map<String, Object> data;
}
