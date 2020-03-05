package com.dg.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dg.myblog.model.entity.Aboutme;

import java.util.List;
import java.util.Map;

public interface AboutmeService extends IService<Aboutme> {
    Map<String, List<String>> getInfoMap();
}
