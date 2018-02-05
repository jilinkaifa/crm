package com.pandawork.crm.service.party.security;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.party.security.SecurityGroup;
import com.pandawork.crm.common.entity.party.security.SecurityUserGroup;

import java.util.List;

/**
 * 用户安全组service
 *
 * @author: zhangteng
 * @time: 15/10/10 下午9:14
 */
public interface SecurityUserGroupService {

    /**
     * 新增
     *
     * @param securityUserGroup
     * @throws
     */
    public SecurityUserGroup newSecurityUserGroup(SecurityUserGroup securityUserGroup) throws SSException, SSException;

    /**
     * 根据Id删除
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;


    /**
     * 获取用户拥有的权限组
     *
     * @param userId
     * @return
     * @throws SSException
     */
    public List<SecurityUserGroup> listByUserId(int userId) throws SSException;

    /**
     * 获取用户未选择的权限组
     *
     * @param userId
     * @return
     * @throws SSException
     */
    public List<SecurityGroup> listNotSelectedGroup(int userId) throws SSException;

    /**
     * 根据loginName获取用户安全组
     *
     * @param loginName
     * @return
     * @throws SSException
     */
    public List<SecurityGroup> listSecurityGroupByLoginName(String loginName) throws SSException;

    /**
     * 根据权限组id，删除用户-权限组关联
     *
     * @param groupId
     * @throws SSException
     */
    public void delByGroupId(int groupId) throws SSException;

    /**
     * 根据UserID删除用户-权限组关联
     *
     * @param userId
     * @throws SSException
     */
    public void delByUserId(int userId) throws SSException;

    /**
     * 根据安全组id获取所有用户安全组list
     *
     * @param groupId
     * @throws SSException
     */
    public List<SecurityUserGroup> listByGroupId(int groupId) throws SSException;

    /**
     * 根据用户的id查询
     *
     * @param userId
     * @return
     * @throws SSException
     */
    public SecurityUserGroup queryByUserId(int userId) throws SSException;
}
