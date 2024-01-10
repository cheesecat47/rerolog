package com.github.cheesecat47.myBlog.post.model.mapper;

import com.github.cheesecat47.myBlog.post.model.PostDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface PostMapper {
    List<PostDto> getPosts(String userId, String categoryId, String order, String offset, String limit) throws SQLException;
}
