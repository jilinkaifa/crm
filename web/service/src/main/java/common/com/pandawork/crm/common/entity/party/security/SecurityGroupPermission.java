package com.pandawork.crm.common.entity.party.security;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 安全组-权限关联实体
 *
 * @author: zhangteng
 * @time: 15/10/12 上午8:56
 */
@Entity
@Table(name = "t_party_security_group_permission")
public class SecurityGroupPermission extends AbstractEntity {

    // 主键
    @Id
    private Integer id;

    // 安全组ID
    @Column(name = "group_id")
    private Integer groupId;

    // 权限ID
    @Column(name = "permission_id")
    private Integer permissionId;

    // 创建时间
    @Column(name = "created_time")
    private Date createdTime;

    // 最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    // 权限名称
    @Transient
    private String permissionDescription;

    // 权限表达式
    @Transient
    private String permissionExpression;

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

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
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

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }

    public String getPermissionExpression() {
        return permissionExpression;
    }

    public void setPermissionExpression(String permissionExpression) {
        this.permissionExpression = permissionExpression;
    }
}
