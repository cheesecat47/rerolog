package com.github.cheesecat47.myBlog.user.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "로그 아웃 파라미터 객체")
public class RefreshRequestDto {
    @Schema(description = "토큰을 재발급하려는 유저 아이디. DB의 `id_str` 값.")
    String userId;

    @Schema(description = "리프레시 토큰")
    String refreshToken;
}
