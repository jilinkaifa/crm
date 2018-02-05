package com.pandawork.crm.common.dto.event.processing;

import java.util.Date;

/**
 * recordDto
 * Author： wychen
 * Date: 2017/8/5
 * Time: 14:00
 */
public class RecordDto {

    //活动通知记录id
    private Integer id;

    //本期参与状态: 1已完成 0未完成
    private Integer eventParticipantStatus;

    //创建时间
    private Date createdTime;

    //检查项id
    private Integer checkItemId;

    //检查项结果
    private String checkItemResult;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventParticipantStatus() {
        return eventParticipantStatus;
    }

    public void setEventParticipantStatus(Integer eventParticipantStatus) {
        this.eventParticipantStatus = eventParticipantStatus;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getCheckItemId() {
        return checkItemId;
    }

    public void setCheckItemId(Integer checkItemId) {
        this.checkItemId = checkItemId;
    }

    public String getCheckItemResult() {
        return checkItemResult;
    }

    public void setCheckItemResult(String checkItemResult) {
        this.checkItemResult = checkItemResult;
    }

}
