package com.pandawork.crm.service.event;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.client.points.PointsItem;

import java.util.List;

/**
 * PointsItemService
 * 活动关联积分项Service
 *
 * @author Flying
 * @date 2017/8/3 9:29
 */
public interface PointsItemService {

    /**
     * 添加积分项
     *
     * @param pointsItem
     * @return
     * @throws SSException
     */
    public PointsItem newPointsItem(PointsItem pointsItem) throws SSException;

    /**
     * 根据eventId获取所有积分项
     *
     * @param eventId
     * @return
     * @throws SSException
     */
    public List<PointsItem> listByEventId(int eventId) throws SSException;

    /**
     * 根据eventId删除所有关联的积分项
     *
     * @param eventId
     * @throws SSException
     */
    public void delByEventId(int eventId) throws SSException;
}
