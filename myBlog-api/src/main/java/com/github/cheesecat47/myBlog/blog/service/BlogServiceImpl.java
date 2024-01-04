package com.github.cheesecat47.myBlog.blog.service;

import com.github.cheesecat47.myBlog.blog.model.BlogInfoDto;
import com.github.cheesecat47.myBlog.blog.model.mapper.BlogMapper;
import com.github.cheesecat47.myBlog.common.exception.MyBlogCommonException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);
    private final BlogMapper blogMapper;

    /**
     * 입력 받은 유저 아이디에 해당하는 블로그 정보를 조회.
     *
     * @param idStr 조회할 블로그 주인 유저의 아이디.
     * @return 입력 받은 아이디에 해당하는 블로그 정보 객체.
     * @throws SQLException          DB 조회 중 오류 발생.
     * @throws MyBlogCommonException 입력 받은 유저 아이디에 문제가 있는 경우 발생.
     */
    @Override
    public BlogInfoDto getBlogInfo(String idStr) throws Exception {
        logger.info("getBlogInfo: idStr: {}", idStr);

        // 공백인 경우
        if (idStr.isEmpty() || idStr.isBlank()) {
            logger.error("getBlogInfo: 유저 아이디는 필수입니다.");
            throw new MyBlogCommonException(
                    HttpStatus.BAD_REQUEST,
                    "유저 아이디는 필수입니다",
                    new HashMap<>() {{
                        put("id", idStr);
                    }}
            );
        }

        // DB에서 조회
        BlogInfoDto blogInfoDto;
        try {
            blogInfoDto = blogMapper.getBlogInfo(idStr);
            logger.info("getBlogInfo: blogInfo: {}", blogInfoDto);

            // 조회된 블로그가 없는 경우
            if (blogInfoDto == null) {
                logger.error("getBlogInfo: 입력한 아이디에 해당하는 블로그 정보가 없습니다.");

                throw new MyBlogCommonException(
                        HttpStatus.NOT_FOUND,
                        "입력한 아이디에 해당하는 블로그가 없습니다.",
                        new HashMap<>() {{
                            put("id", idStr);
                        }}
                );
            }
        } catch (SQLException e) {
            throw new MyBlogCommonException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "DB 조회 중 오류가 발생했습니다.",
                    new HashMap<>() {{
                        put("id", idStr);
                        put("error", e.getMessage());
                    }}
            );
        }

        return blogInfoDto;
    }

    /**
     * 입력 받은 유저 아이디에 해당하는 블로그의 게시판 목록을 조회.
     *
     * @param idStr 조회할 블로그 주인 유저의 아이디.
     * @return 입력 받은 아이디에 해당하는 게시판 이름 배열.
     * @throws SQLException          DB 조회 중 오류 발생.
     * @throws MyBlogCommonException 입력 받은 유저 아이디에 문제가 있는 경우 발생.
     */
    @Override
    public List<String> getCategories(String idStr) throws Exception {
        logger.info("getCategories: idStr: {}", idStr);

        // 공백인 경우
        if (idStr.isEmpty() || idStr.isBlank()) {
            logger.error("getCategories: 유저 아이디는 필수입니다.");
            throw new MyBlogCommonException(
                    HttpStatus.BAD_REQUEST,
                    "유저 아이디는 필수입니다",
                    new HashMap<>() {{
                        put("id", idStr);
                    }}
            );
        }

        // DB에서 조회
        List<String> categoryNames;
        try {
            // 존재하지 않는 블로그인 경우
            BlogInfoDto blogInfoDto = blogMapper.getBlogInfo(idStr);
            if (blogInfoDto == null) {
                logger.error("getCategories: 입력한 아이디에 해당하는 블로그가 없습니다.");
                throw new MyBlogCommonException(
                        HttpStatus.NOT_FOUND,
                        "입력한 아이디에 해당하는 블로그가 없습니다.",
                        new HashMap<>() {{
                            put("id", idStr);
                        }}
                );
            }

            categoryNames = blogMapper.getCategories(idStr);
            logger.info("getCategories: categoryNames: {}", categoryNames);
        } catch (SQLException e) {
            throw new MyBlogCommonException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "DB 조회 중 오류가 발생했습니다.",
                    new HashMap<>() {{
                        put("id", idStr);
                        put("error", e.getMessage());
                    }}
            );
        }

        return categoryNames;
    }
}
