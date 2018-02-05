package com.pandawork.crm.service.event.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.entity.event.EventRecordNotice;
import com.pandawork.crm.common.entity.event.EventTerm;
import com.pandawork.crm.common.enums.event.EventTermStatusEnums;
import com.pandawork.crm.common.enums.event.NoticeStatusEnums;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.common.utils.DateUtils;
import com.pandawork.crm.mapper.event.EventTermMapper;
import com.pandawork.crm.service.event.EventRecordNoticeService;
import com.pandawork.crm.service.event.EventTermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * EventTermServiceImpl
 * Author： wychen
 * Date: 2017/8/10
 * Time: 9:48
 */
@Service("eventTermService")
public class EventTermServiceImpl implements EventTermService{

    @Autowired
    private EventTermMapper eventTermMapper;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private EventRecordNoticeService eventRecordNoticeService;

    @Override
    public List<EventTerm> listAllByStatus(int status) throws SSException{
        try{
            return eventTermMapper.listAllByStatus(status);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListAllProcessingFail, e);
        }
    }

    @Override
    public List<EventTerm> searchTOP20ByName(String name) throws SSException{
        try {
            return eventTermMapper.searchTOP20ByName(name);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.SearchEventTermByNameFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void autoUpdateEventTermStatus() throws SSException{
        try{
            //获取通知期的活动
            int status = 0;
            List<EventTerm> eventTermList = eventTermMapper.listAllByNotificationExecutionActivity();
            if(Assert.isNotNull(eventTermList)){
                String date = DateUtils.formatDateSimple(new Date());
                for (EventTerm eventTerm : eventTermList){
                    if(eventTerm.getStatus() == 1){ //通知期
                        if (Assert.isNotNull(eventTerm.getStartDate())){
                            String startDate = DateUtils.formatDateSimple(eventTerm.getStartDate());
                            if (date.compareTo(startDate) == 0){
                                eventTerm.setStatus(EventTermStatusEnums.Doing.getId());
                                commonDao.update(eventTerm);
                                status = 3;
                            }
                        }
                    }else if(eventTerm.getStatus() == 2){ //执行期
                        if (Assert.isNotNull(eventTerm.getEndDate())){
                            //处理结束日期加一天
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(eventTerm.getEndDate());
                            cal.add(Calendar.DAY_OF_MONTH, 1);
                            String endDate = DateUtils.formatDateSimple(cal.getTime());
                            if(date.compareTo(endDate) == 0){
                                //归档活动
                                eventTerm.setStatus(EventTermStatusEnums.Filed.getId());
                                commonDao.update(eventTerm);
                                status = 4;
                            }
                        }
                    }
                    //处理活动记录通知表
                    if (status == 3 || status == 4){
                        if (Assert.isNotNull(eventTerm.getId())){
                            //获取活动记录通知表中的当前期活动的所有通知记录
                            List<EventRecordNotice> eventRecordNoticeList = eventRecordNoticeService.listByEventTermId(eventTerm.getId(), null, null, -1, -1);
                            if (Assert.isNotNull(eventRecordNoticeList)){
                                for (EventRecordNotice eventRecordNotice : eventRecordNoticeList){
                                    if (Assert.isNotNull(eventRecordNotice.getEventStatus())){
                                        //修改活动状态为---已通知
                                        eventRecordNotice.setEventStatus(status);
                                        commonDao.update(eventRecordNotice);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.AutoUpdateEventTermStatusFail, e);
        }
    }

    /**
     * 获取已经到通知期的活动
     *
     * @return
     * @throws SSException
     */
    public List<EventTerm> getTodayNoticeActivitiesList() throws SSException{
        try {
            return eventTermMapper.getTodayNoticeActivitiesList();
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.SearchEventTermByNameFail, e);
        }
    }
}
