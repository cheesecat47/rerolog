package com.github.cheesecat47.myBlog.blog.service;

import com.github.cheesecat47.myBlog.blog.model.BlogInfoDto;
import com.github.cheesecat47.myBlog.blog.model.request.CreateCategoryRequestDto;
import com.github.cheesecat47.myBlog.blog.model.response.CategoryDto;

import java.util.List;

public interface BlogService {
    BlogInfoDto getBlogInfo(String userId) throws Exception;

    List<CategoryDto> getCategories(String userId) throws Exception;

    void createCategory(CreateCategoryRequestDto params) throws Exception;
}
