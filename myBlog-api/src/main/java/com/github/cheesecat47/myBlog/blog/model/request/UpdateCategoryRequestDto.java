package com.github.cheesecat47.myBlog.blog.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateCategoryRequestDto {
    @Schema(description = "블로그 아이디. 유저 아이디(`userId`)와 동일", hidden = true)
    String blogId;

    @Schema(description = "게시판 아이디. Mapper에서 사용", hidden = true)
    String categoryId;

    @Schema(description = "정보를 변경하려는 게시판 이름", hidden = true)
    String categoryName;

    @Schema(description = "액세스 토큰", hidden = true)
    String accessToken;

    @Schema(description = "새 게시판 이름")
    String newCategoryName;
}
