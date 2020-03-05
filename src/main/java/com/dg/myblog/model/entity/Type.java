package com.dg.myblog.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * t_type
 * @author 
 */
@Data
@Accessors(chain = true)
@ToString
@TableName("t_type")
public class Type extends Model<Type> implements Serializable {

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
        Blog blog = new Blog();
        List<Blog> blogs = blog.selectList(
                Wrappers.<Blog>query()
                        .eq("type_id", this.id)
                        .eq(BooleanUtil.isTrue(onlyPublished), "published", true)
        );
        this.blogs = blogs;
        return this.blogs;
    }
}