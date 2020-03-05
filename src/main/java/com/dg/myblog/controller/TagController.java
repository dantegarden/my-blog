package com.dg.myblog.controller;

import com.dg.myblog.model.entity.Tag;
import com.dg.myblog.service.BlogService;
import com.dg.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description: 标签
 * @author: lij
 * @create: 2020-03-03 18:20
 */
@Controller
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/{id}")
    public String tags(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                       @PathVariable Long id, Model model) {
        List<Tag> tags = tagService.listTagTop(10000);
        if (id == -1) {
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlogByTagsId(id, pageNum));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
