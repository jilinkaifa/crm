package com.pandawork.crm.common.dto.event.processing;

import java.util.Date;

/**
 * CountParticipant
 * Author： wychen
 * Date: 2017/7/30
 * Time: 20:30
 */
public class CountParticipantDto {

    //id
    private Integer id;

    //活动记录通知Id
    private Integer eventRecordNoticeId;

    //参与者姓名
    private String name;

    //本期开始日期
    private Date  BQStartDate;

    //内容
    private String content;

    //备注
    private String comment;

    //操作人id
    private Integer createdPartyId;

    //操作人名称
    private String createdPartyName;

    //操作时间
    private Date createdTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventRecordNoticeId() {
        return eventRecordNoticeId;
    }

    public void setEventRecordNoticeId(Integer eventRecordNoticeId) {
        this.eventRecordNoticeId = eventRecordNoticeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBQStartDate() {
        return BQStartDate;
    }

    public void setBQStartDate(Date BQStartDate) {
        this.BQStartDate = BQStartDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCreatedPartyId() {
        return createdPartyId;
    }

    public void setCreatedPartyId(Integer createdPartyId) {
        this.createdPartyId = createdPartyId;
    }

    public String getCreatedPartyName() {
        return createdPartyName;
    }

    public void setCreatedPartyName(String createdPartyName) {
        this.createdPartyName = createdPartyName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
