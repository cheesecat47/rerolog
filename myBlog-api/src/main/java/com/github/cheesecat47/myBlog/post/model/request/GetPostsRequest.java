package com.github.cheesecat47.myBlog.post.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetPostsRequest {
    @Schema(description = "유저 아이디.")
    String userId;

    @Schema(description = "게시판 아이디. 특정 게시판에 속한 글만 필터링 할 때 사용. 없으면 전체 글 목록.")
    int categoryId;

    @Schema(description = "latest(최신순), oldest(오래된순), popular(인기순) 중 택 1. 기본값은 latest")
    String order;

    @Schema(description = "정렬된 결과 중 offset부터 반환. 기본값 0")
    int offset;

    @Schema(description = "offset부터 limit개 조회. 기본값 10")
    int limit;
}