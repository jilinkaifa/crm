package com.pandawork.crm.service.event.prepareNotice;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.event.PrepareNoticeSearchDto;
import com.pandawork.crm.common.entity.event.EventTerm;

import java.util.List;

/**
 * PrepareNoticeService
 *
 * @author Daydreamer
 * @date 2017/7/31 14:55
 */
public interface PrepareNoticeService {

    /**
     * 查询所有活动
     *
     * @return
     * @throws SSException
     */
    public List<EventTerm> listAll() throws SSException;

    /**
     * 根据查询条件查询活动列表
     *
     * @param prepareNoticeSearchDto
     * @return
     * @throws SSException
     */
    public List<EventTerm> listBySearchDto(PrepareNoticeSearchDto prepareNoticeSearchDto) throws SSException;

    /**
     * 查询待办活动通知数量
     *
     * @return
     * @throws SSException
     */
    public Integer countPrepareNotice(PrepareNoticeSearchDto prepareNoticeSearchDto) throws SSException;

    /**
     * 查询所有待办活动通知数量
     *
     * @return
     * @throws SSException
     */
    public Integer countAll() throws SSException;

    /**
     * 根据id获取本期活动信息
     *
     * @param id
     * @return
     * @throws SSException
     */
    public EventTerm queryById(int id) throws SSException;
}
