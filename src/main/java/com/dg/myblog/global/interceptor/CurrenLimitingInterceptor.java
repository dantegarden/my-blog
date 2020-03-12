package com.dg.myblog.global.interceptor;

import com.dg.myblog.global.exception.RateLimitException;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @description: 令牌桶限流
 * @author: lij
 * @create: 2020-03-12 12:39
 */
public class CurrenLimitingInterceptor extends HandlerInterceptorAdapter {

    private static final RateLimiter limiter = RateLimiter.create(10);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isLimited = limiter.tryAcquire(2000, TimeUnit.MILLISECONDS);
        if(!isLimited){
            throw new RateLimitException("访问太过频繁！");
        }
        return true;
    }
}
