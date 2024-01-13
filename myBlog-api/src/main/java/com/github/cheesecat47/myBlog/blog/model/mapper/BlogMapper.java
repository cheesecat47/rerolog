package com.github.cheesecat47.myBlog.blog.model.mapper;

import com.github.cheesecat47.myBlog.blog.model.BlogInfoDto;
import com.github.cheesecat47.myBlog.blog.model.response.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface BlogMapper {
    BlogInfoDto getBlogInfo(String userId) throws SQLException;

    List<CategoryDto> getCategories(String userId) throws SQLException;
}