package com.pandawork.crm.web.controller.admin.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.dto.event.archived.EventArchivedSearchDto;
import com.pandawork.crm.common.entity.event.Event;
import com.pandawork.crm.common.entity.event.EventRecordNotice;
import com.pandawork.crm.common.entity.party.group.employee.Employee;
import com.pandawork.crm.common.enums.event.EventLevelEnums;
import com.pandawork.crm.common.enums.event.EventParticipantStatusEnums;
import com.pandawork.crm.common.enums.event.EventTypeEnums;
import com.pandawork.crm.common.enums.event.NoticeStatusEnums;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.DateUtils;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.service.event.archived.EventArchivedService;
import com.pandawork.crm.web.spring.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * EventArchivedController
 * Author： csy
 * Date: 2017/7/29
 * Time: 8:44
 */
@Controller
@Module(ModuleEnums.AdminECRBEventArchived)
@RequestMapping(value = URLConstants.ADMIN_EVENT_ARCHIVED_URL)
public class EventArchivedController extends AbstractController {

    private int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 去列表页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventArchivedList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model) {
        int pageCount = 0;
        EventArchivedSearchDto eventArchivedSearchDto = new EventArchivedSearchDto();
        try {
            pageCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE, eventArchivedService.countBySearchDto(eventArchivedSearchDto));
            model.addAttribute("pageCount", pageCount);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/event/archived/list";
    }


    /**
     * ajax获取列表
     *
     * @param pageNo
     * @param eventArchivedSearchDto
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventArchivedList)
    @RequestMapping(value = {"ajax/list"}, method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@RequestParam("pageNo") Integer pageNo,
                         EventArchivedSearchDto eventArchivedSearchDto) {
        JSONObject json = new JSONObject();
        int offset = 0;
        if (Assert.isNotNull(pageNo)) {
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * DEFAULT_PAGE_SIZE;
        }


        String name = eventArchivedSearchDto.getName();
        if (name != null) {
            try {
                name = new String(name.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String eventName = eventArchivedSearchDto.getEventName();
        if (eventName != null) {
            try {
                eventName = new String(eventName.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        eventArchivedSearchDto.setName(name);
        eventArchivedSearchDto.setEventName(eventName);
        List<Event> eventList = Collections.emptyList();
        String str = "无";

        EventArchivedSearchDto eventArchivedSearchDto1 = new EventArchivedSearchDto();
        List<Event> events = Collections.emptyList();

        try {
            if(Assert.isNull(eventArchivedSearchDto.getName())&&Assert.isNull(eventArchivedSearchDto.getIdcardNum())
                    &&Assert.isNull(eventArchivedSearchDto.getEventName())
                    &&Assert.isNull(eventArchivedSearchDto.getEventLevel())
                    &&Assert.isNull(eventArchivedSearchDto.getEventType())){
                events = eventArchivedService.queryEventArchivedByEventDto(eventArchivedSearchDto1);
            }else {
                eventArchivedSearchDto.setOffset(null);
                eventArchivedSearchDto.setPageSize(null);
                eventArchivedSearchDto.setPage(null);
                eventArchivedSearchDto1 = eventArchivedSearchDto;
                events = eventArchivedService.queryEventArchivedByEventDto(eventArchivedSearchDto1);
            }

            eventArchivedSearchDto.setOffset(offset);
            eventArchivedSearchDto.setPage(pageNo);
            eventArchivedSearchDto.setPageSize(DEFAULT_PAGE_SIZE);

            eventList = eventArchivedService.queryEventArchivedByEventDto(eventArchivedSearchDto);
            JSONArray jsonArray = new JSONArray();
            for (Event event : eventList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", event.getId());
                if(Assert.isNotNull(event.getEventTermId())){
                    jsonObject.put("eventTermId",event.getEventTermId());
                }else{
                    jsonObject.put("eventTermId", -1);
                }
                jsonObject.put("eventName", event.getName());
                jsonObject.put("eventType", EventTypeEnums.valueOf(event.getType()).getType());
                jsonObject.put("eventLevel", EventLevelEnums.valueOf(event.getLevel()).getLevel());
                if (Assert.isNull(event.getEventStatus())) {
                    jsonObject.put("eventStatus", "已注销");
                } else {
                    jsonObject.put("eventStatus", "已归档");
                }
                //待通知人数
                if (Assert.isNull(event.getEventStatus())) {
                    jsonObject.put("countNoticeing", 0);
                } else {
                    jsonObject.put("countNoticeing", processingEventService.countToBeNoticedPerson(event.getEventTermId()));
                }
                if (Assert.isNull(event.getBQStartDate())) {
                    jsonObject.put("BQStartDate", str);
                } else {
                    jsonObject.put("BQStartDate", DateUtils.formatDateSimple(event.getBQStartDate()));
                }
                if (Assert.isNull(event.getMemberGroupName())) {
                    jsonObject.put("memberName", str);
                } else {
                    jsonObject.put("memberName", event.getMemberGroupName());
                }
                //本期结束时间
                if (Assert.isNull(event.getBQEndDate())) {
                    jsonObject.put("BQEndDate", str);
                } else {
                    jsonObject.put("BQEndDate", DateUtils.formatDateSimple(event.getBQEndDate()));
                }
                //待完成人数
                if (Assert.isNull(event.getBQStartDate())) {
                    jsonObject.put("countFinshing", 0);
                } else {
                    jsonObject.put("countFinshing", processingEventService.countToBeFinishedPerson(eventArchivedService.queryEventTermByEventId(event.getEventTermId()).getId()));
                }
                jsonObject.put("createdPartyName", event.getCreatedPartyName());
                jsonObject.put("createdTime", DateUtils.formatDateSimple(event.getCreatedTime()));
                jsonArray.add(jsonObject);
            }
            int dataCount = 0, numCount = 0;
//            numCount = eventArchivedService.countBySearchDto(eventArchivedSearchDto);
            numCount = events.size();
            dataCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE, numCount);
            json.put("code", AJAX_SUCCESS_CODE);
            json.put("list", jsonArray);
            json.put("dataCount", dataCount);
            json.put("numCount", numCount);
            return json;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax创建人姓名模糊查询
     *
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventArchivedList)
    @RequestMapping(value = {"ajax/searchByName"}, method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSearchByName(@RequestParam(value = "createdPartyName", required = false) String name) {
        JSONArray jsonArray = new JSONArray();
        EventArchivedSearchDto eventArchivedSearchDto = new EventArchivedSearchDto();
        try {
            eventArchivedSearchDto.setName(name);
            List<Event> eventList = eventArchivedService.queryEventArchivedByEventDto(eventArchivedSearchDto);

            for (int i = 0; i < eventList.size() - 1; i++) {
                for (int j = eventList.size() - 1; j > i; j--) {
                    if (eventList.get(j).getCreatedPartyName().equals(eventList.get(i).getCreatedPartyName())) {
                        eventList.remove(j);
                    }
                }
            }

            if (eventList.size() > 20) {
                eventList.subList(0, 19);
            }

            for (Event event : eventList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", event.getId());
                jsonObject.put("name", event.getCreatedPartyName());
                jsonArray.add(jsonObject);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray);
    }

    /**
     * ajax创建人身份证号模糊查询
     *
     * @param idcardNum
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventArchivedList)
    @RequestMapping(value = {"ajax/searchByIdcard"}, method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSearchByIdcardNum(@RequestParam("idcardNum") String idcardNum) {
         JSONArray list = new JSONArray();
        JSONObject json = new JSONObject();
        EventArchivedSearchDto eventArchivedSearchDto = new EventArchivedSearchDto();
        try {
            eventArchivedSearchDto.setIdcardNum(idcardNum);
            List<Event> eventList = eventArchivedService.queryEventArchivedByEventDto(eventArchivedSearchDto);
            List<String> idcardNums = eventArchivedService.queryIdcardByIdcard(idcardNum);

            for (int i = 0; i < idcardNums.size() - 1; i++) {
                for (int j = idcardNums.size() - 1; j > i; j--) {
                    if (idcardNums.get(j).equals(idcardNums.get(i))) {
                        idcardNums.remove(j);
                    }
                }
            }

            if (idcardNums.size() > 20) {
                idcardNums.subList(0, 19);
            }
            for (String item : idcardNums) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", item);
                list.add(jsonObject);
            }
            json.put("code", AJAX_SUCCESS_CODE);
            json.put("list", list);
            return json;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax活动名称模糊查询
     *
     * @param eventName
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventArchivedList)
    @RequestMapping(value = {"ajax/searchByEventName"}, method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSearchByEventName(@RequestParam("eventName") String eventName) {
        JSONArray jsonArray = new JSONArray();
        EventArchivedSearchDto eventArchivedSearchDto = new EventArchivedSearchDto();
        try {
            eventArchivedSearchDto.setEventName(eventName);
            List<Event> eventList = eventArchivedService.queryEventArchivedByEventDto(eventArchivedSearchDto);

            for (int i = 0; i < eventList.size() - 1; i++) {
                for (int j = eventList.size() - 1; j > i; j--) {
                    if (eventList.get(j).getName().equals(eventList.get(i).getName())) {
                        eventList.remove(j);
                    }
                }
            }
            if (eventList.size() > 20) {
                eventList.subList(0, 19);
            }

            for (Event event : eventList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", event.getId());
                jsonObject.put("name", event.getName());
                jsonArray.add(jsonObject);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray);
    }

    /**
     * 去往详情页
     *
     * @param id
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventArchivedDetail)
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String toDetail(@RequestParam("id") Integer id,
                           Model model) {
        Event event = new Event();
        List<Event> events = Collections.emptyList();
        int offset = 0;
        try {
            event = eventService.queryById(id);
            events = eventArchivedService.queryEventArchivedById(id, offset, pageSize);
            model.addAttribute("event", event);
            model.addAttribute("eventRecordNotices", events);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "/admin/event/archived/detail";
    }

    /**
     * 去往通知详情页面
     *
     * @param id
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminECRBEventArchivedDetail, extModule = ModuleEnums.AdminECRBEventArchivedDetailNotice)
    @RequestMapping(value = "notice", method = RequestMethod.GET)
    public String toNotice(@RequestParam("id") Integer id,
                           Model model) {
        Event event = new Event();
        List<Event> events = Collections.emptyList();
        int offset = 0;
        try {
            event = eventService.queryById(id);
            events = eventArchivedService.queryEventArchivedById(id, offset, pageSize);
            model.addAttribute("event", event);
            model.addAttribute("eventRecordNotices", events);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "/admin/event/archived/notice";

    }

    /**
     * ajax获取详情页列表
     *
     * @param pageNo
     * @param eventArchivedSearchDto
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventArchivedDetail)
    @RequestMapping(value = {"ajax/detailList"}, method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxDetailList(@RequestParam("pageNo") Integer pageNo,
                               @RequestParam("id") Integer eventId,
                               EventArchivedSearchDto eventArchivedSearchDto) {
        JSONObject json = new JSONObject();
        int offset = 0;
        if (Assert.isNotNull(pageNo)) {
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * DEFAULT_PAGE_SIZE;
        }

        eventArchivedSearchDto.setOffset(offset);
        eventArchivedSearchDto.setPage(pageNo);
        eventArchivedSearchDto.setOffset(DEFAULT_PAGE_SIZE);
        eventArchivedSearchDto.setEventId(eventId);

        String name = eventArchivedSearchDto.getName();
        if (name != null) {
            try {
                name = new String(name.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        eventArchivedSearchDto.setName(name);
        String str = "无";

        List<EventRecordNotice> eventRecordNotices = Collections.emptyList();

        try {
            eventRecordNotices = eventArchivedService.listEventRecordNoticeBySearchDto(eventArchivedSearchDto);
            for (int i = 0; i < eventRecordNotices.size() - 1; i++) {
                for (int j = eventRecordNotices.size() - 1; j > i; j--) {
                    if (eventRecordNotices.get(j).getClientId().equals(eventRecordNotices.get(i).getClientId())) {
                        eventRecordNotices.remove(j);
                    }
                }
            }
            JSONArray jsonArray = new JSONArray();
            for (EventRecordNotice eventRecordNotice : eventRecordNotices) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", eventRecordNotice.getId());
                if (Assert.isNull(eventRecordNotice.getEventId())) {
                    jsonObject.put("eventId", str);
                } else {
                    jsonObject.put("eventId", eventRecordNotice.getEventId());
                }
                if (Assert.isNull(eventRecordNotice.getParticipantName())) {
                    jsonObject.put("name", str);
                } else {
                    jsonObject.put("name", eventRecordNotice.getParticipantName());
                }
                if (Assert.isNull(eventRecordNotice.getParticipantIdcard())) {
                    jsonObject.put("idcard", str);
                } else {
                    jsonObject.put("idcard", eventRecordNotice.getParticipantIdcard());
                }
                if (Assert.isNull(eventRecordNotice.getParticipantTel())) {
                    jsonObject.put("tel", str);
                } else {
                    jsonObject.put("tel", eventRecordNotice.getParticipantTel());
                }
                if (Assert.isNull(eventRecordNotice.getNoticeStatus())) {
                    jsonObject.put("noticeStatus", str);
                } else {
                    jsonObject.put("noticeStatus", NoticeStatusEnums.valueOf(eventRecordNotice.getNoticeStatus()).getStatus());
                }
                if (Assert.isNull(eventRecordNotice.getEventStartDate())) {
                    jsonObject.put("BQStartDate", str);
                } else {
                    jsonObject.put("BQStartDate", DateUtils.formatDateSimple(eventRecordNotice.getEventStartDate()));
                }
                jsonObject.put("eventPaticipant", eventArchivedService.countCurrentParticipation(eventRecordNotice.getId()));//本期参与情况
                if (Assert.isNull(eventRecordNotice.getEventParticipantStatus())) {
                    jsonObject.put("eventPaticipantStatus", str);
                } else {
                    jsonObject.put("eventPaticipantStatus", EventParticipantStatusEnums.valueOf(eventRecordNotice.getEventParticipantStatus()).getStatus());
                }
                jsonObject.put("accJoinSituation", eventArchivedService.countCumulativeParticipationWithoutArchived(eventId));//累计参与情况
                jsonArray.add(jsonObject);
            }
            int count = 0;
            int pageCount = 0;
            count = eventArchivedService.countNOLogoutBySearchDto(eventArchivedSearchDto);
            pageCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE, count);
            json.put("code", AJAX_SUCCESS_CODE);
            json.put("list", jsonArray);
            json.put("dataCount", pageCount);
            json.put("numCount", count);
            return json;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax参与人姓名模糊查询
     *
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventArchivedDetail)
    @RequestMapping(value = {"ajax/searchByParticipantName"}, method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSearchByParticipantName(@RequestParam("id") Integer eventId,
                                            @RequestParam(value = "name", required = false) String name) {
        JSONArray jsonArray = new JSONArray();
        EventArchivedSearchDto eventArchivedSearchDto = new EventArchivedSearchDto();
        try {
            eventArchivedSearchDto.setName(name);
            eventArchivedSearchDto.setEventId(eventId);
            List<EventRecordNotice> eventRecordNotices = eventArchivedService.listEventRecordNoticeBySearchDto(eventArchivedSearchDto);

            if (eventRecordNotices.size() > 20) {
                eventRecordNotices.subList(0, 19);
            }

            for (EventRecordNotice eventRecordNotice : eventRecordNotices) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", eventRecordNotice.getId());
                jsonObject.put("name", eventRecordNotice.getParticipantName());
                jsonArray.add(jsonObject);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray);
    }

    /**
     * ajax参与人身份证号模糊查询
     *
     * @param idcard
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventArchivedDetail)
    @RequestMapping(value = {"ajax/searchByParticipantIdcard"}, method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSearchByParticipantIdcard(@RequestParam("id") Integer eventId,
                                              @RequestParam("idcard") String idcard) {
        JSONArray list = new JSONArray();
        JSONObject json = new JSONObject();
        EventArchivedSearchDto eventArchivedSearchDto = new EventArchivedSearchDto();
        try {
            eventArchivedSearchDto.setIdcardNum(idcard);
            eventArchivedSearchDto.setEventId(eventId);
            List<String> idcardNums = eventArchivedService.queryParticipantIdcardByIdcard(idcard);

            for (int i = 0; i < idcardNums.size() - 1; i++) {
                for (int j = idcardNums.size() - 1; j > i; j--) {
                    if (idcardNums.get(j).equals(idcardNums.get(i))) {
                        idcardNums.remove(j);
                    }
                }
            }

            if (idcardNums.size() > 20) {
                idcardNums.subList(0, 19);
            }

            for (String item : idcardNums) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", item);
                list.add(jsonObject);
            }

            json.put("code", AJAX_SUCCESS_CODE);
            json.put("list", list);
            return json;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

    }

    /**
     * ajax获取详情页活动通知列表
     *
     * @param pageNo
     * @param eventArchivedSearchDto
     * @return
     */
    @Module(ModuleEnums.AdminECRBEventArchivedDetailNotice)
    @RequestMapping(value = {"ajax/detaiNoticelList"}, method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxDetailNoticeList(@RequestParam("pageNo") Integer pageNo,
                                     @RequestParam("id") Integer eventId,
                                     EventArchivedSearchDto eventArchivedSearchDto) {
        int offset = 0;
        if (Assert.isNotNull(pageNo)) {
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * DEFAULT_PAGE_SIZE;
        }

        eventArchivedSearchDto.setOffset(offset);
        eventArchivedSearchDto.setPage(pageNo);
        eventArchivedSearchDto.setOffset(DEFAULT_PAGE_SIZE);
        eventArchivedSearchDto.setEventId(eventId);

        List<EventRecordNotice> eventRecordNotices = Collections.emptyList();
        String str = "无";
        JSONObject json = new JSONObject();

        try {
            eventRecordNotices = eventArchivedService.listEventRecordNoticeBySearchDto(eventArchivedSearchDto);
            for (int i = 0; i < eventRecordNotices.size() - 1; i++) {
                for (int j = eventRecordNotices.size() - 1; j > i; j--) {
                    if (eventRecordNotices.get(j).getClientId().equals(eventRecordNotices.get(i).getClientId())) {
                        eventRecordNotices.remove(j);
                    }
                }
            }
            JSONArray jsonArray = new JSONArray();
            for (EventRecordNotice eventRecordNotice : eventRecordNotices) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", eventRecordNotice.getId());
                jsonObject.put("name", eventRecordNotice.getParticipantName());
                if (Assert.isNull(eventRecordNotice.getParticipantIdcard())) {
                    jsonObject.put("idcard", str);
                } else {
                    jsonObject.put("idcard", eventRecordNotice.getParticipantIdcard());
                }
                if (Assert.isNull(eventRecordNotice.getParticipantIdcard())) {
                    jsonObject.put("tel", str);
                } else {
                    jsonObject.put("tel", eventRecordNotice.getParticipantTel());
                }
                if (Assert.isNull(eventRecordNotice.getNoticeStatus())) {
                    jsonObject.put("noticeStatus", str);
                } else {
                    jsonObject.put("noticeStatus", NoticeStatusEnums.valueOf(eventRecordNotice.getNoticeStatus()).getStatus());
                }
                if (Assert.isNull(eventRecordNotice.getComment())) {
                    jsonObject.put("comment", str);
                } else {
                    jsonObject.put("comment", eventRecordNotice.getComment());
                }
                if (Assert.isNull(eventRecordNotice.getNoticeTime())) {
                    jsonObject.put("noticeTime", str);
                } else {
                    jsonObject.put("noticeTime", DateUtils.formatDateSimple(eventRecordNotice.getNoticeTime()));
                }
                if (Assert.isNull(eventRecordNotice.getSysName())) {
                    jsonObject.put("noticeName", str);
                } else {
                    jsonObject.put("noticeName", eventRecordNotice.getSysName());
                }
                jsonArray.add(jsonObject);
            }
            int count = 0;
            int pageCount = 0;
//            count = eventArchivedService.countBySearchDto(eventArchivedSearchDto);
//            pageCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE, count);

            count = eventArchivedService.countNOLogoutBySearchDto(eventArchivedSearchDto);
            pageCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE, count);
            json.put("code", AJAX_SUCCESS_CODE);
            json.put("list", jsonArray);
            json.put("dataCount", pageCount);
            json.put("numCount", count);
            return json;
//            jsonObject1.put("list",pageCount);
//            jsonObject1.put("numCount",count);
//
//            return jsonObject1;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

//    /**
//     * 去往本期参与情况页
//     *
//     * @param eventId
//     * @param pageNo
//     * @return
//     */
//    @Module(value = ModuleEnums.AdminECRBEventArchived,extModule = ModuleEnums.AdminECRBEventArchivedDetail)
//    @RequestMapping(value = {"current"},method = RequestMethod.GET)
//    public String toCurrent(@RequestParam("eventId") Integer eventId,
//                            @RequestParam("recordNoticeId") Integer recordNoticeId,
//                            @RequestParam("pageNo") Integer pageNo,
//                            Model model){
//        int offset = 0;
//        if(Assert.isNotNull(pageNo)){
//            pageNo = pageNo <= 0 ? 0 : pageNo-1;
//            offset = pageNo*DEFAULT_PAGE_SIZE;
//        }
//
//        EventArchivedSearchDto eventArchivedSearchDto = new EventArchivedSearchDto();
//        eventArchivedSearchDto.setOffset(offset);
//        eventArchivedSearchDto.setPage(pageNo);
//        eventArchivedSearchDto.setOffset(DEFAULT_PAGE_SIZE);
//        eventArchivedSearchDto.setEventId(eventId);
//        JSONArray jsonArray = new JSONArray();
//
//        try {
//            List<EventRecordNotice> eventRecordNoticeList = eventArchivedService.listEventRecordNoticeBySearchDto(eventArchivedSearchDto);
//            for(int i = 0 ; i < eventRecordNoticeList.size() ; i++){
//                EventRecordNotice eventRecordNotice = eventRecordNoticeList.get(i);
//
//                //获取此次活动记录通知
//                if(eventRecordNotice.getId() == recordNoticeId){
//                    Employee employee = employeeService.queryByPartyId(eventRecordNotice.getCreatedPartyId());
//                    List<PointsItem> pointsItemList = eventArchivedService.queryPointsItemByEventId(eventRecordNotice.getId());
//                    List<CheckItem> checkItemList = eventArchivedService.queryCheckItemByEventId(eventRecordNotice.getId());
//                    List<String> contentList = Collections.emptyList();
//                    int size = pointsItemList.size() + checkItemList.size();
//
//                    //将积分及积分值放入内容列表
//                    if(Assert.isNotNull(pointsItemList)){
//                        for (PointsItem pointsItem : pointsItemList){
//                            String content = pointsItem.getName() + String.valueOf(pointsItem.getPointsValue());
//                            contentList.add(content);
//                        }
//                    }
//                    //将检查项放入列表
//                    if(Assert.isNotNull(checkItemList)){
//                        for(CheckItem checkItem : checkItemList){
//                            contentList.add(checkItem.getName());
//                        }
//                    }
//                    for(int j = 0 ; j <= size-1 ; j++ ){
//                        JSONObject json = new JSONObject();
//                        json.put("id",eventRecordNotice.getId());
//                        json.put("name",eventRecordNotice.getParticipantName());
//                        json.put("startDate",eventRecordNotice.getBQStartDate());
//                        json.put("content",contentList.get(i));
//                        json.put("comment",eventRecordNotice.getComment());
//                        json.put("createPartyName",employee.getName());
//                        json.put("createdTime",eventRecordNotice.getCreatedTime());
//                        jsonArray.add(json);
//                    }
//                 }
//            }
//
//            model.addAttribute("jsonArray",jsonArray);
//
//        }catch (SSException e){
//            LogClerk.errLog.error(e);
//            sendErrMsg(e.getMessage());
//            return ADMIN_SYS_ERR_PAGE;
//        }
//        return "/admin/event/archived/current";
//
//
//    }


}
