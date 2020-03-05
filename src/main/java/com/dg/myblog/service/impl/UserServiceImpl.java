package com.dg.myblog.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dg.myblog.dao.UserMapper;
import com.dg.myblog.model.entity.User;
import com.dg.myblog.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: lij
 * @create: 2020-03-02 16:14
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User checkUser(String username, String password) {
        QueryWrapper<User> queryWrapper = Wrappers.<User>query();
        queryWrapper.eq("username", username).eq("password", SecureUtil.md5(password));
        User user = this.baseMapper.selectOne(queryWrapper);
        return user;
    }
}
