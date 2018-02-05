package com.pandawork.crm.common.entity.party.member;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Member
 * 会员组实体
 *
 * Author： Flying
 * Date: 2017/7/15 9:20
 */
@Entity
@Table(name = "t_member_group")
public class Member extends AbstractEntity {
    @Id
    private Integer id;

    //组名
    @Column(name = "name")
    private String name;

    //备注
    @Column(name = "comment")
    private String comment;

    //是否删除
    @Column(name = "delated")
    private Integer delated;

    //创建者id
    @Column(name = "created_party_id")
    private int createdPartyId;

    //创建时间
    @Column(name = "create_time")
    private Date createTime;

    //最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    @Column(name = "type")
    private Integer type;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getDelated() {
        return delated;
    }

    public void setDelated(Integer delated) {
        this.delated = delated;
    }

    public int getCreatedPartyId() {
        return createdPartyId;
    }

    public void setCreatedPartyId(int createdPartyId) {
        this.createdPartyId = createdPartyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Integer getType() {return type;}

    public void setType(Integer type) {this.type = type;}
}
