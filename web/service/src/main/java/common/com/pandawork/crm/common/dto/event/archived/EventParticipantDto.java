package com.pandawork.crm.common.dto.event.archived;

import com.pandawork.crm.common.entity.event.EventRecordNotice;

import java.util.Date;

/**
 * EventParticipantDto
 * Author： csy
 * Date: 2017/7/27
 * Time: 14:53
 */
public class EventParticipantDto {

    //活动记录通知实体
    private EventRecordNotice eventRecordNotice;

    //活动参与者姓名
    private String name;

    // 页码
    private Integer page;

    // 分页大小
    private Integer pageSize;

    // 偏移量
    private Integer offset;

    public EventRecordNotice getEventRecordNotice() {
        return eventRecordNotice;
    }

    public void setEventRecordNotice(EventRecordNotice eventRecordNotice) {
        this.eventRecordNotice = eventRecordNotice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
