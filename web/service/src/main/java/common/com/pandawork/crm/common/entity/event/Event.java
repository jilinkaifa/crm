package com.pandawork.crm.common.entity.event;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * event
 * Author： wychen
 * Date: 2017/7/20
 * Time: 17:24
 */
@Entity
@Table(name = "t_event")
public class Event extends AbstractEntity{

    //主键
    @Id
    private Integer id;

    //活动名称
    @Column(name = "name")
    private String name;

    //使用的模板id
    @Column(name = "template_id")
    private Integer templateId;

    //标记模板：1是 0否
    @Column(name = "is_marked")
    private Integer isMarked;

    //模板是否被使用：1是 0否
    @Column(name = "is_template_used")
    private Integer isTemplateUsed;

    //是否是单纯的模板：1是 0否
    @Column(name = "is_pure_template")
    private Integer isPureTemplate;

    //活动类型：1会员关怀型 2营销型
    @Column(name = "type")
    private Integer type;

    @Transient
    private String typeValue;

    //参与人员（组）
    @Column(name = "member_group_id")
    private Integer memberGroupId;

    //参与人员（组）名称
    @Transient
    private String memberGroupName;

    //活动级别：1一级 2二级 3三级
    @Column(name = "level")
    private Integer level;

    @Transient
    private String levelValue;

    //提醒时间（单位为天）
    @Column(name = "remind_time")
    private Integer remindTime;

    //活动开始时间
    @Column(name = "start_date")
    private Date startDate;

    //活动结束时间
    @Column(name = "end_date")
    private Date endDate;

    //循环粒度（0指定时间 1年 2月）
    @Column(name = "polling_time")
    private Integer pollingTime;

    //默认第一期开始时间
    @Column(name = "polling_start_date")
    private Date pollingStartTime;

    //默认第一期结束时间
    @Column(name = "polling_end_date")
    private Date pollingEndTime;

    //关联积分：1是 0否
    @Column(name = "is_points_related")
    private Integer isPointsRelated;

    //关联检查项：1是 0否
    @Column(name = "is_checkitem_related")
    private Integer isCheckItemRelated;

    //活动内容
    @Column(name = "content")
    private String content;

    //附件id
    @Column(name = "attachment")
    private Integer attachment;

    //通知类型
    @Column(name = "dic_notice_type")
    private Integer dicNoticeType;

    //通知内容
    @Column(name = "notice_content")
    private String noticeContent;

    //申请时间
    @Column(name = "apply_time")
    private Date applyTime;

    //审批人id
    @Column(name = "approval_id")
    private Integer approvalId;

    //审批状态：1是 0否
    @Column(name = "approval_status")
    private Integer approvalStatus;

    //审批状态值
    @Transient
    private String approvalStatusValue;

    //审批时间
    @Column(name = "approval_time")
    private Date approvalTime;

    //审批备注
    @Column(name = "approval_comment")
    private String approvalComment;

    //是否被注销
    @Column(name = "is_logout")
    private Integer isLogout;

    //注销时间
    @Column(name = "logout_time")
    private Date logoutTime;

    //创建者id
    @Column(name = "created_party_id")
    private Integer createdPartyId;

    //活动创建人姓名
    @Transient
    private String createdPartyName;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //创建日期
    @Transient
    private String date;

    //最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    //活动状态
    @Transient
    private Integer eventStatus;

    //本期开始时间
    @Transient
    private Date BQStartDate;

    @Transient
    private Date BQEndDate;

    @Transient
    private Integer eventTermId;

    //是否被暂停： 1暂停 0不暂停
    @Column(name = "pause")
    private Integer pause;

    @Column(name = "state")
    private Integer state;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getIsMarked() {
        return isMarked;
    }

    public void setIsMarked(Integer isMarked) {
        this.isMarked = isMarked;
    }

    public Integer getIsTemplateUsed() {
        return isTemplateUsed;
    }

    public void setIsTemplateUsed(Integer isTemplateUsed) {
        this.isTemplateUsed = isTemplateUsed;
    }

    public Integer getIsPureTemplate() {
        return isPureTemplate;
    }

    public void setIsPureTemplate(Integer isPureTemplate) {
        this.isPureTemplate = isPureTemplate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Integer remindTime) {
        this.remindTime = remindTime;
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

    public Integer getPollingTime() {
        return pollingTime;
    }

    public void setPollingTime(Integer pollingTime) {
        this.pollingTime = pollingTime;
    }

    public Date getPollingStartTime() {
        return pollingStartTime;
    }

    public void setPollingStartTime(Date pollingStartTime) {
        this.pollingStartTime = pollingStartTime;
    }

    public Date getPollingEndTime() {
        return pollingEndTime;
    }

    public void setPollingEndTime(Date pollingEndTime) {
        this.pollingEndTime = pollingEndTime;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAttachment() {
        return attachment;
    }

    public void setAttachment(Integer attachment) {
        this.attachment = attachment;
    }

    public Integer getDicNoticeType() {
        return dicNoticeType;
    }

    public void setDicNoticeType(Integer dicNoticeType) {
        this.dicNoticeType = dicNoticeType;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Integer approvalId) {
        this.approvalId = approvalId;
    }

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    public Integer getIsLogout() {
        return isLogout;
    }

    public void setIsLogout(Integer isLogout) {
        this.isLogout = isLogout;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
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

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Integer getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(Integer eventStatus) {
        this.eventStatus = eventStatus;
    }

    public Date getBQStartDate() {
        return BQStartDate;
    }

    public void setBQStartDate(Date BQStartDate) {
        this.BQStartDate = BQStartDate;
    }

    public String getApprovalComment() {
        return approvalComment;
    }

    public void setApprovalComment(String approvalComment) {
        this.approvalComment = approvalComment;
    }

    public Integer getPause() {
        return pause;
    }

    public void setPause(Integer pause) {
        this.pause = pause;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getLevelValue() {
        return levelValue;
    }

    public void setLevelValue(String levelValue) {
        this.levelValue = levelValue;
    }

    public String getApprovalStatusValue() {
        return approvalStatusValue;
    }

    public void setApprovalStatusValue(String approvalStatusValue) {
        this.approvalStatusValue = approvalStatusValue;
    }

    public Integer getEventTermId() {
        return eventTermId;
    }

    public void setEventTermId(Integer eventTermId) {
        this.eventTermId = eventTermId;
    }

    public Date getBQEndDate() {
        return BQEndDate;
    }

    public void setBQEndDate(Date BQEndDate) {
        this.BQEndDate = BQEndDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
