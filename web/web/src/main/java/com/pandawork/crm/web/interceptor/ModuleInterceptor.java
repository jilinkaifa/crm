package com.pandawork.crm.web.interceptor;

import com.pandawork.crm.common.annotation.Module;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Module拦截器
 *
 * @author: zhangteng
 * @time: 2015/8/24 10:23
 **/
public class ModuleInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            // 如果不是方法请求地址，不拦截
            return true;
        }

        // 获取类级别的权限
        Module classModule = ((HandlerMethod) handler).getBean().getClass().getAnnotation(Module.class);
        if (classModule != null) {
            request.setAttribute("PWModule", classModule.value().getName());
        }

        // 获取方法级别的权限
        Module methodModule = ((HandlerMethod) handler).getMethod().getAnnotation(Module.class);
        if (methodModule != null) {
            request.setAttribute("MethodModule", methodModule.value().getName());
            request.setAttribute("ExtModule", methodModule.extModule().getName());
        }
        return true;
    }
}
