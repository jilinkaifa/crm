package com.pandawork.crm.test.profile.portrayal.action;


import com.pandawork.crm.common.dto.profile.action.ActionSearchDto;
import com.pandawork.crm.common.entity.client.basic.Client;
import com.pandawork.crm.service.profile.label.LabelItemService;
import com.pandawork.crm.service.profile.label.LabelTypeService;
import com.pandawork.crm.service.profile.action.ActionAnalyseService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ActionAnalyseServiceTest
 * Author： linjie
 * Date: 2017/8/2
 * Time: 10:44
 */
public class ActionAnalyseServiceTest extends AbstractTestCase {
    @Autowired
    private ActionAnalyseService actionAnalyseService;
    @Autowired
    private LabelItemService labelItemService;
    @Autowired
    private LabelTypeService labelTypeService;
    //所有用户类型及其数量
    @Test
    public void queryIsMemberCountAllTest() throws Exception {
        try{
            List<Client> clients = Collections.emptyList();
            clients = actionAnalyseService.queryIsMemberCountAll();
            for (Client client : clients) {
                System.out.println( client.getIsMemberName()+":" + client.getIsMemberCount());
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    //所有医保类型及其数量
    @Test
    public void queryDicMCITypeCountAll() throws Exception {
        try{
            List<Client> clients = Collections.emptyList();
            clients = actionAnalyseService.queryDicMCITypeCountAll();
            for (Client client : clients) {
                System.out.println(client.getDicMCIType()+client.getDicMCITypeName() + ":" + client.getDicMCITypeCount());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //所有性别
    @Test
    public void queryGenderAllTest() throws Exception {
        try{
            List<Client> clients = Collections.emptyList();
            clients = actionAnalyseService.queryGenderAll();
            for (Client client : clients) {
                System.out.println(client.getGenderName());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //所有民族名字
    @Test
    public void querydicNationNameAllTest() throws Exception {
        try{
            List<Client> clients = Collections.emptyList();
            clients = actionAnalyseService.queryDicNationNameAll();
            for (Client client : clients) {
                System.out.println(client.getDicNation() + ":"+client.getDicNationName());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //查询总条数用于分页（无查询条件）
    @Test
    public void queryCountAllTest() throws Exception{
        try{
           int count = actionAnalyseService.queryCountAll();
            System.out.println(count);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void queryClientTest() throws Exception{
        try{
            List<Client> clients = Collections.emptyList();
            clients = actionAnalyseService.queryClient();
            for(Client client : clients){
                System.out.println(client.getName() + client.getMaxCost() + client.getAge());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void listActionListDtoBySearchDto() throws Exception{
        ActionSearchDto actionSearchDto = new ActionSearchDto();
//        actionSearchDto.setGender(1);
//        actionSearchDto.setLabelItemFirst(31);
        List<Client> clientList = actionAnalyseService.listActionListDtoBySearchDto(actionSearchDto);
        System.out.println("111");
    }

    @Test
    public void selectEvent() throws Exception{
        int eventId = 1;
        List<Integer> clientList = new ArrayList<Integer>();
        clientList.add(1);
        clientList.add(2);
        int parttyId = 1;
        actionAnalyseService.selectEventCase(eventId,clientList,parttyId);
        System.out.println("111");

    }
}