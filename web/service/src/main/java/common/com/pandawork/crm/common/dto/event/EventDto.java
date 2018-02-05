package com.pandawork.crm.common.dto.event;

import com.pandawork.crm.common.entity.client.points.PointsItem;
import com.pandawork.crm.common.entity.event.CheckItem;
import com.pandawork.crm.common.entity.event.Event;

import java.util.List;

/**
 * EventDto
 *
 * @author Flying
 * @date 2017/8/3 8:35
 */
public class EventDto {

    //活动实体
    private Event event;

    //活动关联检查项list
    private List<CheckItem> checkItemList;

    //活动关联积分项list
    private List<PointsItem> pointsItemList;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<CheckItem> getCheckItemList() {
        return checkItemList;
    }

    public void setCheckItemList(List<CheckItem> checkItemList) {
        this.checkItemList = checkItemList;
    }

    public List<PointsItem> getPointsItemList() {
        return pointsItemList;
    }

    public void setPointsItemList(List<PointsItem> pointsItemList) {
        this.pointsItemList = pointsItemList;
    }
}
