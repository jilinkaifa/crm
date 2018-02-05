package com.pandawork.crm.common.dto.employee;


import com.pandawork.crm.common.entity.party.group.employee.Employee;

import java.util.List;

/**
 * 员工信息Dto
 * @author xiaozl
 * @date 2015-10-23
 */
public class EmployeeDto {

    //用户实体
    private Employee employee;

    //员工登录名
    private String loginName;

    //用户角色标识
    private List<Integer> role;

    //用户角色名
    private List<String> roleName;

    //用户描述
    private String description;

    //启用与禁用
    private String status;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Integer> getRole() {
        return role;
    }

    public void setRole(List<Integer> role) {
        this.role = role;
    }

    public List<String> getRoleName() {
        return roleName;
    }

    public void setRoleName(List<String> roleName) {
        this.roleName = roleName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getDescription(){return description;}

    public void setDescription(String description) { this.description = description; }
}
