package com.pandawork.crm.service.party.member;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.party.member.Member;

import java.util.List;

/**
 * MemberGroupService
 *
 * @author Flying
 * @date 2017/7/16 14:48
 */
public interface MemberGroupService {

    /**
     * 获取所有会员组的信息
     *
     * @return
     * @throws SSException
     */
    public List<Member> listAll() throws SSException;

    /**
     * 添加新的会员组
     *
     * @param member
     * @return
     * @throws SSException
     */
    public Member newMember(Member member) throws SSException;

    /**
     * 根据id删除会员组
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 修改会员分组信息
     *
     * @param member
     * @throws SSException
     */
    public void updateMember(Member member) throws SSException;

    /**
     * 根据id查询会员组的信息
     *
     * @param id
     * @return
     * @throws SSException
     */
    public Member queryById(int id) throws SSException;

    /**
     * 查询所有未被删除的会员组
     *
     * @return
     * @throws SSException
     */
    public List<Member> isNotDeleted() throws SSException;

    /**
     * 分页获取数据
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<Member> listByPage(int pageNo, int pageSize) throws SSException;

    /**
     * 获取数据总量
     *
     * @return
     * @throws SSException
     */
    public int count() throws SSException;

}
