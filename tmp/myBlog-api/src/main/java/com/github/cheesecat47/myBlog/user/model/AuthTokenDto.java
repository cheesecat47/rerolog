package com.github.cheesecat47.myBlog.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthTokenDto {
    @Schema(description = "로그인 한 유저 아이디")
    String userId;

    @Schema(description = "액세스 토큰")
    String accessToken;

    @Schema(description = "리프레시 토큰")
    String refreshToken;
}
