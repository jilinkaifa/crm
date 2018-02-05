package com.pandawork.crm.test.event;

import com.pandawork.crm.common.dto.event.EventSearchDto;
import com.pandawork.crm.common.entity.event.Event;
import com.pandawork.crm.common.enums.event.EventLevelEnums;
import com.pandawork.crm.service.event.prepare.EventService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * EventTest
 * 活动模块的测试类
 *
 * @author Flying
 * @date 2017/7/25 19:53
 */
public class EventTest extends AbstractTestCase {

    @Autowired
    private EventService eventService;

    @Test
    public void newEvent() throws Exception{
        Date dt = new Date();
        Event event = new Event();
        event.setName("中秋节活动1");
        event.setTemplateId(1);
        event.setIsMarked(1);
        event.setIsTemplateUsed(1);
        event.setIsMarked(0);
        event.setType(1);
        event.setMemberGroupId(1);
        event.setLevel(1);
        event.setRemindTime(3);
        event.setStartDate(dt);
        event.setEndDate(dt);
        event.setPollingTime(1);
        event.setPollingStartTime(dt);
        event.setPollingEndTime(dt);
        event.setIsPointsRelated(1);
        event.setIsCheckItemRelated(1);
        event.setContent("这个活动就是要一起嗨起来");
        event.setDicNoticeType(111);
        event.setNoticeContent("快来参加");
        event.setApplyTime(dt);
        event.setApprovalId(1);
        event.setApprovalStatus(1);
        event.setApprovalTime(dt);
        event.setIsLogout(0);
        event.setCreatedPartyId(1);
        try{
            eventService.newEvent(event);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void listAll() throws Exception{
        List<Event> list = Collections.emptyList();
        try {
            list = eventService.listAll();
        }catch (Exception e){
            throw new Exception(e);
        }
        for (Event event : list){
            System.out.println(event.getName());
        }
    }

    @Test
    public void queryById() throws Exception{
        Event event = new Event();
        try {
            event = eventService.queryById(1);
        }catch (Exception e){
            throw new Exception(e);
        }
        System.out.println(event);
    }

    @Test
    public void checkEventNameIsExit() throws Exception{
        boolean exit = eventService.checkEventNameIsExit("中秋节活动");
        System.out.println(exit);
    }

    @Test
    public void updateEvent() throws Exception{
        Event event = new Event();
        event.setId(1);
        event.setName("中秋节活动的升级版");
        eventService.updateEvent(event);
    }

    @Test
    public void updateApprovalStatus() throws Exception{
        eventService.updateApprovalStatus(1, 2, 1, "" );
    }

    @Test
    public void notLogoutEvent() throws Exception{
        List<Event> list = Collections.emptyList();
        try {
            list = eventService.listNotLogoutEvent();
        }catch (Exception e){
            throw new Exception(e);
        }
        for (Event event : list){
            System.out.println(event.getName());
        }
    }

    @Test
    public void notAdoptEvent() throws Exception{
        List<Event> list = Collections.emptyList();
        try {
            list = eventService.listNotAdoptEvent();
        }catch (Exception e){
            throw new Exception(e);
        }
        for (Event event : list){
            System.out.println(event.getName());
        }
    }

//    @Test
//    public void autoLogoutEvent() throws Exception{
//        eventService.autoLogoutEvent();
//    }

    @Test
    public void countByEventSearchDto() throws Exception{
        EventSearchDto eventSearchDto = new EventSearchDto();
        eventSearchDto.setName("中秋节");
        int count = eventService.countByEventSearchDto(eventSearchDto);
        System.out.println(count);
    }

    @Test
    public void listByEventSearchDto()throws Exception{
        List<Event> eventList = Collections.emptyList();
        EventSearchDto eventSearchDto = new EventSearchDto();
        List<Integer> partyIdList = new ArrayList<Integer>();
        partyIdList.add(133);
        partyIdList.add(1);
        //eventSearchDto.setName("中秋节");
        eventSearchDto.setOffset(0);
        eventSearchDto.setPageNo(0);
        eventSearchDto.setPageSize(10);
        eventSearchDto.setPartyIdList(partyIdList);
        eventList = eventService.listByEventSearchDto(eventSearchDto);
        for (Event event : eventList) {
            String name = event.getName();
            System.out.println(name);
        }
    }

//    @Test
//    public void autoCreateEventRecordNoticeAndEventTerm() throws Exception{
//        Event event = eventService.queryById(1);
//        Date da = new Date("2017/10/31");
//        Calendar cal = Calendar.getInstance();
//        //int currentYear = cal.get(Calendar.YEAR);
//        //cal.setTime(Calendar.YEAR, currentYear);
//        //Calendar lastDate = (Calendar) cal.clone();
//        cal.setTime(da);
//        int month = cal.get(cal.MONTH)+1;
//        int day = cal.get(cal.DATE);
//        cal.add(Calendar.MONTH, 1);
//        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
//        String time = fmt.format(cal.getTime());
//        Calendar cal2 = Calendar.getInstance();
//        int day2 = cal2.get(Calendar.DATE);
//        System.out.println("**********************");
//        System.out.println(day2);
//        System.out.println(day);
//        System.out.println(month);
//        System.out.println(time);
//        System.out.println("**********************");
//    }

    @Test
    public void autoCreateEventRecordNoticeAndEventTerm() throws Exception{
        eventService.autoCreateEventRecordNoticeAndEventTerm();
    }

    @Test
    public void autoLogoutEvent() throws Exception{
        eventService.autoLogoutEvent();
    }
}
