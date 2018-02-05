package com.pandawork.crm.service.party.security.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.entity.party.security.SecurityGroup;
import com.pandawork.crm.common.entity.party.security.SecurityUserGroup;
import com.pandawork.crm.common.exception.PartyException;
import com.pandawork.crm.common.utils.CommonUtil;
import com.pandawork.crm.mapper.party.security.SecurityUserGroupMapper;
import com.pandawork.crm.service.other.EncodeService;
import com.pandawork.crm.service.party.security.SecurityGroupService;
import com.pandawork.crm.service.party.security.SecurityUserGroupService;
import com.pandawork.crm.service.party.security.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 用户权限组service实现
 *
 * @author: zhangteng
 * @time: 15/10/10 下午9:14
 */
@Service("securityUserGroupService")
public class SecurityUserGroupServiceImpl implements SecurityUserGroupService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private SecurityUserGroupMapper securityUserGroupMapper;

    @Autowired
    private SecurityGroupService securityGroupService;

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private EncodeService encodeService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public SecurityUserGroup newSecurityUserGroup(SecurityUserGroup securityUserGroup) throws SSException {
        try {
            if (Assert.isNull(securityUserGroup)) {
                return null;
            }
            // 非空检查
            CommonUtil.checkId(securityUserGroup.getUserId(), PartyException.UserIdNotNull);
            CommonUtil.checkId(securityUserGroup.getGroupId(), PartyException.SecurityGroupIdNotNull);

            return commonDao.insert(securityUserGroup);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.UserGroupInsertFailed, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)){
            return ;
        }
        try{
            commonDao.deleteById(SecurityUserGroup.class, id);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.UserGroupDeleteFailed, e);
        }
    }

    @Override
    public List<SecurityUserGroup> listByUserId(int userId) throws SSException {
        List<SecurityUserGroup> list = Collections.emptyList();
        if (Assert.lessOrEqualZero(userId)){
            return list;
        }

        try{
            list = securityUserGroupMapper.listByUserId(userId);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.UserGroupQueryFailed, e);
        }
        return list;
    }

    @Override
    public List<SecurityGroup> listNotSelectedGroup(int userId) throws SSException {
        List<SecurityGroup> list = Collections.emptyList();
        list.isEmpty();
        if (Assert.lessOrEqualZero(userId)) {
            return list;
        }

        // 已经选择权限的Idlist
        List<Integer> selectedIdList = Collections.emptyList();
        try {
            selectedIdList = securityUserGroupMapper.listGroupIdByUserId(userId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }

        // 获取未选择的权限组列表
        try {
            list = securityGroupService.listByIdList(selectedIdList, false);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }

        return list;
    }

    @Override
    public List<SecurityGroup> listSecurityGroupByLoginName(String loginName) throws SSException {
        List<SecurityGroup> list = Collections.emptyList();
        if (Assert.isNull(loginName)) {
            return list;
        }

        try {
            list = securityUserGroupMapper.listSecurityGroupByLoginName(loginName);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }

        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delByGroupId(int groupId) throws SSException {
        if (Assert.lessOrEqualZero(groupId)) {
            return ;
        }
        try {
            securityUserGroupMapper.delByGroupId(groupId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delByUserId(int userId) throws SSException {
        if (Assert.lessOrEqualZero(userId)) {
            return ;
        }
        try {
            securityUserGroupMapper.delByUserId(userId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    @Override
    public List<SecurityUserGroup>  listByGroupId(int groupId) throws SSException{
        List<SecurityUserGroup> securityUserGroups = Collections.emptyList();
        try{
           if(Assert.isNotNull(groupId)){
               securityUserGroups = securityUserGroupMapper.listByGroupId(groupId);
           }
       }catch (Exception e){
           throw SSException.get(PartyException.SystemException, e);
       }
       return securityUserGroups;
    }

    @Override
    public SecurityUserGroup queryByUserId(int userId) throws SSException{
        try {
            if (Assert.isNull(userId)){
                throw SSException.get(PartyException.UserIdNotNull);
            }
            return securityUserGroupMapper.queryByUserId(userId);
        } catch (Exception e){
            throw SSException.get(PartyException.SystemException, e);
        }
    }
}
