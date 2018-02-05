package com.pandawork.crm.common.dto.client.quest;


import java.util.Date;

/**
 * QuestSearchDto
 * Created by shura
 * Date:  2017/7/24.
 * Time:  14:42
 */
public class QuestSearchDto {

    //姓名
    private String name;

    //身份证号
    private String idCardNum;

    //电话
    private String tel;

    //会员组id
    private Integer memberGroupId;

    //问卷次数
    private Integer questCount;

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

    public Integer getQuestCount() {
        return questCount;
    }

    public void setQuestCount(Integer questCount) {
        this.questCount = questCount;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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
