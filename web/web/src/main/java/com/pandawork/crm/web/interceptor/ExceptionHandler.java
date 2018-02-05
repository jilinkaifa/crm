package com.pandawork.crm.web.interceptor;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ExceptionHandler
 *
 * @author: zhangteng
 * @time: 2015/11/5 17:35
 **/
public class ExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        System.out.println("没有异常...");
        return null;
    }
}
