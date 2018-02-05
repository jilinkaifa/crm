package com.pandawork.crm.service.client.member;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.client.basic.ClientSearchDto;
import com.pandawork.crm.common.dto.client.member.MemberDto;
import com.pandawork.crm.common.dto.client.member.MemberSearchDto;
import com.pandawork.crm.common.entity.client.basic.Client;
import com.pandawork.crm.common.entity.party.member.Member;
import com.pandawork.crm.common.enums.client.MemberStatusEnums;

import java.util.List;

/**
 * MemberService
 * Created by shura
 * Date:  2017/7/25.
 * Time:  12:53
 */
public interface MemberService {


    /**
     * 分页查询
     *  @param searchDto
     * @return
     * @throws SSException
     */
    public List<Client> listBySearchDto(MemberSearchDto searchDto)throws SSException;

    /**
     * 修改会员状态（0：在用，1：锁定）
     * @param id
     * @param memberStatusEnums
     * @throws SSException
     */
    public void updateMemberStatus(int id, MemberStatusEnums memberStatusEnums)throws SSException;

    /**
     * 到期自动锁定会员状态
     * @throws SSException
     */
    public void deadlineLock()throws SSException;


    /**
     * 统计会员组会员数量
     *
     * @param memberGroupId
     * @return
     * @throws SSException
     */
    public int countByGroup(int memberGroupId)throws SSException;

    /**
     * 条件统计会员数量
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public int countBySearchDto(MemberSearchDto searchDto)throws SSException;


    /**
     * 修改会员
     *
     * @param memberDto
     * @throws SSException
     */
    public void updateMember(MemberDto memberDto)throws SSException;

    /**
     * 清空会员积分
     *
     * @param clientId
     * @throws SSException
     */
    public void clearPoints(int clientId)throws SSException;
}
