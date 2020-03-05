package com.dg.myblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dg.myblog.model.entity.BlogTags;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BlogTagsMapper extends BaseMapper<BlogTags> {

}