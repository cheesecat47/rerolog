package com.github.cheesecat47.myBlog.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@Schema(description = "사용자 정보 객체.")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonPropertyOrder({"id", "name", "content", "created_at", "profile_img", "contacts"})
public class UserInfoDto {
    @JsonProperty("id")
    @Schema(name = "id", description = "유저 아이디. DB의 `id_str` 값.")
    String idStr;

    @Schema(description = "유저명.")
    String name;

    @Schema(description = "유저 소개. 공백 가능.")
    String content;

    @Schema(name = "created_at", description = "회원 가입일. ISO 8601 형식. UTC.")
    String createdAt;

    @Schema(name = "profile_img", description = "프로필 이미지 URL.")
    String profileImg;

    List<ContactDto> contacts;
}
