package com.github.cheesecat47.myBlog.blog.model.mapper;

import com.github.cheesecat47.myBlog.blog.model.BlogInfoDto;
import com.github.cheesecat47.myBlog.blog.model.request.CreateCategoryRequestDto;
import com.github.cheesecat47.myBlog.blog.model.request.DeleteCategoryRequestDto;
import com.github.cheesecat47.myBlog.blog.model.request.UpdateCategoryRequestDto;
import com.github.cheesecat47.myBlog.blog.model.response.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface BlogMapper {
    BlogInfoDto getBlogInfo(String userId) throws SQLException;

    List<CategoryDto> getCategories(String userId) throws SQLException;

    int createCategory(CreateCategoryRequestDto params) throws SQLException;

    int updateCategory(UpdateCategoryRequestDto params) throws SQLException;

    int deleteCategory(DeleteCategoryRequestDto params) throws SQLException;
}
