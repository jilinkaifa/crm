package com.pandawork.crm.common.utils;


import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.sytem.SystemInstance;

import java.io.UnsupportedEncodingException;

/**
 * 常量
 */
public class WebConstants {

    static  {
        // 读取属性文件中的值，设置初值
        SystemInstance systemInstance = SystemInstance.getIntance();
        String temp = "";
        staticWebsite = (String) systemInstance.getProperty("staticWebsite");
        uploadWebsite = (String) systemInstance.getProperty("uploadWebsite");
        MAIL_FROM_ADDRESS = (String) systemInstance.getProperty("mail.fromAddress");

        temp = (String) systemInstance.getProperty("webTitle");
        try {
            webTitle = new String(temp.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LogClerk.errLog.error(e.getMessage());
            webTitle = "";
        }
        temp = (String) systemInstance.getProperty("webName");
        try {
            webName = new String(temp.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LogClerk.errLog.error(e.getMessage());
            webName = "";
        }
        temp = (String) systemInstance.getProperty("webFullName");
        try {
            webFullName = new String(temp.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LogClerk.errLog.error(e.getMessage());
            webFullName = "";
        }
    }



    // 网站标题
    public static String webTitle;

    // 网站名称
    public static String webName;

    public static String webFullName;

    public final static String staticWebsite;

    // 文件上传服务器地址
    public static final String uploadWebsite;

    // 用户登录的id
    public static final String WEB_SECURITY_USER_ID = "web_security_user_id";

    // 用户的partyId
    public static final String WEB_PARTY_ID = "web_party_id";

    // 用户类型
    public static final String WEB_PARTY_TYPE = "web_party_type";

    // 员工姓名或者是企业简称
    public static final String WEB_USER_SHORT_NAME = "web_user_short_name";

    // 用户登录名称
    public static final String WEB_USER_LOGIN_NAME = "web_user_login_name";

    public static String MAIL_FROM_ADDRESS;

    // 二维码地址
    public final static String uploadQrCodePath = "/upload/qrcode/";

    //错误码
    // 错误码
    public final static String sysErrorCode = "admin/500";

    public final static String accessErrorCode = "admin/403";

    public final static String notFoundErrorCode = "admin/404";

    public final static String SESSIONUID="sessionUid";

}
