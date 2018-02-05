package com.pandawork.crm.mapper.party.member;

import com.pandawork.crm.common.entity.party.member.Member;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * MemberGroupMapper
 * 会员组Mapper
 *
 * Author： Flying
 * Date: 2017/7/15 13:47
 *
 */
public interface MemberGroupMapper {

    /**
     * 获取所有会员组的信息
     *
     * @return
     * @throws Exception
     */
    public List<Member> listAll() throws Exception;

    /**
     * 检查重名
     *
     * @param name
     * @return
     * @throws Exception
     */
    public int countByMemberGroupName(@Param("name") String name) throws Exception;

    /**
     * 查询所有未被删除的会员组信息
     *
     * @return
     * @throws Exception
     */
    public List<Member> isNotDeleted() throws Exception;

    /**
     * 分页查询数据
     *
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<Member> listByPage(@Param("offset") int offset,
                                   @Param("pageSize") int pageSize) throws Exception;

    /**
     * 获取数据总量
     *
     * @return
     * @throws Exception
     */
    public int count() throws Exception;
}
