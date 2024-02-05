package com.github.cheesecat47.myBlog.user.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LogoutResponseDto {
    @Schema(description = "응답 코드. API 명세서 참고")
    String code;

    @Schema(description = "응답 메시지")
    String message;

    @Schema(description = "로그 아웃은 데이터 없음")
    Object data = null;
}
