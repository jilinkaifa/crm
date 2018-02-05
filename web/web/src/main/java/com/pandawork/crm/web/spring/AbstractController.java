package com.pandawork.crm.web.spring;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.framework.bean.StaticAutoWire;
import com.pandawork.core.framework.web.spring.controller.Base;
import com.pandawork.crm.common.utils.WebConstants;
import com.pandawork.crm.service.client.basic.ClientService;
import com.pandawork.crm.service.client.member.MemberService;
import com.pandawork.crm.service.client.points.PointsService;
import com.pandawork.crm.service.client.quest.QuestItemService;
import com.pandawork.crm.service.client.quest.QuestService;
import com.pandawork.crm.service.client.visit.VisitService;
import com.pandawork.crm.service.event.*;
import com.pandawork.crm.service.event.archived.EventArchivedService;
import com.pandawork.crm.service.event.prepare.EventAttachmentService;
import com.pandawork.crm.service.event.prepare.EventService;
import com.pandawork.crm.service.event.prepareNotice.PrepareNoticeService;
import com.pandawork.crm.service.event.processing.ProcessingEventService;
import com.pandawork.crm.service.event.template.TemplateService;
import com.pandawork.crm.service.party.dictionary.DictionaryService;
import com.pandawork.crm.service.party.group.PartyService;
import com.pandawork.crm.service.party.group.employee.EmployeeService;
import com.pandawork.crm.service.party.login.LoginManageService;
import com.pandawork.crm.service.party.member.MemberGroupService;
import com.pandawork.crm.service.party.security.*;
import com.pandawork.crm.service.profile.label.LabelItemService;
import com.pandawork.crm.service.profile.label.LabelTypeService;
import com.pandawork.crm.service.profile.portrayal.PortrayalService;
import com.pandawork.crm.service.profile.action.ActionAnalyseService;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 添加抽象父类，进行封装核心的信息。
 *
 * @author Lionel
 * @E-mail lionel@pandawork.net
 * @time 2012-8-14
 */
public class AbstractController extends Base {
    // 默认分页size
    protected final static int DEFAULT_PAGE_SIZE = 10;
    // ajax默认成功代码
    protected final static int AJAX_SUCCESS_CODE = 0;
    // ajax默认失败代码
    protected final static int AJAX_FAILURE_CODE = 1;

    protected final static String DISABLED_STRING = "（已停用）";

    // 系统异常返回页面
    protected final static String ADMIN_SYS_ERR_PAGE = "admin/500";
    // 禁止访问返回页面
    protected final static String ADMIN_FORBIDDEN_PAGE = "admin/403";
    // 无法找到404页面
    protected final static String ADMIN_NOT_FOUND_PAGE = "admin/404";

    // 顾客点餐平台异常返回页面
    protected final static String MOBILE_SYS_ERR_PAGE = "mobile/500";
    // 顾客点餐平台禁止访问返回页面
    protected final static String MOBILE_FORBIDDEN_PAGE = "mobile/403";
    // 顾客点餐平台无法找到404页面
    protected final static String MOBILE_NOT_FOUND_PAGE = "mobile/404";
    // 顾客点餐平台Session过期页面
    protected final static String MOBILE_SESSION_OVERDUE_PAGE = "mobile/session";
    // 顾客点餐平台未开台页面
    protected final static String MOBILE_NOT_OPEN_PAGE = "mobile/notopen";

    // 微信异常返回页面
    protected final static String WECHAT_SYS_ERR_PAGE = "wechat/500";
    // 微信禁止访问返回页面
    protected final static String WECHAT_FORBIDDEN_PAGE = "wechat/403";
    // 微信无法找到404页面
    protected final static String WECHAT_NOT_FOUND_PAGE = "wechat/404";

    //后厨管理端无法找到404页面
    protected final static String COOK_NOT_FOUND_PAGE = "cook/404";

    // 系统异常返回页面
    protected final static String VIP_SYS_ERR_PAGE = "forward:/500.jsp";
    // 禁止访问返回页面
    protected final static String VIP_FORBIDDEN_PAGE = "forward:/403.jsp";
    // 无法找到404页面
    protected final static String VIP_NOT_FOUND_PAGE = "forward:/404.jsp";

    //公共页面异常返回页面
    protected final static String PUB_SYS_ERR_PAGE = "forward:/500.jsp";
    //公共页面无法找到页面
    protected final static String PUB_NOT_FOUND_PAGE = "forward:/404.jsp";
    //公共页面禁止访问返回页
    protected final static String PUB_FORBIDDEN_PAGE = "forward:/403.jsp";

    // 添加成功
    protected final static String NEW_SUCCESS_MSG = "添加成功!";

    // 修改成功
    protected final static String UPDATE_SUCCESS_MSG = "修改成功!";

    /**
     * 当前用户ID
     */
    private ThreadLocal<Integer> uId = new ThreadLocal<Integer>();
    private ThreadLocal<Integer> partyId = new ThreadLocal<Integer>();

    // ${website}
    private ThreadLocal<String> website = new ThreadLocal<String>();


    @Override
    public void init(HttpServletRequest request, HttpServletResponse response) {
        super.init(request, response);
        // 设置uId
        uId.set((Integer) request.getAttribute(WebConstants.WEB_SECURITY_USER_ID));
        // 设置partyId
        partyId.set((Integer) request.getAttribute(WebConstants.WEB_PARTY_ID));
        // 设置website
        website.set((String) request.getAttribute("website"));
    }

    /**
     * 获取用户ID,如果当前用户不登录，默认返回0；
     *
     * @return
     * @author Lionel
     * @E-mail lionel@pandawork.net
     * @time 2012-8-14
     */
    protected Integer getUId() {
        return uId.get() == null ? 0 : uId.get();
    }

    /**
     * 获取当前用户partyId,如果当前用户不登录，默认返回0
     * @return
     * @author Lionel
     * @E-mail lionel@pandawork.net
     * @time 2012-8-26
     * @return
     */
    protected Integer getPartyId() {
        if (partyId.get() == null) {
            return 0;
        }
        return uId.get();
    }


    /**
     * 获取当前网址
     *
     * @return
     */
    protected String getWebsite() {
        return this.website.get();
    }

    /**
     * 发送无数据与的json对象
     *
     * @param code
     * @return
     */
    protected JSONObject sendJsonObject(int code) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        return jsonObject;
    }

    /**
     * 发送json对象
     *
     * @param json
     * @return
     */
    protected JSONObject sendJsonObject(JSON json, int code) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        if (json != null) {
            jsonObject.put("data", json);
        }
        return jsonObject;
    }

    // 发送ajax错误信息
    protected JSONObject sendErrMsgAndErrCode(SSException e) {
        JSONObject json = new JSONObject();
        json.put("code", AJAX_FAILURE_CODE);
        json.put("errMsg", e.getMessage());
        return json;
    }

    // 发送ajax 编号和信息
    protected JSONObject sendMsgAndCode(int code , String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("errMsg", msg);
        return json;
    }

    // 发送ajax 编号和信息字符串  IE下载JSON
    protected String sendMsgAndCodeStr(int code , String msg)throws Exception {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("errMsg", msg);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(json);
        return content;
    }


    // 发送ajax分页信息
    protected JSONObject sendJsonArray(JSONArray jsonArray, int dataCount, int pageSize) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", AJAX_SUCCESS_CODE);
        jsonObject.put("list", jsonArray);
        jsonObject.put("dataCount", dataCount);
        jsonObject.put("pageSize", pageSize);
        return jsonObject;
    }

    // 默认按照默认的pagesize返回数据
    protected JSONObject sendJsonArray(JSONArray jsonArray, int dataCount) {
        return sendJsonArray(jsonArray, dataCount, DEFAULT_PAGE_SIZE);
    }

    protected JSONObject sendJsonArray(JSONArray jsonArray) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", AJAX_SUCCESS_CODE);
        jsonObject.put("list", jsonArray);
        return jsonObject;
    }

    @StaticAutoWire
    @Qualifier("loginManageService")
    protected static LoginManageService loginManageService;

    @StaticAutoWire
    @Qualifier("partyService")
    protected static PartyService partyService;

    //字典值管理
    @StaticAutoWire
    @Qualifier("dictionaryService")
    protected static DictionaryService dictionaryService;

    @StaticAutoWire
    @Qualifier("securityPermissionService")
    protected static SecurityPermissionService securityPermissionService;

    @StaticAutoWire
    @Qualifier("securityGroupService")
    protected static SecurityGroupService securityGroupService;

    @StaticAutoWire
    @Qualifier("securityGroupPermissionService")
    protected static SecurityGroupPermissionService securityGroupPermissionService;

    @StaticAutoWire
    @Qualifier("employeeService")
    protected static EmployeeService employeeService;

    @StaticAutoWire
    @Qualifier("securityUserService")
    protected static SecurityUserService securityUserService;

    //系统管理
    @StaticAutoWire
    @Qualifier("memberGroupService")
    protected static MemberGroupService memberGroupService;

    //患者基本信息管理
    @StaticAutoWire
    @Qualifier("clientService")
    protected static ClientService clientService;

    //患者基本信息管理
    @StaticAutoWire
    @Qualifier("memberService")
    protected static MemberService memberService;

    //问卷管理
    @StaticAutoWire
    @Qualifier("questService")
    protected static QuestService questService;

    //问卷详情管理
    @StaticAutoWire
    @Qualifier("questItemService")
    protected static QuestItemService questItemService;

    //患者来访管理
    @StaticAutoWire
    @Qualifier("visitService")
    protected static VisitService visitService;

    //患者积分管理
    @StaticAutoWire
    @Qualifier("pointsService")
    protected static PointsService pointsService;

    //画像管理
    @StaticAutoWire
    @Qualifier("labelTypeService")
    protected static LabelTypeService labelTypeService;

    //待办活动
    @StaticAutoWire
    @Qualifier("eventService")
    protected static EventService eventService;

    //待办活动
    @StaticAutoWire
    @Qualifier("checkItemService")
    protected static CheckItemService checkItemService;

    //待办活动
    @StaticAutoWire
    @Qualifier("pointsItemService")
    protected static PointsItemService pointsItemService;

    //模板管理
    @StaticAutoWire
    @Qualifier("templateService")
    protected static TemplateService templateService;

    @StaticAutoWire
    @Qualifier("labelItemService")
    protected static LabelItemService labelItemService;

    //患者画像
    @StaticAutoWire
    @Qualifier("portrayalService")
    protected static PortrayalService portrayalService;

    //行为分析
    @StaticAutoWire
    @Qualifier("actionAnalyseService")
    protected static ActionAnalyseService actionAnalyseService;

    //活动附件
    @StaticAutoWire
    @Qualifier("eventAttachmentService")
    protected static EventAttachmentService eventAttachmentService;

    //待办事务
    @StaticAutoWire
    @Qualifier("affairPreparedService")
    protected static AffairPreparedService affairPreparedService;

    @StaticAutoWire
    @Qualifier("securityUserGroupService")
    protected static SecurityUserGroupService securityUserGroupService;

    @StaticAutoWire
    @Qualifier("processingEventService")
    protected static ProcessingEventService processingEventService;

    @StaticAutoWire
    @Qualifier("eventRecordNoticeService")
    protected static EventRecordNoticeService eventRecordNoticeService;

    @StaticAutoWire
    @Qualifier("eventTermService")
    protected static EventTermService eventTermService;

    @StaticAutoWire
    @Qualifier("checkItemResultService")
    protected static CheckItemResultService checkItemResultService;

    @StaticAutoWire
    @Qualifier("eventArchivedService")
    protected static EventArchivedService eventArchivedService;

    @StaticAutoWire
    @Qualifier("prepareNoticeService")
    protected static PrepareNoticeService prepareNoticeService;

}



