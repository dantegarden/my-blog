package com.dg.myblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dg.myblog.model.entity.Aboutme;
import com.dg.myblog.model.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AboutmeMapper extends BaseMapper<Aboutme> {
}