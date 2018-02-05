package com.pandawork.crm.common.entity.event;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * RecordPointItem
 * 活动记录积分项实体
 *
 * @author Flying
 * @date 2017/7/20 19:16
 */
@Entity
@Table(name = "t_event_record_points_item")
public class EventRecordPointsItem extends AbstractEntity{

    @Id
    private Integer id;

    //活动记录通知id
    @Column(name = "event_record_notice_id")
    private Integer eventRecordNoticeId;

    //活动积分项id
    @Column(name = "points_item_id")
    private Integer pointsItemId;

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

    public Integer getPointsItemId() {
        return pointsItemId;
    }

    public void setPointsItemId(Integer pointsItemId) {
        this.pointsItemId = pointsItemId;
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
