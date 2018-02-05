package com.pandawork.crm.service.event.archived.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.dto.event.archived.EventArchivedSearchDto;
import com.pandawork.crm.common.entity.client.points.PointsItem;
import com.pandawork.crm.common.entity.event.CheckItem;
import com.pandawork.crm.common.entity.event.Event;
import com.pandawork.crm.common.entity.event.EventRecordNotice;
import com.pandawork.crm.common.entity.event.EventTerm;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.mapper.event.EventArchivedMapper;
import com.pandawork.crm.service.event.EventRecordNoticeService;
import com.pandawork.crm.service.event.archived.EventArchivedService;
import com.pandawork.crm.service.event.prepare.EventService;
import com.pandawork.crm.service.party.group.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * EventArchivedServiceImpl
 * Author： csy
 * Date: 2017/7/26
 * Time: 19:46
 */
@Service("eventArchivedService")
public class EventArchivedServiceImpl implements EventArchivedService {

    @Autowired
    private EventArchivedMapper eventArchivedMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EventRecordNoticeService eventRecordNoticeService;

    @Autowired
    private EventService eventService;


    /**
     * 根据eventArchivedSearchDto分页获取已归活动信息详情
     *
     * @param eventArchivedSearchDto
     * @return
     * @throws SSException
     */
    @Override
    public List<Event> queryEventArchivedByEventDto(EventArchivedSearchDto eventArchivedSearchDto) throws SSException {

        List<Event> events = Collections.emptyList();
        try {
            events = eventArchivedMapper.listEventArchivedByDto(eventArchivedSearchDto);
//            for(Event event : events){
//                setEventValue(event);
//            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEventArchivedFail, e);
        }
        return events;
    }

    /**
     * 根据活动id显示已归档活动详情
     *
     * @param id
     * @return
     * @throws SSException
     */
    @Override
    public List<Event> queryEventArchivedById(int id, int offset, int pageSize) throws SSException {
        try {
            return eventArchivedMapper.queryEventArchivedById(id, offset, pageSize);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEventArchivedByIdFail, e);
        }
    }


    /**
     * 计算查询数量
     *
     * @param eventArchivedSearchDto
     * @return
     * @throws SSException
     */
    @Override
    public int countBySearchDto(EventArchivedSearchDto eventArchivedSearchDto) throws SSException {
        Integer count = 0;
        try {
            count = eventArchivedMapper.countByEventSearchDto(eventArchivedSearchDto) + eventArchivedMapper.countLogoutByEventSearchDto();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountBySearchDtoFail, e);
        }
        return count == null ? 0 : count;
    }

    @Override
    public int countNOLogoutBySearchDto(EventArchivedSearchDto eventArchivedSearchDto) throws SSException {
        Integer count = 0;
        try {
            List<EventRecordNotice> eventRecordNotices = eventArchivedMapper.countQueryEventRecordNoticeBySearchDto(eventArchivedSearchDto);
            for (int i = 0; i < eventRecordNotices.size() - 1; i++) {
                for (int j = eventRecordNotices.size() - 1; j > i; j--) {
                    if (eventRecordNotices.get(j).getClientId().equals(eventRecordNotices.get(i).getClientId())) {
                        eventRecordNotices.remove(j);
                    }
                }
            }
            count = eventRecordNotices.size();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountBySearchDtoFail, e);
        }
        return count == null ? 0 : count;
    }

    /**
     * 根据idcard模糊获取idcard
     *
     * @param idcardNum
     * @return
     * @throws SSException
     */
    @Override
    public List<String> queryIdcardByIdcard(String idcardNum) throws SSException {
        List<String> idcardNums = Collections.emptyList();
        try {
            return idcardNums = eventArchivedMapper.queryIdcardByIdcard(idcardNum);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryIdcardNumByIdcardNumFail, e);
        }
    }

    /**
     * 根据idcard模糊查询参与人员的身份证号
     *
     * @param idcardNum
     * @return
     * @throws SSException
     */
    @Override
    public List<String> queryParticipantIdcardByIdcard(String idcardNum) throws SSException {
        List<String> idcardNums = Collections.emptyList();
        try {
            return idcardNums = eventArchivedMapper.queryParticipantIdcardByIdcard(idcardNum);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryIdcardNumByIdcardNumFail, e);
        }
    }

    /**
     * 根据searchDto获取活动记录通知
     *
     * @param eventArchivedSearchDto
     * @return
     * @throws SSException
     */
    @Override
    public List<EventRecordNotice> listEventRecordNoticeBySearchDto(EventArchivedSearchDto eventArchivedSearchDto) throws SSException {
        List<EventRecordNotice> eventRecordNoticeList = Collections.emptyList();
        try {
            eventRecordNoticeList = eventArchivedMapper.queryEventRecordNoticeBySearchDto(eventArchivedSearchDto);
            Iterator<EventRecordNotice> eventRecordNoticeIterator = eventRecordNoticeList.iterator();
//            while(eventRecordNoticeIterator.hasNext()){
//                EventRecordNotice eventRecordNotice = eventRecordNoticeIterator.next();
//                if(eventRecordNotice.getEventId() != eventArchivedSearchDto.getEventId())
//                    eventRecordNoticeIterator.remove();
//            }
//            for(EventRecordNotice eventRecordNotice : eventRecordNoticeList){
//                setEventRecordNoticeValue(eventRecordNotice);
//            }
            return eventRecordNoticeList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryIdcardNumByIdcardNumFail, e);
        }
    }



    /**
     * 根据活动id计算累计参与情况
     *
     * @param eventRecordNoticeId
     * @return
     * @throws SSException
     */
    @Override
    public int countCurrentParticipation(int eventRecordNoticeId) throws SSException{
        try {
            int countBQ = eventArchivedMapper.countPointsByEventId(eventRecordNoticeId) + eventArchivedMapper.countCheckItemByEventId(eventRecordNoticeId);
            return countBQ;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryIdcardNumByIdcardNumFail, e);
        }

    }


    /**
     * 根据活动id计算累计参与情况
     *
     * @param eventId
     * @return
     * @throws SSException
     */
    @Override
    public int countCumulativeParticipationWithoutArchived(int eventId) throws SSException{

        int countBQ = 0;
        int sum = 0;

        try {
            List<EventRecordNotice> eventRecordNotices = eventArchivedMapper.listEventRecordNoticeByEventId(eventId);
            for(EventRecordNotice eventRecordNotice : eventRecordNotices){
                countBQ = eventArchivedMapper.countPointsByEventId(eventRecordNotice.getId()) + eventArchivedMapper.countCheckItemByEventId(eventRecordNotice.getId());
                sum = sum + countBQ;
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryIdcardNumByIdcardNumFail, e);
        }
        return sum;
    }

    /**
     *  根据活动Id获取积分项
     *
     * @param eventRecordNoticeId
     * @return
     * @throws SSException
     */
    @Override
    public List<PointsItem> queryPointsItemByEventId(int eventRecordNoticeId) throws SSException{
        try{
            List<PointsItem> pointsItem = eventArchivedMapper.queryPointsByEventId(eventRecordNoticeId);
            return pointsItem;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryIdcardNumByIdcardNumFail, e);
        }
    }

    /**
     *  根据活动Id获取检查项
     *
     * @param eventId
     * @return
     * @throws SSException
     */
    @Override
    public List<CheckItem> queryCheckItemByEventId(int eventId) throws SSException{
        try{
            List<CheckItem> checkItem = eventArchivedMapper.queryCheckItemByEventId(eventId);
            return checkItem;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryIdcardNumByIdcardNumFail, e);
        }
    }

    @Override
    public EventTerm queryEventTermByEventId(int eventId) throws SSException{
        try{
           EventTerm eventTerm = eventArchivedMapper.queryEventTermByEventId(eventId);
           return eventTerm;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryOneEventTermByEventIdFail, e);
        }
    }


    /*********************私有方法**************************************/

    public EventRecordNotice setEventRecordNoticeValue(EventRecordNotice eventRecordNotice) throws Exception{
        if(Assert.isNotNull(eventRecordNotice)){
            try{
                if(Assert.isNotNull(eventRecordNotice.getSysId())){
                    eventRecordNotice.setSysName(employeeService.queryNameByPartyId(eventRecordNotice.getSysId()));
                }
            }catch (Exception e){
                LogClerk.errLog.error(e);
                throw SSException.get(CrmException.SystemException, e);
            }
        }
        return eventRecordNotice;
    }

//    public Event setEventValue(Event event) throws Exception{
//        if(Assert.isNotNull(event)){
//            try{
//                if(Assert.isNotNull(event.getId())) {
//                    event.setBQStartDate(eventArchivedMapper.queryEventRecordNoticeByEventId(event.getId()).getEventStartDate());
//                }
//            }catch (Exception e){
//                LogClerk.errLog.error(e);
//                throw SSException.get(CrmException.SystemException, e);
//            }
//        }
//        return event;
//    }


}
