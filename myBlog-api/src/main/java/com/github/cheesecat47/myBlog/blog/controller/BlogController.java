package com.github.cheesecat47.myBlog.blog.controller;

import com.github.cheesecat47.myBlog.blog.model.BlogInfoDto;
import com.github.cheesecat47.myBlog.blog.model.response.CategoryDto;
import com.github.cheesecat47.myBlog.blog.model.response.GetBlogInfoResponse;
import com.github.cheesecat47.myBlog.blog.model.response.GetCategoriesResponse;
import com.github.cheesecat47.myBlog.blog.service.BlogService;
import com.github.cheesecat47.myBlog.common.exception.ResponseCode;
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

import java.util.List;

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
    private final BlogService blogService;

    @Operation(summary = "getBlogInfo 블로그 정보 조회", description = "아이디에 해당하는 블로그 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "블로그 정보 조회 성공", content = {@Content(schema = @Schema(implementation = GetBlogInfoResponse.class))}),
            @ApiResponse(responseCode = "400", description = "블로그 정보 조회 실패"),
            @ApiResponse(responseCode = "404", description = "블로그 정보 조회 실패"),
            @ApiResponse(responseCode = "500", description = "블로그 정보 조회 실패")
    })
    @GetMapping(value = "/{userId}")
    public ResponseEntity<GetBlogInfoResponse> getBlogInfo(
            @Parameter(description = "유저 아이디") @PathVariable String userId
    ) throws Exception {
        logger.info("getBlogInfo");
        GetBlogInfoResponse response = new GetBlogInfoResponse();

        BlogInfoDto blogInfoDto = blogService.getBlogInfo(userId);

        response.setCode(ResponseCode.NORMAL_SERVICE);
        response.setMessage("블로그 정보 조회 성공");
        response.setData(blogInfoDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "getCategories 게시판 목록 조회", description = "블로그에 있는 게시판 목록 조회.<br/>만약 조회 조건은 문제가 없지만 해당하는 게시판이 없다면 상태코드는 200, data는 길이가 0인 배열 반환.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시판 목록 조회 성공", content = {@Content(schema = @Schema(implementation = GetCategoriesResponse.class))}),
            @ApiResponse(responseCode = "400", description = "게시판 목록 조회 실패"),
            @ApiResponse(responseCode = "404", description = "게시판 목록 조회 실패"),
            @ApiResponse(responseCode = "500", description = "게시판 목록 조회 실패")
    })
    @GetMapping(value = "/{userId}/category")
    public ResponseEntity<GetCategoriesResponse> getCategories(
            @Parameter(description = "유저 아이디") @PathVariable String userId
    ) throws Exception {
        logger.info("getCategories");
        GetCategoriesResponse response = new GetCategoriesResponse();

        List<CategoryDto> categoryNames = blogService.getCategories(userId);

        response.setCode(ResponseCode.NORMAL_SERVICE);
        response.setMessage("게시판 목록 조회 성공");
        response.setData(categoryNames);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
