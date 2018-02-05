package com.pandawork.crm.common.entity.client.visit;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * PointsConvert
 * Created by shura
 * Date:  2017/8/7.
 * Time:  15:10
 */
@Entity
@Table(name = "t_points_convert")
public class PointsConvert extends AbstractEntity{

    //来访id
    @Id
    private Integer id;

    //消费积分转换比率
    @Column(name = "money_to_points")
    private BigDecimal moneyToPoints;

    //创建者partyId
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

    public BigDecimal getMoneyToPoints() {
        return moneyToPoints;
    }

    public void setMoneyToPoints(BigDecimal moneyToPoints) {
        this.moneyToPoints = moneyToPoints;
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
