package com.pandawork.crm.test.client.member;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.client.member.MemberDto;
import com.pandawork.crm.common.dto.client.member.MemberSearchDto;
import com.pandawork.crm.common.entity.client.basic.Client;
import com.pandawork.crm.common.enums.client.MemberStatusEnums;
import com.pandawork.crm.service.client.member.MemberService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * MemberServiceTest
 * Created by shura
 * Date:  2017/7/25.
 * Time:  13:30
 */
public class MemberServiceTest  extends AbstractTestCase {

    @Autowired
    private MemberService memberService;

    @Test
    public void updateMemberStatus()throws SSException {
        memberService.updateMemberStatus(1, MemberStatusEnums.Locked);
    }

    @Test
    public void updateMember()throws SSException{
        MemberDto memberDto = new MemberDto();
        memberDto.setId(1);
        memberDto.setLevel(2);
        memberDto.setMemberStatus(0);
        memberDto.setMemberGroupId(1);
        memberDto.setDeadLine(new Date());
        memberService.updateMember(memberDto);
    }

    @Test
    public void listMemberBySearchDto()throws SSException{
        MemberSearchDto searchDto = new MemberSearchDto();
        List<Client> clientList = memberService.listBySearchDto(searchDto);
        for(Client client : clientList){
            System.out.println(client.getName());
        }
    }

    @Test
    public void deadlineLockTest()throws SSException{
        memberService.deadlineLock();
    }

    @Test
    public  void  countByGroupIp()throws SSException{
        int count = memberService.countByGroup(1);
        System.out.println(count);
    }

    @Test
    public void countMember()throws SSException{
        MemberSearchDto searchDto = new MemberSearchDto();
        int count  = memberService.countBySearchDto(searchDto);
        System.out.println(count);
    }

}
