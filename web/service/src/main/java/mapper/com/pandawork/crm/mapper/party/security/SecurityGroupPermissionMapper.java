package com.pandawork.crm.mapper.party.security;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.party.security.SecurityGroupPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SecurityGroupPermissionMapper
 *
 * @author: zhangteng
 * @time: 15/10/13 下午1:50
 */
public interface SecurityGroupPermissionMapper {

    /**
     * 根据安全组ID删除
     *
     * @param groupId
     * @throws Exception
     */
    public void delByGroupId(@Param("groupId") int groupId) throws Exception;

    /**
     * 根据安全组ID获取数据
     *
     * @param groupId
     * @throws Exception
     */
    public List<SecurityGroupPermission> listByGroupId(@Param("groupId") int groupId) throws Exception;

    /**
     * 根据安全组的id分页获取权限
     *
     * @param groupId
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<SecurityGroupPermission> listByGroupIdAndPage(@Param("groupId") int groupId,
                                                              @Param("offset") int offset,
                                                              @Param("pageSize") int pageSize) throws Exception;

    /**
     * 根据安全组的id获取权限数量
     *
     * @param groupId
     * @return
     * @throws SSException
     */
    public int countByGroupId(@Param("groupId") int groupId) throws Exception;

    /**
     * 根据安全组ID获取权限ID
     *
     * @param groupId
     * @return
     * @throws Exception
     */
    public List<Integer> listPermissionIdByGroupId(@Param("groupId") int groupId) throws Exception;
}
