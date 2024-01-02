package com.github.cheesecat47.myBlog.blog.model.mapper;

import com.github.cheesecat47.myBlog.blog.model.BlogInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface BlogMapper {
    BlogInfoDto getBlogInfo(String idStr) throws SQLException;
}
