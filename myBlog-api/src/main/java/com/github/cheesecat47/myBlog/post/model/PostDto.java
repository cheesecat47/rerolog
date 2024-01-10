package com.github.cheesecat47.myBlog.post.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "글 정보 객체.")
public class PostDto {
    @Schema(description = "글 아이디.")
    int postId;

    @Schema(description = "게시판 아이디.")
    int categoryId;

    @Schema(description = "게시판 이름.")
    String categoryName;

    @Schema(description = "글 제목.")
    String title;

    @Schema(description = "작성자 정보.")
    AuthorDto author;

    @Schema(description = "작성일. ISO8601 형식. UTC.")
    String createdAt;

    @Schema(description = "조회수.")
    int hit;

    @Schema(description = "글 요약.")
    String excerpt;

    @Schema(description = "썸네일 URL.")
    String thumbnail;

    @Schema(description = "댓글 개수.")
    int numOfComments;
}
