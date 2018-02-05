package com.pandawork.crm.service.event;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.event.EventRecordPointsItem;

/**
 * ArchivedEventService
 * Author： wychen
 * Date: 2017/7/21
 * Time: 10:37
 */
public interface EventRecordPointsItemService {

    /**
     * 批量添加积分项
     *
     * @param eventRecordPointsItem
     * @throws SSException
     */
    public void newPointsItem(EventRecordPointsItem eventRecordPointsItem) throws SSException;

    /**
     * 根据活动记录id获取患者参与检查项信息
     *
     * @param eventRecordNoticeId
     * @return
     * @throws SSException
     */
//    public List<CountParticipant> queryByEventRecordNoticeId(int eventRecordNoticeId) throws SSException;
}
