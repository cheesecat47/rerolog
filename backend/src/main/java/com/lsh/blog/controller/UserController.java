package com.lsh.blog.controller;

import com.lsh.blog.dto.UserDto;
import com.lsh.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public UserDto get(@PathVariable int userId) throws Exception {
        return userService.get(userId);
    }
}
