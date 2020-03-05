package com.dg.myblog;

import com.dg.myblog.dao.UserMapper;
import com.dg.myblog.model.entity.Blog;
import com.dg.myblog.model.entity.Type;
import com.dg.myblog.model.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBlogApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
        User nweUser = new User();
        nweUser.setAvatar("https://unsplash.it/100/100?image=1005").setCreateTime(new Date()).setNickname("cccc").setPassword("123456").setType(0).setUsername("cccc");
        userMapper.insert(nweUser);

        User user = userMapper.selectById(1L);
        List<Blog> blogs = user.getBlogs();
        for (Blog blog : blogs) {
            Type type = blog.getType();
            System.out.println(type);
        }
    }

}
