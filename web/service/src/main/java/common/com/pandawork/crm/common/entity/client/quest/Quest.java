package com.pandawork.crm.common.entity.client.quest;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * quest
 * Created by shura
 * Date:  2017/7/24.
 * Time:  11:03
 */
@Entity
@Table(name = "t_quest")
public class Quest extends AbstractEntity{

    //主键id
    @Id
    private Integer id;

    //患者主键
    @Column(name = "client_id")
    private Integer clientId;

    //姓名
    @Transient
    private String clientName;

    //身份证号
    @Transient
    private String idCardNum;

    //电话
    @Transient
    private String tel;

    //累计消费
    @Transient
    private Integer allCost;

    //分组名称
    @Transient
    private String memberGroupName;

    //会员等级
    @Transient
    private Integer isMember;

    //问卷次数
    @Transient
    private Integer questCount;

    //会员组id
    @Column(name = "member_group_id")
    private Integer memberGroupId;

    //问卷记录时间
    @Column(name = "quest_time")
    private Date questTime;

    //本次问卷向数量
    @Column(name = "quest_types")
    private Integer questTypes;

    //下次问卷时间
    @Column(name = "next_quest_time")
    private Date nextQuestTime;

    //是否提醒问卷  0：不提醒   1：提醒
    @Column(name = "is_remindahead")
    private Integer isRemindahead;

    //提前提醒天数
    @Column(name = "remindahead_days")
    private Integer remindaheadDays;

    @Column(name = "comment")
    private String comment;
    // 创建者ID
    @Column(name = "created_party_id")
    private Integer createdPartyId;

    // 创建时间
    @Column(name = "created_time")
    private Date createdTime;

    // 最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getAllCost() {
        return allCost;
    }

    public void setAllCost(Integer allCost) {
        this.allCost = allCost;
    }

    public Integer getMemberGroupId() {
        return memberGroupId;
    }

    public void setMemberGroupId(Integer memberGroupId) {
        this.memberGroupId = memberGroupId;
    }

    public Date getQuestTime() {
        return questTime;
    }

    public void setQuestTime(Date questTime) {
        this.questTime = questTime;
    }

    public Integer getQuestTypes() {
        return questTypes;
    }

    public void setQuestTypes(Integer questTypes) {
        this.questTypes = questTypes;
    }

    public Date getNextQuestTime() {
        return nextQuestTime;
    }

    public void setNextQuestTime(Date nextQuestTime) {
        this.nextQuestTime = nextQuestTime;
    }

    public Integer getIsRemindahead() {
        return isRemindahead;
    }

    public void setIsRemindahead(Integer isRemindahead) {
        this.isRemindahead = isRemindahead;
    }

    public Integer getRemindaheadDays() {
        return remindaheadDays;
    }

    public void setRemindaheadDays(Integer remindaheadDays) {
        this.remindaheadDays = remindaheadDays;
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

    public String getMemberGroupName() {
        return memberGroupName;
    }

    public void setMemberGroupName(String memberGroupName) {
        this.memberGroupName = memberGroupName;
    }

    public Integer getIsMember() {
        return isMember;
    }

    public void setIsMember(Integer isMember) {
        this.isMember = isMember;
    }

    public Integer getQuestCount() {
        return questCount;
    }

    public void setQuestCount(Integer questCount) {
        this.questCount = questCount;
    }
}
