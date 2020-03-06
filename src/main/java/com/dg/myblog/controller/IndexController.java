package com.dg.myblog.controller;

import com.dg.myblog.model.entity.Blog;
import com.dg.myblog.service.BlogService;
import com.dg.myblog.service.CommentService;
import com.dg.myblog.service.TagService;
import com.dg.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: lij
 * @create: 2020-03-02 15:28
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "true";
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, Model model) {
        model.addAttribute("page", blogService.listBlog(pageNum));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlog(query, pageNum));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        Blog blog = blogService.getAndConvert(id);
        model.addAttribute("blog", blog);
        model.addAttribute("comments", commentService.eachComment(blog.getComments()));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        return "common/footer :: newblogList";
    }
}
