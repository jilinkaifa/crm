package com.pandawork.crm.service.event;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.event.CheckItem;

import java.util.List;

/**
 * checkItemService
 * Author： wychen
 * Date: 2017/7/20
 * Time: 21:40
 */
public interface CheckItemService {

    /**
     * 获取检查项列表
     *
     * @return
     * @throws SSException
     */
    public List<CheckItem> listAll() throws SSException;

    /**
     * 根据活动id获取检查项列表
     *
     * @param eventId
     * @return
     * @throws SSException
     */
    public List<CheckItem> listByEventId(int eventId) throws SSException;

    /**
     * 新增检查项
     *
     * @param checkItem
     * @return
     * @throws SSException
     */
    public CheckItem newCheckItem(CheckItem checkItem) throws SSException;

    /**
     * 根据获取检查项
     *
     * @param id
     * @return
     * @throws SSException
     */
    public CheckItem queryById(int id) throws SSException;

    /**
     * 检查名称是否已经存在
     *
     * @param name
     * @return
     * @throws SSException
     */
    public Boolean checkExistByName(String name) throws SSException;

    /**
     * 根据活动id删除检查项
     *
     * @param eventId
     * @throws SSException
     */
    public void deleteByEventId(int eventId) throws SSException;

}
