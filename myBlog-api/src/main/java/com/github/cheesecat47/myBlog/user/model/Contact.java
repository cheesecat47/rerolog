package com.github.cheesecat47.myBlog.user.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "연락처 배열. 등록된 연락처가 없으면 길이가 0인 배열.")
public record Contact(
        @Schema(description = "연락처 타입. Email, GitHub, LinkedIn, WebSite 중 하나.")
        String type,

        @Schema(description = "연락처 값.")
        String value
) {
}
