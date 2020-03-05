package com.dg.myblog.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dg.myblog.dao.AboutmeMapper;
import com.dg.myblog.model.entity.Aboutme;
import com.dg.myblog.service.AboutmeService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lij
 * @create: 2020-03-03 22:20
 */
@Transactional
@Service
public class AboutmeServiceImpl extends ServiceImpl<AboutmeMapper, Aboutme> implements AboutmeService {
    @Override
    public Map<String, List<String>> getInfoMap() {
        QueryWrapper<Aboutme> queryWrapper = Wrappers.<Aboutme>query();
        queryWrapper.orderByAsc("type", "sort");
        List<Aboutme> aboutmes = this.baseMapper.selectList(queryWrapper);
        Map<String, List<String>> infoMap = Maps.newHashMap();
        if(CollectionUtil.isNotEmpty(aboutmes)){
            for (Aboutme aboutme : aboutmes) {
                List<String> vals = infoMap.get(aboutme.getType());
                if(vals == null){
                    vals = new ArrayList<>();
                    infoMap.put(aboutme.getType(), vals);
                }
                vals.add(aboutme.getValue());
            }
        }

        return infoMap;
    }
}
