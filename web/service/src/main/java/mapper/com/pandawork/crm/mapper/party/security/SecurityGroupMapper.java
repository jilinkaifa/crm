package com.pandawork.crm.mapper.party.security;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.party.security.SecurityGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SecurityGroupMapper
 *
 * @author: zhangteng
 * @time: 15/10/12 上午11:18
 */
public interface SecurityGroupMapper {

    /**
     * 分页查询
     *
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<SecurityGroup> listByPage(@Param("offset") int offset,
                                          @Param("pageSize") int pageSize) throws Exception;

    /**
     * 获取数量
     *
     * @return
     * @throws Exception
     */
    public int count() throws Exception;

    /**
     * 查询全部
     *
     * @return
     * @throws Exception
     */
    public List<SecurityGroup> listAll() throws Exception;

    /**
     * 获取所有的安全组ID
     *
     * @return
     * @throws Exception
     */
    public List<Integer> listAllGroupId() throws Exception;

    /**
     * 根据idList查询在list中或不在list中得数据
     *
     * @param idList
     * @param isInList
     * @return
     * @throws Exception
     */
    public List<SecurityGroup> listByIdList(@Param("idList") List<Integer> idList,
                                            @Param("isInList") boolean isInList) throws Exception;

    /**
     * 根据安全组名称查询数量
     *
     * @param groupName
     * @return
     * @throws Exception
     */
    public int countByGroupName(@Param("groupName") String groupName) throws Exception;

}
