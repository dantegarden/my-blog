package com.dg.myblog.vo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: lij
 * @create: 2020-03-04 17:40
 */
@Data
public class BlogVO {

    private Long id;

    private Boolean appreciation;

    private Boolean commentabled;

    private String content;

    private Date createTime;

    private String description;

    private String firstPicture;

    private String flag;

    private Boolean published;

    private Boolean recommend;

    private Boolean shareStatement;

    private String title;

    private Date updateTime;

    private Integer views;

    private Long typeId;

    private Long userId;

    private String tagIds;
}
