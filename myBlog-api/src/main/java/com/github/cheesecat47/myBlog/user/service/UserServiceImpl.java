package com.github.cheesecat47.myBlog.user.service;

import com.github.cheesecat47.myBlog.common.exception.MyBlogCommonException;
import com.github.cheesecat47.myBlog.common.exception.ResponseCode;
import com.github.cheesecat47.myBlog.user.model.UserInfoDto;
import com.github.cheesecat47.myBlog.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserMapper userMapper;

    /**
     * 입력 받은 유저 아이디에 해당하는 유저 정보를 조회.
     *
     * @param userId 조회할 유저의 아이디.
     * @return 입력 받은 아이디에 해당하는 유저 정보 객체.
     * @throws SQLException          DB 조회 중 오류 발생.
     * @throws MyBlogCommonException 입력 받은 유저 아이디에 문제가 있는 경우 발생.
     */
    @Override
    public UserInfoDto getUserInfo(Optional<String> userId) throws Exception {
        logger.info("getUserInfo: userId: {}", userId);

        // 입력한 아이디가 없는 경우
        if (userId.isEmpty()) {
            logger.error("getUserInfo: 유저 아이디는 필수입니다.");
            throw new MyBlogCommonException(
                    ResponseCode.NO_REQUIRED_REQUEST_PARAMETER,
                    "유저 아이디는 필수입니다",
                    new HashMap<>() {{
                        put("userId", userId);
                    }}
            );
        }

        // DB에서 유저 조회
        UserInfoDto userInfoDto;
        try {
            userInfoDto = userMapper.getUserInfo(userId.get());
            logger.info("getUserInfo: userInfo: {}", userInfoDto);

            // 조회된 유저가 없는 경우
            if (userInfoDto == null) {
                logger.error("getUserInfo: 입력한 아이디에 해당하는 유저가 없습니다.");
                throw new MyBlogCommonException(
                        ResponseCode.NO_RESULT,
                        "입력한 아이디에 해당하는 유저가 없습니다.",
                        new HashMap<>() {{
                            put("userId", userId);
                        }}
                );
            }
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

        return userInfoDto;
    }
}
