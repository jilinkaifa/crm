package com.pandawork.crm.mapper.event;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.client.points.PointsItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * PointsItemMapper
 *
 * @author Flying
 * @date 2017/8/5 11:06
 */
public interface PointsItemMapper {

    /**
     * 根据eventId获取所有积分项
     *
     * @param eventId
     * @return
     * @throws SSException
     */
    public List<PointsItem> listByEventId(@Param("eventId") Integer eventId) throws Exception;

    /**
     * 根据eventId删除所有关联的积分项
     *
     * @param eventId
     * @throws SSException
     */
    public void delByEventId(@Param("eventId") Integer eventId) throws Exception;
}
