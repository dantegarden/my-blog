package com.dg.myblog.model.entity;

import java.io.Serializable;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.dg.myblog.global.model.BaseModel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * t_blog_tags
 * @author 
 */
@Data
@Accessors(chain = true)
@ToString
@TableName("t_blog_tags")
public class BlogTags extends BaseModel<BlogTags> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long blogsId;

    private Long tagsId;

    @TableField(exist = false)
    private Blog blog;
    @TableField(exist = false)
    private Tag tag;

    public Blog fetchBlog(){
        Blog blog = new Blog().setId(this.blogsId);
        this.blog = blog.selectById();
        return this.blog;
    }

    public Tag fetchTag(){
        Tag tag = new Tag().setId(this.tagsId);
        this.tag = tag.selectById();
        return this.tag;
    }
}