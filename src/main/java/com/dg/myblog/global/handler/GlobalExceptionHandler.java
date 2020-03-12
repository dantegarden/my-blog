package com.dg.myblog.global.handler;

import com.dg.myblog.global.exception.RateLimitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 全局异常处理器
 * @author: lij
 * @create: 2020-03-01 20:48
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {
        log.error("Request URL : {}，Exception : {}", request.getRequestURL(), e);

        //非自定义异常自动抛出
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURL());
        mv.addObject("exception", e);
        mv.setViewName("error/error"); //导向error页面
        return mv;
    }

    @ExceptionHandler(RateLimitException.class)
    public ModelAndView rateLimitExceptionHander(HttpServletRequest request, Exception e) throws Exception {
        log.error("Request URL : {}，Exception : {}", request.getRequestURL(), e);
        ModelAndView mv = new ModelAndView();
        mv.addObject("rateLimited", true);
        mv.setViewName("error/error"); //导向error页面
        return mv;
    }
}
