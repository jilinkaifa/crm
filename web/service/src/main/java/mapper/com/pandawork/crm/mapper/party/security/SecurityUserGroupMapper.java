package com.pandawork.crm.mapper.party.security;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.party.security.SecurityGroup;
import com.pandawork.crm.common.entity.party.security.SecurityUserGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SecurityUserGroupMapper
 *
 * @author: zhangteng
 * @time: 15/10/12 上午9:39
 */
public interface SecurityUserGroupMapper {

    /**
     * 根据用户ID获取用户-安全组
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public List<SecurityUserGroup> listByUserId(@Param("userId") int userId) throws Exception;

    /**
     * 根据用户ID获取安全组ID列表
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public List<Integer> listGroupIdByUserId(@Param("userId") int userId) throws Exception;

    /**
     * 根据用户ID获取安全组列表
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public List<SecurityGroup> listSecurityGroupByUserId(@Param("userId") int userId) throws Exception;

    /**
     * 根据用户名查询安全组
     *
     * @param loginName
     * @return
     * @throws Exception
     */
    public List<SecurityGroup> listSecurityGroupByLoginName(@Param("loginName") String loginName) throws Exception;

    /**
     * 根据安全组ID删除
     *
     * @param groupId
     * @throws Exception
     */
    public void delByGroupId(@Param("groupId") int groupId) throws Exception;

    /**
     * 根据用户ID删除
     *
     * @param userId
     * @throws Exception
     */
    public void delByUserId(@Param("userId") int userId) throws Exception;

    /**
     * 根据安全组id获取所有用户安全组list
     *
     * @param groupId
     * @throws Exception
     */
    public List<SecurityUserGroup>  listByGroupId(@Param("groupId") int groupId) throws Exception;

    /**
     * 根据userId查询
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public SecurityUserGroup queryByUserId(@Param("userId") int userId) throws Exception;
}
