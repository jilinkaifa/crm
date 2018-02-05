package com.pandawork.crm.mapper.party.group.employee;

import org.apache.ibatis.annotations.Param;

/**
 * EmployeeRoleMapper
 *
 * @author xiaozl
 * @date: 2016/7/25
 */
public interface EmployeeRoleMapper {

    /**
     * 根据partyId删除会员角色
     * @param partyId
     * @throws Exception
     */
    public void delByPartyId(@Param("partyId") Integer partyId) throws Exception;

}
