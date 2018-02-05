package com.pandawork.crm.common.dto.client.quest;

import java.util.Date;

/**
 * QuestItemSearchDto
 * Created by shura
 * Date:  2017/7/26.
 * Time:  11:16
 */
public class QuestItemSearchDto {

    //问卷id
    private Integer questId;

    //患者id
    private Integer clientId;

    //问卷项id
    private Integer questItemId;

    //记录开始时间
    private Date startDate;

    //记录结束时间
    private Date endDate;

    // 页码
    private Integer page;

    // 分页大小
    private Integer pageSize;

    // 偏移量
    private Integer offset;

    public Integer getQuestId() {
        return questId;
    }

    public void setQuestId(Integer questId) {
        this.questId = questId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getQuestItemId() {
        return questItemId;
    }

    public void setQuestItemId(Integer questItemId) {
        this.questItemId = questItemId;
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
