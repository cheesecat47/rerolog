package com.github.cheesecat47.myBlog.user.service;

import com.github.cheesecat47.myBlog.common.exception.MyBlogCommonException;
import com.github.cheesecat47.myBlog.common.exception.ResponseCode;
import com.github.cheesecat47.myBlog.user.model.UserInfoDto;
import com.github.cheesecat47.myBlog.user.model.mapper.UserMapper;
import com.github.cheesecat47.myBlog.user.model.request.LoginRequestDto;
import com.github.cheesecat47.myBlog.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JWTUtil jwtUtil;

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
        log.debug("getUserInfo: userId: {}", userId);

        // 입력한 아이디가 없는 경우
        if (userId.isEmpty()) {
            String msg = "유저 아이디는 필수입니다";
            log.error("getUserInfo: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.NO_REQUIRED_REQUEST_PARAMETER,
                    msg,
                    new HashMap<>() {{
                        put("userId", userId);
                    }}
            );
        }

        // DB에서 유저 조회
        UserInfoDto userInfoDto;
        try {
            userInfoDto = userMapper.getUserInfo(userId.get());
            log.debug("getUserInfo: userInfo: {}", userInfoDto);

            // 조회된 유저가 없는 경우
            if (userInfoDto == null) {
                String msg = "입력한 아이디에 해당하는 유저가 없습니다";
                log.error("getUserInfo: {}", msg);
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
            log.error("getUserInfo: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.SQL_ERROR,
                    msg,
                    new HashMap<>() {{
                        put("userId", userId);
                        put("error", e.getMessage());
                    }}
            );
        }

        return userInfoDto;
    }

    @Override
    public UserInfoDto login(LoginRequestDto params) throws Exception {
        log.debug("login: params: {}", params);

        // 입력한 아이디가 없는 경우
        if (params.getUserId().isEmpty()) {
            String msg = "유저 아이디는 필수입니다";
            log.error("login: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.NO_REQUIRED_REQUEST_PARAMETER,
                    msg,
                    new HashMap<>() {{
                        put("userId", params.getUserId());
                    }}
            );
        }

        // 입력한 비밀번호가 없는 경우
        if (params.getUserPw().isEmpty()) {
            String msg = "유저 비밀번호는 필수입니다";
            log.error("login: {}", msg);
            throw new MyBlogCommonException(
                    ResponseCode.NO_REQUIRED_REQUEST_PARAMETER,
                    msg,
                    new HashMap<>() {{
                        put("userPw", params.getUserPw());
                    }}
            );
        }

        // DB에서 유저 조회
        UserInfoDto userInfoDto;
        try {
            // 아이디, 비밀번호 일치하는 유저 존재하는지 확인
            int count = userMapper.login(params);
            log.debug("login: count: {}", count);

            // 조회된 유저가 없는 경우
            if (count != 1) {
                String msg = "로그인에 실패했습니다";
                log.error("login: {}", msg);
                throw new MyBlogCommonException(
                        ResponseCode.UNAUTHORIZED,
                        msg,
                        new HashMap<>() {{
                            put("userId", params.getUserId());
                            put("userPw", params.getUserPw());
                        }}
                );
            }

            userInfoDto = userMapper.getUserInfo(params.getUserId());

            // 로그인 성공 시 토큰 발급
            String accessToken = jwtUtil.createAccessToken(params.getUserId());
            userInfoDto.setAccessToken(accessToken);

            String refreshToken = jwtUtil.createRefreshToken(params.getUserId());
            userInfoDto.setRefresnToken(refreshToken);

            // TODO: DB에 Refresh Token 업데이트

            log.debug("login: userInfoDto: {}", userInfoDto);
        } catch (SQLException e) {
            throw new MyBlogCommonException(
                    ResponseCode.SQL_ERROR,
                    "DB 조회 중 오류가 발생했습니다.",
                    new HashMap<>() {{
                        put("userId", params.getUserId());
                        put("userPw", params.getUserPw());
                        put("error", e.getMessage());
                    }}
            );
        }

        return userInfoDto;
    }
}