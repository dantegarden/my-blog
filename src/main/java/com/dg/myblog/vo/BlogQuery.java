package com.dg.myblog.vo;

import com.dg.myblog.model.entity.Blog;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * Created by limi on 2017/10/20.
 */
@Data
@Accessors(chain = true)
public class BlogQuery {

    private String title;
    private Long typeId;
    private Boolean recommend;

    public Blog convert2Blog(){
        Blog b = new Blog();
        BeanUtils.copyProperties(this, b);
        return b;
    }
}
