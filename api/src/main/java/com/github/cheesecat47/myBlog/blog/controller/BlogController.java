package com.github.cheesecat47.myBlog.blog.controller;

import com.github.cheesecat47.myBlog.blog.model.BlogInfoDto;
import com.github.cheesecat47.myBlog.blog.model.request.CreateCategoryRequestDto;
import com.github.cheesecat47.myBlog.blog.model.request.DeleteCategoryRequestDto;
import com.github.cheesecat47.myBlog.blog.model.request.UpdateCategoryRequestDto;
import com.github.cheesecat47.myBlog.blog.model.response.*;
import com.github.cheesecat47.myBlog.blog.service.BlogService;
import com.github.cheesecat47.myBlog.common.exception.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private final BlogService blogService;

    @Operation(summary = "getBlogInfo 블로그 정보 조회", description = "아이디에 해당하는 블로그 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "블로그 정보 조회 성공", content = {@Content(schema = @Schema(implementation = GetBlogInfoResponse.class))}),
            @ApiResponse(responseCode = "400", description = "블로그 정보 조회 실패"),
            @ApiResponse(responseCode = "404", description = "블로그 정보 조회 실패"),
            @ApiResponse(responseCode = "500", description = "블로그 정보 조회 실패")
    })
    @GetMapping(value = "/{blogId}")
    public ResponseEntity<GetBlogInfoResponse> getBlogInfo(
            @Parameter(description = "블로그 아이디. 유저 아이디(`userId`)와 동일") @PathVariable String blogId
    ) throws Exception {
        log.debug("getBlogInfo: blogId: {}", blogId);

        GetBlogInfoResponse response = new GetBlogInfoResponse();

        BlogInfoDto blogInfoDto = blogService.getBlogInfo(blogId);

        String msg = "블로그 정보 조회 성공";
        log.info("getBlogInfo: {}", msg);
        response.setCode(ResponseCode.NORMAL_SERVICE);
        response.setMessage(msg);
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
    @GetMapping(value = "/{blogId}/category")
    public ResponseEntity<GetCategoriesResponse> getCategories(
            @Parameter(description = "블로그 아이디. 유저 아이디(`userId`)와 동일") @PathVariable String blogId
    ) throws Exception {
        log.debug("getCategories: blogId: {}", blogId);

        GetCategoriesResponse response = new GetCategoriesResponse();

        List<CategoryDto> categoryNames = blogService.getCategories(blogId);

        String msg = "게시판 목록 조회 성공";
        log.info("getCategories: {}, size: {}", msg, categoryNames.size());
        response.setCode(ResponseCode.NORMAL_SERVICE);
        response.setMessage(msg);
        response.setData(categoryNames);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "createCategory 게시판 생성", description = "게시판 생성.<br/>로그인 상태의 블로그 주인 유저만 생성 가능.", security = {@SecurityRequirement(name = "Access Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시판 생성 성공", content = {@Content(schema = @Schema(implementation = CreateCategoriesResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "게시판 생성 실패"),
            @ApiResponse(responseCode = "401", description = "게시판 생성 실패"),
            @ApiResponse(responseCode = "500", description = "게시판 생성 실패")
    })
    @PostMapping(value = "/{blogId}/category")
    public ResponseEntity<CreateCategoriesResponseDto> createCategory(
            @Parameter(description = "블로그 아이디. 유저 아이디(`userId`)와 동일") @PathVariable String blogId,
            @RequestBody CreateCategoryRequestDto params,
            HttpServletRequest request
    ) throws Exception {
        params.setBlogId(blogId);
        params.setAccessToken(request.getHeader("Authorization"));
        log.debug("createCategory: params: {}", params);

        CreateCategoriesResponseDto response = new CreateCategoriesResponseDto();

        blogService.createCategory(params);

        String msg = "게시판 생성 성공";
        log.info("getCategories: {}", msg);
        response.setCode(ResponseCode.NORMAL_SERVICE);
        response.setMessage(msg);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "updateCategory 게시판 정보 변경", security = {@SecurityRequirement(name = "Access Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "게시판 변경 성공", content = {@Content(schema = @Schema(implementation = UpdateCategoryResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "게시판 변경 실패"),
            @ApiResponse(responseCode = "401", description = "게시판 변경 실패"),
            @ApiResponse(responseCode = "500", description = "게시판 변경 실패")
    })
    @PatchMapping(value = "/{blogId}/category/{categoryName}")
    public ResponseEntity<UpdateCategoryResponseDto> updateCategory(
            @Parameter(description = "블로그 아이디. 유저 아이디(`userId`)와 동일") @PathVariable String blogId,
            @Parameter(description = "정보를 변경하려는 게시판 이름") @PathVariable String categoryName,
            HttpServletRequest request,
            @RequestBody UpdateCategoryRequestDto params
    ) throws Exception {
        params.setBlogId(blogId);
        params.setCategoryName(categoryName);
        params.setAccessToken(request.getHeader("Authorization"));
        log.debug("updateCategory: params: {}", params);

        UpdateCategoryResponseDto response = new UpdateCategoryResponseDto();

        blogService.updateCategory(params);

        String msg = "게시판 정보 변경 성공";
        log.info("updateCategory: {}", msg);
        response.setMessage(msg);
        response.setCode(ResponseCode.NORMAL_SERVICE);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @Operation(summary = "deleteCategory 게시판 삭제", security = {@SecurityRequirement(name = "Access Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "게시판 삭제 성공", content = {@Content(schema = @Schema(implementation = DeleteCategoryResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "게시판 삭제 실패"),
            @ApiResponse(responseCode = "401", description = "게시판 삭제 실패"),
            @ApiResponse(responseCode = "500", description = "게시판 삭제 실패")
    })
    @DeleteMapping(value = "/{blogId}/category/{categoryName}")
    public ResponseEntity<DeleteCategoryResponseDto> deleteCategory(
            @Parameter(description = "블로그 아이디. 유저 아이디(`userId`)와 동일") @PathVariable String blogId,
            @Parameter(description = "삭제하려는 게시판 이름") @PathVariable String categoryName,
            HttpServletRequest request
    ) throws Exception {
        DeleteCategoryRequestDto params = new DeleteCategoryRequestDto();
        params.setBlogId(blogId);
        params.setCategoryName(categoryName);
        params.setAccessToken(request.getHeader("Authorization"));
        log.debug("deleteCategory: params: {}", params);

        DeleteCategoryResponseDto response = new DeleteCategoryResponseDto();

        blogService.deleteCategory(params);

        String msg = "게시판 삭제 성공";
        log.info("deleteCategory: {}", msg);
        response.setMessage(msg);
        response.setCode(ResponseCode.NORMAL_SERVICE);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
