package com.pandawork.crm.mapper.client.quest;

import com.pandawork.crm.common.dto.client.quest.QuestItemSearchDto;
import com.pandawork.crm.common.entity.client.quest.QuestItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QuestItemMapper
 * Created by shura
 * Date:  2017/7/26.
 * Time:  10:45
 */
public interface QuestItemMapper {

    /**
     * 根据searchDto查询问卷详情
     *
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<QuestItem> listQuestItemBySearchDto(@Param("searchDto") QuestItemSearchDto searchDto)throws Exception;

    /**
     * 根据searchDto统计问卷项数量
     *
     * @param searchDto
     * @return
     * @throws Exception
     */
    public int count(@Param("searchDto") QuestItemSearchDto searchDto)throws Exception;
}
