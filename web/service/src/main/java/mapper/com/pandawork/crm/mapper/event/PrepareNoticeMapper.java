package com.pandawork.crm.mapper.event;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.event.EventSearchDto;
import com.pandawork.crm.common.dto.event.PrepareNoticeSearchDto;
import com.pandawork.crm.common.entity.event.EventTerm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * PrepareNoticeMapper
 *
 * @author Daydreamer
 * @date 2017/7/31 15:44
 */
public interface PrepareNoticeMapper {

    /**
     * 查询所有活动
     *
     * @return
     * @throws Exception
     */
    public List<EventTerm> listAll() throws Exception;

    /**
     * 根据PrepareNoticeSearchDto查询所有符合条件的活动
     *
     * @param prepareNoticeSearchDto
     * @return
     * @throws Exception
     */
    public List<EventTerm> listEventBySearchDto(@Param("prepareNoticeSearchDto")PrepareNoticeSearchDto prepareNoticeSearchDto)throws Exception;

    /**
     * 查询所有活动个数
     *
     * @return
     * @throws SSException
     */
    public int countAll() throws SSException;

    /**
     * 计算符合条件活动的个数
     *
     * @param prepareNoticeSearchDto
     * @return
     * @throws Exception
     */
    public int countEventBySearchDto(@Param("prepareNoticeSearchDto")PrepareNoticeSearchDto prepareNoticeSearchDto)throws Exception;
}
