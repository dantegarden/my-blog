package com.dg.myblog.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * t_user
 * @author 
 */
@Data
@Accessors(chain = true)
@ToString
@TableName(value = "t_user")
public class User extends Model<User> implements Serializable {
    @TableId
    private Long id;

    private String avatar;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private String email;

    private String nickname;

    private String password;

    private Integer type;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private String username;

    @TableField(exist = false)
    private List<Blog> blogs = Lists.newArrayList();

    private static final long serialVersionUID = 1L;
}