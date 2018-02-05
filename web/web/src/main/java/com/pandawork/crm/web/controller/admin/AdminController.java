package com.pandawork.crm.web.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.annotation.IgnoreAuthorization;
import com.pandawork.crm.common.annotation.IgnoreLogin;
import com.pandawork.crm.common.entity.party.security.SecurityUser;
import com.pandawork.crm.common.enums.TrueEnums;
import com.pandawork.crm.common.enums.party.LoginTypeEnums;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.common.utils.*;
import com.pandawork.crm.web.spring.AbstractController;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.omg.CORBA.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;


/**
 * LoginController
 *
 * @author: zhangteng
 * @time: 15/10/22 下午10:10
 */
@Controller
@RequestMapping(value = URLConstants.ADMIN_URL)
public class AdminController extends AbstractController {

    @IgnoreAuthorization
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toIndex() {
        return "admin/index";
    }

    /**
     * 去登录页
     *
     * @return
     */
    @IgnoreLogin
    @IgnoreAuthorization
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String toLogin(HttpSession httpSession) {
        try {
            Subject subject = loginManageService.isLogined(getRequest());
            if (!Assert.isNull(subject) && Assert.isNotNull(httpSession.getAttribute("partyId"))) {
                return "redirect:/admin";
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }

        return "admin/login/login";
    }

    /**
     * ajax登录提交
     *
     * @param loginName
     * @param password
     * @param isRemember
     * @return
     */
    @IgnoreLogin
    @IgnoreAuthorization
    @RequestMapping(value = "ajax/login", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxLogin(@RequestParam("loginName") String loginName,
                          @RequestParam("password") String password,
                          @RequestParam(value = "isRememberMe", required = false) Integer isRemember,
                          HttpSession httpSession) {
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
        if (!Assert.isNull(isRemember)
                && TrueEnums.True.equals(TrueEnums.valueOf(isRemember))) {
            token.setRememberMe(true);
        }
        try {
            loginManageService.validLogin(token, LoginTypeEnums.BackgroundLogin,getRequest(), getResponse());
            httpSession.setAttribute("userName", loginName);

            // TODO: 在后台页面测试服务员端App的AJAX请求要用，发布前可以删掉
            // 把partyId放入Session
            SecurityUser user = securityUserService.queryByLoginName(loginName);
            Integer partyId = user.getPartyId();
            httpSession.setAttribute("partyId", partyId);
            //将登录信息添加到登录session的列表中
            SessionUserListener.addUserSession(httpSession);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 登录
     *
     * @return
     */
    @IgnoreLogin
    @IgnoreAuthorization
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login() {
        return "redirect:/admin";
    }

    /**
     * 退出登录
     *
     * @param httpSession
     * @return
     */
    @IgnoreAuthorization
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession httpSession) {
        try {
            loginManageService.logOut(getRequest());
            //清空session
            httpSession.invalidate();
            //将该用户从登录session列表
            SessionUserListener.removeSession(httpSession.getId());
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "redirect:/admin/login";
    }

    /**
     * 去404
     *
     * @return
     */
    @IgnoreLogin
    @IgnoreAuthorization
    @RequestMapping(value = "404", method = RequestMethod.GET)
    public String to404() {
        return ADMIN_NOT_FOUND_PAGE;
    }

    /**
     * 去403
     *
     * @return
     */
    @IgnoreLogin
    @IgnoreAuthorization
    @RequestMapping(value = "403", method = RequestMethod.GET)
    public String to403() {
        return ADMIN_FORBIDDEN_PAGE;
    }

    /**
     * 去500
     *
     * @return
     */
    @IgnoreLogin
    @IgnoreAuthorization
    @RequestMapping(value = "500", method = RequestMethod.GET)
    public String to500() {
        int statusCode = getResponse().getStatus();
        if (statusCode == HttpServletResponse.SC_BAD_REQUEST) {
            String url = (String) getRequest().getAttribute("eUrl");

            // ajax请求
            if (url.contains("ajax")) {
                JSONObject json = new JSONObject();
                json.put("code", AJAX_FAILURE_CODE);
                json.put("errMsg", "少传了参数!");

                try {
                    getResponse().setContentType("application/json;charset=UTF-8");
                    PrintWriter writer = getResponse().getWriter();
                    writer.write(json.toJSONString());
                    writer.close();
                    return "";
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                }
            } else {
                getRequest().setAttribute("eMsg", "少传了参数!");
                getRequest().setAttribute("javax.servlet.error.request_uri", url);
            }
        }
        return ADMIN_SYS_ERR_PAGE;
    }

//    /**
//     * 去个人信息修改页
//     * @param model
//     * @return
//     */
//    @IgnoreAuthorization
//    @RequestMapping(value = "personal/information", method = RequestMethod.GET)
//    public String toPersonalInformation(HttpSession httpSession, Model model) {
//        try {
//            int partyId = (Integer) httpSession.getAttribute("partyId");
//
//            Employee employee = employeeService.queryByPartyId(partyId);
//            model.addAttribute("phone", employee.getPhone());
//
//            return "admin/index/personal_home";
//        } catch (SSException e) {
//            LogClerk.errLog.error(e);
//            sendErrMsg(e.getMessage());
//            return ADMIN_SYS_ERR_PAGE;
//        }
//    }

    /**
     * 去往修改密码页面
     *
     * @param httpSession
     * @param model
     * @return
     * @throws Exception
     */
    @IgnoreAuthorization
    @RequestMapping(value = "personal/information", method = RequestMethod.GET)
    public String toPersonal(HttpSession httpSession,
                             Model model)throws Exception{
        try {
            int partyId = (Integer) httpSession.getAttribute("partyId");
            SecurityUser securityUser = securityUserService.queryByPartyId(partyId);
            model.addAttribute("loginName", securityUser.getLoginName());
            return "admin/login/modifypwd";
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
    }

    /**
     * 验证密码是否正确
     *
     * @param oldPassword
     * @param httpSession
     * @return
     */
    @IgnoreAuthorization
    @RequestMapping(value = "ajax/personal/checkpassword", method = RequestMethod.POST)
    @ResponseBody
    public JSON checkPassword(@RequestParam("oldPassword") String oldPassword,
                           HttpSession httpSession){
        try {
            int partyId = (Integer) httpSession.getAttribute("partyId");
            SecurityUser securityUser = securityUserService.queryByPartyId(partyId);
            if(securityUser.getPassword().equals(JmAndKlUtils.KL(oldPassword))){
                return sendJsonObject(AJAX_SUCCESS_CODE);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return sendJsonObject(AJAX_FAILURE_CODE);
    }


    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @param httpSession
     * @return
     */
    @IgnoreAuthorization
    @RequestMapping(value = "ajax/personal/update/password", method = RequestMethod.POST)
    @ResponseBody
    public JSON toPersonal(@RequestParam("oldPassword") String oldPassword,
                           @RequestParam("newPassword") String newPassword,
                           HttpSession httpSession){
        try {
            int partyId = (Integer) httpSession.getAttribute("partyId");
            SecurityUser securityUser = securityUserService.queryByPartyId(partyId);
            if(Assert.isNotNull(securityUser)){
                if (!securityUser.getPassword().equals(JmAndKlUtils.KL(oldPassword))) {
                    throw SSException.get(CrmException.OldPasswordWrong);
                }
                securityUser.setPassword(JmAndKlUtils.KL(newPassword));
                securityUserService.updateSecurityUser(securityUser);
                httpSession.invalidate();
            }else {
                return sendJsonObject(AJAX_FAILURE_CODE);
            }

        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

}
