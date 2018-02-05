package com.pandawork.crm.test.party.member;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.client.basic.ClientSearchDto;
import com.pandawork.crm.common.entity.client.basic.Client;
import com.pandawork.crm.common.entity.party.member.Member;
import com.pandawork.crm.service.client.basic.ClientService;
import com.pandawork.crm.service.party.member.MemberGroupService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Flying
 * @date 2017/7/17 10:50
 */
public class MemberGroupServiceTest extends AbstractTestCase {

    @Autowired
    private MemberGroupService memberGroupService;

    @Autowired
    private ClientService clientService;

    @Test
    public void newMember(){
        Member member = new Member();
        member.setName("B组");
        member.setComment("b组");
        try{
            memberGroupService.newMember(member);
        }catch (SSException e){
            e.printStackTrace();
        }
    }

    @Test
    public void listAll() throws SSException {
        List<Member> memberList = memberGroupService.listAll();
        for(Member member : memberList){
            System.out.println(member.getName());
        }
    }

    @Test
    public void isNotDeleted() throws SSException{
        List<Member> memberList = memberGroupService.isNotDeleted();
        for (Member member : memberList){
            System.out.println(member.getName());
        }
    }

    @Test
    public void listByPage() throws SSException{
        int pageNo = 1;
        int pageSize = 10;
        List<Member> memberList = memberGroupService.listByPage(pageNo , pageSize);
        for(Member member : memberList){
            System.out.println(member.getName());
        }
    }

    @Test
    public void listMemberForSearch()throws SSException{
        ClientSearchDto searchDto = new ClientSearchDto();
        searchDto.setIsMember(1);
        List<Client> clientList = clientService.listForSearch(searchDto);
        for(Client client : clientList){
            System.out.println(client.getName());
        }
    }
}
