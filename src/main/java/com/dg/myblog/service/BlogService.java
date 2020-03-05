package com.dg.myblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dg.myblog.model.entity.Blog;
import com.dg.myblog.vo.BlogQuery;

import java.util.List;
import java.util.Map;

public interface BlogService extends IService<Blog> {
    IPage<Blog> listBlog(Integer pageNum);

    IPage<Blog> listBlog(String query, Integer pageNum);

    IPage<Blog> listBlog(Integer pageNum, BlogQuery blogQuery);

    List<Blog> listRecommendBlogTop(Integer size);

    Blog getAndConvert(Long id);

    IPage<Blog> listBlogByTagsId(Long tagsId, Integer pageNum);

    Map<String, List<Blog>> archiveBlog();

    Long countBlog();

    void deleteBlog(Long id);

    boolean saveOrUpdateBlog(Blog blog);

    IPage<Blog> listBlogByTypeId(Long typeId, Integer pageNum);
}
