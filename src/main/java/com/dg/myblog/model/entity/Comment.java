package com.dg.myblog.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * t_comment
 * @author 
 */
@Data
@Accessors(chain = true)
@ToString
@TableName("t_comment")
public class Comment extends Model<Comment> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private Boolean adminComment;

    private String avatar;

    private String content;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private String email;

    private String nickname;

    private Long blogId;

    private Long parentCommentId;
    //重写setter
    public void setParentCommentId(Long parentCommentId){
        if(parentCommentId!=null && parentCommentId > 0){
            this.parentCommentId = parentCommentId;
        }
    }

    @TableField(exist = false)
    private List<Comment> replyComments = new ArrayList<>();

    @TableField(exist = false)
    private Comment parentComment;

    @TableField(exist = false)
    private Blog blog;

    public Blog fetchBlog(){
        Blog blog = new Blog();
        this.blog = blog.selectById(this.blogId);
        return this.blog;
    }

    public List<Comment> fetchReplyComments(){
        List<Comment> replyComments = new Comment().selectList(Wrappers.<Comment>query().eq("parent_comment_id", this.id));
        if(CollectionUtil.isNotEmpty(replyComments)){
            replyComments.forEach(comment -> {
                comment.setParentComment(this);
                comment.fetchReplyComments();
            });
            this.replyComments = replyComments;
        }
        return this.replyComments;
    }

}