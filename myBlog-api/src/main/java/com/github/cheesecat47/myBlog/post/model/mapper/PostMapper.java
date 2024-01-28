package com.github.cheesecat47.myBlog.post.model.mapper;

import com.github.cheesecat47.myBlog.post.model.PostDto;
import com.github.cheesecat47.myBlog.post.model.request.CreatePostRequestDto;
import com.github.cheesecat47.myBlog.post.model.request.GetPostsRequest;
import com.github.cheesecat47.myBlog.post.model.request.UpdatePostRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface PostMapper {
    List<PostDto> getPosts(GetPostsRequest params) throws SQLException;

    PostDto getPostByTitle(String postTitle) throws SQLException;

    int createPost(CreatePostRequestDto params) throws SQLException;

    int updatePost(UpdatePostRequestDto params) throws SQLException;
}
