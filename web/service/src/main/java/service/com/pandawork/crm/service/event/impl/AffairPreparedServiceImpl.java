package com.pandawork.crm.service.event.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.entity.event.Event;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.mapper.event.AffairPreparedMapper;
import com.pandawork.crm.service.event.AffairPreparedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 待办事务Service实现
 * AffairToDoServiceImpl
 * Author： linjie
 * Date: 2017/7/29
 * Time: 9:19
 */
@Service("affairPreparedService")
public class AffairPreparedServiceImpl implements AffairPreparedService{

    @Autowired
    private AffairPreparedMapper affairPreparedMapper;

    /**
     * 分页查询待审批事务列表
     * @param approvalId
     * @return
     * @throws SSException
     */
    @Override
    public List<Event> listAllToApprovalByPage(int approvalId,int offset,int pageSize ) throws SSException{
        List<Event> events = Collections.emptyList();
        try{
            events = affairPreparedMapper.listAllToApprovalByPage(approvalId,offset,pageSize);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.listAllToApprovalFail,e);
        }
        return events;
    }

    /**
     * 分页查询被驳回事务列表
     * @param createdPartyId
     * @return
     * @throws SSException
     */
    @Override
    public List<Event> listAllRejectedByPage(int createdPartyId,int offset,int pageSize) throws SSException{
        List<Event> events = Collections.emptyList();
        try{
            events = affairPreparedMapper.listAllRejectedByPage(createdPartyId,offset,pageSize);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.listAllRejectedFail,e);
        }
        return events;
    }

    /**
     * 查询被驳回事务条数
     * @param createdPartyId
     * @return
     * @throws SSException
     */
    @Override
    public int countRejected(int createdPartyId)throws SSException{
        try{
            int count = affairPreparedMapper.countRejected(createdPartyId);
            return count;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.countRejectedFail,e);
        }
    }

    /**
     * 查询待审批事务条数
     * @param approvalId
     * @return
     * @throws SSException
     */
    @Override
    public int countToApproval(int approvalId)throws SSException{
        try{
            int count = affairPreparedMapper.countToApproval(approvalId);
            return count;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.countToApprovalFail,e);
        }
    }

    /**
     * 将时间转换为例如2017.3.21格式
     * @param events
     * @throws SSException
     */
    @Override
    public void dateConvert(List<Event> events) throws SSException{
        try {
            for (Event event : events) {
                Date date = event.getCreatedTime();
                String time = new SimpleDateFormat("yyyy-MM-dd").format(date);
                String[] dates = time.split("-");
                int month = Integer.parseInt(dates[1]);
                int day = Integer.parseInt(dates[2]);
                String t = dates[0] + "." + month + "." + day;
                event.setDate(t);
            }
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.dateConvertFail,e);
        }
    }

    @Override
    public List<Event> listEventByPartyIdListAndStatus(List<Integer> partyIdList, int status, int offset, int pageSize) throws SSException{
        try {
            List<Event> eventList = Collections.emptyList();
            if (Assert.isNotNull(partyIdList)){
                eventList = affairPreparedMapper.listEventByPartyIdListAndStatus(partyIdList, status, offset, pageSize);
            }
            return eventList;
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEventByStatusFail,e);
        }
    }

    @Override
    public int countEventByPartyIdListAndStatus(List<Integer> partyIdList, int status) throws SSException{
        int count = 0;
        try{
            if (Assert.isNotNull(partyIdList)){
                count = affairPreparedMapper.countEventByPartyIdListAndStatus(partyIdList, status);
            }
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEventByStatusFail,e);
        }
        return count;
    }
}
