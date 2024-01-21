package com.github.cheesecat47.myBlog.blog.service;

import com.github.cheesecat47.myBlog.blog.model.BlogInfoDto;
import com.github.cheesecat47.myBlog.blog.model.mapper.BlogMapper;
import com.github.cheesecat47.myBlog.blog.model.request.CreateCategoryRequestDto;
import com.github.cheesecat47.myBlog.blog.model.request.UpdateCategoryRequestDto;
import com.github.cheesecat47.myBlog.blog.model.response.CategoryDto;
import com.github.cheesecat47.myBlog.common.exception.MyBlogCommonException;
import com.github.cheesecat47.myBlog.common.exception.ResponseCode;
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
public class BlogServiceImpl implements BlogService {

    private final BlogMapper blogMapper;
    private final JWTUtil jwtUtil;

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

    @Override
    public void createCategory(CreateCategoryRequestDto params) throws Exception {
        log.debug("createCategory: params: {}", params);

        // 공백인 경우
        if (params.getBlogId().isEmpty() || params.getBlogId().isBlank()) {
            String msg = "블로그 아이디는 필수입니다";
            log.error("createCategory: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.NO_REQUIRED_REQUEST_PARAMETER,
                    msg,
                    new HashMap<>() {{
                        put("blogId", params.getBlogId());
                    }}
            );
        }

        if (params.getAccessToken() == null || !jwtUtil.checkToken(params.getAccessToken(), params.getBlogId())) {
            String msg = "로그인 후 이용 바랍니다";
            log.error("createCategory: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.UNAUTHORIZED,
                    msg,
                    new HashMap<>() {{
                        put("blogId", params.getBlogId());
                        put("Authorization", params.getAccessToken());
                    }}
            );
        }

        // DB에 삽입
        try {
            // 존재하지 않는 블로그인 경우
            BlogInfoDto blogInfoDto = blogMapper.getBlogInfo(params.getBlogId());
            log.debug("createCategory: blogInfo: {}", blogInfoDto);

            if (blogInfoDto == null) {
                String msg = "입력한 아이디에 해당하는 블로그가 없습니다";
                log.error("createCategory: {}", msg);
                throw new MyBlogCommonException(
                        ResponseCode.NO_RESULT,
                        msg,
                        new HashMap<>() {{
                            put("blogId", params.getBlogId());
                        }}
                );
            }

            // TODO: 게시판 이름 중복 체크

            int count = blogMapper.createCategory(params);
            if (count != 1) {
                String msg = "게시판 생성에 실패했습니다";
                log.error("createCategory: {}", msg);
                throw new MyBlogCommonException(
                        ResponseCode.INTERNAL_SERVER_ERROR,
                        msg,
                        new HashMap<>() {{
                            put("blogId", params.getBlogId());
                            put("categoryName", params.getCategoryName());
                        }}
                );
            }
            log.debug("createCategory: 게시판 생성 성공");
        } catch (SQLException e) {
            String msg = "DB 삽입 중 오류가 발생했습니다";
            log.error("createCategory: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.SQL_ERROR,
                    msg,
                    new HashMap<>() {{
                        put("blogId", params.getBlogId());
                        put("error", e.getMessage());
                    }}
            );
        }
    }

    @Override
    public void updateCategory(UpdateCategoryRequestDto params) throws Exception {
        log.debug("updateCategory: params: {}", params);

        if (params.getNewCategoryName() == null) {
            String msg = "새 게시판 이름을 입력하세요";
            log.error("updateCategory: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.NO_REQUIRED_REQUEST_PARAMETER,
                    msg,
                    new HashMap<>() {{
                        put("newCategoryName", params.getNewCategoryName());
                    }}
            );
        }

        if (params.getAccessToken() == null || !jwtUtil.checkToken(params.getAccessToken(), params.getBlogId())) {
            String msg = "로그인 후 이용 바랍니다";
            log.error("updateCategory: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.UNAUTHORIZED,
                    msg,
                    new HashMap<>() {{
                        put("blogId", params.getBlogId());
                        put("Authorization", params.getAccessToken());
                    }}
            );
        }

        try {
            // 존재하지 않는 블로그인 경우
            BlogInfoDto blogInfoDto = blogMapper.getBlogInfo(params.getBlogId());
            log.debug("updateCategory: blogInfo: {}", blogInfoDto);

            if (blogInfoDto == null) {
                String msg = "입력한 아이디에 해당하는 블로그가 없습니다";
                log.error("updateCategory: {}", msg);
                throw new MyBlogCommonException(
                        ResponseCode.NO_RESULT,
                        msg,
                        new HashMap<>() {{
                            put("blogId", params.getBlogId());
                        }}
                );
            }

            // TODO: 게시판 이름 체크

            int count = blogMapper.updateCategory(params);
            if (count != 1) {
                String msg = "게시판 정보 변경에 실패했습니다";
                log.error("updateCategory: {}", msg);
                throw new MyBlogCommonException(
                        ResponseCode.INTERNAL_SERVER_ERROR,
                        msg,
                        new HashMap<>() {{
                            put("blogId", params.getBlogId());
                            put("categoryName", params.getCategoryName());
                            put("newCategoryName", params.getNewCategoryName());
                        }}
                );
            }
            log.debug("updateCategory: 게시판 생성 성공");
        } catch (SQLException e) {
            String msg = "DB 업데이트 중 오류가 발생했습니다";
            log.error("createCategory: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.SQL_ERROR,
                    msg,
                    new HashMap<>() {{
                        put("blogId", params.getBlogId());
                        put("error", e.getMessage());
                    }}
            );
        }
    }
}
