package com.github.cheesecat47.myBlog.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 게시판 컨트롤러
 *
 * @author cheesecat47
 */
@RestController
@Tag(name = "게시판 API")
@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    /**
     * 로거 선언
     */
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Operation(summary = "게시판 목록 조회", description = "게시판 이름 목록 배열 타입으로 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "")
    public ResponseEntity<Map<String, Object>> getBoardList() {
        Map<String, Object> result = new HashMap<>();

        /* TODO: service에서 게시판 목록 얻기
            이 경우는 게시판이 하나도 없으면 길이가 0인 리스트를 반환하므로
            400을 반환하지 않음
        */
        List<?> boardList = new ArrayList<>(); // service.getBoardList();
        logger.debug("boardList: {}", boardList);

        result.put("status", HttpStatus.OK);
        result.put("message", "게시판 목록 조회 성공");
        result.put("data", boardList);
        logger.debug("result: {}", result);

        return ResponseEntity.status((HttpStatusCode) result.get("status")).body(result);
    }
}
