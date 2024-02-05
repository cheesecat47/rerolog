package com.github.cheesecat47.myBlog.user.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "로그인 파라미터 객체")
public class LoginRequestDto {
    @Schema(description = "유저 아이디. DB의 `id_str` 값.")
    String userId;

    @Schema(description = "유저 비밀번호")
    String userPw;
}
