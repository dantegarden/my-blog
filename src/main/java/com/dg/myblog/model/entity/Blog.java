package com.dg.myblog.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.dg.myblog.global.model.BaseModel;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * t_blog
 * @author 
 */
@Data
@Accessors(chain = true)
@ToString
@TableName(value = "t_blog")
public class Blog extends BaseModel<Blog> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private Boolean appreciation;

    private Boolean commentabled;

    private String content;

    private String contentView;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private String description;

    private String firstPicture;

    private String flag;

    private Boolean published;

    private Boolean recommend;

    private Boolean shareStatement;

    private String title;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private Integer views;

    private Long typeId;

    private Long userId;

    //多对一
    @TableField(exist = false)
    private Type type;

    //多对多
    @TableField(exist = false)
    private List<Tag> tags = Lists.newArrayList();

    //多对一
    @TableField(exist = false)
    private User user;

    //一对多
    @TableField(exist = false)
    private List<Comment> comments = new ArrayList<>();

    @TableField(exist = false)
    private String tagIds;

    public User fetchUser(){
        User user = new User().setId(this.userId);
        this.user = user.selectById();
        return this.user;
    }

    public List<Comment> fetchComments(Boolean isFetchAllComments){
        Comment comment = new Comment();
        QueryWrapper<Comment> queryWrapper = Wrappers.<Comment>query()
                .eq("blog_id", this.id)
                .isNull("parent_comment_id")
                .orderByDesc("create_time");
        this.comments = comment.selectList(queryWrapper);
        if(isFetchAllComments && CollectionUtil.isNotEmpty(this.comments)){
            this.comments.forEach(_comment -> _comment.fetchReplyComments());
        }
        return this.comments;
    }

    public Type fetchType(){
        Type type = new Type().setId(this.typeId);
        this.type = type.selectById();
        return this.type;
    }

    public List<Tag> fetchTags(){
        BlogTags blogTags = new BlogTags().setBlogsId(this.id);
        List<BlogTags> blogTagList = blogTags.selectListByExample();
        if(CollectionUtil.isNotEmpty(blogTagList)){
            this.tags = blogTagList.stream().map(BlogTags::fetchTag).collect(Collectors.toList());
        }
        return this.tags;
    }

    //加载所有的关联关系
    public void loadRelations(Boolean isFetchAll){
        this.fetchComments(isFetchAll);
        this.fetchTags();
        this.fetchType();
        this.fetchUser();
        this.tagsToIds(this.tags);
    }

    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            List<Long> tagIdList = tags.stream().map(Tag::getId).collect(Collectors.toList());
            this.tagIds = StrUtil.join(",", tagIdList);
        }
        return this.tagIds;
    }


}