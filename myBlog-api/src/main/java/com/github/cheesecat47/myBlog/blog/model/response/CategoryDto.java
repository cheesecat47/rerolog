package com.github.cheesecat47.myBlog.blog.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "게시판 정보 객체.")
public class CategoryDto {
    @Schema(description = "게시판 아이디.")
    int categoryId;

    @Schema(description = "게시판 이름.")
    String categoryName;
}
