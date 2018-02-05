package com.pandawork.crm.service.event.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.entity.client.basic.Client;
import com.pandawork.crm.common.entity.event.EventRecordNotice;
import com.pandawork.crm.common.entity.event.EventTerm;
import com.pandawork.crm.common.enums.event.EventParticipantStatusEnums;
import com.pandawork.crm.common.enums.event.NoticeStatusEnums;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.mapper.event.EventRecordNoticeMapper;
import com.pandawork.crm.service.client.basic.ClientService;
import com.pandawork.crm.service.client.member.MemberService;
import com.pandawork.crm.service.event.EventRecordNoticeService;
import com.pandawork.crm.service.event.processing.ProcessingEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * EventRecordNoticeServiceImpl
 * Author： wychen
 * Date: 2017/7/21
 * Time: 10:30
 */
@Service("eventRecordNoticeService")
public class EventRecordNoticeServiceImpl implements EventRecordNoticeService{


    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private EventRecordNoticeMapper eventRecordNoticeMapper;

    @Autowired
    private ProcessingEventService processingEventService;

    @Autowired
    private ClientService clientService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public EventRecordNotice newEventRecordNotice(EventRecordNotice eventRecordNotice) throws SSException{
        EventRecordNotice newEventRecordNotice = new EventRecordNotice();
        try {
            if(Assert.isNotNull(eventRecordNotice)){
                newEventRecordNotice = commonDao.insert(eventRecordNotice);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.NewEventRecordNoticeFail, e);
        }
        return newEventRecordNotice;
    }

    @Override
    public EventRecordNotice queryById(int id) throws SSException{
        EventRecordNotice eventRecordNotice = new EventRecordNotice();
        try{
            if(Assert.isNotNull(id)){
                eventRecordNotice = commonDao.queryById(EventRecordNotice.class,id);
//                setExtraValue(eventRecordNotice);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryByEventRecordNoticeFail, e);
        }
        return eventRecordNotice;
    }

    @Override
    public List<EventRecordNotice> listByEventTermId(int eventTermId, String participantName, String participantIdcard, int offset, int pageSize) throws SSException{
        List<EventRecordNotice> eventRecordNotices = Collections.emptyList();
        try{
            if(Assert.isNotNull(eventTermId)){
                eventRecordNotices = eventRecordNoticeMapper.queryByEventTermId(eventTermId, participantName, participantIdcard,offset,pageSize);
                for(EventRecordNotice eventRecordNotice: eventRecordNotices){
                    setExtraValue(eventRecordNotice);
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryByEventRecordNoticeFail, e);
        }
        return eventRecordNotices;
    }

    @Override
    public int countByEventTermId(int eventId, String participantName, String participantIdcard) throws SSException{
        try{
            return eventRecordNoticeMapper.countByEventTermId(eventId,participantName, participantIdcard);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryByEventRecordNoticeFail, e);
        }
    }

    @Override
    public List<EventRecordNotice> queryByEventTermIdWithoutNotice(int eventTermId, int offset, int pageSize) throws SSException{
        List<EventRecordNotice> eventRecordNotices = Collections.emptyList();
        try{
            if(Assert.isNotNull(eventTermId)){
                eventRecordNotices = eventRecordNoticeMapper.queryByEventTermIdWithoutNotice(eventTermId,offset,pageSize);
                for(EventRecordNotice eventRecordNotice: eventRecordNotices){
                    setExtraValue(eventRecordNotice);
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryByEventRecordNoticeFail, e);
        }
        return eventRecordNotices;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void update(EventRecordNotice eventRecordNotice) throws SSException{
        try{
            if(Assert.isNotNull(eventRecordNotice)){
                commonDao.update(eventRecordNotice);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryByEventRecordNoticeFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void newByClientIdAndGroupId(int clientId, int groupId) throws SSException{
        try{
            if (Assert.isNotNull(clientId) && Assert.isNotNull(groupId)){
                List<EventTerm> eventTermList = processingEventService.listByGroupId(groupId);
                for (EventTerm eventTerm : eventTermList){
                    EventRecordNotice eventRecordNotice = new EventRecordNotice();
                    Client client = clientService.queryById(clientId);
                    eventRecordNotice.setEventId(eventTerm.getEventId());
                    eventRecordNotice.setEventStartDate(eventTerm.getEventStartDate());
                    eventRecordNotice.setEventEndDate(eventTerm.getEventEndDate());
                    eventRecordNotice.setMemberGroupId(eventTerm.getMemberGroupId());
                    eventRecordNotice.setClientId(clientId);
                    eventRecordNotice.setParticipantName(client.getName());
                    eventRecordNotice.setParticipantIdcard(client.getIdCardNum());
                    eventRecordNotice.setParticipantTel(client.getTel());
                    eventRecordNotice.setNoticeStatus(1);
                    eventRecordNotice.setEventStatus(1);
                    eventRecordNotice.setEventTermId(eventTerm.getId());
                    commonDao.insert(eventRecordNotice);
                }
            }
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.NewEventRecordNoticeFail, e);
        }
    }

    @Override
    public List<EventRecordNotice> listNameTop20ByEventTermId(int eventTermId, String participantName) throws SSException{
        List<EventRecordNotice> eventRecordNotices = Collections.emptyList();
        try{
            if(Assert.isNotNull(eventTermId)){
                eventRecordNotices = eventRecordNoticeMapper.listNameTop20ByEventTermId(eventTermId,participantName);
            }
            return eventRecordNotices;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListNameTop20ByEventTermIdFail, e);
        }
    }

    @Override
    public List<EventRecordNotice> listIdcardTop20ByEventTermId(int eventTermId, String participantIdcard) throws SSException{
        List<EventRecordNotice> eventRecordNotices = Collections.emptyList();
        try{
            if(Assert.isNotNull(eventTermId)){
                eventRecordNotices = eventRecordNoticeMapper.listIdcardTop20ByEventTermId(eventTermId,participantIdcard);
            }
            return eventRecordNotices;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListIdcardTop20ByEventTermIdFail, e);
        }
    }

    /**************************************私有方法******************************************/

    /**
     * 设置枚举值value
     *
     * @param eventRecordNotice
     * @throws SSException
     */
    private void setExtraValue(EventRecordNotice eventRecordNotice) throws SSException{
        if(Assert.isNotNull(eventRecordNotice)){
            try{
                if(Assert.isNotNull(eventRecordNotice.getNoticeStatus())){
                    eventRecordNotice.setNoticeStatusValue(NoticeStatusEnums.valueOf(eventRecordNotice.getNoticeStatus()).getStatus());
                }
                if(Assert.isNotNull(eventRecordNotice.getEventParticipantStatus())){
                    eventRecordNotice.setEventParticipantStatusValue(EventParticipantStatusEnums.valueOf(eventRecordNotice.getEventParticipantStatus()).getStatus());
                }
                if(Assert.isNull(eventRecordNotice.getComment())){
                    eventRecordNotice.setComment("无");
                }
                if(Assert.isNull(eventRecordNotice.getSysName())){
                    eventRecordNotice.setSysName("无");
                }
            }catch (Exception e){
                LogClerk.errLog.error(e);
                throw SSException.get(CrmException.SetEventRecordNoticeExtraDataFail, e);
            }

        }
    }

}
