package com.github.cheesecat47.myBlog.post.service;

import com.github.cheesecat47.myBlog.common.exception.MyBlogCommonException;
import com.github.cheesecat47.myBlog.common.exception.ResponseCode;
import com.github.cheesecat47.myBlog.post.model.PostDto;
import com.github.cheesecat47.myBlog.post.model.mapper.PostMapper;
import com.github.cheesecat47.myBlog.post.model.request.GetPostsRequest;
import com.github.cheesecat47.myBlog.user.model.UserInfoDto;
import com.github.cheesecat47.myBlog.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
    private final PostMapper postMapper;
    private final UserMapper userMapper;

    @Override
    public List<PostDto> getPosts(String userId, String categoryId, String order, String offset, String limit) throws Exception {
        logger.info("getPosts: userId: {}", userId);

        // 공백인 경우
        if (userId.isEmpty() || userId.isBlank()) {
            logger.error("getPosts: 유저 아이디는 필수입니다.");
            throw new MyBlogCommonException(
                    ResponseCode.NO_REQUIRED_REQUEST_PARAMETER,
                    "유저 아이디는 필수입니다",
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
            if (userInfoDto == null) {
                logger.error("getPosts: 입력한 아이디에 해당하는 유저가 없습니다.");
                throw new MyBlogCommonException(
                        ResponseCode.NO_RESULT,
                        "입력한 아이디에 해당하는 블로그가 없습니다.",
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

            posts = postMapper.getPosts(params);
            logger.info("getPosts: # of posts: {}", posts.size());
        } catch (SQLException e) {
            throw new MyBlogCommonException(
                    ResponseCode.SQL_ERROR,
                    "DB 조회 중 오류가 발생했습니다.",
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
        logger.debug("getPostById: userId: {}", userId);
        logger.debug("getPostById: postId: {}", postId);

        // 공백인 경우
        if (userId.isEmpty() || userId.isBlank()) {
            logger.error("getPostById: 유저 아이디는 필수입니다.");
            throw new MyBlogCommonException(
                    ResponseCode.NO_REQUIRED_REQUEST_PARAMETER,
                    "유저 아이디는 필수입니다",
                    new HashMap<>() {{
                        put("userId", userId);
                    }}
            );
        }

        // 공백인 경우
        if (postId.isEmpty() || postId.isBlank()) {
            logger.error("getPostById: 글 아이디는 필수입니다.");
            throw new MyBlogCommonException(
                    ResponseCode.NO_REQUIRED_REQUEST_PARAMETER,
                    "글 아이디는 필수입니다",
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
                logger.error("getPostById: 입력한 아이디에 해당하는 유저가 없습니다.");
                throw new MyBlogCommonException(
                        ResponseCode.NO_RESULT,
                        "입력한 아이디에 해당하는 블로그가 없습니다.",
                        new HashMap<>() {{
                            put("userId", userId);
                        }}
                );
            }

            postDto = postMapper.getPostById(userId, postId);
            logger.debug("getPostById: postDto: {}", postDto);

            // 존재하는 글인지 확인
            if (postDto == null) {
                logger.error("getPostById: 해당하는 글이 없습니다.");
                throw new MyBlogCommonException(
                        ResponseCode.NO_RESULT,
                        "해당하는 글이 없습니다.",
                        new HashMap<>() {{
                            put("postId", postId);
                        }}
                );
            }

            // 댓글 개수 업데이트
            postDto.setNumOfComments(postDto.getComments().size());

        } catch (SQLException e) {
            throw new MyBlogCommonException(
                    ResponseCode.SQL_ERROR,
                    "DB 조회 중 오류가 발생했습니다.",
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
