package com.dg.myblog.global.model;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @description: mybatisplus的AR扩展
 * @author: lij
 * @create: 2020-03-03 11:39
 */
public class BaseModel<T extends BaseModel> extends Model<T>{

    public List<T> selectListByExample(){
        return this.selectList(getQueryWrapperByExample());
    }

    public T selectOneByExample(){
        return this.selectOne(getQueryWrapperByExample());
    }

    public QueryWrapper<T> getQueryWrapperByExample(){
        QueryWrapper<T> wrapper = Wrappers.<T>query();
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if("serialVersionUID".equals(field.getName())){
                    continue;
                }
                TableField tableFieldAnno = field.getDeclaredAnnotation(TableField.class);
                if(tableFieldAnno!=null && !tableFieldAnno.exist()){
                    continue;
                }

                if(field.get(this) != null){
                    wrapper.eq(StrUtil.toUnderlineCase(field.getName()), field.get(this));
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return wrapper;
    }

}
