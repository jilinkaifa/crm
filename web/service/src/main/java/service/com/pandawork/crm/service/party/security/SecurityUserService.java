package com.pandawork.crm.service.party.security;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.party.security.SecurityUser;
import com.pandawork.crm.common.enums.party.AccountTypeEnums;
import com.pandawork.crm.common.enums.party.EnableEnums;

import java.util.List;

/**
 * 登陆用户Service
 *
 * @author: zhangteng
 * @time: 15/10/16 上午9:18
 */
public interface SecurityUserService {

    /**
     * 新增登录用户
     *
     * @param securityUser
     * @return
     * @throws SSException
     */
    public SecurityUser newSecurityUser(SecurityUser securityUser) throws SSException;

    /**
     * 根据uId禁用启用
     *
     * @param id
     * @param status
     * @throws SSException
     */
    public void updateStatusById(int id,
                                 EnableEnums status) throws SSException;

    /**
     * 按照登录名查询用户信息
     *
     * @param loginName
     * @return
     * @throws SSException
     */
    public SecurityUser queryByLoginName(String loginName) throws SSException;

    /**
     * 查询用户名称是否存在
     *
     * @param loginName
     * @return
     * @throws SSException
     */
    public boolean checkLoginNameIsExist(String loginName) throws SSException;

    /**
     * 根据userId查询用户
     *
     * @param id
     * @return
     * @throws SSException
     */
    public SecurityUser queryById(int id) throws SSException;

    /**
     * 根据partyId查询用户
     *
     * @param partyId
     * @return
     * @throws SSException
     */
    public List<SecurityUser> listByPartyId(int partyId) throws SSException;

    /**
     * 根据partyId和账户类型查询用户
     *
     * @param partyId
     * @param accountType
     * @return
     * @throws SSException
     */
    public SecurityUser queryByPartyIdAndAccountType(int partyId,
                                                     AccountTypeEnums accountType) throws SSException;

    /**
     * 根据partyId查询security
     *
     * @param partyId
     * @return
     * @throws SSException
     */
    public SecurityUser queryByPartyId(int partyId) throws SSException;

    /**
     * 更新securityuser
     *
     * @param securityUser
     * @throws SSException
     */
    public void updateSecurityUser(SecurityUser securityUser) throws SSException;

 }

