package com.pandawork.crm.common.entity.client.quest;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * QuestItem
 * Created by shura
 * Date:  2017/7/24.
 * Time:  11:04
 */
@Entity
@Table(name = "t_quest_item")
public class QuestItem extends AbstractEntity {

    //主键id
    @Id
    private Integer id;

    //问卷id
    @Column(name = "quest_id")
    private Integer questId;

    //问卷项id 字典值
    @Column(name = "dic_quest_item")
    private Integer dicQuestItem;

    //问卷项名称
    @Transient
    private String dicQuestItemName;

    //问卷内容
    @Column(name = "quest_content")
    private String questContent;

    //开始记录日期
    @Column(name = "start_date")
    private Date startDate;

    //结束记录日期
    @Column(name = "end_date")
    private Date endDate;

    //药剂名称
    @Column(name = "dosage_name")
    private String dosageName;

    //药剂量
    @Column(name = "dosage_quantity")
    private BigDecimal dosageQuantity;

    //单位
    @Column(name = "dic_dosage_unit")
    private Integer dicDosageUnit;

    //单位名称
    @Transient
    private String dicDosageUnitName;

    //每日次数
    @Column(name = "dosage_num")
    private  Integer dosageNum;

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

    public Integer getQuestId() {
        return questId;
    }

    public void setQuestId(Integer questId) {
        this.questId = questId;
    }

    public Integer getDicQuestItem() {
        return dicQuestItem;
    }

    public void setDicQuestItem(Integer dicQuestItem) {
        this.dicQuestItem = dicQuestItem;
    }

    public String getDicQuestItemName() {
        return dicQuestItemName;
    }

    public void setDicQuestItemName(String dicQuestItemName) {
        this.dicQuestItemName = dicQuestItemName;
    }

    public String getQuestContent() {
        return questContent;
    }

    public void setQuestContent(String questContent) {
        this.questContent = questContent;
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

    public String getDosageName() {
        return dosageName;
    }

    public void setDosageName(String dosageName) {
        this.dosageName = dosageName;
    }

    public BigDecimal getDosageQuantity() {
        return dosageQuantity;
    }

    public void setDosageQuantity(BigDecimal dosageQuantity) {
        this.dosageQuantity = dosageQuantity;
    }

    public Integer getDicDosageUnit() {
        return dicDosageUnit;
    }

    public void setDicDosageUnit(Integer dicDosageUnit) {
        this.dicDosageUnit = dicDosageUnit;
    }

    public String getDicDosageUnitName() {
        return dicDosageUnitName;
    }

    public void setDicDosageUnitName(String dicDosageUnitName) {
        this.dicDosageUnitName = dicDosageUnitName;
    }

    public Integer getDosageNum() {
        return dosageNum;
    }

    public void setDosageNum(Integer dosageNum) {
        this.dosageNum = dosageNum;
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
