package com.pandawork.crm.common.dto.event;

import org.apache.commons.fileupload.util.LimitedInputStream;

import java.util.List;

/**
 * EventSearchDto
 * 活动查询的Dto
 *
 * @author Flying
 * @date 2017/7/26 8:41
 */
public class EventSearchDto {

    //活动名称
    private String name;

    //活动类型
    private Integer type;

    //审批状态
    private Integer approvalStatus;

    //活动级别
    private Integer level;

    //创建者姓名
    private String createdPartyName;

    //创建者身份证号
    private String idcardNum;

    //创建者的partyIdList
    private List<Integer> partyIdList;

    // 页码
    private Integer pageNo;

    // 分页size
    private Integer pageSize;

    // 偏移量
    private Integer offset;

    //活动状态
    private Integer status;

    public EventSearchDto() {
        this.pageNo = -1;
        this.pageSize = -1;
        this.offset = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCreatedPartyName() {
        return createdPartyName;
    }

    public void setCreatedPartyName(String createdPartyName) {
        this.createdPartyName = createdPartyName;
    }

    public String getIdcardNum() {
        return idcardNum;
    }

    public void setIdcardNum(String idcardNum) {
        this.idcardNum = idcardNum;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Integer> getPartyIdList() {
        return partyIdList;
    }

    public void setPartyIdList(List<Integer> partyIdList) {
        this.partyIdList = partyIdList;
    }
}
