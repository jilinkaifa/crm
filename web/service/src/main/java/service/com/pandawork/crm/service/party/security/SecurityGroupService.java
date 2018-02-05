package com.pandawork.crm.service.party.security;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.party.security.SecurityGroup;

import java.util.List;

/**
 * 安全组service
 *
 * @author: zhangteng
 * @time: 15/10/12 上午11:14
 */
public interface SecurityGroupService{

    /**
     * 新增安全组
     *
     * @param securityGroup
     * @return
     * @throws SSException
     */
    public SecurityGroup newSecurityGroup(SecurityGroup securityGroup) throws SSException;

    /**
     * 根据Id删除安全组,同时删除安全组权限信息
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 更新安全组
     *
     * @param securityGroup
     * @return
     * @throws SSException
     */
    public void update(SecurityGroup securityGroup) throws SSException;

    /**
     * 分页获取数据
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<SecurityGroup> listByPage(int pageNo,
                                          int pageSize) throws SSException;

    /**
     * 获取所有
     *
     * @return
     * @throws SSException
     */
    public List<SecurityGroup> listAll() throws SSException;

    /**
     * 获取所有安全组ID
     *
     * @return
     * @throws SSException
     */
    public List<Integer> listAllGroupId() throws SSException;

    /**
     * 获取数据总量
     *
     * @return
     * @throws SSException
     */
    public int count() throws SSException;

    /**
     * 根据IdList获取在list中或不在list中得数据
     *
     * @param groupIdList
     * @param isInList
     * @return
     * @throws SSException
     */
    public List<SecurityGroup> listByIdList(List<Integer> groupIdList,
                                            boolean isInList) throws SSException;

    /**
     * 根据id进行查询
     *
     * @param id
     * @return
     * @throws SSException
     */
    public SecurityGroup queryById(int id) throws SSException;

}
