package com.pandawork.crm.web.controller.admin.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.dto.event.EventSearchDto;
import com.pandawork.crm.common.dto.event.processing.CountParticipantDto;
import com.pandawork.crm.common.dto.event.processing.RecordDto;
import com.pandawork.crm.common.entity.client.points.PointsItem;
import com.pandawork.crm.common.entity.event.*;
import com.pandawork.crm.common.entity.party.group.employee.Employee;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.service.event.CheckItemResultService;
import com.pandawork.crm.service.event.EventRecordNoticeService;
import com.pandawork.crm.service.event.processing.ProcessingEventService;
import com.pandawork.crm.web.spring.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * ProcessingEventController
 * Author： wychen
 * Date: 2017/7/28
 * Time: 16:51
 */
@Controller
@Module(ModuleEnums.AdminECRBEvent)
@RequestMapping(value = URLConstants.ADMIN_EVENT_PROCESSING_URL)
public class ProcessingEventController extends AbstractController {

    private int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 去往列表页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model) {
        return "/admin/event/processing/list";
    }

    /**
     * 根据条件获取进行中活动列表
     *
     * @param eventSearchDto
     * @param request
     * @param httpSession
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingList)
    @RequestMapping(value = "ajax/list", method = RequestMethod.POST)
    @ResponseBody
    public JSON getList(EventSearchDto eventSearchDto,
                        HttpServletRequest request,
                        HttpSession httpSession) {
        JSONObject json = new JSONObject();
        List<EventTerm> eventTerms = Collections.emptyList();
        int offset = 0, pageNo = 0;
        if (Assert.isNotNull(eventSearchDto.getPageNo())) {
            pageNo = eventSearchDto.getPageNo() <= 0 ? 0 : eventSearchDto.getPageNo() - 1;
            offset = pageNo * pageSize;
        }
        eventSearchDto.setOffset(offset);
        eventSearchDto.setPageNo(pageNo);
        eventSearchDto.setPageSize(pageSize);
        int dataCount = 0;
        try {
            int count = processingEventService.countProcessingEventBySearchDto(eventSearchDto);
            dataCount = DataUtils.getPageCount(pageSize, count);
            eventTerms = processingEventService.listProcessingEventBySearchDto(eventSearchDto);
            json.put("code", AJAX_SUCCESS_CODE);
            json.put("list", eventTerms);
            json.put("dataCount", dataCount);
            json.put("numCount", count);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return json;
    }

    /**
     * 去往详情页
     *
     * @param eventTermId
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingDetail)
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String toDetail(@RequestParam("eventTermId") Integer eventTermId,
                           Model model) {
        EventTerm eventTerm = new EventTerm();
        List<PointsItem> pointsItems = Collections.emptyList();
        try {
            if (Assert.isNotNull(eventTermId)) {
                eventTerm = processingEventService.queryById(eventTermId);
                int eventId = eventTerm.getEventId();
                pointsItems = pointsItemService.listByEventId(eventId);
                model.addAttribute("event", eventTerm);
                model.addAttribute("pointsItems", pointsItems);
            }

        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/event/processing/detail";
    }

    /**
     * ajax根据活动id获取活动记录通知
     *
     * @param eventTermId
     * @param pageNo
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingDetail)
    @RequestMapping(value = "ajax/eventNoticeList", method = RequestMethod.POST)
    @ResponseBody
    public JSON getEventNoticeList(@RequestParam("eventTermId") Integer eventTermId,
                                   @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                   @RequestParam(value = "participantName", required = false) String participantName,
                                   @RequestParam(value = "participantIdcard", required = false) String participantIdcard) {
        JSONObject json = new JSONObject();
        List<EventRecordNotice> eventRecordNotices = Collections.emptyList();
        int offset = 0, dataCount = 0, numCount = 0;
        if (Assert.isNotNull(eventTermId) && Assert.isNotNull(pageNo) && pageNo > 0) {
            offset = (pageNo - 1) * pageSize;
        }
        try {
            eventRecordNotices = eventRecordNoticeService.listByEventTermId(eventTermId, participantName, participantIdcard, offset, pageSize);
            for (EventRecordNotice eventRecordNotice : eventRecordNotices) {
                eventRecordNotice.setTotalParticipant(processingEventService.countAllParticipant(eventRecordNotice.getId()));
            }
            numCount = eventRecordNoticeService.countByEventTermId(eventTermId, participantName, participantIdcard);
            dataCount = DataUtils.getPageCount(pageSize, numCount);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", eventRecordNotices);
        json.put("dataCount", dataCount);
        json.put("numCount", numCount);
        return json;
    }

    /**
     * 暂停一期活动
     *
     * @param eventTermId
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingPause)
    @RequestMapping(value = "ajax/pauseTerm", method = RequestMethod.POST)
    @ResponseBody
    public JSON pauseTerm(@RequestParam("eventTermId") Integer eventTermId) {
        try {
            if (Assert.isNotNull(eventTermId)) {
                EventTerm eventTerm = processingEventService.queryById(eventTermId);
                if(Assert.isNotNull(eventTerm)){
                    if(Assert.isNotNull(eventTerm.getEventId())){
                        //判断是否存在下一期活动
                        int countNextTerm = processingEventService.countNextTerm(eventTerm.getEventId(),"next");
                        if(countNextTerm != 1){
                            return sendMsgAndCode(AJAX_FAILURE_CODE, "没有能暂停的下一期活动！");
                        }
                        //如果存在则暂停
                        processingEventService.pauseNextTerm(eventTerm.getEventId(),"next");
                        return sendMsgAndCode(AJAX_SUCCESS_CODE, "下一期活动暂停成功！");
                    }
                }
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return sendMsgAndCode(AJAX_FAILURE_CODE, "暂停失败！");
    }

    /**
     * 暂停整个活动
     *
     * @param eventTermId
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingPause)
    @RequestMapping(value = "ajax/pauseEvent", method = RequestMethod.POST)
    @ResponseBody
    public JSON pauseEvent(@RequestParam("eventTermId") Integer eventTermId) {
        try {
            if (Assert.isNotNull(eventTermId)) {
                EventTerm eventTerm = processingEventService.queryById(eventTermId);
                if(Assert.isNotNull(eventTerm)){
                    if(Assert.isNotNull(eventTerm.getEventId())){
                        //判断是否存在分期活动
                        int countNextTerm = processingEventService.countNextTerm(eventTerm.getEventId(),"all");
                        if(countNextTerm <= 0){
                            return sendMsgAndCode(AJAX_FAILURE_CODE, "没有能暂停的活动！");
                        }
                        //如果存在则暂停
                        processingEventService.pauseNextTerm(eventTerm.getEventId(),"all");
                        return sendMsgAndCode(AJAX_SUCCESS_CODE, "暂停所有成功！");
                    }
                }
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return sendMsgAndCode(AJAX_FAILURE_CODE, "暂停失败！");
    }

    /**
     * 去往该活动待通知人员详情页面
     *
     * @param eventTermId
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingList)
    @RequestMapping(value = "toNotice", method = RequestMethod.GET)
    public String toHandleNotice(@RequestParam("eventTermId") Integer eventTermId,
                                 Model model) {
        EventTerm eventTerm = new EventTerm();
        try {
            if (Assert.isNotNull(eventTermId)) {
                eventTerm = processingEventService.queryById(eventTermId);
                model.addAttribute("event", eventTerm);
            }

        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/event/processing/toNotice";
    }

    /**
     * 通知记录列表页
     *
     * @param eventTermId
     * @param pageNo
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingList)
    @RequestMapping(value = "ajax/notice/detail", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxToBeNoticePerson(@RequestParam("eventTermId") Integer eventTermId,
                                     @RequestParam("pageNo") Integer pageNo,
                                     @RequestParam(value = "participantName", required = false) String participantName,
                                     @RequestParam(value = "participantIdcard", required = false) String participantIdcard) {
        JSONObject json = new JSONObject();
        int offset = 0, dataCount = 0, numCount = 0;
        List<EventRecordNotice> eventRecordNotices = Collections.emptyList();
        try {
            if (Assert.isNotNull(eventTermId) && Assert.isNotNull(pageNo) && pageNo > 0) {
                offset = (pageNo - 1) * pageSize;
            }
//            eventRecordNotices = eventRecordNoticeService.queryByEventTermIdWithoutNotice(eventTermId, offset, pageSize);
//            numCount = processingEventService.countToBeNoticedPerson(eventTermId);
            eventRecordNotices = eventRecordNoticeService.listByEventTermId(eventTermId,participantName,participantIdcard,offset,pageSize);
            numCount = eventRecordNoticeService.countByEventTermId(eventTermId,participantName,participantIdcard);
            dataCount = DataUtils.getPageCount(pageSize, numCount);

        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", eventRecordNotices);
        json.put("dataCount", dataCount);
        json.put("numCount", numCount);
        return json;
    }


    /**
     * 根据患者id获取进入通知期但是未通知的活动列表
     *
     * @param clientId
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingList)
    @RequestMapping(value = "ajax/eventTermList", method = RequestMethod.POST)
    @ResponseBody
    public JSON batchNotice(@RequestParam("clientId") Integer clientId,
                            @RequestParam("pageNo") Integer pageNo) {
        List<EventTerm> eventTerms = Collections.emptyList();
        JSONObject json = new JSONObject();
        int offset = 0, dataCount = 0, numCount = 0;
        if (Assert.isNotNull(pageNo)) {
            offset = (pageNo - 1) * pageSize;
        }
        try {
            if (Assert.isNotNull(clientId)) {
                eventTerms = processingEventService.queryByClientId(clientId, offset, pageSize);
                numCount = processingEventService.countEventTermByClientId(clientId);
                dataCount = DataUtils.getPageCount(pageSize, numCount);
            }

        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", eventTerms);
        json.put("dataCount", dataCount);
        json.put("numCount", numCount);
        return json;
    }

    /**
     * 批量通知
     *
     * @param idStr
     * @param status
     * @param comment
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingBatchNotice)
    @RequestMapping(value = "ajax/batchNotice", method = RequestMethod.POST)
    @ResponseBody
    public JSON batchNotice(@RequestParam("id") String idStr,
                            @RequestParam(value = "status", required = false) String status,
                            @RequestParam(value = "comment", required = false) String comment) {
        List<Integer> idList = DataUtils.stringToListInt(idStr);
        int statusInt = Integer.parseInt(status);
        try {
            if (Assert.isNotNull(idList) && Assert.isNotNull(statusInt)) {
                processingEventService.batchNotice(idList, statusInt, comment);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }


    /**
     * 批量添加积分项
     *
     * @param eventTermId
     * @param ids
     * @param pointsItemId
     * @param httpSession
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingBatchPoints)
    @RequestMapping(value = "ajax/batchPoints", method = RequestMethod.POST)
    @ResponseBody
    public JSON batchPoints(@RequestParam("eventTermId") Integer eventTermId,
                            @RequestParam("ids") String ids,
                            @RequestParam("pointsItemId") Integer pointsItemId,
                            HttpSession httpSession) {
        int partyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
        List<Integer> idList = DataUtils.stringToListInt(ids);
        try {
            if (Assert.isNotNull(eventTermId) && Assert.isNotNull(idList) && Assert.isNotNull(pointsItemId)) {
                processingEventService.batchAddPointsItems(eventTermId, idList, pointsItemId, partyId);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 获取该页患者其他活动记录信息
     *
     * @param eventRecordNoticeId
     * @param clientId
     * @param pageNo
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingList)
    @RequestMapping(value = "ajax/clientEventNotice", method = RequestMethod.POST)
    @ResponseBody
    public JSON clientEventNotice(@RequestParam("eventRecordNoticeId") Integer eventRecordNoticeId,
                                  @RequestParam("clientId") Integer clientId,
                                  @RequestParam("pageNo") Integer pageNo) {
        JSONObject json = new JSONObject();
        if (pageNo == null) {
            pageNo = 1;
        }
        int offset = (pageNo - 1) * pageSize;
        int dataCount = 0, numCount = 0;
        List<EventTerm> eventTerms = Collections.emptyList();
        try {
            if (Assert.isNotNull(eventRecordNoticeId)) {
                eventTerms = processingEventService.listByClientId(eventRecordNoticeId, clientId, offset, pageNo);
                numCount = processingEventService.countByClientId(eventRecordNoticeId, clientId);
                dataCount = DataUtils.getPageCount(pageSize, numCount);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", eventTerms);
        json.put("dataCount", dataCount);
        json.put("numCount", numCount);
        return json;
    }

    /**
     * 处理通知情况
     *
     * @param eventRecordNoticeId
     * @param status
     * @param date
     * @param checkItemId
     * @param result
     * @param httpSession
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingHandle)
    @RequestMapping(value = "ajax/handle", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxHandle(@RequestParam("eventRecordNoticeId") Integer eventRecordNoticeId,
                           @RequestParam("status") Integer status,
                           @RequestParam("date") Date date,
                           @RequestParam("checkItemId") Integer checkItemId,
                           @RequestParam("result") String result,
                           HttpSession httpSession) {
        int partyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
        try {
            if (Assert.isNotNull(eventRecordNoticeId) && Assert.isNotNull(status) && Assert.isNotNull(date) && Assert.isNotNull(checkItemId)) {
                processingEventService.handleNotice(eventRecordNoticeId, status, date, checkItemId, result, partyId);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 去往通知页
     *
     * @param clientId
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingList)
    @RequestMapping(value = "notice", method = RequestMethod.GET)
    public String toNotice(@RequestParam("clientId") Integer clientId,
                           Model model) {
        model.addAttribute("clientId", clientId);
        return "admin/event/processing/notice";
    }


    /**
     * 去往记录页
     *
     * @param eventTermId
     * @param clientId
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingRecord)
    @RequestMapping(value = "record", method = RequestMethod.GET)
    public String toRecord(@RequestParam("eventTermId") Integer eventTermId,
                           @RequestParam("clientId") Integer clientId,
                           @RequestParam("eventRecordNoticeId") Integer eventRecordNoticeId,
                           Model model) {
        EventTerm eventTerm = new EventTerm();
        List<CheckItem> checkItems = Collections.emptyList();
        try {
            if (Assert.isNotNull(eventTermId)) {
                eventTerm = processingEventService.queryById(eventTermId);
                checkItems = checkItemService.listByEventId(eventTerm.getEventId());
            }

        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }

        model.addAttribute("eventRecordNoticeId", eventRecordNoticeId);
        model.addAttribute("clientId", clientId);
        model.addAttribute("event", eventTerm);
        model.addAttribute("checkItems", checkItems);
        return "admin/event/processing/record";
    }

    /**
     * 活动通知记录页该人的其他活动列表
     *
     * @param clientId
     * @param eventRecordNoticeId
     * @param pageNo
     * @param httpSession
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingRecord)
    @RequestMapping(value = "ajax/record/list", method = RequestMethod.POST)
    @ResponseBody
    public JSON record(@RequestParam("clientId") Integer clientId,
                       @RequestParam("eventRecordNoticeId") Integer eventRecordNoticeId,
                       @RequestParam("pageNo") Integer pageNo,
                       HttpSession httpSession) {
        JSONObject json = new JSONObject();
        List<EventTerm> eventTerms = Collections.emptyList();
        int dataCount = 0, numCount = 0;
        if (pageNo == null) {
            pageNo = 1;
        }
        int offset = (pageNo - 1) * pageSize;
        try {
            if (Assert.isNotNull(clientId) && Assert.isNotNull(eventRecordNoticeId)) {
                eventTerms = processingEventService.listByClientId(eventRecordNoticeId, clientId, offset, pageSize);
                numCount = processingEventService.countByClientId(eventRecordNoticeId, clientId);
                dataCount = DataUtils.getPageCount(pageSize, numCount);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", eventTerms);
        json.put("dataCount", dataCount);
        json.put("numCount", numCount);
        return json;
    }

    /**
     * 活动通知记录保存
     *
     * @param recordDto
     * @param httpSession
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingRecord)
    @RequestMapping(value = "ajax/record/save", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxRecordSave(RecordDto recordDto,
                               HttpSession httpSession) {
        try {
            if (Assert.isNotNull(httpSession.getAttribute("partyId"))) {
               int partyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
                if (Assert.isNotNull(recordDto)) {
                    int eventRecordNoticeId = recordDto.getId();
                    EventRecordNotice eventRecordNotice = eventRecordNoticeService.queryById(eventRecordNoticeId);
                    //若检查项不为空，则参与次数+1
                    if(Assert.isNotNull(recordDto.getCheckItemId())){
                        eventRecordNotice.setEventParticipantTimes(eventRecordNotice.getEventParticipantTimes() + 1);
                    }
                    //设置活动完成状态
                    eventRecordNotice.setEventParticipantStatus(recordDto.getEventParticipantStatus());

                    //新增检查项记录
                    eventRecordNoticeService.update(eventRecordNotice);
                    if (Assert.isNotNull(recordDto.getCheckItemId())) {
                        CheckItemResult checkItemResult = new CheckItemResult();
                        checkItemResult.setCheckTime(recordDto.getCreatedTime());
                        checkItemResult.setCreatedPartyId(partyId);
                        checkItemResult.setCheckItemId(recordDto.getCheckItemId());
                        checkItemResult.setCheckResult(recordDto.getCheckItemResult());
                        checkItemResult.setEventRecordNoticeId(eventRecordNoticeId);
                        checkItemResultService.newCheckItemResult(checkItemResult);
                    }
                }
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }


    /**
     * 去往本期参与次数钻取详情页
     *
     * @param eventRecordNoticeId
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingList)
    @RequestMapping(value = "current/participant", method = RequestMethod.GET)
    public String toCurrentParticipant(@RequestParam("id") Integer eventRecordNoticeId,
                                       Model model) {
        model.addAttribute("eventRecordNoticeId", eventRecordNoticeId);
        return "admin/event/processing/currentParticipant";
    }

    /**
     * 分页获取本期参与情况
     *
     * @param eventRecordNoticeId
     * @param pageNo
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingList)
    @RequestMapping(value = "ajax/current/participant", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxCurrentParticipant(@RequestParam("eventRecordNoticeId") Integer eventRecordNoticeId,
                                       @RequestParam("pageNo") Integer pageNo) {
        JSONObject json = new JSONObject();
        if (pageNo == null) {
            pageNo = 1;
        }
        int offset = (pageNo - 1) * pageSize;
        int dataCount = 0, numCount = 0;
        List<CountParticipantDto> countParticipants = Collections.emptyList();
        try {
            if (Assert.isNotNull(eventRecordNoticeId)) {
                countParticipants = processingEventService.listCountBQParticipantById(eventRecordNoticeId, offset, pageSize);
                numCount = countParticipants.size();
//                numCount = processingEventService.totalPointsItemAndCheckItem(eventRecordNoticeId);
                dataCount = DataUtils.getPageCount(pageSize, numCount);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", countParticipants);
        json.put("dataCount", 0);//不分页
        json.put("numCount", numCount);
        return json;
    }

    /**
     * 去往累计参与次数钻取详情页
     *
     * @param eventRecordNoticeId
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingList)
    @RequestMapping(value = "total/participant", method = RequestMethod.GET)
    public String toTotalParticipant(@RequestParam("id") Integer eventRecordNoticeId,
                                     Model model) {
        model.addAttribute("eventRecordNoticeId", eventRecordNoticeId);
        return "admin/event/processing/totalParticipant";
    }


    /**
     * 累计参与情况详情列表
     *
     * @param eventRecordNoticeId
     * @param pageNo
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingList)
    @RequestMapping(value = "ajax/total/participant", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxTotalParticipant(@RequestParam("eventRecordNoticeId") Integer eventRecordNoticeId,
                                     @RequestParam("pageNo") Integer pageNo) {
        JSONObject json = new JSONObject();
        List<CountParticipantDto> countParticipantDtos = Collections.emptyList();
        int dataCount = 0, numCount = 0;
        if (pageNo == null) {
            pageNo = 1;
        }
        int offset = (pageNo - 1) * pageSize;
        try {
            countParticipantDtos = processingEventService.allParticipant(eventRecordNoticeId);
            numCount = processingEventService.allParticipant(eventRecordNoticeId).size();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", countParticipantDtos);
        json.put("dataCount", dataCount);
        json.put("numCount", numCount);
        return json;
    }


    /**
     * 去往患者参与活动列表页
     *
     * @param clientId
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingList)
    @RequestMapping(value = "total/list", method = RequestMethod.GET)
    public String toTotalEvent(@RequestParam("clientId") Integer clientId,
                               Model model) {
        model.addAttribute("clientId", clientId);
        return "admin/event/client-event-list";
    }


    /**
     * 患者参与活动列表
     *
     * @param clientId
     * @param pageNo
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingList)
    @RequestMapping(value = "ajax/total/list", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxTotalList(@RequestParam("clientId") Integer clientId,
                              @RequestParam("pageNo") Integer pageNo) {
        JSONObject json = new JSONObject();
        List<Event> events = Collections.emptyList();
        int dataCount = 0, numCount = 0;
        if (pageNo == null) {
            pageNo = 1;
        }
        int offset = (pageNo - 1) * pageSize;
        try {
            events = processingEventService.listEventByClientId(clientId, offset, pageSize);
            numCount = processingEventService.countEventByClientId(clientId);
            dataCount = DataUtils.getPageCount(pageSize, numCount);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", events);
        json.put("dataCount", dataCount);
        json.put("numCount", numCount);
        return json;
    }

    /**
     * 根据名称查询前20个活动名称
     *
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingList)
    @RequestMapping(value = "ajax/searchByEventName", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSearchByEventName(@RequestParam("name") String name) {
        JSONObject json = new JSONObject();
        List<EventTerm> eventTerms = Collections.emptyList();
        JSONArray list = new JSONArray();
        try {
            eventTerms = eventTermService.searchTOP20ByName(name);
            for(EventTerm eventTerm : eventTerms){
               JSONObject jsonObject = new JSONObject();
               jsonObject.put("id",eventTerm.getId());
               jsonObject.put("name",eventTerm.getName());
               list.add(jsonObject);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", list);
        return json;
    }

    /**
     * 根据创建人姓名模糊查询前20条姓名
     *
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingList)
    @RequestMapping(value = "ajax/searchByName", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSearchByName(@RequestParam("name") String name) {
        JSONObject json = new JSONObject();
        List<Employee> employees = Collections.emptyList();
        JSONArray list = new JSONArray();
        try {
            employees = processingEventService.listTop20EmployeeByName(name);
            for (Employee employee : employees) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", employee.getId());
                jsonObject.put("name", employee.getName());
                list.add(jsonObject);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", employees);
        return json;
    }

    /**
     * 根据创建人身份证号模糊查询前20条身份证号码
     *
     * @param idcardNum
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingList)
    @RequestMapping(value = "ajax/searchByIdcard", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSearchByIdcard(@RequestParam("idcardNum") String idcardNum) {
        JSONObject json = new JSONObject();
        List<Employee> employees = Collections.emptyList();
        JSONArray list = new JSONArray();
        try {
            employees = processingEventService.listTop20EmployeeByIdcard(idcardNum);
            for (Employee employee : employees) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", employee.getId());
                jsonObject.put("name", employee.getIdcardNum());
                list.add(jsonObject);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", list);
        return json;
    }


    /**
     * 根据参与人name模糊查询前20条name
     *
     * @param eventTermId
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingList)
    @RequestMapping(value = "ajax/searchByParticipantName", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSearchByParticipantName(@RequestParam("eventTermId") Integer eventTermId,
                                            @RequestParam("name") String name) {
        JSONObject json = new JSONObject();
        List<EventRecordNotice> eventRecordNotices = Collections.emptyList();
        JSONArray list = new JSONArray();
        try {
            eventRecordNotices = eventRecordNoticeService.listNameTop20ByEventTermId(eventTermId, name);
            for(EventRecordNotice eventRecordNotice : eventRecordNotices){
               JSONObject jsonObject = new JSONObject();
               jsonObject.put("id",eventRecordNotice.getId());
               jsonObject.put("name",eventRecordNotice.getParticipantName());
               list.add(jsonObject);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", list);
        return json;
    }

    /**
     * 根据参与人idcardNum模糊查询前20条idcardNum
     *
     * @param eventTermId
     * @param idcardNum
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventProcessingList)
    @RequestMapping(value = "ajax/searchByParticipantIdcard", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSearchByParticipantIdcard(@RequestParam("eventTermId") Integer eventTermId,
                                              @RequestParam("idcardNum") String idcardNum) {
        JSONObject json = new JSONObject();
        List<EventRecordNotice> eventRecordNotices = Collections.emptyList();
        JSONArray list = new JSONArray();
        try {
            eventRecordNotices = eventRecordNoticeService.listIdcardTop20ByEventTermId(eventTermId, idcardNum);
            for (EventRecordNotice eventRecordNotice : eventRecordNotices) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", eventRecordNotice.getId());
                jsonObject.put("name", eventRecordNotice.getParticipantIdcard());
                list.add(jsonObject);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", list);
        return json;
    }

}
