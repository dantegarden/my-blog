package com.dg.myblog.global.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @description: mybatis-plus自动填充字段
 * @author: lij
 * @create: 2020-03-03 16:14
 */
@Component
@Slf4j
public class MybatisFillHandler implements MetaObjectHandler {

    private static final Map<String,Object> DEFAULT_INSERT_MAP = ImmutableMap.of("createTime", new Date(), "updateTime", new Date());
    private static final Map<String,Object> DEFAULT_UPDATE_MAP = ImmutableMap.of("updateTime", new Date());

    @Override
    public void insertFill(MetaObject metaObject) {
        for (Map.Entry<String, Object> entry : DEFAULT_INSERT_MAP.entrySet()) {
            String fieldName = entry.getKey();
            if(getFieldValByName(fieldName, metaObject) == null && metaObject.hasSetter(fieldName)){
                log.info("自动填充属性{}", fieldName);
                this.setInsertFieldValByName(fieldName, entry.getValue(), metaObject);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        for (Map.Entry<String, Object> entry : DEFAULT_UPDATE_MAP.entrySet()) {
            String fieldName = entry.getKey();
            if(getFieldValByName(fieldName, metaObject) == null && metaObject.hasSetter(fieldName)){
                log.info("自动更新属性{}", fieldName);
                this.setInsertFieldValByName(fieldName, entry.getValue(), metaObject);
            }
        }
    }
}
