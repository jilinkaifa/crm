package com.pandawork.crm.service.profile.portrayal.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.entity.profile.portrayal.Profile;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.mapper.profile.portrayal.PortrayalMapper;
import com.pandawork.crm.service.profile.portrayal.PortrayalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Flying
 * @date 2017/7/19 11:01
 */
@Service("portrayalService")
public class PortrayalServiceImpl implements PortrayalService {

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private PortrayalMapper portrayalMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public Profile newProfile(Profile profile) throws SSException {
        try {
            if(!checkBeforeSave(profile)){
                return null;
            }
            return commonDao.insert(profile);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.InsertProfileFail,e);
        }
    }

    @Override
    public boolean queryProfileIsExist(int clientId, int typeId, int itemId) throws SSException{
        int num = 0;
        try {
            num = portrayalMapper.countProfile(clientId, typeId, itemId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ProfileIsExist,e);
        }
        return num > 0 ;
    }

    @Override
    public boolean checkBeforeSave(Profile profile) throws SSException {
        //非空检查
        if (Assert.isNull(profile)){
            return false;
        }
        //检查患者id是否为空
        Assert.isNotNull(profile.getClientId(), CrmException.ClientIdNotNull);
        //检查标签项id是否为空
        Assert.isNotNull(profile.getLabelItemId(), CrmException.LabelItemIdNotNull);
        //检查标签类型id是否为空
        Assert.isNotNull(profile.getLabelTypeId(), CrmException.LabelTypeIdNotNull);

        if(queryProfileIsExist(profile.getClientId(), profile.getLabelTypeId(), profile.getLabelItemId())){
            throw SSException.get(CrmException.LabelAllIdNotNull);
        }
        return true;
    }

    @Override
    public List<Profile> queryByClientId(int clientId) throws SSException{
        try{
            if (Assert.isNull(clientId)){
                throw SSException.get(CrmException.ClientIdNotNull);
            }
            return portrayalMapper.queryByClientId(clientId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryProfileByClientIdFail, e);
        }
    }

    @Override
    public int countByClientIdAndTypeId(int clientId, int typeId) throws SSException{
        try{
            if(Assert.isNull(clientId)){
                throw SSException.get(CrmException.ClientIdNotNull);
            }
            if(Assert.isNull(typeId)){
                throw SSException.get(CrmException.LabelTypeIdNotNull);
            }
            return portrayalMapper.countByClientIdAndTypeId(clientId, typeId);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryProfileByClientIdFail, e);
        }
    }

    @Override
    public List<String> listProfileByClientIdAndTypeId(int clientId, int typeId )throws SSException{
        try{
            if(Assert.isNull(clientId)){
                throw SSException.get(CrmException.ClientIdNotNull);
            }
            return portrayalMapper.listProfileByClientIdAndTypeId(clientId, typeId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryProfileByClientIdFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void batchDelProfile(List<Integer> clientIdList, int typeId) throws SSException{
        try{
            if(Assert.isNull(clientIdList)){
                throw SSException.get(CrmException.ClientIdNotNull);
            }
            if(Assert.isNull(typeId)){
                throw SSException.get(CrmException.LabelTypeIdNotNull);
            }
            for (Integer idList : clientIdList){
                portrayalMapper.delByByClientIdAndTypeId(idList, typeId);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryProfileByClientIdFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void batchNewProfile(List<Integer> clientIdList, int typeId, int itemId, int partyId) throws SSException{
        try {
            if(Assert.isNull(itemId)){
                throw SSException.get(CrmException.LabelItemIdNotNull);
            }
            if(Assert.isNull(clientIdList)){
                throw SSException.get(CrmException.ClientIdNotNull);
            }
            for (Integer idList : clientIdList){
                Profile profile = new Profile();
                profile.setClientId(idList);
                profile.setLabelItemId(itemId);
                profile.setLabelTypeId(typeId);
                profile.setCreatedPartyId(partyId);
                commonDao.insert(profile);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.InsertProfileFail, e);
        }
    }

    @Override
    public List<Profile> listByItemId(int itemId) throws SSException{
        try{
            if (Assert.isNull(itemId)){
                throw SSException.get(CrmException.LabelItemIdError);
            }
            return portrayalMapper.listByItemId(itemId);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.InsertProfileFail, e);
        }
    }

}
