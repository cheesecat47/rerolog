package com.github.cheesecat47.myBlog.user.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 정보 객체.")
public record UserInfo(
        @Schema(name = "id_str", description = "유저 아이디.")
        String idStr,

        @Schema(description = "유저명.")
        String name,

        @Schema(description = "유저 소개. 공백 가능.")
        String content,

        @Schema(name = "created_at", description = "회원 가입일. ISO 8601 형식. UTC.")
        String createdAt,

        @Schema(name = "profile_img", description = "프로필 이미지 URL.")
        String profileImg,

        Contact[] contacts
) {
}
