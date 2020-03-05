package com.dg.myblog.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dg.myblog.dao.TagMapper;
import com.dg.myblog.model.entity.BlogTags;
import com.dg.myblog.model.entity.Tag;
import com.dg.myblog.service.TagService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: lij
 * @create: 2020-03-02 16:15
 */
@Transactional
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Override
    public List<Tag> listTagTop(Integer size) {
        List<Tag> tags = this.baseMapper.selectByTop(size);
        if(CollectionUtil.isNotEmpty(tags)){
            tags.forEach(tag -> tag.fetchPublishedBlogs());
        }
        return tags;
    }

    @Override
    public void deleteTagAndUpdateBlogs(Long id) {
        BlogTags blogTags = new BlogTags();
        blogTags.delete(Wrappers.<BlogTags>query().eq("tags_id", id)); //清理多对多
        this.baseMapper.deleteById(id);
    }
}
