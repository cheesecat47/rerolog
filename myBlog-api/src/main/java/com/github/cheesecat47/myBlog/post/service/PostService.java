package com.github.cheesecat47.myBlog.post.service;

import com.github.cheesecat47.myBlog.post.model.PostDto;
import com.github.cheesecat47.myBlog.post.model.request.GetPostsRequest;

import java.util.List;

public interface PostService {
    List<PostDto> getPosts(GetPostsRequest params) throws Exception;

    PostDto getPostById(String userId, String postId) throws Exception;
}
