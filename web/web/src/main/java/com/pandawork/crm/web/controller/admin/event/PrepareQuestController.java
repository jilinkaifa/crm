package com.pandawork.crm.web.controller.admin.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.dto.client.quest.QuestSearchDto;
import com.pandawork.crm.common.entity.client.quest.Quest;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.DateUtils;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * PrepareQuestController
 * Created by shura
 * Date:  2017/7/29.
 * Time:  9:34
 */
@Controller
@Module(ModuleEnums.AdminECRBPrepareQuest)
@RequestMapping(value = URLConstants.ADMIN_EVENT_PREPAREQUEST_URL)
public class PrepareQuestController  extends AbstractController{


    /**
     * 去列表页
     *
     * @param model
     * @param httpSession
     * @return
     */
    @Module(ModuleEnums.AdminECRBPrepareQuestList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model, HttpSession httpSession){
        int partyId = 0;
        int memberGroupId = 0;
        int dataCount = 0;
        QuestSearchDto searchDto = new QuestSearchDto();
        try{
            //获取操作人
            int createdPartyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
            //获取登录用户的id
            int userId = securityUserService.queryByPartyId(createdPartyId).getId();
            //根据用户id获取角色id
            int securityGroupId = securityUserGroupService.queryByUserId(userId).getGroupId();
            searchDto.setMemberGroupId(memberGroupId);
            dataCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE, questService.countBySearchDto(searchDto));
            model.addAttribute("dataCount", dataCount);
        } catch(SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/event/quest/prepare/list";
    }

    /**
     * 分页查询
     *
     * @param searchDto
     * @return
     */
    @Module(ModuleEnums.AdminECRBPrepareQuestList)
    @RequestMapping(value = {"ajax/list"},method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(QuestSearchDto searchDto,HttpSession httpSession) {
        JSONObject json = new JSONObject();
        //分页偏移量处理
//        Integer pageSize = searchDto.getPageSize();
//        pageSize = (pageSize == null || pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
        Integer pageNo = searchDto.getPage();
        int offset = 0;
        int partyId = 0;
        int memberGroupId = 0;
        int pageCount = 0;
        if (Assert.isNotNull(pageNo)) {
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * DEFAULT_PAGE_SIZE;
        }
        JSONArray jsonArray = new JSONArray();
        Date today = new Date();
        int dataCount = 0,numCount = 0;
        try {
            //获取操作人
            int createdPartyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
            //获取登录用户的id
            int userId = securityUserService.queryByPartyId(createdPartyId).getId();
            //根据用户id获取角色id
            int securityGroupId = securityUserGroupService.queryByUserId(userId).getGroupId();
            if(securityGroupId != 3){
                //若为超级管理员可查看所有
                searchDto.setMemberGroupId(null);
            }else{
                //若为其他包医人只可查看自己分组
                memberGroupId = employeeService.getGroupIdByPartyId(createdPartyId);
                searchDto.setMemberGroupId(memberGroupId);
            }
            searchDto.setOffset(offset);
            searchDto.setPage(pageNo);
            searchDto.setPageSize(DEFAULT_PAGE_SIZE);
            List<Quest> questList = questService.listBySearchDto(searchDto);
            int i = 1 + pageNo * 10;
            String nextQuestTime = "";
            Date remindDay = null;
            Date aheadMonthDay = null;
            for (Quest quest : questList) {
                JSONObject jsonObject = new JSONObject();
                //格式化时间
                if(Assert.isNotNull(quest.getNextQuestTime())){
                    nextQuestTime = DateUtils.formatDateSimple(quest.getNextQuestTime());
                    //是否提醒问卷判空
                    if(Assert.isNotNull(quest.getIsRemindahead()) && quest.getIsRemindahead() == 1
                            && Assert.isNotNull(quest.getRemindaheadDays())){
                        //判断是否存在提前提醒天数
                        remindDay = DateUtils.aheadDay(quest.getNextQuestTime(),quest.getRemindaheadDays());
                    }else {
                        //获取下次问卷时间的前一天
                        remindDay = DateUtils.yesterday(quest.getNextQuestTime());
                }
                    //获取近一个月的最小日期
                    aheadMonthDay = DateUtils.aheadDay(today, 30);
                }
                if ((DateUtils.isSameDay(remindDay,today) || (remindDay.getTime() <= today.getTime())
                        && aheadMonthDay.getTime() < remindDay.getTime())) {
                    jsonObject.put("number", i++);
                    jsonObject.put("id", quest.getClientId());
                    jsonObject.put("nextQuestTime", nextQuestTime);
                    jsonObject.put("type", "问卷调查");
                    jsonObject.put("content", quest.getClientName() + "的待办问卷");
                    jsonArray.add(jsonObject);
                    //numCount++;
                }
            }
//            numCount = questService.countBySearchDtoNew(searchDto);
            numCount = this.countBySearchDtoNew(searchDto,today);
            dataCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE, numCount);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", jsonArray);
        json.put("dataCount", dataCount);
        json.put("numCount", numCount);
        return json;
    }

    /**
     *  计算代办问卷统计数
     *
     * @param searchDto
     * @param today
     * @return
     * @throws SSException
     */
    private  int countBySearchDtoNew(QuestSearchDto searchDto, Date today) throws SSException {
        //为了取出全部数据
        searchDto.setOffset(-1);
        searchDto.setPageSize(-1);

        int count = 0;
        List<Quest> questList = questService.listBySearchDto(searchDto);
        String nextQuestTime = "";
        Date remindDay = null;
        Date aheadMonthDay = null;
        for (Quest quest : questList) {
            JSONObject jsonObject = new JSONObject();
            //格式化时间
            if(Assert.isNotNull(quest.getNextQuestTime())){
                nextQuestTime = DateUtils.formatDateSimple(quest.getNextQuestTime());
                //是否提醒问卷判空
                if(Assert.isNotNull(quest.getIsRemindahead()) && quest.getIsRemindahead() == 1
                        && Assert.isNotNull(quest.getRemindaheadDays())){
                    //判断是否存在提前提醒天数
                    remindDay = DateUtils.aheadDay(quest.getNextQuestTime(),quest.getRemindaheadDays());
                }else {
                    //获取下次问卷时间的前一天
                    remindDay = DateUtils.yesterday(quest.getNextQuestTime());
                }
                //获取近一个月的最小日期
                aheadMonthDay = DateUtils.aheadDay(today, 30);
            }
            if ((DateUtils.isSameDay(remindDay,today) || (remindDay.getTime() <= today.getTime())
                    && aheadMonthDay.getTime() < remindDay.getTime())) {
                count++;
            }
        }
        return count;
    }
}
