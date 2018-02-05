package com.pandawork.crm.mapper.client.quest;

import com.pandawork.crm.common.dto.client.quest.QuestSearchDto;
import com.pandawork.crm.common.entity.client.quest.Quest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QuestMapper
 * Created by shura
 * Date:  2017/7/24.
 * Time:  16:14
 */
public interface QuestMapper {

    /**
     * 根据searchDto查询问卷信息
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<Quest> listBySearchDto(@Param("searchDto") QuestSearchDto searchDto)throws Exception;

    /**
     * 根据searchDto查询问卷信息总条数
     *
     * @return
     * @throws Exception
     */
    public int countBySearchDtoNew(@Param("searchDto") QuestSearchDto searchDto)throws Exception;

    /**
     * 统计会员数量
     *
     * @return
     * @throws Exception
     */
    public Integer countBySearchDto(@Param("searchDto") QuestSearchDto searchDto)throws Exception;

    /**
     * 根据患者id查询问卷
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Quest queryByClientId(@Param("id") Integer id)throws Exception;

    /**
     * 根据患者id统计问卷数量
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Integer countByClientId(@Param("id") Integer id)throws Exception;
}
