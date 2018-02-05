package com.pandawork.crm.service.party.login.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.common.util.CommonUtil;
import com.pandawork.crm.common.entity.party.security.SecurityUser;
import com.pandawork.crm.common.enums.party.EnableEnums;
import com.pandawork.crm.common.enums.party.LoginTypeEnums;
import com.pandawork.crm.common.exception.PartyException;
import com.pandawork.crm.common.utils.DateUtils;
import com.pandawork.crm.common.utils.JmAndKlUtils;
import com.pandawork.crm.service.party.login.LoginManageService;
import com.pandawork.crm.service.party.login.SubjectStore;
import com.pandawork.crm.service.party.security.SecurityUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * LoginManageServiceImpl
 *
 * @author: zhangteng
 * @time: 2015/10/22 15:21
 **/
@Service("loginManageService")
public class LoginManageServiceImpl implements LoginManageService {

    // 登录用户的cookie标识
    public static final String USER_ID_COOKIE_NAME = "uid";

    public static final String TGT_COOKIE_NAME = "tgt";

    @Autowired
    @Qualifier("subjectStoreLRUImpl")
    private SubjectStore subjectStore;

    @Autowired
    @Qualifier("securityManager")
    private DefaultSecurityManager securityManager;

    @Autowired
    @Qualifier("sessionIdCookie")
    private SimpleCookie simpleCookie;

    @Autowired
    @Qualifier("securityUserService")
    private SecurityUserService securityUserService;

    @Override
    public Subject isLogined(HttpServletRequest request) throws SSException {
        /**
         *从Header的set-cookie字段中获取tgt，然后放入Attribute的subjectId字段中。
         * C#客户端在登陆后的第一次请求执行该操作，使之后的请求可以使用Attribute中的subjectId通过登录验证。
         * 仅C#客户端需要使用，Web端登陆后自动将subjectId放入Cookie中，之后的请求使用Cookie通过登录验证即可。
         *
         * @author: yangch
         * @time: 2016/7/13 17:09
         */
        String setCookie = request.getHeader("set-cookie");
        if (setCookie != null && setCookie.length() > 0) {
            String[] strings = setCookie.split(";");
            String tgt = strings[0].substring(4);
            request.setAttribute("subjectId", tgt);
        }

        Subject subject = null;
        String uidStr = this.queryCookieValue(request, USER_ID_COOKIE_NAME);
        if (Assert.isNull(uidStr)) {
            uidStr = request.getParameter(USER_ID_COOKIE_NAME);
        }
        // 如果两个都没有，则说明用户没登录过
        if (Assert.isNull(uidStr)) {
            return subject;
        }

        subject = this.querySubject(request);
        // 没有找到TGT，并且，说明用户没有登录
        if (Assert.isNull(subject)) {
            return subject;
        }

        // 如果没有通过身份验证，说明没有登录
        if (!subject.isAuthenticated()) {
            return null;
        }

        String loginName = "";
        try {
            loginName = (String) subject.getPrincipal();
        } catch (UnknownSessionException e) { // 不存在的session
            LogClerk.errLog.error(e);

            // 删除subject
            this.delSubject(request);
            return null;
        } catch (ExpiredSessionException e) { // session过期
            LogClerk.errLog.error(e);

            // 删除subject
            this.delSubject(request);
            return null;
        }

        SecurityUser user = securityUserService.queryByLoginName(loginName);
        if (Assert.isNull(user)) {
            return null;
        }
        Integer uid = user.getId();
        EnableEnums statusEnums = EnableEnums.valueOf(user.getStatus());
        if (Assert.isNull(uid)
                || !uid.toString().equals(uidStr)
                || Assert.isNull(statusEnums)
                || statusEnums.equals(EnableEnums.Disabled)) {
            return null;
        }

        // 绑定到当前的线程中
        ThreadContext.bind(subject);
        return subject;
    }

    @Override
    public Subject validLogin(UsernamePasswordToken token, LoginTypeEnums loginType, HttpServletRequest request, HttpServletResponse response) throws SSException {
        Assert.isNotNull(token, PartyException.LoginTokenNotNull);
        Assert.isNotNull(token.getUsername(), PartyException.LoginNameNotNull);
        String password = String.valueOf(token.getPassword());
        Assert.isNotNull(password, PartyException.PasswordNotNull);

        // 进行加密
        password = JmAndKlUtils.KL(password);
        token.setPassword(password.toCharArray());

        Subject subject = null;
        // 登陆验证
        try {
            subject = securityManager.login(null, token);
        } catch (UnknownAccountException e) {
            // 用户不存在
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.UserNotExist);
        } catch (DisabledAccountException e) {
            // 用户被禁用
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.UserDisabled);
        } catch (AuthenticationException e) {
            // 密码错误
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.LoginNameOrPasswordNotCorrect);
        } catch (ExpiredSessionException e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }

        // 添加到缓存中
        this.addSubject(subject);
        if (loginType == LoginTypeEnums.BackgroundLogin || loginType == LoginTypeEnums.WaiterLogin
                || loginType == LoginTypeEnums.BarLogin || loginType == LoginTypeEnums.BackKitchenLogin){
            // 生成T票
            this.generateTGT(subject.getSession().getId().toString(), request, response, token.isRememberMe());
        }
        return subject;
    }

    @Override
    public void logOut(HttpServletRequest request) throws SSException {
        String uidCookie = this.queryCookieValue(request, USER_ID_COOKIE_NAME);
        // 用户id为空，说明没有登录
        if (Assert.isNull(uidCookie)) {
            return ;
        }

        Subject subject = this.querySubject(request);
        // subject为空，说明没有登录
        if (Assert.isNull(subject)) {
            return ;
        }
        subject.logout();
    }

    @Override
    public void addSubject(Subject subject) throws SSException {
        subjectStore.addTGT(subject.getSession().getId().toString(), subject);
        ThreadContext.bind(subject);
    }

    @Override
    public void generateTGT(String sessionId, HttpServletRequest request, HttpServletResponse response, boolean isRemember) throws SSException {
        Subject subject = subjectStore.querySubject(sessionId);
        // 如果不存在,则不需要生成
        if (Assert.isNull(subject)) {
            return ;
        }

        // 保存到客户端中
        storeSessionIdToCookie(sessionId, request, response, isRemember);
        if (isRemember) {
            subject.getSession().setTimeout(DateUtils.SECONDS_OF_ONE_MONTH);
        }

        // 和当前线程绑定
        ThreadContext.bind(subject);

        // 根据用户名获取用户id
        String loginName = (String) subject.getPrincipal();
        SecurityUser user = securityUserService.queryByLoginName(loginName);
        if (Assert.isNull(user)) {
            return ;
        }
        // 保存uid到客户端中
        storeUidToCookie(user.getId(), request, response, isRemember);
    }

    @Override
    public Subject querySubject(HttpServletRequest request) throws SSException {
        // 获取subject cookie
        String subjectId = this.queryCookieValue(request, simpleCookie.getName());
        // 如果cookie没有，判断是否在参数中
        if (Assert.isNull(subjectId)) {
            subjectId = request.getParameter(subjectId);
        }
        // 如果参数中还没有，判断是否在Attribute中(C#端使用这种方法)
        if (Assert.isNull(subjectId)) {
            subjectId = (String)request.getAttribute("subjectId");
        }
        // tgt为空,说明用户没有登录过
        if (Assert.isNull(subjectId)) {
            return null;
        }

        // decode
        subjectId = Base64.decodeToString(subjectId);
        Subject subject = subjectStore.querySubject(subjectId);

        return subject;
    }

    @Override
    public void delSubject(HttpServletRequest request) throws SSException {
        // 获取subject cookie
        String subjectCookie = this.queryCookieValue(request, simpleCookie.getName());
        // tgt为空,说明用户没有登录过
        if (Assert.isNull(subjectCookie)) {
            return ;
        }

        subjectStore.delTGT(subjectCookie);
    }

    /**
     * 把sessionId存储到客户端cookie中
     *
     * @param sessionId
     * @param request
     * @param response
     * @param isRemember
     */
    private void storeSessionIdToCookie(Serializable sessionId,
                                         HttpServletRequest request,
                                         HttpServletResponse response,
                                         boolean isRemember) {
        if (sessionId == null) {
            String msg = "sessionId cannot be null when persisting for subsequent requests.";
            throw new IllegalArgumentException(msg);
        }
        org.apache.shiro.web.servlet.Cookie template = simpleCookie;
        org.apache.shiro.web.servlet.Cookie cookie = new SimpleCookie(template);
        if (isRemember) {
            cookie.setMaxAge(DateUtils.SECONDS_OF_ONE_MONTH);
        }
        String idString = sessionId.toString();
        // base64 encode
        idString = Base64.encodeToString(idString.getBytes());
        cookie.setValue(idString);
        cookie.saveTo(request, response);
    }

    /**
     * 把uid存储到客户端cookie中
     *
     * @param uid
     * @param request
     * @param response
     * @param isRemember
     */
    private void storeUidToCookie(Serializable uid,
                                   HttpServletRequest request,
                                   HttpServletResponse response,
                                   boolean isRemember) {
        org.apache.shiro.web.servlet.Cookie template = simpleCookie;
        org.apache.shiro.web.servlet.Cookie cookie = new SimpleCookie(template);
        if (isRemember) {
            cookie.setMaxAge(DateUtils.SECONDS_OF_ONE_MONTH);
        }
        cookie.setName(USER_ID_COOKIE_NAME);
        cookie.setValue(uid.toString());
        cookie.saveTo(request, response);
    }

    private String queryCookieValue(HttpServletRequest request,
                                     String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }
}
