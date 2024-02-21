package com.github.cheesecat47.myBlog.user.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "로그 아웃 파라미터 객체")
public class LogoutRequestDto {
    @Schema(description = "로그 아웃 하려는 유저 아이디. DB의 `id_str` 값.")
    String userId;

    @Schema(hidden = true)
    String uid;

    @Schema(description = "액세스 토큰")
    String accessToken;
}
