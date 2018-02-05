package com.pandawork.crm.common.entity.party.security;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 安全组实体
 *
 * @author: zhangteng
 * @time: 15/10/12 上午8:53
 */
@Entity
@Table(name = "t_party_security_group")
public class SecurityGroup extends AbstractEntity {

    // 主键
    @Id
    private Integer id;

    // 安全组名称
    private String name;

    // 安全组描述
    private String description;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
