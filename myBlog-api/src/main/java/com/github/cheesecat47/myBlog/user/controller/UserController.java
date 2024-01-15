package com.github.cheesecat47.myBlog.user.controller;

import com.github.cheesecat47.myBlog.common.exception.MyBlogCommonException;
import com.github.cheesecat47.myBlog.common.exception.ResponseCode;
import com.github.cheesecat47.myBlog.user.model.AuthTokenDto;
import com.github.cheesecat47.myBlog.user.model.UserInfoDto;
import com.github.cheesecat47.myBlog.user.model.request.LoginRequestDto;
import com.github.cheesecat47.myBlog.user.model.request.LogoutRequestDto;
import com.github.cheesecat47.myBlog.user.model.request.RefreshRequestDto;
import com.github.cheesecat47.myBlog.user.model.response.GetUserInfoResponse;
import com.github.cheesecat47.myBlog.user.model.response.LoginResponseDto;
import com.github.cheesecat47.myBlog.user.model.response.LogoutResponseDto;
import com.github.cheesecat47.myBlog.user.model.response.RefreshResponseDto;
import com.github.cheesecat47.myBlog.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private final UserService userService;

    /**
     * 입력 받은 유저 아이디에 해당하는 유저 정보를 조회.
     *
     * @param userId 조회할 유저의 아이디. DB에서는 PK로 사용하는 int `id`와 구분하기 위해 유저 문자열 아이디를 `id_str`로 사용했으나, API 사용자에게는 pk id는 보일 필요가 없고, `id`가 더 익숙하므로 API 파라미터는 `id`로 받음.
     * @see MyBlogCommonException
     */
    @Operation(summary = "getUserInfo 유저 정보 조회", description = "아이디에 해당하는 유저 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유저 정보 조회 성공", content = {@Content(schema = @Schema(implementation = GetUserInfoResponse.class))}),
            @ApiResponse(responseCode = "400", description = "유저 정보 조회 실패"),
            @ApiResponse(responseCode = "404", description = "유저 정보 조회 실패"),
            @ApiResponse(responseCode = "500", description = "유저 정보 조회 실패")
    })
    @GetMapping(value = "/{userId}")
    public ResponseEntity<GetUserInfoResponse> getUserInfo(
            @Parameter(description = "유저 아이디") @PathVariable Optional<String> userId
    ) throws Exception {
        log.debug("getUserInfo: userId: {}", userId);

        GetUserInfoResponse response = new GetUserInfoResponse();

        UserInfoDto userInfoDto = userService.getUserInfo(userId);

        String msg = "유저 정보 조회 성공";
        log.info("getUserInfo: {}", msg);
        response.setCode(ResponseCode.NORMAL_SERVICE);
        response.setMessage(msg);
        response.setData(userInfoDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "login 로그인", description = "아이디와 비밀번호를 입력 받아 로그인 처리")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = {@Content(schema = @Schema(implementation = LoginResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "로그인 실패"),
            @ApiResponse(responseCode = "401", description = "로그인 실패"),
            @ApiResponse(responseCode = "500", description = "로그인 실패")
    })
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto params
    ) throws Exception {
        log.debug("login: params: {}", params);

        LoginResponseDto response = new LoginResponseDto();

        AuthTokenDto authTokenDto = userService.login(params);

        String msg = "로그인 성공";
        log.info("login: {}", msg);
        response.setCode(ResponseCode.NORMAL_SERVICE);
        response.setMessage(msg);
        response.setData(authTokenDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "logout 로그 아웃", description = "유저 로그 아웃 처리", security = {@SecurityRequirement(name = "Access Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그 아웃 성공", content = {@Content(schema = @Schema(implementation = LogoutResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "로그 아웃 실패"),
            @ApiResponse(responseCode = "401", description = "로그 아웃 실패"),
            @ApiResponse(responseCode = "500", description = "로그 아웃 실패")
    })
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDto> logout(
            @Parameter(description = "로그 아웃 하려는 유저 아이디") @RequestBody LogoutRequestDto params,
            HttpServletRequest request
    ) throws Exception {
        log.debug("logout: params: {}", params);

        LogoutResponseDto response = new LogoutResponseDto();

        params.setAccessToken(request.getHeader("Authorization"));
        userService.logout(params);

        String msg = "로그 아웃 성공";
        log.info("logout: {}", msg);
        response.setCode(ResponseCode.NORMAL_SERVICE);
        response.setMessage(msg);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "refresh 액세스 토큰 재발급", description = "만료된 액세스 토큰을 재발급.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "액세스 토큰 재발급 성공", content = {@Content(schema = @Schema(implementation = RefreshResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "액세스 토큰 재발급 실패"),
            @ApiResponse(responseCode = "401", description = "액세스 토큰 재발급 실패"),
            @ApiResponse(responseCode = "500", description = "액세스 토큰 재발급 실패")
    })
    @PostMapping(value = "/refresh")
    public ResponseEntity<RefreshResponseDto> refresh(
            @Parameter(description = "토큰 재발급 시 필요한 정보") @RequestBody RefreshRequestDto params
    ) throws Exception {
        log.debug("refresh: params: {}", params);

        RefreshResponseDto response = new RefreshResponseDto();

        AuthTokenDto authTokenDto = userService.refresh(params);

        String msg = "액세스 토큰 재발급 성공";
        log.info("login: {}", msg);
        response.setCode(ResponseCode.NORMAL_SERVICE);
        response.setMessage(msg);
        response.setData(authTokenDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
