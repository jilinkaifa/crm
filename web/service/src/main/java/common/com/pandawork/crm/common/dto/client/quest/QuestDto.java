package com.pandawork.crm.common.dto.client.quest;

import com.pandawork.crm.common.entity.client.quest.Quest;
import com.pandawork.crm.common.entity.client.quest.QuestItem;

import java.util.Date;
import java.util.List;

/**
 * QuestDto
 * Created by shura
 * Date:  2017/7/27.
 * Time:  9:40
 */
public class QuestDto {

    private Integer clientId;

    //是否提醒
    private Integer isRemindahead;

    //提前提醒天数
    private Integer remindaheadDays;

    //备注
    private String comment;

    //下次问卷时间
    private Date nextQuestTime;

    //开始记录日期
    private Date startDate;

    //结束记录日期
    private Date endDate;

    //创建者Id
    private Integer createdPartyId;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    //问卷详情 问卷项
    private List<QuestItem> questItemList;

    public List<QuestItem> getQuestItemList() {
        return questItemList;
    }

    public void setQuestItemList(List<QuestItem> questItemList) {
        this.questItemList = questItemList;
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

    public Date getNextQuestTime() {
        return nextQuestTime;
    }

    public void setNextQuestTime(Date nextQuestTime) {
        this.nextQuestTime = nextQuestTime;
    }

    public Integer getCreatedPartyId() {
        return createdPartyId;
    }

    public void setCreatedPartyId(Integer createdPartyId) {
        this.createdPartyId = createdPartyId;
    }
}
