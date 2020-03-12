package com.dg.myblog.global.config;

import com.dg.myblog.global.interceptor.CurrenLimitingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description:
 * @author: lij
 * @create: 2020-03-02 23:09
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CurrenLimitingInterceptor())
                .addPathPatterns("/**");
    }
}
