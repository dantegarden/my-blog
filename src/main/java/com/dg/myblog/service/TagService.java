package com.dg.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dg.myblog.model.entity.Tag;

import java.util.List;

public interface TagService extends IService<Tag> {
    List<Tag> listTagTop(Integer size);

    void deleteTagAndUpdateBlogs(Long id);
}
