package com.pandawork.crm.service.event.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.entity.event.CheckItem;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.mapper.event.CheckItemMapper;
import com.pandawork.crm.service.event.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


/**
 * CheckItemServiceImpl
 * Author： wychen
 * Date: 2017/7/20
 * Time: 21:45
 */
@Service("checkItemService")
public class CheckItemServiceImpl implements CheckItemService{

    @Autowired
    private CheckItemMapper checkItemMapper;

    @Autowired
    private CommonDao commonDao;

    @Override
    public List<CheckItem> listAll() throws SSException {
        List<CheckItem> checkItems = Collections.emptyList();
        try{
            checkItems = checkItemMapper.listAll();
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListAllCheckItemFail, e);
        }
        return checkItems;
    }

    @Override
    public List<CheckItem> listByEventId(int eventId) throws SSException {
        List<CheckItem> checkItems = Collections.emptyList();
        try{
            checkItems = checkItemMapper.listByEventId(eventId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListByEventIdCheckItemFail, e);
        }
        return checkItems;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public CheckItem newCheckItem(CheckItem checkItem) throws SSException{
        try{
            if (Assert.isNotNull(checkItem)){
                commonDao.insert(checkItem);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryByCheckItemIdFail, e);
        }
        return checkItem;
    }

    @Override
    public CheckItem queryById(int id) throws SSException{
        CheckItem checkItem = new CheckItem();
        try{
            checkItem = commonDao.queryById(CheckItem.class,id);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.NewCheckItemFail, e);
        }
        return checkItem;
    }

    @Override
    public Boolean checkExistByName(String name) throws SSException{
        try {
            if(Assert.isNotNull(name)){
                int result = checkItemMapper.checkExistByName(name);
                if(result == 1){
                    return true;
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CheckExistByCheckItemNameFail, e);
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void deleteByEventId(int eventId) throws SSException{
        try {
            if(Assert.isNotNull(eventId)){
                checkItemMapper.deleteByEventId(eventId);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.DeleteCheckItemByEvenIdFail, e);
        }
    }
}
