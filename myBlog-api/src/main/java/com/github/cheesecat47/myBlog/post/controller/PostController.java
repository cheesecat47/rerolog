package com.github.cheesecat47.myBlog.post.controller;

import com.github.cheesecat47.myBlog.common.exception.ResponseCode;
import com.github.cheesecat47.myBlog.post.model.PostDto;
import com.github.cheesecat47.myBlog.post.model.response.GetPostByIdResponse;
import com.github.cheesecat47.myBlog.post.model.response.GetPostsResponse;
import com.github.cheesecat47.myBlog.post.service.PostService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 게시글 컨트롤러
 *
 * @author Shin Juyong <a href="mailto:cheesecat47@gmail.com">cheesecat47@gmail.com</a>
 */
@RestController
@Tag(name = "게시글 API")
@RequestMapping("/post")
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    @Operation(summary = "getPosts 글 목록 조회", description = "조건에 맞는 글 목록 조회.<br/>만약 조회 조건은 문제가 없지만 해당하는 글이 없다면 data는 길이가 0인 배열 반환.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "글 목록 조회 성공", content = {@Content(schema = @Schema(implementation = GetPostsResponse.class))}),
            @ApiResponse(responseCode = "400", description = "글 목록 조회 실패"),
            @ApiResponse(responseCode = "404", description = "글 목록 조회 실패"),
            @ApiResponse(responseCode = "500", description = "글 목록 조회 실패")
    })
    @GetMapping(value = "/{userId}")
    public ResponseEntity<GetPostsResponse> getPosts(
            @Parameter(description = "유저 아이디") @PathVariable String userId,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false, defaultValue = "latest") String order,
            @RequestParam(required = false, defaultValue = "0") String offset,
            @RequestParam(required = false, defaultValue = "10") String limit
    ) throws Exception {
        GetPostsResponse response = new GetPostsResponse();

        List<PostDto> posts = postService.getPosts(userId, categoryId, order, offset, limit);

        response.setCode(ResponseCode.NORMAL_SERVICE);
        response.setMessage("글 목록 조회 성공");
        response.setData(posts);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "getPostById 글 상세 조회", description = "글 상세 조회. 이 글과 연결된 댓글 포함.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "글 조회 성공", content = {@Content(schema = @Schema(implementation = GetPostsResponse.class))}),
            @ApiResponse(responseCode = "400", description = "글 조회 실패"),
            @ApiResponse(responseCode = "404", description = "글 조회 실패"),
            @ApiResponse(responseCode = "500", description = "글 조회 실패")
    })
    @GetMapping(value = "/{userId}/{postId}")
    public ResponseEntity<GetPostByIdResponse> getPostById(
            @Parameter(description = "유저 아이디") @PathVariable String userId,
            @Parameter(description = "글 아이디") @PathVariable String postId
    ) throws Exception {
        GetPostByIdResponse response = new GetPostByIdResponse();

        PostDto postDto = postService.getPostById(userId, postId);

        response.setCode(ResponseCode.NORMAL_SERVICE);
        response.setMessage("글 상세 조회 성공");
        response.setData(postDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
