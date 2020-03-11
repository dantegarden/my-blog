package com.dg.myblog.controller.admin;

import com.dg.myblog.global.utils.SessionUtils;
import com.dg.myblog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @description: 后台管理登录
 * @author: lij
 * @create: 2020-03-03 23:28
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage() {
        //如果已经登录，直接跳转到首页
        if(SessionUtils.getCurrentUser() != null){
            return "redirect: admin/index";
        }
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //执行登录，没异常代表登陆成功
            SecurityUtils.getSubject().login(token);
            return "admin/index";
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return "redirect:/admin";
    }
}
