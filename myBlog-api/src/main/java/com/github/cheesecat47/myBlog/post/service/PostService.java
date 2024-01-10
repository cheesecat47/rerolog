package com.github.cheesecat47.myBlog.post.service;

import com.github.cheesecat47.myBlog.post.model.PostDto;

import java.util.List;

public interface PostService {
    List<PostDto> getPosts(String userId, String categoryId, String order, String offset, String limit) throws Exception;

    PostDto getPostById(String userId, String postId) throws Exception;
}
