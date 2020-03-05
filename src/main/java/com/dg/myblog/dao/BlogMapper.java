package com.dg.myblog.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dg.myblog.model.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogMapper extends BaseMapper<Blog> {
    int updateViews(@Param("id") Long id);

    IPage<Blog> selectBolgByTagId(Page<Blog> page, @Param(Constants.WRAPPER) Wrapper<Blog> wrapper);

    List<String> selectGroupMonth();

}