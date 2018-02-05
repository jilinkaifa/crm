package com.pandawork.crm.web.controller.admin.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.dto.event.EventDto;
import com.pandawork.crm.common.dto.event.EventSearchDto;
import com.pandawork.crm.common.entity.client.points.PointsItem;
import com.pandawork.crm.common.entity.event.CheckItem;
import com.pandawork.crm.common.entity.event.Event;
import com.pandawork.crm.common.entity.event.EventAttachment;
import com.pandawork.crm.common.entity.party.dictionary.Dictionary;
import com.pandawork.crm.common.entity.party.member.Member;
import com.pandawork.crm.common.enums.event.EventApprovalStatusEnums;
import com.pandawork.crm.common.enums.event.EventLevelEnums;
import com.pandawork.crm.common.enums.event.EventTypeEnums;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.enums.party.dictionary.DictionaryEnums;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.DateUtils;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.pandawork.crm.common.utils.CommonUtil.uuid;

/**
 * TemplateController
 * 模板管理Controller
 *
 * @author Flying
 * @date 2017/8/2 15:20
 */
@Controller
@Module(ModuleEnums.AdminECRBTemplate)
@RequestMapping(value = URLConstants.ADMIN_EVENT_TEMPLATE_URL)
public class TemplateController extends AbstractController {

    /**
     * 去列表页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminECRBTemplateList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model, HttpSession httpSession){
        int pageCount = 0;
        int partyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
        EventSearchDto eventSearchDto = new EventSearchDto();
        try{
            pageCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE, templateService.countTemplateByEventSearchDto(eventSearchDto));
            model.addAttribute("pageCount", pageCount);
            model.addAttribute("partyId", partyId);
        } catch(SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/event/template/list";
    }

    /**
     * ajax获取列表
     *
     * @param pageNo
     * @param eventSearchDto
     * @return
     */
    @Module(ModuleEnums.AdminECRBTemplateList)
    @RequestMapping(value = "ajax/list", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@RequestParam("pageNo") Integer pageNo,
                         EventSearchDto eventSearchDto){
        JSONObject json = new JSONObject();
        int offset = 0;
        if(Assert.isNotNull(pageNo)){
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * DEFAULT_PAGE_SIZE;
        }
        eventSearchDto.setOffset(offset);
        eventSearchDto.setPageNo(pageNo);
        eventSearchDto.setPageSize(DEFAULT_PAGE_SIZE);
        List<Event> eventList = Collections.emptyList();
        try {
            eventList = templateService.listTemplateByEventSearchDto(eventSearchDto);
            JSONArray jsonArray = new JSONArray();
            int i = 1 + pageNo * DEFAULT_PAGE_SIZE;
            for (Event event : eventList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("number", i++);
                jsonObject.put("id", event.getId());
                jsonObject.put("name", event.getName());
                jsonObject.put("type", EventTypeEnums.valueOf(event.getType()).getType());
                jsonObject.put("level", EventLevelEnums.valueOf(event.getLevel()).getLevel());
                if (Assert.isNull(event.getStartDate())){
                    jsonObject.put("startDate", "无");
                }else {
                    jsonObject.put("startDate", DateUtils.formatDateSimple(event.getStartDate()));
                }
                if (Assert.isNull(event.getEndDate())){
                    jsonObject.put("endDate", "无");
                }else {
                    jsonObject.put("endDate", DateUtils.formatDateSimple(event.getEndDate()));
                }
                if (Assert.isNotNull(event.getMemberGroupId())){
                    jsonObject.put("memberGroupName", event.getMemberGroupName());
                } else {
                    jsonObject.put("memberGroupName", "无");
                }
                jsonObject.put("createdPartyId", event.getCreatedPartyId());
                jsonObject.put("createdPartyName", event.getCreatedPartyName());
                jsonObject.put("createdTime", DateUtils.formatDateSimple(event.getCreatedTime()));
                jsonArray.add(jsonObject);
            }
            int count = 0;
            int pageCount = 0;
            count = templateService.countTemplateByEventSearchDto(eventSearchDto);
            pageCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE, count);
            json.put("code", AJAX_SUCCESS_CODE);
            json.put("list", jsonArray);
            json.put("dataCount", pageCount);
            json.put("numCount", count);
            return json;
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 去新建模板页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminECRBTemplateNew)
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String toList(Model model){
        List<Member> memberList = Collections.emptyList();
        List<Dictionary> checkItemList = Collections.emptyList();
        List<Dictionary> pointsItemList = Collections.emptyList();
        try {
            //获取所有会员组
            memberList = memberGroupService.isNotDeleted();
            model.addAttribute("memberList", memberList);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/event/template/new";
    }

    /**
     * ajax 检验模板名称是否重复，新建活动的时候
     *
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminECRBTemplateNew)
    @RequestMapping(value = "ajax/new/checkname", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject checkTemplateName(@RequestParam("name") String name){
        if (Assert.isNull(name)){
            return sendJsonObject(AJAX_FAILURE_CODE);
        }
        try {
            if (Assert.isNotNull(name)){
                String name1 = name.replaceAll(" ", "");
                if (name1.equals("")){
                    return sendMsgAndCode(AJAX_FAILURE_CODE, "模板名称不能为空");
                }
            }
            if (templateService.checkTemplateNameIsExit(name)){
                return sendJsonObject(AJAX_FAILURE_CODE);
            }else {
                return sendJsonObject(AJAX_SUCCESS_CODE);
            }
        } catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax 保存模板
     *
     * @return
     */
    @Module(ModuleEnums.AdminECRBTemplateNew)
    @RequestMapping(value = "ajax/new", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject newTemplate(Event event,
                                  HttpSession httpSession) throws Exception {
        JSONObject json = new JSONObject();
        int partyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
        //申请人
        event.setCreatedPartyId(partyId);
        //申请时间
        event.setApplyTime(new Date());
        //标记为模板：1是
        event.setIsMarked(1);
        //模板是否被使用：0否
        event.setIsTemplateUsed(0);
        try{
            templateService.newTemplate(event);
            json.put("code", 0);
            json.put("tipMsg", "保存模板成功");
            return json;
        } catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }

    }

    /**
     * 检查活动名称是否重复，修改活动时
     *
     * @param id
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminECRBTemplateUpdate)
    @RequestMapping(value = "ajax/update/checkname", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject checkEventName(@RequestParam("id") Integer id,
                                     @RequestParam("name") String name){
        if (Assert.isNull(name)){
            return sendJsonObject(AJAX_FAILURE_CODE);
        }
        try{
            Assert.isNotNull(id, CrmException.TemplateIdNotNull);
            Event template = templateService.queryById(id);
            Assert.isNotNull(template, CrmException.QueryTemplateByIdFail);
            Assert.isNotNull(name, CrmException.TemplateNameNotNull);
            if (template.getName().equals(name)){
                return sendJsonObject(AJAX_SUCCESS_CODE);
            } else {
                if (templateService.checkTemplateNameIsExit(name)){
                    return sendJsonObject(AJAX_FAILURE_CODE);
                }else {
                    return sendJsonObject(AJAX_SUCCESS_CODE);
                }
            }
        } catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax 删除模板
     *
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminECRBTemplateDelete)
    @RequestMapping(value = "ajax/delete", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxLogout(@RequestParam("id") Integer id){
        try{
            Assert.isNotNull(id, CrmException.EventIdNotNull);
            //int partyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
            templateService.delTemplateById(id);
            return sendMsgAndCode(AJAX_SUCCESS_CODE, "注销成功");
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 去详情页
     *
     * @param id
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminECRBTemplateDetail)
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String toDetail(@RequestParam("id") Integer id,
                           Model model){
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String eventAttachmentName = "";
        try {
            Event template = new Event();
            template = templateService.queryById(id);
            if(Assert.isNotNull(template.getAttachment())){
                eventAttachmentName = eventAttachmentService.queryById(template.getAttachment()).getName();
            }
            //处理活动人员
            if (Assert.isNull(template.getMemberGroupId())){
                template.setMemberGroupName("无");
            }else {
                Member member = memberGroupService.queryById(template.getMemberGroupId());
                template.setMemberGroupName(member.getName());
            }
            model.addAttribute("eventAttachmentName",eventAttachmentName);
            model.addAttribute("template", template);
        } catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/event/template/detail";
    }

    /**
     * 去修改页
     *
     * @param id
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminECRBTemplateUpdate)
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String toUpdate(@RequestParam("id") Integer id,
                           Model model){
        String eventAttachmentName = "";
        try{
            if (Assert.isNotNull(id)) {
                Event template = new Event();
                template = templateService.queryById(id);
                //处理活动人员
                if (Assert.isNull(template.getMemberGroupId())){
                    template.setMemberGroupName("无");
                }else {
                    Member member = memberGroupService.queryById(template.getMemberGroupId());
                    template.setMemberGroupName(member.getName());
                }
                if(Assert.isNotNull(template.getAttachment())){
                    eventAttachmentName = eventAttachmentService.queryById(template.getAttachment()).getName();
                }
                model.addAttribute("template", template);
            }
            List<Member> memberGroupList = memberGroupService.isNotDeleted();
            model.addAttribute("memberGroupList", memberGroupList);
            model.addAttribute("templateId", id);
            model.addAttribute("eventAttachmentName", eventAttachmentName);
        } catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/event/template/update";
    }

    /**
     * 修改模板
     *
     * @param template
     * @param httpSession
     * @return
     */
    @Module(ModuleEnums.AdminECRBTemplateUpdate)
    @RequestMapping(value = "ajax/update", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateTemplate(Event template, HttpSession httpSession){
        try{
            if (Assert.isNull(template)){
                return sendMsgAndCode(AJAX_FAILURE_CODE, "修改失败!");
            }
            int partyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
            //申请人
            template.setCreatedPartyId(partyId);
            //申请时间
            template.setApplyTime(new Date());
            //标记为模板：1是
            template.setIsMarked(1);
            //模板是否被使用：0否
            template.setIsTemplateUsed(0);
            templateService.updateTemplate(template);
            return sendMsgAndCode(AJAX_SUCCESS_CODE, "修改成功!");
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 去选择模板页
     *
     * @param model
     * @param httpSession
     * @return
     */
    @Module(ModuleEnums.AdminECRBTemplateList)
    @RequestMapping(value = "select", method = RequestMethod.GET)
    public String toSelectList(Model model, HttpSession httpSession){
        int pageCount = 0;
        int partyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
        EventSearchDto eventSearchDto = new EventSearchDto();
        try{
            pageCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE, templateService.countTemplateByEventSearchDto(eventSearchDto));
            model.addAttribute("pageCount", pageCount);
        } catch(SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/event/template/select";
    }

    /**
     * ajax 获取选择模板列表
     *
     * @param pageNo
     * @param eventSearchDto
     * @return
     */
    @Module(ModuleEnums.AdminECRBTemplateList)
    @RequestMapping(value = "ajax/select/list", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxListSelect(@RequestParam("pageNo") Integer pageNo,
                               EventSearchDto eventSearchDto){
        int offset = 0;
        if(Assert.isNotNull(pageNo)){
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * DEFAULT_PAGE_SIZE;
        }
        eventSearchDto.setOffset(offset);
        eventSearchDto.setPageNo(pageNo);
        eventSearchDto.setPageSize(DEFAULT_PAGE_SIZE);
        List<Event> eventList = Collections.emptyList();
        try {
            eventList = templateService.listTemplateByEventSearchDto(eventSearchDto);
            JSONArray jsonArray = new JSONArray();
            int number = 1;
            for (Event event : eventList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("number", number++);
                jsonObject.put("id", event.getId());
                jsonObject.put("name", event.getName());
                jsonObject.put("type", EventTypeEnums.valueOf(event.getType()).getType());
                jsonObject.put("level", EventLevelEnums.valueOf(event.getLevel()).getLevel());
                if (Assert.isNull(event.getStartDate())){
                    jsonObject.put("startDate", "无");
                }else {
                    jsonObject.put("startDate", DateUtils.formatDatetime2(event.getStartDate()));
                }
                if (Assert.isNull(event.getStartDate())){
                    jsonObject.put("endDate", "无");
                }else {
                    jsonObject.put("endDate", DateUtils.formatDatetime2(event.getEndDate()));
                }
                jsonObject.put("memberGroupName", event.getMemberGroupName());
                jsonObject.put("createdPartyId", event.getCreatedPartyId());
                jsonObject.put("createdPartyName", event.getCreatedPartyName());
                jsonObject.put("createdTime", DateUtils.formatDatetime2(event.getCreatedTime()));
                jsonArray.add(jsonObject);
            }
            int count = 0;
            int pageCount = 0;
            count = templateService.countTemplateByEventSearchDto(eventSearchDto);
            pageCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE, count);
            return sendJsonArray(jsonArray, pageCount);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }


}
