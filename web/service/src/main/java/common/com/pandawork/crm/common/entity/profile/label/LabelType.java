package com.pandawork.crm.common.entity.profile.label;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * labelType
 * Author： Daydreamer
 * Date: 2017/7/18
 * Time: 13:35
 */
@Entity
@Table(name = "t_label_type")
public class LabelType extends AbstractEntity {
    @Id
    private Integer id;

    //类型名称
    private String name;

    //是否多选
    @Column(name = "is_multiple")
    private Integer isMultiple;

    //标签项数量
    @Column(name = "label_count")
    private Integer labelCount;

    //标签项简略
    @Column(name = "label_text")
    private String labelText;

    //备注
    @Column(name = "label_comment")
    private String labelComment;

    //创建者id
    @Column(name = "created_party_id")
    private Integer createdPartyId;

    //创建者姓名
    @Transient
    private String createdPartyName;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    //是否删除，0可用，1已删
    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsMultiple() {
        return isMultiple;
    }

    public void setIsMultiple(Integer isMultiple) {
        this.isMultiple = isMultiple;
    }

    public Integer getLabelCount() {
        return labelCount;
    }

    public void setLabelCount(Integer labelCount) {
        this.labelCount = labelCount;
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String lableText) {
        this.labelText = lableText;
    }

    public String getLabelComment() {
        return labelComment;
    }

    public void setLabelComment(String labelComment) {
        this.labelComment = labelComment;
    }

    public Integer getCreatedPartyId() {
        return createdPartyId;
    }

    public void setCreatedPartyId(Integer createdPartyId) {
        this.createdPartyId = createdPartyId;
    }

    public String getCreatedPartyName() {
        return createdPartyName;
    }

    public void setCreatedPartyName(String createdPartyName) {
        this.createdPartyName = createdPartyName;
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
