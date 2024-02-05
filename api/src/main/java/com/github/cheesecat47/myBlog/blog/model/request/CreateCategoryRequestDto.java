package com.github.cheesecat47.myBlog.blog.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateCategoryRequestDto {
    @Schema(description = "게시판 이름")
    String categoryName;

    @Schema(hidden = true)
    String accessToken;

    @Schema(hidden = true)
    String blogId;
}
