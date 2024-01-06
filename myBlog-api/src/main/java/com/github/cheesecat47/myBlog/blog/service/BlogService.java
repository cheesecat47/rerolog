package com.github.cheesecat47.myBlog.blog.service;

import com.github.cheesecat47.myBlog.blog.model.BlogInfoDto;

public interface BlogService {
    BlogInfoDto getBlogInfo(String idStr) throws Exception;
}
