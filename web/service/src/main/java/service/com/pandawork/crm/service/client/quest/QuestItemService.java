package com.pandawork.crm.service.client.quest;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.client.quest.QuestItemSearchDto;

import com.pandawork.crm.common.entity.client.quest.QuestItem;
import com.pandawork.crm.common.entity.party.dictionary.Dictionary;


import java.util.List;

/**
 * QusetItemService
 * Created by shura
 * Date:  2017/7/25.
 * Time:  21:49
 */
public interface QuestItemService{

    /**
     * 添加问卷项
     *
     * @param questItem
     * @return
     * @throws SSException
     */
    public QuestItem newQuestItem(QuestItem questItem)throws SSException;


    /**
     * 根据searchDto查询问卷详情列表
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<QuestItem> listBySearchDto(QuestItemSearchDto searchDto)throws SSException;

    /**
     * 根据searchDto统计问卷项数量
     *
     * @return
     * @throws SSException
     */
    public int countBySearchDto(QuestItemSearchDto searchDto)throws SSException;
}
