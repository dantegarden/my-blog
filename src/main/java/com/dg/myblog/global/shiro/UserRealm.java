package com.dg.myblog.global.shiro;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dg.myblog.global.GlobalConstants;
import com.dg.myblog.model.entity.User;
import com.dg.myblog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @description:
 * @author: lij
 * @create: 2020-03-05 13:49
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**授权*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("用户鉴权 doGetAuthorizationInfo");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 角色
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        // 权限
        // 测试用权限
        if ("admin".equals(user.getUsername())) {
            roles.add("admin");
            permissions.add("all");
        } else {
            roles.add("user");
            permissions.add("op:read");
        }
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**认证*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("用户认证 doGetAuthenticationInfo");
        // 获取用户密码
        String loginName = (String) authenticationToken.getPrincipal();
        User user = userService.getOne(Wrappers.<User>query().eq("username", loginName));
        if (user == null) {
            //没找到帐号
            throw new UnknownAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(),
                user.getPassword(), getName());
        //session中不需要保存密码
        user.setPassword("");
        //将用户信息放入session中
        SecurityUtils.getSubject().getSession().setAttribute(GlobalConstants.USER_TOKEN, user);
        return authenticationInfo;
    }
}
