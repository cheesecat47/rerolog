package com.github.cheesecat47.myBlog.blog.service;

import com.github.cheesecat47.myBlog.blog.model.BlogInfoDto;
import com.github.cheesecat47.myBlog.blog.model.mapper.BlogMapper;
import com.github.cheesecat47.myBlog.blog.model.response.CategoryDto;
import com.github.cheesecat47.myBlog.common.exception.MyBlogCommonException;
import com.github.cheesecat47.myBlog.common.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogMapper blogMapper;

    /**
     * 입력 받은 유저 아이디에 해당하는 블로그 정보를 조회.
     *
     * @param userId 조회할 블로그 주인 유저의 아이디.
     * @return 입력 받은 아이디에 해당하는 블로그 정보 객체.
     * @throws SQLException          DB 조회 중 오류 발생.
     * @throws MyBlogCommonException 입력 받은 유저 아이디에 문제가 있는 경우 발생.
     */
    @Override
    public BlogInfoDto getBlogInfo(String userId) throws Exception {
        log.debug("getBlogInfo: userId: {}", userId);

        // 공백인 경우
        if (userId.isEmpty() || userId.isBlank()) {
            String msg = "유저 아이디는 필수입니다";
            log.error("getBlogInfo: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.NO_REQUIRED_REQUEST_PARAMETER,
                    msg,
                    new HashMap<>() {{
                        put("userId", userId);
                    }}
            );
        }

        // DB에서 조회
        BlogInfoDto blogInfoDto;
        try {
            blogInfoDto = blogMapper.getBlogInfo(userId);
            log.debug("getBlogInfo: blogInfo: {}", blogInfoDto);

            // 조회된 블로그가 없는 경우
            if (blogInfoDto == null) {
                String msg = "입력한 아이디에 해당하는 블로그 정보가 없습니다";
                log.error("getBlogInfo: {}", msg);
                throw new MyBlogCommonException(
                        ResponseCode.NO_RESULT,
                        msg,
                        new HashMap<>() {{
                            put("userId", userId);
                        }}
                );
            }
        } catch (SQLException e) {
            String msg = "DB 조회 중 오류가 발생했습니다";
            log.error("getBlogInfo: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.SQL_ERROR,
                    msg,
                    new HashMap<>() {{
                        put("userId", userId);
                        put("error", e.getMessage());
                    }}
            );
        }

        return blogInfoDto;
    }

    /**
     * 입력 받은 유저 아이디에 해당하는 블로그의 게시판 목록을 조회.
     *
     * @param userId 조회할 블로그 주인 유저의 아이디.
     * @return 입력 받은 아이디에 해당하는 게시판 이름 배열.
     * @throws SQLException          DB 조회 중 오류 발생.
     * @throws MyBlogCommonException 입력 받은 유저 아이디에 문제가 있는 경우 발생.
     */
    @Override
    public List<CategoryDto> getCategories(String userId) throws Exception {
        log.debug("getCategories: userId: {}", userId);

        // 공백인 경우
        if (userId.isEmpty() || userId.isBlank()) {
            String msg = "유저 아이디는 필수입니다";
            log.error("getCategories: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.NO_REQUIRED_REQUEST_PARAMETER,
                    msg,
                    new HashMap<>() {{
                        put("userId", userId);
                    }}
            );
        }

        // DB에서 조회
        List<CategoryDto> categoryNames;
        try {
            // 존재하지 않는 블로그인 경우
            BlogInfoDto blogInfoDto = blogMapper.getBlogInfo(userId);
            log.debug("getBlogInfo: blogInfo: {}", blogInfoDto);

            if (blogInfoDto == null) {
                String msg = "입력한 아이디에 해당하는 블로그가 없습니다";
                log.error("getCategories: {}", msg);
                throw new MyBlogCommonException(
                        ResponseCode.NO_RESULT,
                        msg,
                        new HashMap<>() {{
                            put("userId", userId);
                        }}
                );
            }

            categoryNames = blogMapper.getCategories(userId);
            log.debug("getCategories: size: {}", categoryNames.size());
        } catch (SQLException e) {
            String msg = "DB 조회 중 오류가 발생했습니다";
            log.error("getCategories: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.SQL_ERROR,
                    msg,
                    new HashMap<>() {{
                        put("userId", userId);
                        put("error", e.getMessage());
                    }}
            );
        }

        return categoryNames;
    }
}
