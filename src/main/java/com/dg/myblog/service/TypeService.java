package com.dg.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dg.myblog.model.entity.Type;

import java.util.List;

public interface TypeService extends IService<Type> {
    List<Type> listTypeTop(Integer size);

    void deleteTypeAndUpdateBlogs(Long id);
}
