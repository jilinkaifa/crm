package com.pandawork.crm.web.interceptor;

import com.pandawork.core.common.log.LogClerk;
import com.pandawork.crm.common.annotation.IgnoreAuthorization;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.enums.party.PartyTypeEnums;
import com.pandawork.crm.common.utils.WebConstants;
import com.pandawork.crm.service.party.login.LoginManageService;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限拦截器
 *
 * @author: zhangteng
 * @time: 2015/8/26 15:03
 **/
public class AuthorInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private LoginManageService loginManageService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            // 如果不是方法请求地址，则不拦截
            return true;
        }

//        // 验证是否有忽略注册机注解
//        IgnoreRegister ignoreRegisterClazz = ((HandlerMethod) handler).getBean().getClass().getAnnotation(IgnoreRegister.class);
//        IgnoreRegister ignoreRegisterMethod = ((HandlerMethod) handler).getMethod().getAnnotation(IgnoreRegister.class);
//        // 有忽略注解，直接通过
//        if (ignoreRegisterClazz != null || ignoreRegisterMethod != null) {
//            return true;
//        }
//        // 否则验证是否进行了注册
//        else {
//            // 若未进行注册，则把所有请求都拦截到注册页面
//            if (!registerService.isRegistered()){
//                String path = request.getContextPath();
//                int port = request.getServerPort();
//                String basePath = request.getScheme() + "://" + request.getServerName()
//                        + (port == 80 ? "" : (":" + port)) + path + "/";
//                response.sendRedirect(basePath + "register");
//
//                return false;
//            }
//        }

        // 获取subject
        Subject subject = loginManageService.querySubject(request);
        if (subject == null) {
            // 没有登录
            return true;
        }

        // 验证是否有忽略权限注解
        IgnoreAuthorization ignoreClazz = ((HandlerMethod) handler).getBean().getClass().getAnnotation(IgnoreAuthorization.class);
        IgnoreAuthorization ignoreMethod = ((HandlerMethod) handler).getMethod().getAnnotation(IgnoreAuthorization.class);
        if (ignoreClazz != null
                || ignoreMethod != null) {
            // 有忽略注解，直接通过
            return true;
        }

        LogClerk.sysout.debug("class or method IgnoreAuthorization annotation is null! means this class need interceptor");

        // 获取类级别的权限
        Module classModule = ((HandlerMethod) handler).getBean().getClass().getAnnotation(Module.class);
        // 获取方法级别的权限
        Module methodModule = ((HandlerMethod) handler).getMethod().getAnnotation(Module.class);

        boolean pass = false;
        if (classModule == null
                && methodModule == null) {
            forwardToForbidden(request, response);
            return false;
        }

        // 先判断类级别的权限
        if (classModule != null) {
            ModuleEnums ms = classModule.value();
            String[] clazzPermissions = { ms.getName() };
            try {
                subject.checkPermissions(clazzPermissions);
                pass = true;
            } catch (UnauthorizedException e) {
                LogClerk.errLog.error(e);
                pass = false;
            }
        }

        // 判断方法级别的权限
        if (methodModule != null) {
            ModuleEnums ms = methodModule.value();
            String[] methodPermissions = { ms.getName() };
            try {
                subject.checkPermissions(methodPermissions);
                pass = true;
            } catch (UnauthorizedException e) {
                LogClerk.errLog.error(e);
                pass = false;
            } catch (IllegalArgumentException e) {
                LogClerk.errLog.equals(e);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/500");
                dispatcher.forward(request, response);

                return false;
            }
        }

        if (!pass) {
            forwardToForbidden(request, response);
            return false;
        }

        return true;
    }

    private void forwardToForbidden(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher dispatcher;
        String str = (String)request.getAttribute("xiaozl");
        Integer partyType = (Integer) request.getAttribute(WebConstants.WEB_PARTY_TYPE);
        if (PartyTypeEnums.Employee.getId().equals(partyType)) {
          dispatcher =request.getRequestDispatcher("/WEB-INF/views/admin/403.jsp");
        } else {
            dispatcher = request.getRequestDispatcher("/403.jsp");
        }
        dispatcher.forward(request, response);
    }
}
