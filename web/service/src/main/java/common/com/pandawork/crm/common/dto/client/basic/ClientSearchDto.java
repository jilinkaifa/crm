package com.pandawork.crm.common.dto.client.basic;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ClientSearchDto
 * Created by shura
 * Date:  2017/7/20.
 * Time:  16:08
 */
public class ClientSearchDto {

    //患者姓名
    private String name;

    //身份证号
    private String idCardNum;

    //电话
    private String tel;

    //患者类型
    private Integer dicType;

    //是否会员
    private Integer isMember;

    //医保类型
    private Integer dicMCIType;

    //会员组id
    private Integer memberGroupId;

    //消费金额大于
    private BigDecimal allCost;

    //当前日期
    private Date startDate;

    //一个月后日期
    private Date endDate;

    // 页码
    private Integer page;

    // 分页大小
    private Integer pageSize;

    // 偏移量
    private Integer offset;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getDicType() {
        return dicType;
    }

    public void setDicType(Integer dicType) {
        this.dicType = dicType;
    }

    public Integer getDicMCIType() {
        return dicMCIType;
    }

    public void setDicMCIType(Integer dicMCIType) {
        this.dicMCIType = dicMCIType;
    }

    public BigDecimal getAllCost() {
        return allCost;
    }

    public void setAllCost(BigDecimal allCost) {
        this.allCost = allCost;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer pageNo) {
        this.page = pageNo;
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

    public Integer getIsMember() {
        return isMember;
    }

    public void setIsMember(Integer isMember) {
        this.isMember = isMember;
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

    public Integer getMemberGroupId() {
        return memberGroupId;
    }

    public void setMemberGroupId(Integer memberGroupId) {
        this.memberGroupId = memberGroupId;
    }
}
