package com.pandawork.crm.common.entity.client.basic;

import com.pandawork.core.common.entity.AbstractEntity;
import javax.persistence.*;
import java.util.Date;
import java.math.BigDecimal;

/**
 * Created by shura on 2017/7/19.
 */
@Entity
@Table(name = "t_client")
public class Client extends AbstractEntity{

    //主键
    @Id
    private Integer id;

    //姓名
    @Column(name = "name")
    private String name;

    //患者类型 字典值
    @Column(name = "dic_type")
    private Integer dicType;

    @Transient
    private String dicTypeName;

    //性别 枚举
    @Column(name = "gender")
    private Integer gender;

    @Transient
    private String genderName;

    //联系方式
    @Column(name = "tel")
    private String tel;

    //身份证号
    @Column(name = "idcard_num")
    private String idCardNum;

    //年龄
    @Transient
    private Integer age;

    //医保类型  字典值
    @Column(name = "dic_mci_type")
    private Integer dicMCIType;

    @Transient
    private String dicMCITypeName;

    //各样医保类型数量
    @Transient
    private Integer dicMCITypeCount;

    //医保卡号
    @Column(name = "mci_num")
    private String MCINum;

    //第二联系人姓名
    @Column(name = "sec_contact")
    private String secContact;

    //第二联系人关系 字典值
    @Column(name = "dic_sec_relation")
    private Integer dicSecRelation;

    //第二联系人关系名称
    @Transient
    private String dicSecRelationName;

    //第二联系人电话  最多填写三个
    @Column(name = "sec_tel")
    private String secTel;

    //工作单位
    @Column(name = "company")
    private String company;

    //诊断类型
    @Column(name = "diagnose_type")
    private String diagnoseType;

    //身高
    @Column(name =  "height")
    private BigDecimal height;

    //体重
    @Column(name = "weight")
    private BigDecimal weight;

    //民族 字典值
    @Column(name = "dic_nation")
    private Integer dicNation;

    //民族名称
    @Transient
    private String dicNationName;

    //地址
    @Column(name = "address")
    private String address;

    //是否会员：1 是  0  否
    @Column(name = "is_member")
    private Integer isMember;

    //会员分组id
    @Column(name = "member_group_id")
    private Integer memberGroupId;

    @Transient
    private String memberGroupName;

    //会员状态 ：0 在用  1 锁定
    @Column(name = "member_status")
    private Integer memberStatus;

    //会员截止日期
    @Column(name = "member_deadline")
    private Date memberDeadline;

    //会员推荐人
    @Column(name = "referrer")
    private String referrer;

    //当前积分
    @Column(name = "member_points")
    private Integer memberPoints;

    //累计积分
    @Column(name = "all_points")
    private Integer allPoints;

    //最高消费金额
    @Transient
    private BigDecimal maxCost;

    //累计消费
    @Column(name = "all_cost")
    private BigDecimal allCost;

    //住院情况
    @Column(name = "hospitalization")
    private String hospitalization;

    //问卷次数
    @Column(name = "quest_count")
    private Integer questCount;

    //档案编号
    @Column(name = "record_id")
    private String recordId;

    //建档日期
    @Column(name = "record_date")
    private Date recordDate;

    //是否删除 0：未删除 1：删除
    @Column(name = "deleted")
    private Integer deleted;

    // 创建者ID
    @Column(name = "created_party_id")
    private Integer createdPartyId;

    // 创建时间
    @Column(name = "created_time")
    private Date createdTime;

    // 最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    //各类型患者数量(普通用户、会员用户)
    @Transient
    private Integer isMemberCount;

    //各类型患者名称(普通用户、会员用户)
    @Transient
    private String isMemberName;

    //是否定点单位
    @Column(name = "is_fixed")
    private Integer isFixed;

    public BigDecimal getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(BigDecimal maxCost) {
        this.maxCost = maxCost;
    }

    public Integer getDicMCITypeCount() {return dicMCITypeCount;}

    public void setDicMCITypeCount(Integer dicMCITypeCount) {this.dicMCITypeCount = dicMCITypeCount;}

    public String getIsMemberName() {return isMemberName;}

    public void setIsMemberName(String isMemberName) {this.isMemberName = isMemberName;}

    public Integer getIsMemberCount() {return isMemberCount;}

    public void setIsMemberCount(Integer isMemberCount) {this.isMemberCount = isMemberCount;}

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

    public Integer getDicType() {
        return dicType;
    }

    public void setDicType(Integer dicType) {
        this.dicType = dicType;
    }

    public String getDicTypeName() {
        return dicTypeName;
    }

    public void setDicTypeName(String dicTypeName) {
        this.dicTypeName = dicTypeName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public Integer getDicMCIType() {
        return dicMCIType;
    }

    public void setDicMCIType(Integer dicMCIType) {
        this.dicMCIType = dicMCIType;
    }

    public String getDicMCITypeName() {
        return dicMCITypeName;
    }

    public void setDicMCITypeName(String dicMCITypeName) {
        this.dicMCITypeName = dicMCITypeName;
    }

    public String getMCINum() {
        return MCINum;
    }

    public void setMCINum(String MCINum) {
        this.MCINum = MCINum;
    }

    public String getSecContact() {
        return secContact;
    }

    public void setSecContact(String secContact) {
        this.secContact = secContact;
    }

    public Integer getDicSecRelation() {
        return dicSecRelation;
    }

    public void setDicSecRelation(Integer dicSecRelation) {
        this.dicSecRelation = dicSecRelation;
    }

    public String getDicSecRelationName() {
        return dicSecRelationName;
    }

    public void setDicSecRelationName(String dicSecRelationName) {
        this.dicSecRelationName = dicSecRelationName;
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

    public String getDicNationName() {
        return dicNationName;
    }

    public void setDicNationName(String dicNationName) {
        this.dicNationName = dicNationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIsMember() {
        return isMember;
    }

    public void setIsMember(Integer isMember) {
        this.isMember = isMember;
    }

    public Integer getMemberGroupId() {
        return memberGroupId;
    }

    public void setMemberGroupId(Integer memberGroupId) {
        this.memberGroupId = memberGroupId;
    }

    public Integer getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(Integer memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Date getMemberDeadline() {
        return memberDeadline;
    }

    public void setMemberDeadline(Date memberDeadline) {
        this.memberDeadline = memberDeadline;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
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

    public BigDecimal getAllCost() {
        return allCost;
    }

    public void setAllCost(BigDecimal allCost) {
        this.allCost = allCost;
    }

    public Integer getQuestCount() {
        return questCount;
    }

    public void setQuestCount(Integer questCount) {
        this.questCount = questCount;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public String getHospitalization() {
        return hospitalization;
    }

    public void setHospitalization(String hospitalization) {
        this.hospitalization = hospitalization;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
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

    public Integer getAge() {return age;}

    public void setAge(Integer age) {this.age = age;}

    public Integer getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(Integer isFixed) {
        this.isFixed = isFixed;
    }
}
