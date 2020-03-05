package com.dg.myblog.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dg.myblog.global.model.BaseModel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @description:
 * @author: lij
 * @create: 2020-03-03 22:18
 */
@Data
@Accessors(chain = true)
@ToString
@TableName(value = "t_aboutme")
public class Aboutme extends BaseModel<Aboutme> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private String type;

    private String value;

    private Integer sort;
}
