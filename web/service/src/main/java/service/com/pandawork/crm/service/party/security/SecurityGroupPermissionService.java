package com.pandawork.crm.service.party.security;


import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.party.security.SecurityGroupPermission;
import com.pandawork.crm.common.entity.party.security.SecurityPermission;

import java.util.List;

/**
 * 安全组-权限service
 *
 * @author: zhangteng
 * @time: 15/10/12 上午11:14
 */
public interface SecurityGroupPermissionService {

    /**
     * 新增
     *
     * @param securityGroupPermission
     * @throws SSException
     */
    public SecurityGroupPermission newSecurityGroupPermission(SecurityGroupPermission securityGroupPermission) throws SSException;

    /**
     * 根据Id删除
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 根据groupId删除
     *
     * @param groupId
     * @throws SSException
     */
    public void delByGroupId(int groupId) throws SSException;

    /**
     * 根据安全组的id分页获取权限
     *
     * @param groupId
     * @return
     * @throws SSException
     */
    public List<SecurityGroupPermission> listByGroupId(int groupId) throws SSException;

    /**
     * 根据安全组的id分页获取权限
     *
     * @param groupId
     * @param pageNo
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<SecurityGroupPermission> listByGroupIdAndPage(int groupId, int pageNo, int pageSize) throws SSException;

    /**
     * 根据安全组的id获取权限数量
     *
     * @param groupId
     * @return
     * @throws SSException
     */
    public int countByGroupId(int groupId) throws SSException;

    /**
     * 获取当前组未选择的权限
     *
     * @param groupId
     * @return
     * @throws SSException
     */
    public List<SecurityPermission> listNotSelectedPermission(int groupId) throws SSException;

}
