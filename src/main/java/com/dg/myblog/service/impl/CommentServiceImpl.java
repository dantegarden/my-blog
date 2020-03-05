package com.dg.myblog.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dg.myblog.dao.CommentMapper;
import com.dg.myblog.model.entity.Comment;
import com.dg.myblog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: lij
 * @create: 2020-03-02 16:14
 */
@Transactional
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        QueryWrapper<Comment> queryWrapper = Wrappers.<Comment>query()
                .eq("blog_id", blogId)
                .isNull("parent_comment_id")
                .orderByDesc("create_time");
        List<Comment> comments = new Comment().selectList(queryWrapper);
        if(CollectionUtil.isNotEmpty(comments)){
            comments.forEach(_comment -> _comment.fetchReplyComments());
        }
        return comments;
    }

    private void combineChildren(List<Comment> commentsView) {
        for (Comment comment : commentsView) {
            List<Comment> tempReplys = new ArrayList<>();
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1, tempReplys);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
        }
    }

    private void recursively(Comment comment, List<Comment> tempReplys) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply, tempReplys);
                }
            }
        }
    }
}
