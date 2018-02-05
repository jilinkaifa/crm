package com.pandawork.crm.common.entity.event;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 活动记录通知实体
 *
 * @author Flying
 * @date 2017/7/20 17:28
 */
@Entity
@Table(name = "t_event_record_notice")
public class EventRecordNotice extends AbstractEntity {

    @Id
    private Integer id;

    //活动id
    @Column(name = "event_id")
    private Integer eventId;

    //活动开始时间
    @Column(name = "event_start_date")
    private Date eventStartDate;

    //活动结束时间
    @Column(name = "event_end_date")
    private Date eventEndDate;

    //参与组id
    @Column(name = "member_group_id")
    private Integer memberGroupId;

    //患者id
    @Column(name = "client_id")
    private Integer clientId;

    //参与者姓名
    @Column(name = "participant_name")
    private String participantName;

    //参与者身份证号
    @Column(name = "participant_idcard")
    private String participantIdcard;

    //参与者电话
    @Column(name = "participant_tel")
    private String participantTel;

    //通知状态:1未通知 2已通知
    @Column(name = "notice_status")
    private Integer noticeStatus;

    @Transient
    private String noticeStatusValue;

    //备注
    @Column(name = "comment")
    private String comment;

    //通知人id
    @Column(name = "sys_id")
    private Integer sysId;

    //通知人姓名
    @Transient
    private String sysName;

    //通知时间
    @Column(name = "notice_time")
    private Date noticeTime;

    //是否继续通知：1-是；0-否
    @Column(name = "is_continue_notice")
    private Integer isContinueNotice;

    //活动参与次数
    @Column(name = "event_participant_times")
    private Integer eventParticipantTimes;

    //活动参与状态：1-完成； 0-未完成
    @Column(name = "event_participant_status")
    private Integer eventParticipantStatus;

    //活动参与状态
    @Transient
    private String eventParticipantStatusValue;


    //活动通知状态：1-未通知; 2-通知中; 3-执行期; 4-已归档
    @Column(name = "event_status")
    private Integer eventStatus;

    //活动通知状态
    @Transient
    private String eventStatusValue;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    //创建者id
    @Column(name = "created_party_id")
    private Integer createdPartyId;

    //本期活动id
    @Column(name = "event_term_id")
    private Integer eventTermId;

    //本期开始时间
    @Transient
    private Date BQStartDate;

    //累计参与次数
    @Transient
    private Integer totalParticipant;

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

    public Date getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(Date eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public Date getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(Date eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public Integer getMemberGroupId() {
        return memberGroupId;
    }

    public void setMemberGroupId(Integer memberGroupId) {
        this.memberGroupId = memberGroupId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getParticipantIdcard() {
        return participantIdcard;
    }

    public void setParticipantIdcard(String participantIdcard) {
        this.participantIdcard = participantIdcard;
    }

    public String getParticipantTel() {
        return participantTel;
    }

    public void setParticipantTel(String participantTel) {
        this.participantTel = participantTel;
    }

    public Integer getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(Integer noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getSysId() {
        return sysId;
    }

    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }

    public Date getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }

    public Integer getIsContinueNotice() {
        return isContinueNotice;
    }

    public void setIsContinueNotice(Integer isContinueNotice) {
        this.isContinueNotice = isContinueNotice;
    }

    public Integer getEventParticipantTimes() {
        return eventParticipantTimes;
    }

    public void setEventParticipantTimes(Integer eventParticipantTimes) {
        this.eventParticipantTimes = eventParticipantTimes;
    }

    public Integer getEventParticipantStatus() {
        return eventParticipantStatus;
    }

    public void setEventParticipantStatus(Integer eventParticipantStatus) {
        this.eventParticipantStatus = eventParticipantStatus;
    }

    public Integer getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(Integer eventStatus) {
        this.eventStatus = eventStatus;
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

    public Integer getCreatedPartyId() {
        return createdPartyId;
    }

    public void setCreatedPartyId(Integer createdPartyId) {
        this.createdPartyId = createdPartyId;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public Integer getEventTermId() {
        return eventTermId;
    }

    public void setEventTermId(Integer eventTermId) {
        this.eventTermId = eventTermId;
    }

    public String getNoticeStatusValue() {
        return noticeStatusValue;
    }

    public void setNoticeStatusValue(String noticeStatusValue) {
        this.noticeStatusValue = noticeStatusValue;
    }

    public String getEventParticipantStatusValue() {
        return eventParticipantStatusValue;
    }

    public void setEventParticipantStatusValue(String eventParticipantStatusValue) {
        this.eventParticipantStatusValue = eventParticipantStatusValue;
    }

    public String getEventStatusValue() {
        return eventStatusValue;
    }

    public void setEventStatusValue(String eventStatusValue) {
        this.eventStatusValue = eventStatusValue;
    }

    public Date getBQStartDate() {
        return BQStartDate;
    }

    public void setBQStartDate(Date BQStartDate) {
        this.BQStartDate = BQStartDate;
    }

    public Integer getTotalParticipant() {
        return totalParticipant;
    }

    public void setTotalParticipant(Integer totalParticipant) {
        this.totalParticipant = totalParticipant;
    }
}
