package com.pandawork.crm.common.entity.event;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * CheckItemResult
 *
 * @author Flying
 * @date 2017/7/20 19:26
 */
@Entity
@Table(name = "t_check_item_result")
public class CheckItemResult extends AbstractEntity {
    @Id
    private Integer id;

    //活动通知记录id
    @Column(name = "event_record_notice_id")
    private Integer eventRecordNoticeId;

    //检查项id
    @Column(name = "check_item_id")
    private Integer checkItemId;

    //检查结果
    @Column(name = "check_result")
    private String checkResult;

    //检查时间
    @Column(name = "check_time")
    private Date checkTime;

    //创建者id
    @Column(name = "created_party_id")
    private Integer createdPartyId;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventRecordNoticeId() {
        return eventRecordNoticeId;
    }

    public void setEventRecordNoticeId(Integer eventRecordNoticeId) {
        this.eventRecordNoticeId = eventRecordNoticeId;
    }

    public Integer getCheckItemId() {
        return checkItemId;
    }

    public void setCheckItemId(Integer checkItemId) {
        this.checkItemId = checkItemId;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
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
}
