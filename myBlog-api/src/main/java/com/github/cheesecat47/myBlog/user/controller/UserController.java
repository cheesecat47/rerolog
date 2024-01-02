package com.github.cheesecat47.myBlog.user.controller;

import com.github.cheesecat47.myBlog.common.exception.MyBlogCommonException;
import com.github.cheesecat47.myBlog.user.model.UserInfoDto;
import com.github.cheesecat47.myBlog.user.model.response.GetUserInfoResponse;
import com.github.cheesecat47.myBlog.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 유저 컨트롤러
 *
 * @author cheesecat47
 */
@RestController
@Tag(name = "유저 API")
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    /**
     * 입력 받은 유저 아이디에 해당하는 유저 정보를 조회.
     *
     * @param idStr 조회할 유저의 아이디. DB에서는 PK로 사용하는 int `id`와 구분하기 위해 유저 문자열 아이디를 `id_str`로 사용했으나, API 사용자에게는 pk id는 보일 필요가 없고, `id`가 더 익숙하므로 API 파라미터는 `id`로 받음.
     * @see MyBlogCommonException
     */
    @Operation(summary = "getUserInfo 유저 정보 조회", description = "아이디에 해당하는 유저 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유저 정보 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GetUserInfoResponse.class))}),
            @ApiResponse(responseCode = "400", description = "유저 정보 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MyBlogCommonException.class))}),
            @ApiResponse(responseCode = "404", description = "유저 정보 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MyBlogCommonException.class))}),
            @ApiResponse(responseCode = "500", description = "유저 정보 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MyBlogCommonException.class))})
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<GetUserInfoResponse> getUserInfo(
            @Parameter(description = "로그인 아이디") @PathVariable(value = "id") Optional<String> idStr
    ) throws Exception {
        logger.info("getUserInfo");
        GetUserInfoResponse response = new GetUserInfoResponse();

        UserInfoDto userInfoDto = userService.getUserInfo(idStr);

        response.setStatus(HttpStatus.OK);
        response.setMessage("유저 정보 조회 성공");
        response.setData(userInfoDto);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
