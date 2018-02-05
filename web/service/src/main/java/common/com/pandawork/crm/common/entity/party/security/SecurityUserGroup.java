package com.pandawork.crm.common.entity.party.security;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户-安全组关联实体
 *
 * @author: zhangteng
 * @time: 15/10/12 上午8:56
 */
@Entity
@Table(name = "t_party_security_user_group")
public class SecurityUserGroup extends AbstractEntity {

    // 主键
    @Id
    private Integer id;

    // 安全组ID
    @Column(name = "group_id")
    private Integer groupId;

    // 用户ID
    @Column(name = "user_id")
    private Integer userId;

    // 创建时间
    @Column(name = "created_time")
    private Date createdTime;

    // 最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;



    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
