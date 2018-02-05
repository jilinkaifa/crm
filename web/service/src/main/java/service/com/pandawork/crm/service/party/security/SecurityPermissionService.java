package com.pandawork.crm.service.party.security;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.party.security.SecurityPermission;

import java.util.List;

/**
 * 权限service
 *
 * @author: zhangteng
 * @time: 15/10/10 下午9:14
 */
public interface SecurityPermissionService {

    /**
     * 新增权限
     *
     * @param securityPermission
     * @throws SSException
     */
    public SecurityPermission newPermission(SecurityPermission securityPermission) throws SSException;

    /**
     * 根据Id删除权限
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 修改权限
     *
     * @param securityPermission
     * @throws SSException
     */
    public void update(SecurityPermission securityPermission) throws SSException;

    /**
     * 分页获取权限
     *
     * @param page
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<SecurityPermission> listByPage(int page, int pageSize) throws SSException;

    /**
     * 获取所有权限
     *
     * @return
     * @throws SSException
     */
    public List<SecurityPermission> listAll() throws SSException;

    /**
     * 获取所有权限的Id
     *
     * @return
     * @throws SSException
     */
    public List<Integer> listAllIds() throws SSException;

    /**
     * 根据IdList获取在列表中或者不在列表中的数据
     *
     * @param idList
     * @param isInList  是否在列表中
     * @return
     * @throws SSException
     */
    public List<SecurityPermission> listByIdList(List<Integer> idList,
                                                 boolean isInList) throws SSException;

    /**
     * 查询数据总量
     *
     * @return
     * @throws SSException
     */
    public int count() throws SSException;


    /**
     * 从TXT文件中导入权限
     *
     * @param filePath
     * @throws Exception
     */
    public void importFromText(String filePath) throws Exception;

}
