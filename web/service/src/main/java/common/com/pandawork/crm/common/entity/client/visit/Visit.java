package com.pandawork.crm.common.entity.client.visit;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Visit
 * Author： liping
 * Date: 2017/7/20
 * Time: 8:42
 */
@Entity
@Table(name = "t_visit")
public class Visit extends AbstractEntity{

    //来访id
    @Id
    private Integer id;

    //来访患者id
    @Column(name = "client_id")
    private Integer clientId;

    //HIS就诊流水号
    @Column(name = "his_id")
    private String hisId;

    @Transient
    private Integer gender;

    @Transient
    private String genderName;

    //选择来访人员
    @Column(name = "client_name")
    private String clientName;

    //身份证编号:同步自患者信息
    @Column(name = "client_idcard_num")
    private String clientIdcardNum;

    //电话:来访时同步自患者信息
    @Column(name = "client_tel")
    private String clientTel;

    //用户类型:来访时同步自患者信息
    @Column(name = "client_type")
    private String clientType;

    @Transient
    private Integer dicType;

    @Transient
    private String typeName;

    //来访时间
    @Column(name = "visit_time")
    private Date visitTime;

    //首诊门诊,首诊住院
    @Column(name = "first_visit_type")
    private String firstVisitType;

    //来访来源:门诊,住院
    @Column(name = "visit_from")
    private Integer visitFrom;

    //来访来源（0,1）
    @Transient
    private String visitFromNum;

    //来访内容
    @Column(name = "visit_content")
    private String visitContent;

    //存储活动记录编号
    @Column(name = "event_related")
    private Integer eventRelated;

    //诊断
    @Column(name = "diagnose_type")
    private String diagnoseType;

    //医嘱
    @Column(name = "doctor_orders")
    private String doctorOrders;

    //消费金额，单位元
    @Column(name = "cost")
    private BigDecimal cost;

    //创建者partyId
    @Column(name = "created_party_id")
    private Integer createdPartyId;

    @Transient
    private String partyName;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    //来访次数
    @Transient
    private Integer visitTimes;

    //删除状态（0可用，1不可用）
    @Column(name = "deleted")
    private Integer deleted;

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

    public String getHisId() {
        return hisId;
    }

    public void setHisId(String hisId) {
        this.hisId = hisId;
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

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getFirstVisitType() {
        return firstVisitType;
    }

    public void setFirstVisitType(String firstVisitType) {
        this.firstVisitType = firstVisitType;
    }

    public Integer getVisitFrom() {
        return visitFrom;
    }

    public void setVisitFrom(Integer visitFrom) {
        this.visitFrom = visitFrom;
    }

    public String getVisitContent() {
        return visitContent;
    }

    public void setVisitContent(String visitContent) {
        this.visitContent = visitContent;
    }

    public Integer getEventRelated() {
        return eventRelated;
    }

    public void setEventRelated(Integer eventRelated) {
        this.eventRelated = eventRelated;
    }

    public String getDiagnoseType() {
        return diagnoseType;
    }

    public void setDiagnoseType(String diagnoseType) {
        this.diagnoseType = diagnoseType;
    }

    public String getDoctorOrders() {
        return doctorOrders;
    }

    public void setDoctorOrders(String doctorOrders) {
        this.doctorOrders = doctorOrders;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getVisitTimes() {
        return visitTimes;
    }

    public void setVisitTimes(Integer visitTimes) {
        this.visitTimes = visitTimes;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public Integer getDicType() {
        return dicType;
    }

    public void setDicType(Integer dicType) {
        this.dicType = dicType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getVisitFromNum() {
        return visitFromNum;
    }

    public void setVisitFromNum(String visitFromNum) {
        this.visitFromNum = visitFromNum;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
