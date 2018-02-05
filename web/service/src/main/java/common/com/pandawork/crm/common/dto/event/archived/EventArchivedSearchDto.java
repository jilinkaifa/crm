package com.pandawork.crm.common.dto.event.archived;

/**
 * EventArchivedSearchDto
 * Author： csy
 * Date: 2017/7/26
 * Time: 20:22
 */
public class EventArchivedSearchDto {

    //id
    private Integer eventId;

    //姓名
    private String name;

    //身份证号
    private String idcardNum;

    //活动名称
    private String eventName;

    //活动类型
    private Integer eventType;

    //活动级别
    private Integer eventLevel;

    //活动组id
    private Integer memberGroupId;

    // 页码
    private Integer page;

    // 分页大小
    private Integer pageSize;

    // 偏移量
    private Integer offset;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcardNum() {
        return idcardNum;
    }

    public void setIdcardNum(String idcardNum) {
        this.idcardNum = idcardNum;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(Integer eventLevel) {
        this.eventLevel = eventLevel;
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

    public Integer getMemberGroupId() {return memberGroupId;}

    public void setMemberGroupId(Integer memberGroupId) {this.memberGroupId = memberGroupId;}
}
