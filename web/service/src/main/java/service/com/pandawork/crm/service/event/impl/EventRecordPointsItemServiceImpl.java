package com.pandawork.crm.service.event.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.entity.event.EventRecordPointsItem;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.service.event.EventRecordPointsItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * ArchivedEventImpl
 * Author： wychen
 * Date: 2017/7/21
 * Time: 10:37
 */
@Service("eventRecordPointsService")
public class EventRecordPointsItemServiceImpl implements EventRecordPointsItemService{

    @Autowired
    private CommonDao commonDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void newPointsItem(EventRecordPointsItem eventRecordPointsItem) throws SSException{
        try{
           if(Assert.isNotNull(eventRecordPointsItem)){
               commonDao.insert(eventRecordPointsItem);
           }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.BatchNewPointsFail, e);
        }
    }

}
