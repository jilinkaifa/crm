package com.pandawork.crm.service.profile.action.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.dto.client.basic.ClientSearchDto;
import com.pandawork.crm.common.dto.profile.action.ActionSearchDto;
import com.pandawork.crm.common.entity.client.basic.Client;
import com.pandawork.crm.common.entity.event.Event;
import com.pandawork.crm.common.entity.party.member.Member;
import com.pandawork.crm.common.entity.profile.analysis.AnalysisResult;
import com.pandawork.crm.common.entity.profile.portrayal.Profile;
import com.pandawork.crm.common.enums.party.MemberGroupTypeEnums;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.mapper.profile.action.ActionAnalyseMapper;
import com.pandawork.crm.service.client.basic.ClientService;
import com.pandawork.crm.service.event.prepare.EventService;
import com.pandawork.crm.service.party.member.MemberGroupService;
import com.pandawork.crm.service.profile.action.ActionAnalyseService;
import com.pandawork.crm.service.profile.analysis.AnalysisResultService;
import com.pandawork.crm.service.profile.label.LabelItemService;
import com.pandawork.crm.service.profile.portrayal.PortrayalService;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ActionAnalyseServiceImpl
 * Author： linjie
 * Date: 2017/8/2
 * Time: 10:28
 */
@Service("actionAnalyseService")
public class ActionAnalyseServiceImpl  implements ActionAnalyseService{

    @Autowired
    private ActionAnalyseMapper actionAnalyseMapper;

    @Autowired
    private PortrayalService portrayalService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private MemberGroupService memberGroupService;

    @Autowired
    private EventService eventService;

    @Autowired
    private AnalysisResultService analysisResultService;

    @Autowired
    private LabelItemService labelItemService;

    /**
     * 查询各用户类型数量(刚进入，无查询条件)
     * @return
     * @throws SSException
     */
    @Override
    public List<Client> queryIsMemberCountAll() throws SSException{
        try{
            List<Client> clients = Collections.emptyList();
            clients = actionAnalyseMapper.queryIsMemberCountAll();
            for(Client client : clients){
                if(client.getIsMember() == 0){
                    client.setIsMemberName("普通用户");
                }else if(client.getIsMember() == 1){
                    client.setIsMemberName("普通会员");
                }else if(client.getIsMember() == 2){
                    client.setIsMemberName("钻石会员");
                }
            }
            return clients;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.queryIsMemberCountAllFail,e);
        }
    }

    /**
     * 查询各医保类型数量(刚进入，无查询条件)
     * @return
     * @throws SSException
     */
    @Override
    public List<Client> queryDicMCITypeCountAll() throws SSException{
        try{
            List<Client> clients = Collections.emptyList();
            clients = actionAnalyseMapper.queryDicMCITypeCountAll();
            return clients;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.queryDicMCITypeCountAllFail,e);
        }
    }

    /**
     *所有性别(刚进入，无查询条件)
     * @return
     * @throws SSException
     */

    @Override
    public List<Client> queryGenderAll() throws SSException{
        try {
            List<Client> clients = Collections.emptyList();
            clients = actionAnalyseMapper.queryGenderAll();
            for (Client client : clients) {
                int gender = client.getGender();
                if (gender == 1) {
                    client.setGenderName("男");
                } else if (gender == 2) {
                    client.setGenderName("女");
                } else if (gender == 3) {
                    client.setGenderName("其他");
                }
            }
            return clients;
        } catch(Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.queryGenderAllFail, e);
        }
    }

    /**
     *所有民族(刚进入，无查询条件)
     * @return
     * @throws SSException
     */
    @Override
    public List<Client> queryDicNationNameAll() throws SSException{
        try{
            List<Client>  clients = Collections.emptyList();
            clients = actionAnalyseMapper.queryDicNationNameAll();
            return clients;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.queryDicNationNameAllFail, e);
        }
    }

    /**
     * 查询各用户(刚进入，无查询条件)
     * @param
     * @return
     * @throws SSException
     */
    @Override
    public List<Client> queryClient() throws SSException{
        try{
            List<Client>  clients = Collections.emptyList();
            clients = actionAnalyseMapper.queryClient();
            for(Client client : clients){
                if(client.getIsMember() == 0){
                    client.setIsMemberName("普通用户");
                }else if(client.getIsMember() == 1){
                    client.setIsMemberName("普通会员");
                }else if(client.getIsMember() == 2){
                    client.setIsMemberName("钻石会员");
                }
                if((Assert.isNull(client.getMaxCost()))){
                    client.setMaxCost(BigDecimal.ZERO);
                }
                int age = 0;
                if(Assert.isNotNull(client.getIdCardNum())){
                    age = DataUtils.getAgeByIdCard(client.getIdCardNum());
                    client.setAge(age);
                }
            }
            return clients;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.queryClientFail, e);
        }
    }
    /**
     * 根据条件查询各用户
     * @param actionSearchDto
     * @return
     * @throws SSException
     */
    @Override
    public List<Client> queryClientByActionSearchDto( ActionSearchDto actionSearchDto) throws SSException{
        try{
            List<Client>  clients = Collections.emptyList();
            clients = actionAnalyseMapper.queryClientByActionSearchDto(actionSearchDto);
            for(Client client : clients){
                if(client.getIsMember() == 0){
                    client.setIsMemberName("普通用户");
                }else if(client.getIsMember() == 1){
                    client.setIsMemberName("普通会员");
                }else if(client.getIsMember() == 2){
                    client.setIsMemberName("钻石会员");
                }
                if((Assert.isNull(client.getMaxCost()))){
                    client.setMaxCost(BigDecimal.ZERO);
                }
                int age = DataUtils.getAgeByIdCard(client.getIdCardNum());
                client.setAge(age);
            }
            return clients;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.queryClientByActionSearchDtoFail, e);
        }
    }

    /**
     * 根据条件查询用户类型数量
     * @param actionSearchDto
     * @return
     * @throws SSException
     */
    @Override
    public List<Client> queryIsMemberCountByActionSearchDto(ActionSearchDto actionSearchDto) throws SSException{
        try{
            List<Client>  clients = Collections.emptyList();
            clients = actionAnalyseMapper. queryIsMemberCountByActionSearchDto(actionSearchDto);
            for(Client client : clients){
                if(client.getIsMember() == 0){
                    client.setIsMemberName("普通用户");
                }else if(client.getIsMember() == 1){
                    client.setIsMemberName("普通会员");
                }else if(client.getIsMember() == 2){
                    client.setIsMemberName("钻石会员");
                }
            }
            return clients;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.queryIsMemberCountByActionSearchDtoFail, e);
        }
    }

    /**
     * 根据searchDto查询各医保类型数量
     * @param actionSearchDto
     * @return
     * @throws SSException
     */
    public List<Client> queryDicMCITypeCountByActionSearchDto(ActionSearchDto actionSearchDto)throws SSException{
        try{
            List<Client>  clients = Collections.emptyList();
            clients = actionAnalyseMapper.queryDicMCITypeCountByActionSearchDto(actionSearchDto);

            return clients;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.queryDicMCITypeCountByActionSearchDtoFail, e);
        }
    }

    /**
     * 总条数用于分页
     * @param actionSearchDto
     * @return
     * @throws SSException
     */
    public Integer countByActionSearchDto( ActionSearchDto actionSearchDto) throws SSException{
        try{
            int count  = actionAnalyseMapper.countByActionSearchDto(actionSearchDto);
            return count;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.countByActionSearchDtoFail, e);
        }
    }

    /**
     * 查询总条数（无查询条件）
     * @return
     * @throws SSException
     */
    public Integer queryCountAll() throws SSException{
        try{
            int count = actionAnalyseMapper.queryCountAll();
            return count;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.queryCountAllFail, e);
        }
    }

    /**
     * 判断and或or所在的位置
     *
     * @param actionSearchDto
     * @return
     */
    private int getSignPosition(ActionSearchDto actionSearchDto){
        if(actionSearchDto.getlabelTypeFirst()!=null&&actionSearchDto.getLabelItemFirst()!=null){
            return 1;
        }
        if(actionSearchDto.getlabelTypeSecond()!=null&&actionSearchDto.getLabelItemSecond()!=null){
            return 2;
        }
        if(actionSearchDto.getlabelTypeThird()!=null&&actionSearchDto.getLabelItemThird()!=null){
            return 3;
        }
        return 0;
    }

    /**
     * 判断标签不为空的查询条件个数
     *
     * @param actionSearchDto
     * @return
     */
    private int getConditionCount(ActionSearchDto actionSearchDto){
        int count = 0;
        if(actionSearchDto.getlabelTypeFirst()!=null&&actionSearchDto.getLabelItemFirst()!=null){
            count++;
        }
        if(actionSearchDto.getlabelTypeSecond()!=null&&actionSearchDto.getLabelItemSecond()!=null){
            count++;
        }
        if(actionSearchDto.getlabelTypeThird()!=null&&actionSearchDto.getLabelItemThird()!=null){
            count++;
        }
        return count;
    }

    /**
     * 根据searchDto获取行为分析患者信息
     *
     * @param actionSearchDto
     * @return
     * @throws SSException
     */
    @Override
    public List<Client> listActionListDtoBySearchDto(ActionSearchDto actionSearchDto) throws SSException{
        try {
            actionSearchDto.setSignPosition(this.getSignPosition(actionSearchDto));
            actionSearchDto.setConditionCount(this.getConditionCount(actionSearchDto));
            return actionAnalyseMapper.listClientBySearchDtoNew(actionSearchDto);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.listActionListDtoBySearchDtoFail, e);
        }
//        List<Client> clientSame = new ArrayList<Client>();
//        try{
//            //根据前四个基本条件查询出患者信息
//            List<Client> clientListB = actionAnalyseMapper.listClientBySearchDto(actionSearchDto);
//
//            //根据三个标签项查询出患者信息
//            List<Client> clientListP = new ArrayList<Client>();
//            if(Assert.isNotNull(actionSearchDto.getLabelItemFirst())){
//                List<Profile> profileList = portrayalService.listByItemId(actionSearchDto.getLabelItemFirst());
//                for(Profile profile : profileList){
//                    Client client = clientService.queryById(profile.getClientId());
//                    clientListP.add(client);
//                }
//            }
//            if(Assert.isNotNull(actionSearchDto.getLabelItemSecond())){
//                List<Profile> profileList = portrayalService.listByItemId(actionSearchDto.getLabelItemSecond());
//                for(Profile profile : profileList){
//                    Client client = clientService.queryById(profile.getClientId());
//                    clientListP.add(client);
//                }
//            }
//            if(Assert.isNotNull(actionSearchDto.getLabelItemThird())){
//                List<Profile> profileList = portrayalService.listByItemId(actionSearchDto.getLabelItemThird());
//                for(Profile profile : profileList){
//                    Client client = clientService.queryById(profile.getClientId());
//                    clientListP.add(client);
//                }
//            }
//            if(Assert.isNull(actionSearchDto.getLabelItemFirst()) && Assert.isNull(actionSearchDto.getLabelItemSecond()) && Assert.isNull(actionSearchDto.getLabelItemThird())){
//                ClientSearchDto clientSearchDto = new ClientSearchDto();
//                List<Client> clientList = clientService.listBySearchDto(clientSearchDto);
//                for(Client client1 : clientList){
//                    clientListP.add(client1);
//                }
//
//            }
//            //取出两个list中相同的元素，结果为最终需要的患者信息
//
//            for(int i = 0; i < clientListP.size();i++){
//                Client temp = clientListP.get(i);
//                for(int j = 0; j < clientListB.size();j++){
//                    if(temp.getId() == (clientListB.get(j)).getId()){
//                        clientSame.add(temp);
//                    }
//                }
//            }
//
//        }catch(Exception e){
//            LogClerk.errLog.error(e);
//            throw SSException.get(CrmException.listActionListDtoBySearchDtoFail, e);
//        }
//        return clientSame;
    }

    @Override
    public int countActionListDtoBySearchDto(ActionSearchDto actionSearchDto) throws SSException{
//        List<Client> clientSame = new ArrayList<Client>();
        try{
            actionSearchDto.setSignPosition(this.getSignPosition(actionSearchDto));
            actionSearchDto.setConditionCount(this.getConditionCount(actionSearchDto));
            return actionAnalyseMapper.countClientBySearchDtoNew(actionSearchDto);

//            //根据前四个基本条件查询出患者信息
//            List<Client> clientListB = actionAnalyseMapper.countClientBySearchDto(actionSearchDto);
//
//            //根据三个标签项查询出患者信息
//            List<Client> clientListP = new ArrayList<Client>();
//            if(Assert.isNotNull(actionSearchDto.getLabelItemFirst())){
//                List<Profile> profileList = portrayalService.listByItemId(actionSearchDto.getLabelItemFirst());
//                for(Profile profile : profileList){
//                    Client client = clientService.queryById(profile.getClientId());
//                    clientListP.add(client);
//                }
//            }
//            if(Assert.isNotNull(actionSearchDto.getLabelItemSecond())){
//                List<Profile> profileList = portrayalService.listByItemId(actionSearchDto.getLabelItemSecond());
//                for(Profile profile : profileList){
//                    Client client = clientService.queryById(profile.getClientId());
//                    clientListP.add(client);
//                }
//            }
//            if(Assert.isNotNull(actionSearchDto.getLabelItemThird())){
//                List<Profile> profileList = portrayalService.listByItemId(actionSearchDto.getLabelItemThird());
//                for(Profile profile : profileList){
//                    Client client = clientService.queryById(profile.getClientId());
//                    clientListP.add(client);
//                }
//            }
//            if(Assert.isNull(actionSearchDto.getLabelItemFirst()) && Assert.isNull(actionSearchDto.getLabelItemSecond()) && Assert.isNull(actionSearchDto.getLabelItemThird())){
//                ClientSearchDto clientSearchDto = new ClientSearchDto();
//                List<Client> clientList = clientService.listBySearchDto(clientSearchDto);
//                for(Client client1 : clientList){
//                    clientListP.add(client1);
//                }
//
//            }
//            //取出两个list中相同的元素，结果为最终需要的患者信息
//
//            for(int i = 0; i < clientListP.size();i++){
//                Client temp = clientListP.get(i);
//                for(int j = 0; j < clientListB.size();j++){
//                    if(temp.getId() == (clientListB.get(j)).getId()){
//                        clientSame.add(temp);
//                    }
//                }
//            }

        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.listActionListDtoBySearchDtoFail, e);
        }
        //return clientSame.size();
    }

    @Override
    public void selectEventCase(int id,List<Integer> clientId,int partyId) throws SSException{
        try{
            Event event = new Event();
            event = eventService.queryById(id);
            //创建一个行为分析组
            Member member = new Member();
            member.setName(event.getName()+"行为分析人员组");
            member.setType(MemberGroupTypeEnums.Analysis.getId());
            member.setCreatedPartyId(partyId);
            member.setDelated(0);
            memberGroupService.newMember(member);


            //创建一个分析结果
            AnalysisResult analysisResult = new AnalysisResult();
            for(Integer client : clientId){
                analysisResult.setClientId(client);
                analysisResult.setMemberGroupId(member.getId());
                analysisResult.setCreatedPartyId(partyId);
                analysisResultService.newAnalysisResult(analysisResult);
            }

            //向活动中添加行为分析人员组
            event.setMemberGroupId(member.getId());
            eventService.updateEvent(event);

        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.listActionListDtoBySearchDtoFail, e);
        }
    }


}

