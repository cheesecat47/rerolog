package com.github.cheesecat47.myBlog.post.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateCommentRequestDto {
    @Schema(description = "액세스 토큰", hidden = true)
    String accessToken;

    @Schema(description = "유저 아이디. DB의 `id_str` 값", hidden = true)
    String userId;

//    @Schema(description = "비회원 댓글 시 유저 아이디. `userId` 또는 이 값 중 하나 필수. 둘 다는 불가. 토큰이 유효하다면 토큰에 포함된 `userId`를 우선함")
//    String tmpUserId;
//
//    @Schema(description = "비회원 댓글 시 유저 비밀번호. `tmpUserId` 사용 시 필요")
//    String tmpUserPw;

    @Schema(description = "댓글을 작성할 글 아이디", hidden = true)
    int postId;

    @Schema(description = "댓글을 작성할 글 제목", hidden = true)
    String postTitle;

    @Schema(description = "댓글 본문. 최대 300자")
    String content;
}
