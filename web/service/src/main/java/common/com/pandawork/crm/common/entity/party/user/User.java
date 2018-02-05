package com.pandawork.crm.common.entity.party.user;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * User
 * Author： csy
 * Dae: 2017/7/15
 * Time: 18:17
 */
@Entity
@Table(name = "t_party")
public class User extends AbstractEntity {

    //主键
    @Id
    private Integer id;

    //当事人ID
    @Column(name = "party_id" )
    private Integer partyId;

    //登录名
    @Column(name = "login_name")
    private String loginName;

    //密码
    @Column(name = "password")
    private String password;

    //用户状态：0-禁用，1-启用
    @Column(name = "status")
    private Integer status;

    //是否需要更新密码:0-不需要，1-需要
    @Column(name = "update_password")
    private Integer updatePassword;

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

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUpdatePassword() {
        return updatePassword;
    }

    public void setUpdatePassword(Integer updatePassword) {
        this.updatePassword = updatePassword;
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
