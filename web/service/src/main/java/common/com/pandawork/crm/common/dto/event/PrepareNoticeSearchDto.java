package com.pandawork.crm.common.dto.event;

import java.util.Date;

/**
 * PrepareNoticeServiceDto
 *
 * @author Daydreamer
 * @date 2017/7/31 17:04
 */
public class PrepareNoticeSearchDto {

    //活动名称
    private String name;

    //活动级别
    private Integer level;

    //活动类型
    private Integer type;

    //本期活动开始时间
    private Date startDate;

    //截止日期处开始时间
    private Date startTime;

    //截止日期处结束时间
    private Date endTime;

    // 页码
    private Integer pageNo;

    // 分页size
    private Integer pageSize;

    // 偏移量
    private Integer offset;

    public PrepareNoticeSearchDto() {
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
}
