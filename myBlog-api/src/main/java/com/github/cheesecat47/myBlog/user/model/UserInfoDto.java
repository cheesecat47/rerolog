package com.github.cheesecat47.myBlog.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "사용자 정보 객체.")
public class UserInfoDto {
    @Schema(description = "유저 아이디. DB의 `id_str` 값.")
    String userId;

    @Schema(description = "유저명.")
    String nickName;

    @Schema(description = "유저 소개. 공백 가능.")
    String content;

    @Schema(description = "회원 가입일. ISO 8601 형식. UTC.")
    String createdAt;

    @Schema(description = "프로필 이미지 URL.")
    String profileImage;

    @Schema(description = "연락처 배열. 등록된 연락처가 없으면 길이가 0인 배열.")
    List<ContactDto> contacts;
}
