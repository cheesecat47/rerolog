package com.github.cheesecat47.myBlog.post.controller;

import com.github.cheesecat47.myBlog.common.exception.ResponseCode;
import com.github.cheesecat47.myBlog.post.model.CommentDto;
import com.github.cheesecat47.myBlog.post.model.PostDto;
import com.github.cheesecat47.myBlog.post.model.request.*;
import com.github.cheesecat47.myBlog.post.model.response.*;
import com.github.cheesecat47.myBlog.post.service.PostService;
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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    private final PostService postService;

    @Operation(summary = "getPosts 글 목록 조회", description = "조건에 맞는 글 목록 조회.<br/>만약 조회 조건은 문제가 없지만 해당하는 글이 없다면 data는 길이가 0인 배열 반환.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "글 목록 조회 성공", content = {@Content(schema = @Schema(implementation = GetPostsResponse.class))}),
            @ApiResponse(responseCode = "400", description = "글 목록 조회 실패"),
            @ApiResponse(responseCode = "404", description = "글 목록 조회 실패"),
            @ApiResponse(responseCode = "500", description = "글 목록 조회 실패")
    })
    @GetMapping(value = "")
    public ResponseEntity<GetPostsResponse> getPosts(
            @ParameterObject GetPostsRequest params
    ) throws Exception {
        log.debug("getPosts: params: {}", params);

        GetPostsResponse response = new GetPostsResponse();

        List<PostDto> posts = postService.getPosts(params);

        String msg = "글 목록 조회 성공";
        log.info("getPosts: {}, size: {}", msg, posts.size());
        response.setCode(ResponseCode.NORMAL_SERVICE);
        response.setMessage(msg);
        response.setData(posts);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "getPostByTitle 글 상세 조회", description = "글 상세 조회. 이 글과 연결된 댓글 포함.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "글 조회 성공", content = {@Content(schema = @Schema(implementation = GetPostByTitleResponse.class))}),
            @ApiResponse(responseCode = "400", description = "글 조회 실패"),
            @ApiResponse(responseCode = "404", description = "글 조회 실패"),
            @ApiResponse(responseCode = "500", description = "글 조회 실패")
    })
    @GetMapping(value = "/{postTitle}")
    public ResponseEntity<GetPostByTitleResponse> getPostByTitle(
            @Parameter(description = "글 제목") @PathVariable String postTitle
    ) throws Exception {
        log.debug("getPostByTitle: postTitle: {}", postTitle);

        GetPostByTitleResponse response = new GetPostByTitleResponse();

        PostDto postDto = postService.getPostByTitle(postTitle);

        String msg = "글 상세 조회 성공";
        log.info("getPostByTitle: {}", msg);
        response.setCode(ResponseCode.NORMAL_SERVICE);
        response.setMessage(msg);
        response.setData(postDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "createPost 글 작성", description = "본인 블로그에 글 작성", security = {@SecurityRequirement(name = "Access Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "글 작성 성공", content = {@Content(schema = @Schema(implementation = CreatePostResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "글 작성 실패"),
            @ApiResponse(responseCode = "404", description = "글 작성 실패"),
            @ApiResponse(responseCode = "500", description = "글 작성 실패")
    })
    @PostMapping(value = "")
    public ResponseEntity<CreatePostResponseDto> createPost(
            HttpServletRequest request,
            @RequestBody CreatePostRequestDto params
    ) throws Exception {
        params.setAccessToken(request.getHeader("Authorization"));
        log.debug("createPost: params: {}", params);

        postService.createPost(params);

        CreatePostResponseDto response = new CreatePostResponseDto();
        String msg = "글 작성 성공";
        log.info("createPost: {}", msg);
        response.setCode(ResponseCode.NORMAL_SERVICE);
        response.setMessage(msg);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "updatePost 글 수정", description = "본인 블로그의 글 수정", security = {@SecurityRequirement(name = "Access Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "글 수정 실패"),
            @ApiResponse(responseCode = "404", description = "글 수정 실패"),
            @ApiResponse(responseCode = "500", description = "글 수정 실패")
    })
    @PatchMapping(value = "/{postTitle}")
    public ResponseEntity updatePost(
            HttpServletRequest request,
            @PathVariable String postTitle,
            @RequestBody UpdatePostRequestDto params
    ) throws Exception {
        params.setAccessToken(request.getHeader("Authorization"));
        params.setPostTitle(postTitle);
        log.debug("updatePost: params: {}", params);

        postService.updatePost(params);
        log.info("updatePost: 글 수정 성공");

        // HTTP 응답 코드가 204 NO CONTENT일 때는 body를 넣더리도 전송되지 않음!
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "deletePost 글 삭제", description = "본인 블로그의 글 삭제", security = {@SecurityRequirement(name = "Access Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "글 삭제 실패"),
            @ApiResponse(responseCode = "404", description = "글 삭제 실패"),
            @ApiResponse(responseCode = "500", description = "글 삭제 실패")
    })
    @DeleteMapping(value = "/{postTitle}")
    public ResponseEntity deletePost(
            HttpServletRequest request,
            @Parameter(description = "삭제할 글 제목") @PathVariable String postTitle,
            @RequestBody DeletePostRequestDto params
    ) throws Exception {
        params.setAccessToken(request.getHeader("Authorization"));
        params.setPostTitle(postTitle);
        log.debug("deletePost: params: {}", params);

        postService.deletePost(params);
        log.info("deletePost: 글 삭제 성공");

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "getCommentsByPostTitle 특정 글에 달린 댓글 목록 조회", description = "해당 글에 달린 댓글 목록 조회.<br/>만약 해당 글에 달린 댓글이 없다면 data는 길이가 0인 배열 반환.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 목록 조회 성공", content = {@Content(schema = @Schema(implementation = GetCommentsByPostTitleResponse.class))}),
            @ApiResponse(responseCode = "400", description = "댓글 목록 조회 실패"),
            @ApiResponse(responseCode = "404", description = "댓글 목록 조회 실패"),
            @ApiResponse(responseCode = "500", description = "댓글 목록 조회 실패")
    })
    @GetMapping(value = "/{postTitle}/comment")
    public ResponseEntity<GetCommentsByPostTitleResponse> getCommentsByPostTitle(
            @Parameter(description = "글 제목") @PathVariable String postTitle
    ) throws Exception {
        log.debug("getCommentsByPostId: postTitle: {}", postTitle);

        GetCommentsByPostTitleResponse response = new GetCommentsByPostTitleResponse();

        List<CommentDto> comments = postService.getCommentsByPostTitle(postTitle);

        String msg = "댓글 목록 조회 성공";
        log.info("getCommentsByPostId: {}, size: {}", msg, comments.size());
        response.setCode(ResponseCode.NORMAL_SERVICE);
        response.setMessage(msg);
        response.setData(new HashMap<String, Object>(2){{
            put("comments",comments);
            put("numOfComments", comments.size());
        }});

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "createComment 특정 글에 댓글 작성", security = {@SecurityRequirement(name = "Access Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "댓글 작성 성공", content = {@Content(schema = @Schema(implementation = CreateCommentResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "댓글 작성 실패"),
            @ApiResponse(responseCode = "401", description = "댓글 작성 실패"),
            @ApiResponse(responseCode = "500", description = "댓글 작성 실패")
    })
    @PostMapping(value = "/{postTitle}/comment")
    public ResponseEntity<CreateCommentResponseDto> createComment(
            HttpServletRequest request,
            @Parameter(description = "글 제목") @PathVariable String postTitle,
            @RequestBody CreateCommentRequestDto params
    ) throws Exception {
        params.setAccessToken(request.getHeader("Authorization"));
        params.setPostTitle(postTitle);
        log.debug("createComment: params: {}", params);

        CreateCommentResponseDto response = new CreateCommentResponseDto();

        postService.createComment(params);

        String msg = "댓글 작성 성공";
        response.setCode(ResponseCode.NORMAL_SERVICE);
        response.setMessage(msg);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
