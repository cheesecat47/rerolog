package com.github.cheesecat47.myBlog.post.service;

import com.github.cheesecat47.myBlog.common.exception.MyBlogCommonException;
import com.github.cheesecat47.myBlog.common.exception.ResponseCode;
import com.github.cheesecat47.myBlog.post.model.PostDto;
import com.github.cheesecat47.myBlog.post.model.mapper.PostMapper;
import com.github.cheesecat47.myBlog.post.model.request.CreatePostRequestDto;
import com.github.cheesecat47.myBlog.post.model.request.GetPostsRequest;
import com.github.cheesecat47.myBlog.post.model.request.UpdatePostRequestDto;
import com.github.cheesecat47.myBlog.user.model.UserInfoDto;
import com.github.cheesecat47.myBlog.user.model.mapper.UserMapper;
import com.github.cheesecat47.myBlog.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final JWTUtil jwtUtil;

    @Override
    public List<PostDto> getPosts(GetPostsRequest params) throws Exception {
        log.debug("getPosts: params: {}", params);

        // DB에서 조회
        List<PostDto> posts;
        try {
            // 존재하는 유저인지 확인
            if (params.getUserId() != null) {
                UserInfoDto userInfoDto = userMapper.getUserInfo(params.getUserId());
                if (userInfoDto == null) {
                    String msg = "입력한 아이디에 해당하는 유저가 없습니다";
                    log.error("getPosts: {}", msg);
                    throw new MyBlogCommonException(
                            ResponseCode.NO_RESULT,
                            msg,
                            new HashMap<>() {{
                                put("userId", params.getUserId());
                            }}
                    );
                }
            }

            posts = postMapper.getPosts(params);
            log.debug("getPosts: posts size: {}", posts.size());
        } catch (SQLException e) {
            String msg = "DB 조회 중 오류가 발생했습니다";
            log.error("getPosts: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.SQL_ERROR,
                    msg,
                    new HashMap<>() {{
                        put("userId", params.getUserId());
                        put("error", e.getMessage());
                    }}
            );
        }

        return posts;
    }

    @Override
    public PostDto getPostByTitle(String postTitle) throws Exception {
        log.debug("getPostByTitle: postTitle: {}", postTitle);

        // 공백인 경우
        if (postTitle.isEmpty() || postTitle.isBlank()) {
            String msg = "글 제목은 필수입니다";
            log.error("getPostByTitle: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.NO_REQUIRED_REQUEST_PARAMETER,
                    msg,
                    new HashMap<>() {{
                        put("postTitle", postTitle);
                    }}
            );
        }

        // DB에서 조회
        PostDto postDto;
        try {
            // 존재하는 글인지 확인
            postDto = postMapper.getPostByTitle(postTitle);
            if (postDto == null) {
                String msg = "해당하는 글이 없습니다";
                log.error("getPostByTitle: {}", msg);
                throw new MyBlogCommonException(
                        ResponseCode.NO_RESULT,
                        msg,
                        new HashMap<>() {{
                            put("postTitle", postTitle);
                        }}
                );
            }

            log.debug("getPostByTitle: postDto: {}", postDto);
        } catch (SQLException e) {
            String msg = "DB 조회 중 오류가 발생했습니다";
            log.error("getPostByTitle: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.SQL_ERROR,
                    msg,
                    new HashMap<>() {{
                        put("postTitle", postTitle);
                        put("error", e.getMessage());
                    }}
            );
        }

        return postDto;
    }

    @Override
    public void createPost(CreatePostRequestDto params) throws Exception {
        log.debug("createPost: params: {}", params);

        // 필수 파라미터 체크
        if (params.getUserId() == null || params.getCategoryName() == null || params.getTitle() == null) {
            String msg = "필수 파라미터를 확인하세요";
            log.error("createPost: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.NO_REQUIRED_REQUEST_PARAMETER,
                    msg,
                    new HashMap<>() {{
                        put("userId", params.getUserId());
                        put("categoryName", params.getCategoryName());
                        put("postTitle", params.getTitle());
                    }}
            );
        }

        // 입력한 액세스 토큰이 없는 경우
        if (params.getAccessToken() == null || !jwtUtil.checkToken(params.getAccessToken(), params.getUserId())) {
            String msg = "로그인 후 이용 바랍니다";
            log.error("createPost: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.UNAUTHORIZED,
                    msg,
                    new HashMap<>() {{
                        put("userId", params.getUserId());
                        put("Authorization", params.getAccessToken());
                    }}
            );
        }

        // DB에 글 정보 삽입
        try {
            // 존재하는 글인지 확인
            PostDto postDto = postMapper.getPostByTitle(params.getTitle());
            if (postDto != null) {
                String msg = "동일한 제목의 글이 이미 존재합니다";
                log.error("createPost: {}", msg);
                throw new MyBlogCommonException(
                        ResponseCode.INVALID_REQUEST_PARAMETER,
                        msg,
                        new HashMap<>() {{
                            put("postTitle", params.getTitle());
                        }}
                );
            }

            int count = postMapper.createPost(params);
            if (count != 1) {
                String msg = "글 작성에 실패했습니다";
                log.error("createPost: {}", msg);
                throw new MyBlogCommonException(
                        ResponseCode.UNAUTHORIZED,
                        msg,
                        new HashMap<>() {{
                            put("userId", params.getUserId());
                            put("categoryName", params.getCategoryName());
                            put("title", params.getTitle());
                            put("excerpt", params.getExcerpt());
                            put("content", params.getContent());
                        }}
                );
            }

            log.debug("createPost: postDto: {}", postDto);
        } catch (SQLException e) {
            String msg = "DB 조회 중 오류가 발생했습니다";
            log.error("createPost: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.SQL_ERROR,
                    msg,
                    new HashMap<>() {{
                        put("postTitle", params.getTitle());
                        put("error", e.getMessage());
                    }}
            );
        }
    }

    @Override
    public void updatePost(UpdatePostRequestDto params) throws Exception {
        log.debug("updatePost: params: {}", params);

        // 필수 파라미터 체크
        if (params.getUserId() == null || params.getPostTitle() == null) {
            String msg = "필수 파라미터를 확인하세요";
            log.error("updatePost: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.NO_REQUIRED_REQUEST_PARAMETER,
                    msg,
                    new HashMap<>() {{
                        put("userId", params.getUserId());
                        put("postTitle", params.getPostTitle());
                    }}
            );
        }

        // 입력한 액세스 토큰이 없는 경우
        if (params.getAccessToken() == null || !jwtUtil.checkToken(params.getAccessToken(), params.getUserId())) {
            String msg = "로그인 후 이용 바랍니다";
            log.error("updatePost: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.UNAUTHORIZED,
                    msg,
                    new HashMap<>() {{
                        put("userId", params.getUserId());
                        put("Authorization", params.getAccessToken());
                    }}
            );
        }

        // DB에서 글 수정
        try {
            // 존재하는 글인지 확인
            PostDto postDto = postMapper.getPostByTitle(params.getPostTitle());
            if (postDto == null) {
                String msg = "수정 대상 글이 존재하지 않습니다";
                log.error("updatePost: {}", msg);
                throw new MyBlogCommonException(
                        ResponseCode.INVALID_REQUEST_PARAMETER,
                        msg,
                        new HashMap<>() {{
                            put("postTitle", params.getPostTitle());
                        }}
                );
            }

            int count = postMapper.updatePost(params);
            if (count != 1) {
                String msg = "글 수정에 실패했습니다";
                log.error("updatePost: {}", msg);
                throw new MyBlogCommonException(
                        ResponseCode.UNAUTHORIZED,
                        msg,
                        new HashMap<>() {{
                            put("userId", params.getUserId());
                            put("categoryName", params.getCategoryName());
                            put("newTitle", params.getPostTitle());
                            put("excerpt", params.getExcerpt());
                            put("content", params.getContent());
                        }}
                );
            }
        } catch (SQLException e) {
            String msg = "DB 수정 중 오류가 발생했습니다";
            log.error("updatePost: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.SQL_ERROR,
                    msg,
                    new HashMap<>() {{
                        put("postTitle", params.getPostTitle());
                        put("error", e.getMessage());
                    }}
            );
        }
    }
}
