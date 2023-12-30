package com.github.cheesecat47.myBlog.user.service;

import com.github.cheesecat47.myBlog.common.exception.WrongRequestParameterException;
import com.github.cheesecat47.myBlog.user.model.UserInfoDto;
import com.github.cheesecat47.myBlog.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
     * @param idStr 조회할 유저의 아이디.
     * @return 입력 받은 아이디에 해당하는 유저 정보 객체.
     * @throws SQLException                   DB 조회 중 오류 발생.
     * @throws WrongRequestParameterException 입력 받은 유저 아이디에 문제가 있는 경우 발생.
     */
    @Override
    public UserInfoDto getUserInfo(Optional<String> idStr) throws SQLException, WrongRequestParameterException {
        logger.info("getUserInfo: idStr: {}", idStr);

        // 입력한 아이디가 없는 경우
        if (idStr.isEmpty()) {
            logger.error("getUserInfo: 유저 아이디는 필수입니다.");
            throw new WrongRequestParameterException(
                    HttpStatus.BAD_REQUEST,
                    "유저 아이디는 필수입니다",
                    new HashMap<>() {{
                        put("id_str", idStr);
                    }}
            );
        }

        // DB에 유저 조회
        UserInfoDto userInfoDto = userMapper.getUserInfo(idStr.get());
        logger.info("getUserInfo: userInfo: {}", userInfoDto);

        // 조회된 유저가 없는 경우
        if (userInfoDto == null) {
            logger.error("getUserInfo: 입력한 아이디에 해당하는 유저가 없습니다.");
            throw new WrongRequestParameterException(
                    HttpStatus.NOT_FOUND,
                    "입력한 아이디에 해당하는 유저가 없습니다.",
                    new HashMap<>() {{
                        put("id_str", idStr);
                    }}
            );
        }

        return userInfoDto;
    }
}
