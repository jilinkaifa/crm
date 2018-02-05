package com.pandawork.crm.web.controller.admin.event;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.entity.event.Event;
import com.pandawork.crm.common.entity.party.group.employee.Employee;
import com.pandawork.crm.common.entity.party.security.SecurityUser;
import com.pandawork.crm.common.entity.party.security.SecurityUserGroup;
import com.pandawork.crm.common.enums.event.EventApprovalStatusEnums;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.service.event.AffairPreparedService;
import com.pandawork.crm.service.party.security.SecurityUserGroupService;
import com.pandawork.crm.web.spring.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

/**
 * AffairPreparedController
 * Author： linjie
 * Date: 2017/7/29
 * Time: 17:27
 */
@Controller
@Module(ModuleEnums.AdminECRBPrepareAffair)
@RequestMapping(value = URLConstants.ADMIN_EVENT_AFFAIR_PREPARED_URL)
public class AffairPreparedController extends AbstractController{

    /**
     * 去列表页
     *
     * @return
     */
    @Module(ModuleEnums.AdminECRBPrepareAffairList)
    @RequestMapping(value = {"","list"},method = RequestMethod.GET)
    public String toList(){
        return "/admin/event/affair/prepare/list";
    }
    /**
     * ajax根据分页获取事务
     *
     * @param curPage
     * @return
     */
    @Module(ModuleEnums. AdminECRBPrepareAffairList)
    @RequestMapping(value = "ajax/list", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxListEmployeeInfo(@RequestParam("page") Integer curPage,
                                     HttpSession httpSession){
        JSONObject json = new JSONObject();
        List<Event> eventList = Collections.emptyList();
        JSONArray jsonArray = new JSONArray();
        int dataCount = 0 , numCount = 0;
        int offSet = 0 ;
        int partyId = 0;
        try{
            if(Assert.isNull(curPage)){
                offSet = 0;
            }else{
                //查询当前页第一个游标值
                offSet = (curPage-1) * DEFAULT_PAGE_SIZE;
            }
            if(Assert.isNotNull(httpSession.getAttribute("partyId"))) {
                partyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
                //根据partyId获取用户Id
                SecurityUser securityUser = securityUserService.queryByPartyId(partyId);
                int userId = securityUser.getId();
                //根据用户Id获取分组角色
                SecurityUserGroup securityUserGroup = securityUserGroupService.queryByUserId(userId);
                //判断当前登录人的角色
                if (Assert.isNotNull(securityUserGroup.getGroupId())){
                    if (securityUserGroup.getGroupId() == 3 || securityUserGroup.getGroupId() == 5){ //如果当前登录人是会员管理员
                        System.out.println("-------------------------------走到这里了吗");
                        eventList = affairPreparedService.listAllRejectedByPage(partyId, offSet, DEFAULT_PAGE_SIZE);
                        numCount = affairPreparedService.countRejected(partyId);
                        dataCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE, numCount);
                    } else if (securityUserGroup.getGroupId() == 2 || securityUserGroup.getGroupId() == 4 ){
                        List<Integer> idList = employeeService.listUnderlingsByPartyId(partyId);
                        if (Assert.isNotNull(idList)){
                            eventList = affairPreparedService.listEventByPartyIdListAndStatus(idList, EventApprovalStatusEnums.Pending.getId(), offSet, DEFAULT_PAGE_SIZE);
                            numCount = affairPreparedService.countEventByPartyIdListAndStatus(idList, EventApprovalStatusEnums.Pending.getId());
                            dataCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE, numCount);
                        }
                    }
                }
                //处理时间
                if (Assert.isNotNull(eventList)){
                    affairPreparedService.dateConvert(eventList);
                }
            }
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        //序号
        int  i = 1 +( curPage - 1 ) * 10;
        for(Event event : eventList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", event.getId());
            jsonObject.put("number", i++);
            jsonObject.put("name", event.getName());
            jsonObject.put("createdTime",event.getCreatedTime() );
            jsonObject.put("approvalComment", event.getApprovalComment() == null ? "无" : event.getApprovalComment());
            int approvalStatus = event.getApprovalStatus();
            //判断类型，以对应的value值显示
            if(approvalStatus == 1){
                jsonObject.put("approvalStatusValue", "待审批活动");
            }else if(approvalStatus == 3){
                jsonObject.put("approvalStatusValue", "审批被驳回");
            }
            jsonObject.put("approvalStatus", event.getApprovalStatus());
            jsonObject.put("approvalComment", event.getContent());
            jsonArray.add(jsonObject);
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", jsonArray);
        json.put("dataCount", dataCount);
        json.put("numCount", numCount);
        return json;
    }
}
