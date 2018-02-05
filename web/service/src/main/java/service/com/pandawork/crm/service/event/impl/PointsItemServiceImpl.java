package com.pandawork.crm.service.event.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.entity.client.points.PointsItem;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.mapper.event.PointsItemMapper;
import com.pandawork.crm.service.event.PointsItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * PointsItemServiceImpl
 *
 * @author Flying
 * @date 2017/8/3 9:30
 */
@Service("pointsItemService")
public class PointsItemServiceImpl implements PointsItemService {

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private PointsItemMapper pointsItemMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public PointsItem newPointsItem(PointsItem pointsItem) throws SSException {
        try{
            if (Assert.isNull(pointsItem)){
                throw SSException.get(CrmException.PointsItemIsNotNull);
            }
            return commonDao.insert(pointsItem);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.InsertNewEventFail, e);
        }
    }

    @Override
    public List<PointsItem> listByEventId(int eventId) throws SSException{
        try{
            if (Assert.isNull(eventId)){
                throw SSException.get(CrmException.EventIdNotNull);
            }
            return pointsItemMapper.listByEventId(eventId);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListPointsItemByEventIdFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delByEventId(int eventId) throws SSException{
        try{
            if (Assert.isNull(eventId)){
                throw SSException.get(CrmException.EventIdNotNull);
            }
            pointsItemMapper.delByEventId(eventId);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.DeletePointsItemByEventIdFail, e);
        }
    }
}
