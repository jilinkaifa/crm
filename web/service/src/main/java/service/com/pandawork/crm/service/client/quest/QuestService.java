package com.pandawork.crm.service.client.quest;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.client.quest.QuestDto;
import com.pandawork.crm.common.dto.client.quest.QuestSearchDto;
import com.pandawork.crm.common.entity.client.quest.Quest;

import java.util.List;

/**
 * QuestService
 * Created by shura
 * Date:  2017/7/24.
 * Time:  14:20
 */
public interface QuestService {

    /**
     * 根据searchDto查询数据
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<Quest> listBySearchDto(QuestSearchDto searchDto)throws SSException;

    /**
     * 根据searchDto查询数据总条数
     *
     * @return
     * @throws SSException
     */
    public int countBySearchDtoNew(QuestSearchDto searchDto) throws SSException;

    /**
     * 条件统计问卷人员数量
     *
     * @return
     * @throws SSException
     */
    public int countBySearchDto(QuestSearchDto searchDto)throws SSException;

    /**
     * 新增问卷信息
     *
     * @param quest
     * @return
     * @throws SSException
     */
    public Quest newQuest(Quest quest)throws SSException;

    /**
     * 新增问卷Dto
     *
     * @param questDto
     * @throws SSException
     */
    public QuestDto newQuestDto(QuestDto questDto)throws SSException;

    /**
     * 根据患者id查询问卷
     *
     * @param id
     * @return
     * @throws SSException
     */
    public Quest queryByClientId(int id)throws SSException;

    /**
     * 统计患者问卷次数
     *
     * @param id
     * @return
     * @throws SSException
     */
    public int countByClientId(int id)throws SSException;

}
