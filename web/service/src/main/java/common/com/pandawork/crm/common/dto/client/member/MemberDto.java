package com.pandawork.crm.common.dto.client.member;

import java.math.BigDecimal;
import java.util.Date;

/**
 * MemberDto
 * Created by shura
 * Date:  2017/7/31.
 * Time:  11:09
 */
public class MemberDto {

    //患者姓名
    private String name;

    //性别
    private Integer gender;

    //联系方式
    private String tel;

    //第二联系人姓名
    private String secContact;

    //医保类型  字典值
    private Integer dicMCIType;

    //医保卡号
    private String MCINum;

    //第二联系人关系 字典值
    private Integer dicSecRelation;

    //第二联系人电话  最多填写三个
    private String secTel;

    //工作单位
    private String company;

    //诊断类型
    private String diagnoseType;

    //身高
    private BigDecimal height;

    //体重
    private BigDecimal weight;

    //民族 字典值
    private Integer dicNation;

    //住院情况
    private String hospitalization;

    //地址
    private String address;

    //会员id
    private Integer id;

    //档案编号
    private String recordId;

    //档案编号
    private Date recordDate;

    //会员状态
    private Integer memberStatus;

    //会员截止日期
    private Date deadLine;

    //会员分组
    private Integer memberGroupId;

    //会员级别
    private Integer level;

    //当前积分
    private Integer memberPoints;

    //是否定点单位 0：是     1：否
    private Integer isFixed;

    //累计积分
    private Integer allPoints;

    //下次问卷时间
    private Date nextQuestTime;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(Integer memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public Integer getMemberGroupId() {
        return memberGroupId;
    }

    public void setMemberGroupId(Integer memberGroupId) {
        this.memberGroupId = memberGroupId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getNextQuestTime() {
        return nextQuestTime;
    }

    public void setNextQuestTime(Date nextQuestTime) {
        this.nextQuestTime = nextQuestTime;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSecContact() {
        return secContact;
    }

    public void setSecContact(String secContact) {
        this.secContact = secContact;
    }

    public Integer getDicMCIType() {
        return dicMCIType;
    }

    public void setDicMCIType(Integer dicMCIType) {
        this.dicMCIType = dicMCIType;
    }

    public String getMCINum() {
        return MCINum;
    }

    public void setMCINum(String MCINum) {
        this.MCINum = MCINum;
    }

    public Integer getDicSecRelation() {
        return dicSecRelation;
    }

    public void setDicSecRelation(Integer dicSecRelation) {
        this.dicSecRelation = dicSecRelation;
    }

    public String getHospitalization() {
        return hospitalization;
    }

    public void setHospitalization(String hospitalization) {
        this.hospitalization = hospitalization;
    }

    public String getSecTel() {
        return secTel;
    }

    public void setSecTel(String secTel) {
        this.secTel = secTel;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDiagnoseType() {
        return diagnoseType;
    }

    public void setDiagnoseType(String diagnoseType) {
        this.diagnoseType = diagnoseType;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getDicNation() {
        return dicNation;
    }

    public void setDicNation(Integer dicNation) {
        this.dicNation = dicNation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getMemberPoints() {
        return memberPoints;
    }

    public void setMemberPoints(Integer memberPoints) {
        this.memberPoints = memberPoints;
    }

    public Integer getAllPoints() {
        return allPoints;
    }

    public void setAllPoints(Integer allPoints) {
        this.allPoints = allPoints;
    }

    public Integer getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(Integer isFixed) {
        this.isFixed = isFixed;
    }
}
