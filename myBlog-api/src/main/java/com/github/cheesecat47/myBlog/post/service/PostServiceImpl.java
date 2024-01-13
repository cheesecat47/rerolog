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
    public List<PostDto> getPosts(String userId, String categoryId, String order, String offset, String limit) throws Exception {
        log.debug("getPosts: userId: {}", userId);
        log.debug("getPosts: categoryId: {}", categoryId);
        log.debug("getPosts: order: {}", order);
        log.debug("getPosts: offset: {}", offset);
        log.debug("getPosts: limit: {}", limit);

        // 공백인 경우
        if (userId.isEmpty() || userId.isBlank()) {
            String msg = "유저 아이디는 필수입니다";
            log.error("getPosts: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.NO_REQUIRED_REQUEST_PARAMETER,
                    msg,
                    new HashMap<>() {{
                        put("userId", userId);
                    }}
            );
        }

        // DB에서 조회
        List<PostDto> posts;
        try {
            // 존재하는 유저인지 확인
            UserInfoDto userInfoDto = userMapper.getUserInfo(userId);
            log.debug("getPosts: userInfoDto: {}", userInfoDto);

            if (userInfoDto == null) {
                String msg = "입력한 아이디에 해당하는 유저가 없습니다";
                log.error("getPosts: {}", msg);
                throw new MyBlogCommonException(
                        ResponseCode.NO_RESULT,
                        msg,
                        new HashMap<>() {{
                            put("userId", userId);
                        }}
                );
            }

            GetPostsRequest params = new GetPostsRequest();
            params.setUserId(userId);
            params.setCategoryId(categoryId == null ? 0 : Integer.parseInt(categoryId));
            params.setOrder(order);
            params.setOffset(Integer.parseInt(offset));
            params.setLimit(Integer.parseInt(limit));
            log.debug("getPosts: params: {}", params);

            posts = postMapper.getPosts(params);
            log.debug("getPosts: size: {}", posts.size());
        } catch (SQLException e) {
            String msg = "DB 조회 중 오류가 발생했습니다";
            log.error("getPosts: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.SQL_ERROR,
                    msg,
                    new HashMap<>() {{
                        put("userId", userId);
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
            log.debug("getPostById: userInfoDto: {}", userInfoDto);

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

            postDto = postMapper.getPostById(userId, postId);
            log.debug("getPostById: postDto: {}", postDto);

            // 존재하는 글인지 확인
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
