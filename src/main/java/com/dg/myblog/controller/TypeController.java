package com.dg.myblog.controller;

import com.dg.myblog.model.entity.Type;
import com.dg.myblog.service.BlogService;
import com.dg.myblog.service.TypeService;
import com.dg.myblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description: 类型
 * @author: lij
 * @create: 2020-03-03 17:14
 */
@Controller
@RequestMapping("/types")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/{id}")
    public String types(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        @PathVariable Long id, Model model) {
        List<Type> types = typeService.listTypeTop(10000);
        if (id == -1) {
            id = types.get(0).getId();
        }
        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlogByTypeId(id, pageNum));
        model.addAttribute("activeTypeId", id);
        return "types";
    }

}
