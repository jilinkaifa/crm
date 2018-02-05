package com.pandawork.crm.common.dto.client.points;

import java.util.Date;

/**
 * PointsSearchDto
 * Created by shura
 * Date:  2017/7/29.
 * Time:  8:55
 */
public class PointsSearchDto {

    //姓名
    private String clientName;

    //身份证号
    private String clientIdcardNum;

    //电话
    private String clientTel;

    //会员组id
    private Integer memberGroupId;

    //积分类型 积分来源
    private Integer pointsFrom;

    //积分项积分值
    private Integer pointsItemValue;

    //关联活动id
    private Integer eventId;

    //开始日期
    private Date startDate;

    //结束日期
    private Date endDate;

    // 分页大小
    private Integer pageSize;

    // 偏移量
    private Integer offset;

    //第几页
    private Integer page;

    //患者id
    private Integer clientId;

    //消费积分从
    private Integer pointsSize;

    //消费积分到
    private Integer pointsSize2;

    //积分关联活动名称
    private String eventName;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientIdcardNum() {
        return clientIdcardNum;
    }

    public void setClientIdcardNum(String clientIdcardNum) {
        this.clientIdcardNum = clientIdcardNum;
    }

    public String getClientTel() {
        return clientTel;
    }

    public void setClientTel(String clientTel) {
        this.clientTel = clientTel;
    }

    public Integer getPointsFrom() {
        return pointsFrom;
    }

    public void setPointsFrom(Integer pointsFrom) {
        this.pointsFrom = pointsFrom;
    }

    public Integer getPointsItemValue() {
        return pointsItemValue;
    }

    public void setPointsItemValue(Integer pointsItemValue) {
        this.pointsItemValue = pointsItemValue;
    }

    public Integer getMemberGroupId() {
        return memberGroupId;
    }

    public void setMemberGroupId(Integer memberGroupId) {
        this.memberGroupId = memberGroupId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getPointsSize() {
        return pointsSize;
    }

    public void setPointsSize(Integer pointsSize) {
        this.pointsSize = pointsSize;
    }

    public Integer getPointsSize2() {
        return pointsSize2;
    }

    public void setPointsSize2(Integer pointsSize2) {
        this.pointsSize2 = pointsSize2;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
