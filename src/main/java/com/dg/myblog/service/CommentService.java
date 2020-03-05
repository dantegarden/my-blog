package com.dg.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dg.myblog.model.entity.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {
    /**循环每个顶级的评论节点*/
    List<Comment> eachComment(List<Comment> comments);

    List<Comment> listCommentByBlogId(Long blogId);
}
