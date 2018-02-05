package com.pandawork.crm.common.entity.client.visit;

import com.pandawork.core.common.entity.AbstractEntity;
import org.omg.PortableInterceptor.INACTIVE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * VisitItem
 * Author： liping
 * Date: 2017/7/20
 * Time: 9:04
 */
@Entity
@Table(name = "t_visit_item")
public class VisitItem extends AbstractEntity{

    //来访明细id
    @Id
    private Integer id;

    //来访id
    @Column(name = "visit_id")
    private Integer visitId;

    //账单大类
    @Column(name = "dic_bill_big_type")
    private Integer dicBillBigType;

    //账单子类
    @Column(name = "dic_bill_small_type")
    private Integer dicBillSmallType;

    //发票大类
    @Column(name = "dic_receipt_big_type")
    private Integer dicReceiptBigType;

    //发票子类
    @Column(name = "dic_receipt_small_type")
    private Integer dicReceiptSmallType;

    //明细
    @Column(name = "detail")
    private String detail;

    //消费金额
    @Column(name = "cost")
    private Double cost;

    //说明
    @Column(name = "content")
    private String content;

    //关联活动id
    @Column(name = "event_id")
    private Integer eventId;

    //创建者id
    @Column(name = "created_party_id")
    private Integer createdPartyId;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVisitId() {
        return visitId;
    }

    public void setVisitId(Integer visitId) {
        this.visitId = visitId;
    }

    public Integer getDicBillBigType() {
        return dicBillBigType;
    }

    public void setDicBillBigType(Integer dicBillBigType) {
        this.dicBillBigType = dicBillBigType;
    }

    public Integer getDicBillSmallType() {
        return dicBillSmallType;
    }

    public void setDicBillSmallType(Integer dicBillSmallType) {
        this.dicBillSmallType = dicBillSmallType;
    }

    public Integer getDicReceiptBigType() {
        return dicReceiptBigType;
    }

    public void setDicReceiptBigType(Integer dicReceiptBigType) {
        this.dicReceiptBigType = dicReceiptBigType;
    }

    public Integer getDicReceiptSmallType() {
        return dicReceiptSmallType;
    }

    public void setDicReceiptSmallType(Integer dicReceiptSmallType) {
        this.dicReceiptSmallType = dicReceiptSmallType;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
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
}
