package com.github.cheesecat47.myBlog.post.service;

import com.github.cheesecat47.myBlog.common.exception.MyBlogCommonException;
import com.github.cheesecat47.myBlog.common.exception.ResponseCode;
import com.github.cheesecat47.myBlog.post.model.PostDto;
import com.github.cheesecat47.myBlog.post.model.mapper.PostMapper;
import com.github.cheesecat47.myBlog.post.model.request.GetPostsRequest;
import com.github.cheesecat47.myBlog.user.model.UserInfoDto;
import com.github.cheesecat47.myBlog.user.model.mapper.UserMapper;
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

    @Override
    public List<PostDto> getPosts(GetPostsRequest params) throws Exception {
        log.debug("getPosts: params: {}", params);

        // 공백인 경우
        if (params.getUserId().isEmpty() || params.getUserId().isBlank()) {
            String msg = "유저 아이디는 필수입니다";
            log.error("getPosts: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.NO_REQUIRED_REQUEST_PARAMETER,
                    msg,
                    new HashMap<>() {{
                        put("userId", params.getUserId());
                    }}
            );
        }

        // DB에서 조회
        List<PostDto> posts;
        try {
            // 존재하는 유저인지 확인
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
    public PostDto getPostById(String userId, String postId) throws Exception {
        log.debug("getPostById: userId: {}", userId);
        log.debug("getPostById: postId: {}", postId);

        // 공백인 경우
        if (userId.isEmpty() || userId.isBlank()) {
            String msg = "유저 아이디는 필수입니다";
            log.error("getPostById: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.NO_REQUIRED_REQUEST_PARAMETER,
                    msg,
                    new HashMap<>() {{
                        put("userId", userId);
                    }}
            );
        }

        // 공백인 경우
        if (postId.isEmpty() || postId.isBlank()) {
            String msg = "글 아이디는 필수입니다";
            log.error("getPostById: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.NO_REQUIRED_REQUEST_PARAMETER,
                    msg,
                    new HashMap<>() {{
                        put("postId", postId);
                    }}
            );
        }

        // DB에서 조회
        PostDto postDto;
        try {
            // 존재하는 유저인지 확인
            UserInfoDto userInfoDto = userMapper.getUserInfo(userId);
            if (userInfoDto == null) {
                String msg = "입력한 아이디에 해당하는 유저가 없습니다";
                log.error("getPostById: {}", msg);
                throw new MyBlogCommonException(
                        ResponseCode.NO_RESULT,
                        msg,
                        new HashMap<>() {{
                            put("userId", userId);
                        }}
                );
            }

            // 존재하는 글인지 확인
            postDto = postMapper.getPostById(userId, postId);
            if (postDto == null) {
                String msg = "해당하는 글이 없습니다";
                log.error("getPostById: {}", msg);
                throw new MyBlogCommonException(
                        ResponseCode.NO_RESULT,
                        msg,
                        new HashMap<>() {{
                            put("postId", postId);
                        }}
                );
            }

            // 댓글 개수 업데이트
            postDto.setNumOfComments(postDto.getComments().size());

            log.debug("getPostById: postDto: {}", postDto);
        } catch (SQLException e) {
            String msg = "DB 조회 중 오류가 발생했습니다";
            log.error("getPostById: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.SQL_ERROR,
                    msg,
                    new HashMap<>() {{
                        put("userId", userId);
                        put("postId", postId);
                        put("error", e.getMessage());
                    }}
            );
        }

        return postDto;
    }
}
