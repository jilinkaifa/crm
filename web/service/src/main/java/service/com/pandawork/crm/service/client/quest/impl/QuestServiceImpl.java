package com.pandawork.crm.service.client.quest.impl;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.dto.client.quest.QuestDto;
import com.pandawork.crm.common.dto.client.quest.QuestSearchDto;
import com.pandawork.crm.common.entity.client.basic.Client;
import com.pandawork.crm.common.entity.client.quest.Quest;
import com.pandawork.crm.common.entity.client.quest.QuestItem;
import com.pandawork.crm.common.enums.party.dictionary.DictionaryEnums;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.mapper.client.quest.QuestMapper;
import com.pandawork.crm.service.client.basic.ClientService;
import com.pandawork.crm.service.client.quest.QuestItemService;
import com.pandawork.crm.service.client.quest.QuestService;
import com.pandawork.crm.service.party.dictionary.DictionaryService;
import com.pandawork.crm.service.party.member.MemberGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * QuestServiceImpl
 * Created by shura
 * Date:  2017/7/24.
 * Time:  15:30
 */
@Service("questService")
public class QuestServiceImpl implements QuestService {

    @Autowired
    private MemberGroupService memberGroupService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private QuestItemService questItemService;

    @Autowired
    private QuestMapper questMapper;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private ClientService clientService;

    /**
     * 根据searchDto查询数据
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    @Override
    public List<Quest> listBySearchDto(QuestSearchDto searchDto)throws SSException{
        List<Quest> questList = new ArrayList<Quest>();
        try{
            questList = questMapper.listBySearchDto(searchDto);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListClientFailed, e);
        }
        return questList;
    }

    /**
     * 根据searchDto查询总条数
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public int countBySearchDtoNew(QuestSearchDto searchDto) throws SSException{
        int count = 0;
        try{
            count = questMapper.countBySearchDtoNew(searchDto);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryPointsFailed,e);
        }
        return count;
    }

    /**
     * 条件统计问卷数量
     *
     * @return
     * @throws SSException
     */
    @Override
    public int countBySearchDto(QuestSearchDto searchDto)throws SSException{
        try{
            return questMapper.countBySearchDto(searchDto);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountMemberFailed, e);
        }
    }

    /**
     * 添加问卷
     *
     * @param quest
     * @return
     * @throws SSException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public Quest newQuest(Quest quest)throws SSException{
        Client client = new Client();
        try{
            //添加问卷
            Quest newQuest = commonDao.insert(quest);
            return newQuest;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.NewQuestFailed, e);
        }
    }

    /**
     * 添加问卷dto
     *
     * @param questDto
     * @return
     * @throws SSException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public QuestDto newQuestDto(QuestDto questDto)throws SSException{
       String questContent = "";
       String unitName = "";
        try{
            Quest quest =new Quest();
            quest.setQuestTime(new Date());
            quest.setNextQuestTime(questDto.getNextQuestTime());
            quest.setComment(questDto.getComment());
            quest.setClientId(questDto.getClientId());
            //查询会员组
            quest.setMemberGroupId(clientService.queryById(questDto.getClientId()).getMemberGroupId());
            quest.setCreatedPartyId(questDto.getCreatedPartyId());
            quest.setRemindaheadDays(questDto.getRemindaheadDays());
            //统计问卷项个数  用药情况算一个问卷项 故初始化 1 若为用药情况字典值则不进行自增
            int questTypes = 1;
            for(QuestItem questItem :questDto.getQuestItemList()){
                if(questItem.getDicQuestItem() != DictionaryEnums.DIC_DOSAGE.getId()){
                    questTypes++;
                }
            }
            quest.setQuestTypes(questTypes);
//          quest.setQuestTypes(questDto.getQuestItemList().size());
            Quest newQuest = newQuest(quest);
            for(QuestItem questItem : questDto.getQuestItemList()){
                    questItem.setStartDate(questDto.getStartDate());
                    questItem.setEndDate(questDto.getEndDate());
                    questItem.setCreatedPartyId(quest.getCreatedPartyId());
                    questItem.setQuestId(newQuest.getId());
                    //若为用药情况就将信息拼接成问卷内容
                   if(questItem.getDicQuestItem() == DictionaryEnums.DIC_DOSAGE.getId()){
                       unitName = dictionaryService.queryById(questItem.getDicDosageUnit()).getName();
                    questContent = questItem.getDosageName() + "药，" +"每次"+questItem.getDosageQuantity() + unitName + "，每日"
                            +questItem.getDosageNum() + "次";
                       questItem.setQuestContent(questContent);
                    }
                    questItemService.newQuestItem(questItem);
            }
            return questDto;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.NewQuestDtoFailed, e);
        }
    }

    /**
     * 根据i患者id查询问卷
     *
     * @param id
     * @return
     * @throws SSException
     */
    @Override
    public Quest queryByClientId(int id)throws SSException{
        try{
            return questMapper.queryByClientId(id);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryQuestByClientFailed, e);
        }
    }

    /**
     * 根据患者id统计数量
     *
     * @param id
     * @return
     * @throws SSException
     */
    @Override
    public int countByClientId(int id)throws SSException{
        try{
            int count = 0;
            count = questMapper.countByClientId(id);
            return count;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountByClientIdFailed, e);
        }
    }


}
