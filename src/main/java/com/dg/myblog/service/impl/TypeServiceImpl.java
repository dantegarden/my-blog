package com.dg.myblog.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dg.myblog.dao.TypeMapper;
import com.dg.myblog.model.entity.Blog;
import com.dg.myblog.model.entity.Type;
import com.dg.myblog.service.TypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: lij
 * @create: 2020-03-02 16:16
 */
@Transactional
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

    private static final Long DEFAULT_TYPE_ID = 1L;

    @Override
    public List<Type> listTypeTop(Integer size) {
        List<Type> types = this.baseMapper.selectByTop(size);
        if(CollectionUtil.isNotEmpty(types)){
            types.forEach(type -> type.fetchPublishedBlogs());
        }
        return types;
    }

    @Override
    public void deleteTypeAndUpdateBlogs(Long id) {
        UpdateWrapper<Blog> updateWrapper = Wrappers.<Blog>update();
        updateWrapper.eq("type_id", id);
        new Blog().setTypeId(DEFAULT_TYPE_ID).update(updateWrapper); //原来type下的博文改到默认type下
        this.baseMapper.deleteById(id);
    }
}
