package com.github.cheesecat47.myBlog.post.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthorDto {
    @Schema(description = "작성자 아이디.")
    String userId;

    @Schema(description = "유저 별명.")
    String nickName;

    @Schema(description = "프로필 이미지 URL.")
    String profileImage;
}
