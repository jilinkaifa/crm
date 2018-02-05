package com.pandawork.crm.mapper.party.security;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.party.security.SecurityPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SecurityPermissionMapper
 *
 * @author: zhangteng
 * @time: 15/10/10 下午9:37
 */
public interface SecurityPermissionMapper {

    /**
     * 分页获取权限
     *
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<SecurityPermission> listByPage(@Param("offset") int offset,
                                               @Param("pageSize") int pageSize) throws Exception;

    /**
     * 获取权限数量
     *
     * @return
     * @throws SSException
     */
    public int count() throws SSException;

    /**
     * 获取所有权限
     *
     * @return
     * @throws Exception
     */
    public List<SecurityPermission> listAll() throws Exception;

    /**
     * 获取所有权限的ID
     *
     * @return
     * @throws Exception
     */
    public List<Integer> listAllIds() throws Exception;

    /**
     * 根据ID获取在列表中或不在列表中的权限
     *
     * @param idList
     * @param isInList
     * @return
     * @throws Exception
     */
    public List<SecurityPermission> listByIdList(@Param("idList") List<Integer> idList,
                                                 @Param("isInList") boolean isInList) throws Exception;

    /**
     * 根据表达式获取权限数量
     *
     * @param expression
     * @return
     * @throws Exception
     */
    public int countByExpression(@Param("expression") String expression) throws Exception;
}
