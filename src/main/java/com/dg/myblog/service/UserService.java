package com.dg.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dg.myblog.model.entity.User;

public interface UserService extends IService<User> {
    User checkUser(String username, String password);
}
