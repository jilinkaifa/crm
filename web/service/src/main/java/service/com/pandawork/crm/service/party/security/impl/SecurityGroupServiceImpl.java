package com.pandawork.crm.service.party.security.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.entity.party.security.SecurityGroup;
import com.pandawork.crm.common.exception.PartyException;
import com.pandawork.crm.common.utils.CommonUtil;
import com.pandawork.crm.mapper.party.security.SecurityGroupMapper;
import com.pandawork.crm.service.party.security.SecurityGroupPermissionService;
import com.pandawork.crm.service.party.security.SecurityGroupService;
import com.pandawork.crm.service.party.security.SecurityUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 安全组service
 *
 * @author: zhangteng
 * @time: 15/10/12 上午11:14
 */
@Service("securityGroupService")
public class SecurityGroupServiceImpl implements SecurityGroupService {
    @Autowired
    private CommonDao commonDao;

    @Autowired
    private SecurityGroupMapper securityGroupMapper;

    @Autowired
    private SecurityGroupPermissionService securityGroupPermissionService;

    @Autowired
    private SecurityUserGroupService securityUserGroupService;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public SecurityGroup newSecurityGroup(SecurityGroup securityGroup) throws SSException {
        try {
            if (!checkBeforeSave(securityGroup)) {
                return null;
            }
            return commonDao.insert(securityGroup);
        } catch (Exception e) {
            LogClerk.errLog.equals(e);
            throw SSException.get(PartyException.SecurityGroupInsertFailed, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)){
            return;
        }
        try {
            commonDao.deleteById(SecurityGroup.class, id);

            // 删除权限组的权限关联信息
            securityGroupPermissionService.delById(id);

            // 删除用户-权限组信息
            securityUserGroupService.delByGroupId(id);
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SecurityGroupDeleteFailed, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void update(SecurityGroup securityGroup) throws SSException {
        try {
            // 非空检查
            if (Assert.isNull(securityGroup)) {
                return ;
            }
            CommonUtil.checkId(securityGroup.getId(), PartyException.SecurityGroupIdNotNull);
            Assert.isNotNull(securityGroup.getName(), PartyException.SecurityGroupNameNotNull);

            SecurityGroup oldGroup = this.queryById(securityGroup.getId());
            Assert.isNotNull(oldGroup, PartyException.SecurityGroupNotExist);
            if (!oldGroup.getName().equals(securityGroup.getName())) {
                // 判断名称是否已经存在
                if (this.queryGroupNameIsExist(securityGroup.getName())) {
                    throw SSException.get(PartyException.SecurityGroupNameIsExist);
                }
            }

            commonDao.update(securityGroup);
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SecurityGroupUpdateFailed, e);
        }
    }

    @Override
    public List<SecurityGroup> listByPage(int curPage, int pageSize) throws SSException {
        curPage = curPage <= 0 ? 0 : curPage - 1;
        int offset = curPage * pageSize;
        if (Assert.lessZero(offset)) {
            return Collections.emptyList();
        }
        List<SecurityGroup> list = Collections.emptyList();
        try {
            list = securityGroupMapper.listByPage(offset, pageSize);
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SecurityGroupQueryFailed, e);
        }

        return list;
    }

    @Override
    public List<SecurityGroup> listAll() throws SSException {
        try {
            return securityGroupMapper.listAll();
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SecurityGroupQueryFailed, e);
        }
    }


    @Override
    public List<Integer> listAllGroupId() throws SSException {
        try {
            return securityGroupMapper.listAllGroupId();
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SecurityGroupQueryFailed, e);
        }
    }

    @Override
    public int count() throws SSException {
        Integer num = 0;
        try {
            num = securityGroupMapper.count();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SecurityGroupQueryFailed, e);
        }
        return num == null ? 0 : num;
    }

    @Override
    public List<SecurityGroup> listByIdList(List<Integer> groupIdList, boolean isInList) throws SSException {
        List<SecurityGroup> list = Collections.emptyList();
        if (Assert.isNull(groupIdList)
                || Assert.lessZero(groupIdList.size())) {
            return list;
        }
        try{
            list = securityGroupMapper.listByIdList(groupIdList, isInList);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SecurityGroupQueryFailed, e);
        }

        return list;
    }

    @Override
    public SecurityGroup queryById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)) {
            return null;
        }
        try {
            return commonDao.queryById(SecurityGroup.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SecurityGroupQueryFailed, e);
        }
    }

    // －－－－－－－私有方法

    /**
     * 判断组名称是否已经存在
     * @param groupName
     * @return
     * @throws SSException
     */
    private boolean queryGroupNameIsExist(String groupName) throws SSException{
        int num = 0;
        try{
            num = securityGroupMapper.countByGroupName(groupName);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return num > 0;
    }

    /**
     * 对插入和更新的安全组进行非空检查
     *
     * @param securityGroup
     * @return
     * @throws SSException
     */
    public boolean checkBeforeSave(SecurityGroup securityGroup) throws SSException {
        // 非空检查
        if (Assert.isNull(securityGroup)) {
            return false;
        }
        Assert.isNotNull(securityGroup.getName(), PartyException.SecurityGroupNameNotNull);

        // 判断名称是否已经存在
        if (this.queryGroupNameIsExist(securityGroup.getName())){
            throw SSException.get(PartyException.SecurityGroupNameIsExist);
        }

        return true;
    }
}
