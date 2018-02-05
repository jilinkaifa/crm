package com.pandawork.crm.service.party.login;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.enums.party.LoginTypeEnums;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录管理Service
 *
 * @author: zhangteng
 * @time: 2015/10/22 15:13
 **/
public interface LoginManageService {

    /**
     * 是否已经登录
     *
     * @param request
     * @return
     * @throws SSException
     */
    public Subject isLogined(HttpServletRequest request) throws SSException;

    /**
     * 验证用户是否具有合法的身份进入
     * @param token
     * @param loginType
     * @param request
     * @param response
     * @return null表示非法用户；如果返回非空值，则说明是验证通过。
     * @throws SSException
     */
    public Subject validLogin(UsernamePasswordToken token,
                              LoginTypeEnums loginType,
                              HttpServletRequest request,
                              HttpServletResponse response) throws SSException;

    /**
     * 登出
     *
     * @param request
     * @throws SSException
     */
    public void logOut(HttpServletRequest request) throws SSException;

    /**
     * 添加Subject到缓存
     *
     * @param subject
     * @throws SSException
     */
    public void addSubject(Subject subject) throws SSException;

    /**
     * 生成TGT
     *
     * @param sessionId
     * @param request
     * @param response
     * @param isRemember
     * @throws SSException
     */
    public void generateTGT(String sessionId,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            boolean isRemember) throws SSException;

    /**
     * 查询subject
     *
     * @param request
     * @return
     * @throws SSException
     */
    public Subject querySubject(HttpServletRequest request) throws SSException;

    /**
     * 删除subject
     *
     *
     * @param request
     */
    public void delSubject(HttpServletRequest request) throws SSException;
}
