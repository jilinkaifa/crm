package com.pandawork.crm.common.entity.party.group.employee;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 员工信息实体
 * @author xiaozl
 * @time 17:14
 */
@Entity
@Table(name = "t_party_employee")
public class Employee extends AbstractEntity {

    //主键
    @Id
    private Integer id;

    //当事人ID
    @Column(name = "party_id")
    private Integer partyId;

    //真实姓名
    @Column(name = "name")
    private String name;

    //用户电话
    @Column(name = "phone")
    private String phone;

    //用户状态：1-启用，2-禁用，3-已删除
    @Column(name = "status")
    private Integer status;

    @Transient
    private String statusValue;

    //所属部门
    @Column(name = "dic_department")
    private Integer dicDepartment;

    @Transient
    private String department;

    //当前职位
    @Column(name = "dic_position")
    private Integer dicPosition;

    @Transient
    private String position;

    //直接上级
    @Column(name = "dic_immediate_s")
    private Integer dicImmediateS;

    @Transient
    private String immediateS;

    //注册时间
    @Column(name = "register_time")
    private Date registerTime;

    //邮箱
    @Column(name = "email")
    private String email;

    //操作账号
    @Column(name = "account")
    private Integer account;

    @Transient
    private String accountValue;

    //账号类型
    @Column(name = "user_type")
    private Integer userType;

    @Transient
    private String userTypeValue;

    //身份证号码
    @Column(name = "idcard_num")
    private String idcardNum;

    //会员组id
    @Column(name = "group_id")
    private Integer groupId;

    //会员组名称
    @Transient
    private String memberGroupName;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    //登录账号
    @Transient
    private String loginName;

    //密码
    @Transient
    private String password;

    //用户角色
    private String roleName;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public Integer getDicDepartment() {
        return dicDepartment;
    }

    public void setDicDepartment(Integer dicDepartment) {
        this.dicDepartment = dicDepartment;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getDicPosition() {
        return dicPosition;
    }

    public void setDicPosition(Integer dicPosition) {
        this.dicPosition = dicPosition;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getDicImmediateS() {
        return dicImmediateS;
    }

    public void setDicImmediateS(Integer dicImmediateS) {
        this.dicImmediateS = dicImmediateS;
    }

    public String getImmediateS() {
        return immediateS;
    }

    public void setImmediateS(String immediateS) {
        this.immediateS = immediateS;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public String getAccountValue() {
        return accountValue;
    }

    public void setAccountValue(String accountValue) {
        this.accountValue = accountValue;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserTypeValue() {
        return userTypeValue;
    }

    public void setUserTypeValue(String userTypeValue) {
        this.userTypeValue = userTypeValue;
    }

    public String getIdcardNum() {
        return idcardNum;
    }

    public void setIdcardNum(String idcardNum) {
        this.idcardNum = idcardNum;
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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getMemberGroupName() {
        return memberGroupName;
    }

    public void setMemberGroupName(String memberGroupName) {
        this.memberGroupName = memberGroupName;
    }
}
