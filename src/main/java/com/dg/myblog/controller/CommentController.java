package com.dg.myblog.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.dg.myblog.global.utils.SessionUtils;
import com.dg.myblog.model.entity.Comment;
import com.dg.myblog.model.entity.User;
import com.dg.myblog.service.BlogService;
import com.dg.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @description: 评论
 * @author: lij
 * @create: 2020-03-03 15:32
 */
@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        if(CollectionUtil.isNotEmpty(comments)){
            comments = commentService.eachComment(comments);
        }
        model.addAttribute("comments", comments);
        return "blog :: commentList";
    }

    @PostMapping("")
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlogId(blogId);
        User user = SessionUtils.getCurrentUser();
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
        }
        commentService.save(comment);
        return "redirect:/comments/" + blogId;
    }
}
