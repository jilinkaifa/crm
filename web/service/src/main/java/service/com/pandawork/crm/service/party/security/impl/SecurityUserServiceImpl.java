package com.pandawork.crm.service.party.security.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.entity.party.security.SecurityUser;
import com.pandawork.crm.common.enums.party.AccountTypeEnums;
import com.pandawork.crm.common.enums.party.EnableEnums;
import com.pandawork.crm.common.exception.PartyException;
import com.pandawork.crm.common.utils.CommonUtil;
import com.pandawork.crm.mapper.party.security.SecurityUserMapper;
import com.pandawork.crm.service.other.EncodeService;
import com.pandawork.crm.service.party.group.PartyService;
import com.pandawork.crm.service.party.security.SecurityUserGroupService;
import com.pandawork.crm.service.party.security.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 登陆用户Service
 *
 * @author: zhangteng
 * @time: 15/10/16 上午9:18
 */
@Service("securityUserService")
public class SecurityUserServiceImpl implements SecurityUserService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private SecurityUserMapper securityUserMapper;

    @Autowired
    private PartyService partyService;

    // 用户权限组
    @Autowired
    private SecurityUserGroupService securityUserGroupService;

    @Autowired
    private EncodeService encodeService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public SecurityUser newSecurityUser(SecurityUser securityUser) throws SSException {
        if (!checkBeforeSave(securityUser)) {
            return null;
        }

        try {
            securityUser = commonDao.insert(securityUser);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return securityUser;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStatusById(int id, EnableEnums status) throws SSException {
        CommonUtil.checkId(id, PartyException.UserIdNotNull);
        Assert.isNotNull(status, PartyException.UserStatusIllegal);

        try {
            securityUserMapper.updateStatusById(id, status.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    @Override
    public SecurityUser queryByLoginName(String loginName) throws SSException {
        SecurityUser securityUser = null;
        if (Assert.isNull(loginName)) {
            return securityUser;
        }
        try {
            securityUser = securityUserMapper.queryByLoginName(loginName);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }

        return securityUser;
    }

    @Override
    public boolean checkLoginNameIsExist(String loginName) throws SSException {
        Integer count = 0;
        try {
            count = securityUserMapper.countByLoginName(loginName);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }

        return count > 0;
    }

    @Override
    public SecurityUser queryById(int id) throws SSException {
        SecurityUser securityUser = null;
        if (Assert.lessOrEqualZero(id)) {
            return securityUser;
        }

        try {
            securityUser = commonDao.queryById(SecurityUser.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }

        return securityUser;
    }

    @Override
    public List<SecurityUser> listByPartyId(int partyId) throws SSException {
        List<SecurityUser> list = Collections.emptyList();
        if (Assert.lessOrEqualZero(partyId)) {
            return list;
        }

        try {
            list = securityUserMapper.listByPartyId(partyId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }

        return list;
    }

    @Override
    public SecurityUser queryByPartyIdAndAccountType(int partyId, AccountTypeEnums accountType) throws SSException {
        SecurityUser securityUser = null;
        if (Assert.lessOrEqualZero(partyId)
                || Assert.isNull(accountType)) {
            return securityUser;
        }

        try {
            securityUser = securityUserMapper.queryByPartyIdAndAccountType(partyId, accountType.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }

        return securityUser;
    }

    @Override
    public SecurityUser queryByPartyId(int partyId) throws SSException {
        SecurityUser securityUser = null;
        if (Assert.lessOrEqualZero(partyId)) {
            return securityUser;
        }

        try {
            securityUser = securityUserMapper.queryByPartyId(partyId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }

        return securityUser;
    }

    @Override
    public void updateSecurityUser(SecurityUser securityUser) throws SSException {
        try {
            if (this.checkBeforeSave(securityUser)) {
                securityUserMapper.updateSecurityUser(securityUser);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    private boolean checkBeforeSave(SecurityUser securityUser) throws SSException {
        if (Assert.isNull(securityUser)) {
            return false;
        }

        Assert.isNotNull(securityUser.getPartyId(), PartyException.PartyIdNotNull);
        Assert.isNotNull(securityUser.getLoginName(), PartyException.LoginNameNotNull);
        Assert.isNotNull(securityUser.getPassword(), PartyException.PasswordNotNull);

        return true;
    }
}

