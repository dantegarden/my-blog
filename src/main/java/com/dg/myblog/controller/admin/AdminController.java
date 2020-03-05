package com.dg.myblog.controller.admin;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dg.myblog.global.utils.SessionUtils;
import com.dg.myblog.model.entity.Blog;
import com.dg.myblog.model.entity.Tag;
import com.dg.myblog.model.entity.Type;
import com.dg.myblog.model.entity.User;
import com.dg.myblog.service.BlogService;
import com.dg.myblog.service.TagService;
import com.dg.myblog.service.TypeService;
import com.dg.myblog.vo.BlogQuery;
import com.dg.myblog.vo.BlogVO;
import com.dg.myblog.vo.TagVO;
import com.dg.myblog.vo.TypeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @description: 后台管理
 * @author: lij
 * @create: 2020-03-04 11:57
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @Autowired
    private TypeService typeService;

    /*************************** blogs ******************************/

    /**博客 列表页**/
    @GetMapping("/blogs")
    public String blogs(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        BlogQuery blogQuery, Model model) {
        model.addAttribute("types", typeService.list());
        model.addAttribute("page", blogService.listBlog(pageNum, blogQuery));
        return "admin/blogs";
    }

    /**博客 搜索**/
    @PostMapping("/blogs/search")
    public String search(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                         BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageNum, blog));
        return "admin/blogs :: blogList";
    }

    /**博客 新增页**/
    @GetMapping("/blogs/input")
    public String input(Model model) {
        model.addAttribute("types", typeService.list());
        model.addAttribute("tags", tagService.list());
        model.addAttribute("blog", new Blog());
        return "admin/blogs-input";
    }

    /**博客 修改页**/
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("types", typeService.list());
        model.addAttribute("tags", tagService.list());
        Blog blog = blogService.getById(id);
        blog.loadRelations(false);
        model.addAttribute("blog", blog);
        return "admin/blogs-input";
    }

    /**博客 提交**/
    @PostMapping("/blogs")
    public String post(BlogVO vo, RedirectAttributes attributes, HttpSession session) {
        vo.setUserId(SessionUtils.getCurrentUserId());
        Blog blog = new Blog();
        BeanUtils.copyProperties(vo, blog);
        boolean success = blogService.saveOrUpdateBlog(blog);
        attributes.addFlashAttribute("message", success?"操作成功":"操作失败");

        return "redirect:/admin/blogs";
    }

    /**博客 删除**/
    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }

    /*************************** types ******************************/

    /**类型 列表页**/
    @GetMapping("/types")
    public String types(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        Model model) {
        model.addAttribute("page",
                typeService.getBaseMapper().selectPage(new Page<>(pageNum, 10, true), null));
        return "admin/types";
    }

    /**类型 新增页 **/
    @GetMapping("/types/input")
    public String typesInput(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    /**类型 修改页 **/
    @GetMapping("/types/{id}/input")
    public String typesEditInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getById(id));
        return "admin/types-input";
    }

    /**类型 新增提交**/
    @PostMapping("/types")
    public String typesPost(@Valid TypeVO vo, BindingResult result, Model model, RedirectAttributes attributes) {
        // 如果有数据校验错误，返回表单页面
        if (result.hasErrors()) {
            model.addAttribute("message", result.getFieldError().getDefaultMessage());
            model.addAttribute("type", new Type());
            return "admin/types-input";
        }
        Type oldType = typeService.getOne(Wrappers.<Type>query().eq("name", vo.getName()));
        if (oldType != null) {
            model.addAttribute("message", "不能添加重复的分类");
            model.addAttribute("type", new Type());
            return "admin/types-input";
        }

        Type type = new Type();
        BeanUtils.copyProperties(vo, type);
        boolean success = typeService.saveOrUpdate(type);
        //重定向带参数，并且隐藏参数，链接地址上不直接暴露，但是能且只能在重定向的页面获取参数值
        attributes.addFlashAttribute("message", success ? "新增成功": "新增失败");
        return "redirect:/admin/types";
    }

    /**类型 修改提交**/
    @PostMapping("/types/{id}")
    public String typesEditPost(@Valid TypeVO vo, BindingResult result,
                           @PathVariable Long id, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("message", result.getFieldError().getDefaultMessage());
            model.addAttribute("type", vo);
            return "admin/types-input";
        }
        Type oldType = typeService.getOne(Wrappers.<Type>query().eq("name", vo.getName()));
        if (oldType != null) {
            model.addAttribute("message", "不能添加重复的分类");
            model.addAttribute("type", vo);
        }
        Type type = new Type();
        BeanUtils.copyProperties(vo, type);
        boolean success = typeService.updateById(type);
        //重定向带参数，并且隐藏参数，链接地址上不直接暴露，但是能且只能在重定向的页面获取参数值
        attributes.addFlashAttribute("message", success ? "修改成功": "修改失败");
        return "redirect:/admin/types";
    }

    /**类型 删除**/
    @GetMapping("/types/{id}/delete")
    public String typesDelete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteTypeAndUpdateBlogs(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }

    /*************************** tags ******************************/

    /**标签 列表页**/
    @GetMapping("/tags")
    public String tags(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                       Model model) {
        model.addAttribute("page",
                tagService.getBaseMapper().selectPage(new Page<>(pageNum, 10, true), null));
        return "admin/tags";
    }

    /**标签 新增页**/
    @GetMapping("/tags/input")
    public String tagsInput(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    /**标签 修改页**/
    @GetMapping("/tags/{id}/input")
    public String tagsEditInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getById(id));
        return "admin/tags-input";
    }

    /**标签 提交新增**/
    @PostMapping("/tags")
    public String tagsPost(@Valid TagVO vo, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("message", result.getFieldError().getDefaultMessage());
            model.addAttribute("tag", new Tag());
            return "admin/tags-input";
        }
        Tag oldTag = tagService.getOne(Wrappers.<Tag>query().eq("name", vo.getName()));
        if (oldTag != null) {
            model.addAttribute("message", "不能添加重复的分类");
            model.addAttribute("tag", new Tag());
            return "admin/tags-input";
        }
        Tag tag = new Tag();
        BeanUtils.copyProperties(vo, tag);
        boolean success = tagService.saveOrUpdate(tag);
        //重定向带参数，并且隐藏参数，链接地址上不直接暴露，但是能且只能在重定向的页面获取参数值
        attributes.addFlashAttribute("message", success ? "新增成功": "新增失败");
        return "redirect:/admin/tags";
    }

    /**标签 提交修改**/
    @PostMapping("/tags/{id}")
    public String tagEditPost(@Valid TagVO vo, BindingResult result, @PathVariable Long id,
                           Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("message", result.getFieldError().getDefaultMessage());
            model.addAttribute("tag", vo);
            return "admin/tags-input";
        }
        Tag oldTag = tagService.getOne(Wrappers.<Tag>query().eq("name", vo.getName()));
        if (oldTag != null) {
            model.addAttribute("message", "不能添加重复的分类");
            model.addAttribute("tag", vo);
            return "admin/tags-input";
        }
        Tag tag = new Tag();
        BeanUtils.copyProperties(vo, tag);
        boolean success = tagService.saveOrUpdate(tag);
        //重定向带参数，并且隐藏参数，链接地址上不直接暴露，但是能且只能在重定向的页面获取参数值
        attributes.addFlashAttribute("message", success ? "修改成功": "修改失败");
        return "redirect:/admin/tags";
    }

    /**标签 删除**/
    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.deleteTagAndUpdateBlogs(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }
}
