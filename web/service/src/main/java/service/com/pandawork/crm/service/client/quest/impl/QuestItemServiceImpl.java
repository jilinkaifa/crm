package com.pandawork.crm.service.client.quest.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.dto.client.quest.QuestItemSearchDto;
import com.pandawork.crm.common.entity.client.quest.QuestItem;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.mapper.client.quest.QuestItemMapper;
import com.pandawork.crm.service.client.quest.QuestItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * QuestItemServiceImpl
 * Created by shura
 * Date:  2017/7/26.
 * Time:  9:20
 */
@Service("questItemService")
public class QuestItemServiceImpl implements QuestItemService {

    @Autowired
    private QuestItemMapper questItemMapper;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;//core包

    /**
     * 添加问卷详情
     *
     * @param questItem
     * @return
     * @throws SSException
     */
    @Override
    public QuestItem newQuestItem(QuestItem questItem)throws SSException{
        try{
            //实体非空校验
            if(Assert.isNull(questItem)){
                throw  SSException.get(CrmException.QuestItemIsNotNull);
            }
            if(Assert.isNull(questItem.getQuestId()) || Assert.lessOrEqualZero(questItem.getQuestId())){
                throw SSException.get(CrmException.QuestIdError);
            }
            if(Assert.isNull(questItem.getDicQuestItem()) || Assert.lessOrEqualZero(questItem.getDicQuestItem())){
                throw SSException.get(CrmException.QuestItemIdError);
            }
            if(Assert.isNull(questItem.getCreatedPartyId()) || Assert.lessOrEqualZero(questItem.getCreatedPartyId())){
                throw SSException.get(CrmException.CreatedPartyIdError);
            }
            return commonDao.insert(questItem);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.NewQuestItemFailed, e);
        }
    }

    /**
     * 根据SearchDto查询问卷项列表
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    @Override
    public List<QuestItem> listBySearchDto(QuestItemSearchDto searchDto)throws SSException{
        try{
            List<QuestItem> questItemList = questItemMapper.listQuestItemBySearchDto(searchDto);
            return questItemList;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListQuestItemFailed, e);
        }
    }

    /**
     * 根据searchDto统计问卷项数量
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    @Override
    public int countBySearchDto(QuestItemSearchDto searchDto)throws SSException{
        try{
            int count = questItemMapper.count(searchDto);
            return count;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountBySearchDtoFailed, e);
        }
    }

}
