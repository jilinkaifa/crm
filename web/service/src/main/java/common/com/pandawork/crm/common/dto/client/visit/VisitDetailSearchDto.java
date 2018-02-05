package com.pandawork.crm.common.dto.client.visit;

import java.util.Date;

/**
 * VisitDetailSearchDto
 * Author： liping
 * Date: 2017/7/21
 * Time: 12:44
 */
public class VisitDetailSearchDto {

    //来访者id（非输入）
    private Integer clientId;

    //来访来源
    private Integer visitFrom;

    //来访内容
    private String visitContent;

    //来访日期1
    private Date startDate;

    //来访日期2
    private Date endDate;

    // 分页大小
    private Integer pageSize;

    // 偏移量
    private Integer offset;

    //第几页
    private Integer page;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
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

    public VisitDetailSearchDto(){}

    public VisitDetailSearchDto(Integer clientId, Integer visitFrom,
                                String visitContent, Date visitTime1,
                                Date visitTime2, Integer offset,
                                Integer pageSize) {
        this.clientId = clientId;
        this.visitFrom = visitFrom;
        this.visitContent = visitContent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.offset = offset;
        this.pageSize = pageSize;
    }

}
