package com.dg.myblog.controller;

import com.dg.myblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 归档
 * @author: lij
 * @create: 2020-03-03 20:59
 */
@Controller
@RequestMapping("/archives")
public class ArchiveController {

    @Autowired
    private BlogService blogService;

    @GetMapping("")
    public String archives(Model model) {
        model.addAttribute("archiveMap", blogService.archiveBlog());
        model.addAttribute("blogCount", blogService.countBlog());
        return "archives";
    }

}
