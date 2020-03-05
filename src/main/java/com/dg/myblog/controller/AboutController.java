package com.dg.myblog.controller;

import com.dg.myblog.service.AboutmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * @description: 关于我
 * @author: lij
 * @create: 2020-03-03 21:47
 */
@Controller
public class AboutController {

    @Autowired
    private AboutmeService aboutmeService;

    @GetMapping("/about")
    public String about(Model model) {
        Map<String, List<String>> infoMap =  aboutmeService.getInfoMap();
        for (Map.Entry<String, List<String>> entry : infoMap.entrySet()) {
            model.addAttribute(entry.getKey(), entry.getValue());
        }

        return "about";
    }

}
