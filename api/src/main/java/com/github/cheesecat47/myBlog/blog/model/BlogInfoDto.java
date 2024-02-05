package com.github.cheesecat47.myBlog.blog.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "블로그 정보 객체.")
public class BlogInfoDto {
    @Schema(description = "블로그 주인 유저 아이디. DB의 `id_str` 값.")
    String userId;

    @Schema(description = "블로그 이름.")
    String blogName;

    @Schema(description = "블로그 소개.  소개 멘트 부재 시 길이 0인 문자열 `\"\"` 반환.")
    String content = "";

    @Schema(description = "블로그 개설일. ISO 8601 형식. UTC.")
    String createdAt;
}
