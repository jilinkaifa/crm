package com.pandawork.crm.common.entity.profile.label;

import org.dom4j.tree.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * labelItem
 * Author： Daydreamer
 * Date: 2017/7/18
 * Time: 14:12
 */
@Entity
@Table(name = "t_label_item")
public class LabelItem extends com.pandawork.core.common.entity.AbstractEntity{
    @Id
    private Integer id;

    //标签类型id
    @Column(name = "label_type_id")
    private Integer labelTypeId;

    //标签字典id
    @Column(name = "dic_label_id")
    private Integer dicLabelId;

    //标签字典值
    @Column(name = "dic_label_name")
    private String dicLabelName;

    //创建者id
    @Column(name = "created_party_id")
    private String createdPartyId;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    //是否可用，0可用，1删除
    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLabelTypeId() {
        return labelTypeId;
    }

    public void setLabelTypeId(Integer labelTypeId) {
        this.labelTypeId = labelTypeId;
    }

    public Integer getDicLabelId() {
        return dicLabelId;
    }

    public void setDicLabelId(Integer dicLabelId) {
        this.dicLabelId = dicLabelId;
    }

    public String getDicLabelName() {
        return dicLabelName;
    }

    public void setDicLabelName(String dicLabelName) {
        this.dicLabelName = dicLabelName;
    }

    public String getCreatedPartyId() {
        return createdPartyId;
    }

    public void setCreatedPartyId(String createdPartyId) {
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

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
