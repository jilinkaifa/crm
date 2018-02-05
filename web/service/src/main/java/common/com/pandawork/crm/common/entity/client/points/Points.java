package com.pandawork.crm.common.entity.client.points;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Points
 * Author： liping
 * Date: 2017/7/19
 * Time: 9:57
 */
@Entity
@Table(name = "t_member_points")
public class Points extends AbstractEntity{

    //积分记录id
    @Id
    private Integer id;

    //调整前积分
    @Column(name = "last_points")
    private Integer lastPoints;

    //积分日期
    @Column(name = "last_visit_date")
    private Date lastVisitDate;

    //来访者id
    @Column(name = "visit_id")
    private Integer visitId;

    //活动id
    @Column(name = "event_id")
    private Integer eventId;

    //患者id
    @Column(name = "client_id")
    private Integer clientId;

    //积分项id
    @Column(name = "points_item_id")
    private Integer pointsItemId;

    //积分项调整额
    @Column(name = "points_size")
    private Integer pointsSize;

    //积分说明
    @Column(name = "points_text")
    private String pointsText;

    //积分来源
    @Column(name = "points_from")
    private Integer pointsFrom;

    //积分来源
    @Transient
    private String pointsFrom1;

    //调整后积分
    @Column(name = "current_points")
    private Integer currentPoints;

    //调整后累计积分
    @Column(name = "current_sumpoints")
    private Integer currentSumpoints;

    //调整时间
    @Column(name = "points_date")
    private Date pointsDate;

    //创建者id
    @Column(name = "created_party_id")
    private Integer createdPartyId;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    //client表姓名
    @Transient
    private String clientName;

    //client表身份证号
    @Transient
    private String clientIdcardNum;

    //client表电话
    @Transient
    private String clientTel;

    //消费次数
    @Transient
    private Integer times;

    //累积消费金额
    @Transient
    private double allCost;

    //关联活动名称
    @Transient
    private String eventName;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLastPoints() {
        return lastPoints;
    }

    public void setLastPoints(Integer lastPoints) {
        this.lastPoints = lastPoints;
    }

    public Date getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(Date lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public Integer getVisitId() {
        return visitId;
    }

    public void setVisitId(Integer visitId) {
        this.visitId = visitId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getPointsItemId() {
        return pointsItemId;
    }

    public void setPointsItemId(Integer pointsItemId) {
        this.pointsItemId = pointsItemId;
    }

    public Integer getPointsSize() {
        return pointsSize;
    }

    public void setPointsSize(Integer pointsSize) {
        this.pointsSize = pointsSize;
    }

    public String getPointsText() {
        return pointsText;
    }

    public void setPointsText(String pointsText) {
        this.pointsText = pointsText;
    }

    public Integer getPointsFrom() {
        return pointsFrom;
    }

    public void setPointsFrom(Integer pointsFrom) {
        this.pointsFrom = pointsFrom;
    }

    public Integer getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(Integer currentPoints) {
        this.currentPoints = currentPoints;
    }

    public Integer getCurrentSumpoints() {
        return currentSumpoints;
    }

    public void setCurrentSumpoints(Integer currentSumpoints) {
        this.currentSumpoints = currentSumpoints;
    }

    public Date getPointsDate() {
        return pointsDate;
    }

    public void setPointsDate(Date pointsDate) {
        this.pointsDate = pointsDate;
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

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public double getAllCost() {
        return allCost;
    }

    public void setAllCost(double allCost) {
        this.allCost = allCost;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getPointsFrom1() {
        return pointsFrom1;
    }

    public void setPointsFrom1(String pointsFrom1) {
        this.pointsFrom1 = pointsFrom1;
    }
}
