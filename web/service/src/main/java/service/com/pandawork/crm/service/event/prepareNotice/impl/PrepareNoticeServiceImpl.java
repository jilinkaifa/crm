package com.pandawork.crm.service.event.prepareNotice.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.dto.event.PrepareNoticeSearchDto;
import com.pandawork.crm.common.entity.event.EventTerm;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.mapper.event.PrepareNoticeMapper;
import com.pandawork.crm.mapper.event.ProcessingEventMapper;
import com.pandawork.crm.service.event.prepareNotice.PrepareNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * PrepareNoticeService
 *
 * @author Daydreamer
 * @date 2017/7/31 15:38
 */
@Service("prepareNoticeService")
public class PrepareNoticeServiceImpl implements PrepareNoticeService{

    @Autowired
    private PrepareNoticeMapper prepareNoticeMapper;

    @Autowired
    private ProcessingEventMapper processingEventMapper;

    @Autowired
    private CommonDao commonDao;

    @Override
    public List<EventTerm> listAll() throws SSException{
        List<EventTerm> eventTerms = Collections.emptyList();
        try {
            eventTerms = prepareNoticeMapper.listAll();
            return eventTerms;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListPrepareNoticeFail, e);
        }
    }

    @Override
    public List<EventTerm> listBySearchDto(PrepareNoticeSearchDto prepareNoticeSearchDto) throws SSException{
        List<EventTerm> eventTerms = Collections.emptyList();
        try {
            eventTerms = prepareNoticeMapper.listEventBySearchDto(prepareNoticeSearchDto);
            for (EventTerm eventTerm : eventTerms){
                int toBeNoticedPerson = processingEventMapper.countToBeNoticedPerson(eventTerm.getEventId());
                eventTerm.setToBeNoticedPerson(toBeNoticedPerson);
            }
            return eventTerms;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListPrepareNoticeFail, e);
        }
    }

    @Override
    public Integer countPrepareNotice(PrepareNoticeSearchDto prepareNoticeSearchDto) throws SSException{
        int count;
        try{
            count = prepareNoticeMapper.countEventBySearchDto(prepareNoticeSearchDto);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountPrepareNoticeFail, e);
        }
        return count;
    }

    @Override
    public Integer countAll() throws SSException{
        int count;
        try {
            count = prepareNoticeMapper.countAll();
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountPrepareNoticeFail, e);
        }
        return count;
    }

    @Override
    public EventTerm queryById(int id) throws SSException{
        EventTerm eventTerm = new EventTerm();
        try{
            if(Assert.isNotNull(id)){
                eventTerm = processingEventMapper.queryById(id);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEventTermById, e);
        }
        return eventTerm;
    }

}
