package com.pandawork.crm.common.dto.client.visit;

/**
 * VisitSearchDto
 * Author： liping
 * Date: 2017/7/20
 * Time: 9:26
 */
public class VisitSearchDto {

    //姓名
    private String clientName;
    //身份证号
    private String clientIdcardNum;
    //电话
    private String clientTel;
    //用户类型(接收前台的值转换成字典值id)
    private Integer clientType;
    //来访次数
    private Integer visitTimes;
    //会员组id
    private Integer memberGroupId;
    // 分页大小
    private Integer pageSize;
    // 偏移量
    private Integer offset;
    //第几页
    private Integer page;

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

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Integer getVisitTimes() {
        return visitTimes;
    }

    public void setVisitTimes(Integer visitTimes) {
        this.visitTimes = visitTimes;
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

    public Integer getMemberGroupId() {
        return memberGroupId;
    }

    public void setMemberGroupId(Integer memberGroupId) {
        this.memberGroupId = memberGroupId;
    }

    public VisitSearchDto(){}

    public VisitSearchDto(String clientName, String clientIdcardNum,
                          String clientTel, Integer clientType, Integer visitTimes,
                          Integer pageSize,Integer memberGroupId, Integer offset) {
        this.clientName = clientName;
        this.clientIdcardNum = clientIdcardNum;
        this.clientTel = clientTel;
        this.clientType = clientType;
        this.visitTimes = visitTimes;
        this.memberGroupId = memberGroupId;
        this.pageSize = pageSize;
        this.offset = offset;
    }
}
