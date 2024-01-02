package com.github.cheesecat47.myBlog.blog.controller;

import com.github.cheesecat47.myBlog.blog.model.response.GetBlogInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 블로그 컨트롤러
 *
 * @author Shin Juyong <a href="mailto:cheesecat47@gmail.com">cheesecat47@gmail.com</a>
 */
@RestController
@Tag(name = "블로그 API")
@RequestMapping("/blog")
@Slf4j
@RequiredArgsConstructor
public class BlogController {

    private final Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Operation(summary = "getBlogInfo 블로그 정보 조회", description = "아이디에 해당하는 블로그 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "블로그 정보 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GetBlogInfoResponse.class))})
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetBlogInfoResponse> getBlogInfo(
            @Parameter(description = "유저 아이디") @PathVariable(value = "id") String idStr
    ) throws Exception {
        logger.info("getBlogInfo");
        GetBlogInfoResponse response = new GetBlogInfoResponse();

        response.setStatus(HttpStatus.OK);
        response.setMessage("블로그 정보 조회 성공");
        response.setData(null);

        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
