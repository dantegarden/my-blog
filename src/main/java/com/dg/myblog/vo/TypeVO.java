package com.dg.myblog.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @description:
 * @author: lij
 * @create: 2020-03-04 13:41
 */
@Data
public class TypeVO {

    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;

}
