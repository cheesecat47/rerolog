package com.github.cheesecat47.myBlog.blog.service;

import com.github.cheesecat47.myBlog.blog.model.BlogInfoDto;

import java.util.List;

public interface BlogService {
    BlogInfoDto getBlogInfo(String idStr) throws Exception;

    List<String> getCategories(String idStr) throws Exception;
}
