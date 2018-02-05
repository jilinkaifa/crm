package com.pandawork.crm.common.entity.client.points;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * PointsItem
 * Author： liping
 * Date: 2017/7/19
 * Time: 10:22
 */
@Entity
@Table(name = "t_points_item")
public class PointsItem extends AbstractEntity{

    //积分项id
    @Id
    private Integer id;

    //活动id
    @Column(name = "event_id")
    private Integer eventId;

    //积分项名称
    @Column(name = "name")
    private String name;

    //积分值
    @Column(name = "points_value")
    private Integer pointsValue;

    //备注
    @Column(name = "comment")
    private String comment;

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

    public void setId(Integer id) {
        this.id=id;
    }

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

    public Integer getPointsValue() {
        return pointsValue;
    }

    public void setPointsValue(Integer pointsValue) {
        this.pointsValue = pointsValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
