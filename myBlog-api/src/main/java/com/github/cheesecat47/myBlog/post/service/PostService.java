package com.github.cheesecat47.myBlog.post.service;

import com.github.cheesecat47.myBlog.post.model.PostDto;

import java.util.List;

public interface PostService {
    List<PostDto> getPosts(String userId) throws Exception;
}
