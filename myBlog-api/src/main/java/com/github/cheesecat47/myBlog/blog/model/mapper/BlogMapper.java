package com.github.cheesecat47.myBlog.blog.model.mapper;

import com.github.cheesecat47.myBlog.blog.model.BlogInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface BlogMapper {
    BlogInfoDto getBlogInfo(String userId) throws SQLException;

    List<String> getCategories(String idStr) throws SQLException;
}
