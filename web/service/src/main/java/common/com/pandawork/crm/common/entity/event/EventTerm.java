package com.pandawork.crm.common.entity.event;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * EventTerm
 * Author： wychen
 * Date: 2017/7/29
 * Time: 9:32
 */
@Entity
@Table(name = "t_event_term")
public class EventTerm  extends AbstractEntity {

    //主键
    @Id
    private Integer id;

    //对应活动id
    @Column(name = "event_id")
    private Integer eventId;

    //活动名称
    @Transient
    private String name;

    //开始日期
    @Column(name = "start_date")
    private Date startDate;

    //结束日期
    @Column(name = "end_date")
    private Date endDate;

    //状态：1通知期 2执行期 3已归档 4暂停 5注销
    @Column(name = "status")
    private Integer status;

    @Transient
    private String statusValue;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    //活动级别
    @Transient
    private Integer level;

    @Transient
    private String levelValue;

    //活动类型
    @Transient
    private Integer type;

    @Transient
    private String typeValue;

    //活动人员组id
    @Column(name = "member_group_id")
    private Integer memberGroupId;

    //活动人员组名称
    @Transient
    private String memberGroupName;

    //活动结束日期
    @Transient
    private Date eventEndDate;

    //待通知人数
    @Transient
    private Integer toBeNoticedPerson;

    //待完成人数
    @Transient
    private Integer toBeFinishedPerson;

    //活动创建人id
    @Transient
    private Integer createdPartyId;

    //活动创建人姓名
    @Transient
    private String createdPartyName;

    //活动创建日期
    @Transient
    private Date eventCreatedTime;

    //通知内容
    @Column(name = "notice_content")
    private String noticeContent;

    //通知时间（单位为天）
    @Transient
    private Integer remindTime;

    //活动开始日期
    @Transient
    private Date eventStartDate;

    //循环粒度: 0指定时间 1年 2月
    @Transient
    private Integer pollingTime;

    //是否关联积分项
    @Transient
    private Integer isPointsRelated;

    //是否关联检查项
    @Transient
    private Integer isCheckItemRelated;

    //活动具体内容
    @Transient
    private String content;

    //暂停状态：0不设置暂停 1暂停下一期 2暂停所有
    @Column(name = "pause_status")
    private Integer pauseStatus;

    @Column(name = "polling_date")
    private Date pollingDate;

    //所在期数
    @Column(name = "periods_num")
    private Integer periodsNum;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelValue() {
        return levelValue;
    }

    public void setLevelValue(String levelValue) {
        this.levelValue = levelValue;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public Integer getMemberGroupId() {
        return memberGroupId;
    }

    public void setMemberGroupId(Integer memberGroupId) {
        this.memberGroupId = memberGroupId;
    }

    public String getMemberGroupName() {
        return memberGroupName;
    }

    public void setMemberGroupName(String memberGroupName) {
        this.memberGroupName = memberGroupName;
    }

    public Date getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(Date eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public Integer getToBeNoticedPerson() {
        return toBeNoticedPerson;
    }

    public void setToBeNoticedPerson(Integer toBeNoticedPerson) {
        this.toBeNoticedPerson = toBeNoticedPerson;
    }

    public Integer getToBeFinishedPerson() {
        return toBeFinishedPerson;
    }

    public void setToBeFinishedPerson(Integer toBeFinishedPerson) {
        this.toBeFinishedPerson = toBeFinishedPerson;
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

    public Date getEventCreatedTime() {
        return eventCreatedTime;
    }

    public void setEventCreatedTime(Date eventCreatedTime) {
        this.eventCreatedTime = eventCreatedTime;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Integer getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Integer remindTime) {
        this.remindTime = remindTime;
    }

    public Date getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(Date eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public Integer getPollingTime() {
        return pollingTime;
    }

    public void setPollingTime(Integer pollingTime) {
        this.pollingTime = pollingTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsPointsRelated() {
        return isPointsRelated;
    }

    public void setIsPointsRelated(Integer isPointsRelated) {
        this.isPointsRelated = isPointsRelated;
    }

    public Integer getIsCheckItemRelated() {
        return isCheckItemRelated;
    }

    public void setIsCheckItemRelated(Integer isCheckItemRelated) {
        this.isCheckItemRelated = isCheckItemRelated;
    }

    public Integer getPauseStatus() {
        return pauseStatus;
    }

    public void setPauseStatus(Integer pauseStatus) {
        this.pauseStatus = pauseStatus;
    }

    public Date getPollingDate() {
        return pollingDate;
    }

    public void setPollingDate(Date pollingDate) {
        this.pollingDate = pollingDate;
    }

    public Integer getPeriodsNum() {
        return periodsNum;
    }

    public void setPeriodsNum(Integer periodsNum) {
        this.periodsNum = periodsNum;
    }
}
