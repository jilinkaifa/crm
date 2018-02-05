package com.pandawork.crm.test.client.basic;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.dto.client.basic.ClientSearchDto;
import com.pandawork.crm.common.entity.client.basic.Client;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.DateUtils;
import com.pandawork.crm.service.client.basic.ClientService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 患者管理测试类
 * Created by shura on 2017/7/18.
 */
public class ClientServiceTest extends AbstractTestCase{

    @Autowired
    private ClientService clientService;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;//core包

    @Test
    public void newClient()throws SSException{
        Client client = new Client();
        client.setName("脑残2");
        client.setGender(3);
        client.setIdCardNum("230103200101018610");
        client.setTel("13112345678");
        client.setDiagnoseType("小帅是神小帅是神小帅是神");
        client.setDicType(1001001);
        client.setDicMCIType(1001002);
        client.setAllCost(BigDecimal.ZERO);
        client.setIsMember(1);
        client.setMemberGroupId(1);
        clientService.newClient(client);
    }
    @Test
    public void listByPage()throws SSException{
        List<Client> clientList = clientService.listByPage(0,10);
        for (Client client : clientList){
            System.out.println(client.getName());
        }
    }

    @Test
    public void queryById()throws SSException{
        Client client = clientService.queryById(2);
        System.out.println(client.getName());
    }

    @Test
    public void update()throws SSException{
        Client client = clientService.queryById(2);
        client.setName("李四");
        clientService.updateClient(client);
    }

    @Test
    public void listByMemberGroupId()throws SSException{
        List<Client> clientList = clientService.listByMemberGroupId(1);
        for(Client client : clientList){
            System.out.println(client.getName());
        }
    }

    @Test
    public void listBySearchDto()throws SSException{
        ClientSearchDto searchDto = new ClientSearchDto();
        searchDto.setPageSize(10);
        searchDto.setOffset(0);
        searchDto.setStartDate(DateUtils.aheadDay(new Date(),10));
        searchDto.setEndDate(new Date());
        List<Client> clientList = clientService.listBySearchDto(searchDto);
        for(Client client : clientList){
            System.out.println(client.getName());
        }
    }
    @Test
    public void count()throws SSException{
        int count = 0;
        ClientSearchDto searchDto = new ClientSearchDto();
        count = clientService.countBySearchDto(searchDto);
        System.out.println(count);
    }

    @Test
    public void addMember()throws SSException{
        clientService.addMember(26);
    }

    @Test
    public void updateQuestCount()throws SSException{
        clientService.updateQuestCount(1);
    }

    @Test
    public void updateAllCost()throws SSException{
        clientService.updateAllCost(1);
        Client client = clientService.queryById(1);
        System.out.println(client.getAllCost());
    }

    @Test
    public void getAgeByIdCard()throws SSException{
        int age = DataUtils.getAgeByIdCard("230103199505206214");
        System.out.println(age);
    }

    @Test
    public void updateClient()throws Exception{
        Client client = new Client();
        clientService.queryById(29);
        client.setAllCost(BigDecimal.valueOf(292.3));
        clientService.updateClient(client);
    }

    @Test
    public void updateAllPointsAndMemberPoints()throws SSException{
        clientService.updateAllPointsAndMemberPoints(1);
    }

    @Test
    public void testProgram()throws SSException{
        StringBuffer stringBuffer = new StringBuffer("hello world");
        int i = 0;
        while(i < stringBuffer.length()){
            if(stringBuffer.charAt(i) == ' '){
                stringBuffer.replace(i,i+1,"%20");
            }
            i++;
        }
        System.out.println(stringBuffer.toString());
    }
}
