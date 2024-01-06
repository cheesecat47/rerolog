package com.github.cheesecat47.myBlog.blog.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "블로그 정보 객체.")
public class BlogInfoDto {
    @Schema(name = "id", description = "유저 아이디. DB의 `id_str` 값.")
    String idStr;

    @Schema(description = "유저명.")
    String name;

    @Schema(name = "created_at", description = "블로그 개설일. ISO 8601 형식. UTC.")
    String createdAt;
}
