package com.pandawork.crm.common.utils;

/**
 * 全站Url地址常量
 * 命名规范：大模块_小模块_小模块_..._URL
 * 必须全部是大写字母
 * 两个小模块之间的地址不空行
 * 两个大模块之间的地址用两个空行隔开
 *
 * @author: zhangteng
 * @time: 2014/9/25 20:06
 */
public final class URLConstants {

    //首页
    public static final String INDEX_URL = "";

    //home页
    public static final String HOME_URL = "home";

    /**********************后台*************************/
    public static final String ADMIN_URL = "admin";

    public static final String ADMIN_COMMON_URL = "admin/common";

    // 后台登录url
    public static final String ADMIN_LOGIN_URL = "admin/login";

    // 后台权限管理
    public static final String ADMIN_PARTY_SECURITY_PERMISSION_URL = "admin/party/security/permission";

    // 后台安全组管理
    public static final String ADMIN_PARTY_SECURITY_GROUP_URL = "admin/party/security/group";

    //搜索风向标url
    public static final String ADMIN_KEYWORDS_URL = "admin/keywords";


    /**********************注册机*************************/
    public static final String REGISTER_URL = "register";


    /************************CRM系统***************************/

    //会员组模块
    public static final String ADMIN_PARTY_MEMBER_GROUP_URL = "admin/party/member/group";

    //员工管理模块
    public static final String ADMIN_PARTY_GROUP_EMPLOYEE_URL = "admin/party/group/employee";

    //字典值管理模块
    public static final String ADMIN_PARTY_DICTIONARY_URL = "admin/party/dictionary";

    //积分转换规则管理
    public static final String ADMIN_PARTY_POINTSCONVERT_URL = "admin/party/pointsConvert";

    //患者管理基础信息
    public static final String ADMIN_CLIENT_BASIC_URL = "admin/client/basic";

    //会员信息管理
    public static final String ADMIN_CLIENT_MEMBER_URL = "admin/client/member";

    //问卷管理
    public static final String ADMIN_CLIENT_QUEST_URL = "admin/client/quest";

    //患者画像模块
    //标签管理
    public static final String ADMIN_PROFILE_LABEL_URL = "admin/profile/label";

    //画像管理
    public static final String ADMIN_PROFILE_PORTRAYAL_URL = "admin/profile/portrayal";

    //行为分析
    public static final String ADMIN_PROFILE_ACTION_URL = "admin/profile/action";

    //统计分析
    public static final String ADMIN_PROFILE_STATISTICS_URL = "admin/profile/statistics";

    //新建活动
    //待开展活动
    public static final String ADMIN_EVENT_PREPARE_URL = "admin/event/prepare";

    //模板管理
    public static final String ADMIN_EVENT_TEMPLATE_URL = "admin/event/template";

    //进行中活动
    public static final String ADMIN_EVENT_PROCESSING_URL = "admin/event/processing";

    //已归档活动
    public static final String ADMIN_EVENT_ARCHIVED_URL = "admin/event/archived";

    //待办问卷
    public static final String ADMIN_EVENT_PREPAREQUEST_URL = "admin/event/quest/prepare";

    //待办事务
    public static final String ADMIN_AFFAIR_PREPARED_URL = "admin/event/affair/prepare";

    public static final String ADMIN_EVENT_AFFAIR_PREPARED_URL = "admin/event/affair/prepare";

    //待办活动通知
    public static final String ADMIN_EVENT_PREPARE_NOTICE_URL = "admin/event/prepareNotice";

    //患者管理来访管理
    public static final String ADMIN_CLIENT_VISIT_URL = "admin/client/visit";

    //患者积分管理
    public static final String ADMIN_CLIENT_POINTS_URL = "admin/client/points";

    //画像管理模块
    public static final String ADMIN_PROFILE_LABEL_TYPE_URL = "admin/profile/label/type";

    //文件上传下载管理
    public static final String ADMIN_FILE = "admin/file";
}
