package com.dg.myblog.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * t_tag
 * @author 
 */
@Data
@Accessors(chain = true)
@ToString
@TableName("t_tag")
public class Tag extends Model<Tag> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private String name;

    @TableField(exist = false)
    private List<Blog> blogs = new ArrayList<>();

    public List<Blog> fetchPublishedBlogs(){
        return fetchBlogs(true);
    }

    public List<Blog> fetchBlogs(Boolean onlyPublished){
        List<BlogTags> blogTagList = new BlogTags().selectList(Wrappers.<BlogTags>query().eq("tags_id", this.id));
        if(CollectionUtil.isNotEmpty(blogTagList)){
            if(BooleanUtil.isTrue(onlyPublished)){
                this.blogs = blogTagList.stream().map(BlogTags::fetchBlog).filter(blog -> blog.getPublished()).collect(Collectors.toList());
            }else{
                this.blogs = blogTagList.stream().map(BlogTags::fetchBlog).collect(Collectors.toList());
            }
        }
        return this.blogs;
    }
}