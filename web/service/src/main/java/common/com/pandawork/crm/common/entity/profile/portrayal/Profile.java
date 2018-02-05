package com.pandawork.crm.common.entity.profile.portrayal;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 患者画像实体
 *
 * @author Flying
 * @date 2017/7/19 10:43
 */

@Entity
@Table(name = "t_profile")
public class Profile extends AbstractEntity {

    @Id
    private Integer id;

    //患者id
    @Column(name = "client_id")
    private Integer clientId;

    //患者名称
    @Transient
    private String clientName;

    //标签类型id
    @Column(name = "label_type_id")
    private Integer labelTypeId;

    //标签类型名称
    @Transient
    private String labelTypeName;

    //标签类型是否多选
    @Transient
    private Integer isMultiple;

    //多选标签项的组合名
    @Transient
    private String allMultipleName;

    //标签项id
    @Column(name = "label_item_id")
    private Integer labelItemId;

    //标签项名称
    @Transient
    private String labelItemName;

    //创建人id
    @Column(name = "created_party_id")
    private Integer createdPartyId;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最后修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getLabelTypeId() {
        return labelTypeId;
    }

    public void setLabelTypeId(Integer labelTypeId) {
        this.labelTypeId = labelTypeId;
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

    public Integer getLabelItemId() {
        return labelItemId;
    }

    public void setLabelItemId(Integer labelItemId) {
        this.labelItemId = labelItemId;
    }

    public String getLabelTypeName() {
        return labelTypeName;
    }

    public void setLabelTypeName(String labelTypeName) {
        this.labelTypeName = labelTypeName;
    }

    public String getLabelItemName() {
        return labelItemName;
    }

    public void setLabelItemName(String labelItemName) {
        this.labelItemName = labelItemName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getIsMultiple() {
        return isMultiple;
    }

    public void setIsMultiple(Integer isMultiple) {
        this.isMultiple = isMultiple;
    }

    public String getAllMultipleName() {
        return allMultipleName;
    }

    public void setAllMultipleName(String allMultipleName) {
        this.allMultipleName = allMultipleName;
    }
}
