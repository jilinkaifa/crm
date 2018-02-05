package com.pandawork.crm.service.party.group.employee;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.employee.EmployeeDto;
import com.pandawork.crm.common.dto.employee.EmployeeSearchDto;
import com.pandawork.crm.common.entity.party.group.employee.Employee;
import com.pandawork.crm.common.entity.party.security.SecurityGroup;

import java.util.List;

/**
 * 用户管理service
 * @author xiaozl
 * @date 2015/10/23
 * @time 10:27
 */
public interface EmployeeService {

    /**
     * 获取所有员工信息
     * @return
     * @throws SSException
     */
    public List<EmployeeDto> listAll() throws SSException;

    /**
     * 根据不同的角色获取员工信息
     * @param roleList
     * @return
     * @throws SSException
     */
    public List<EmployeeDto> listByRoles(List<Integer> roleList) throws SSException;

    /**
     * 检查注册员工是否姓名重名
     * @param employeeName
     * @return
     * @throws SSException
     */
    public boolean checkNameIsExist(String employeeName) throws SSException;

    /**
     * 员工编号是否重复
     * @param EmployeeNumber
     * @return
     * @throws SSException
     */
    public boolean checkNumberIsExist(String EmployeeNumber) throws SSException;

    /**
     * 检查电话是否重复
     * @param phone
     * @return
     * @throws SSException
     */
    public boolean checkPhoneIsExist(String phone) throws SSException;

    /**
     * 添加新员工
     *
     * @param employee
     * @return
     * @throws SSException
     */
    public Employee newEmployee(Employee employee) throws SSException;

    /**
     * 更新系统用户信息
     *
     * @param employee
     * @throws SSException
     */
    public void update(Employee employee) throws SSException;

    /**
     * 修改密码时检查原密码是否正确
     * @param securityUserId
     * @param oldPwd
     * @return
     * @throws SSException
     */
    public boolean checkOldPwd(int securityUserId, String oldPwd) throws SSException;

    /**
     * 修改密码
     * @param securityUserId
     * @param oldPwd
     * @param newPwd
     * @throws SSException
     */
    public void updatePwd(int securityUserId, String oldPwd, String newPwd) throws SSException;

    /**
     * 更新用户状态
     * @param partyId
     * @param status
     * @throws SSException
     */
    public void updateStatus(int partyId, Integer status) throws SSException;

    /**
     * 删除员工
     * @param partyId
     * @throws SSException
     */
    public void delByPartyId(int partyId) throws SSException;

    /**
     * 根据partyId查询员工
     * @param partyId
     * @return
     * @throws SSException
     */
    public Employee queryByPartyId(int partyId) throws SSException;

    /**
     * 根据partyId查询员工(忽略删除，即即使已删除仍可查出)
     * @param partyId
     * @return
     * @throws SSException
     */
    public Employee queryByPartyIdWithoutDelete(int partyId) throws SSException;

    /**
     * 根据partyId查询EmployeeDto
     * @param partyId
     * @return
     * @throws SSException
     */
    public EmployeeDto queryEmployeeDtoByPartyId(int partyId) throws SSException;

    /**
     * 根据员工编号查询员工信息
     * @param employeeNumber
     * @return
     * @throws SSException
     */
    public Employee queryByNumber(String employeeNumber) throws SSException;

    /**
     * 根据员工电话查询员工信息
     * @param phone
     * @return
     * @throws SSException
     */
    public Employee queryByPhone(String phone) throws SSException;

    /**
     * 根据分页查询员工信息
     * @param curPage
     * @param pageSize
     * @return
     */
    public List<EmployeeDto> listByPage(int curPage ,int pageSize) throws SSException;

    /**
     * 所有员工数量
     * @return
     * @throws SSException
     */
    public int count() throws SSException;

    /**
     *根据页码获取未删除的系统用户列表
     *
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<Employee> listAllNotDeleteByPage(int offset, int pageSize) throws SSException;

    /**
     * 根据id获取系统用户
     *
     * @param id
     * @return
     * @throws SSException
     */
    public Employee queryById(int id) throws SSException;

    /**
     * 根据id列表分配角色
     *
     * @return
     * @throws SSException
     */
    public void setRoleByIdList(List<Integer> idList, int roleId, int memberGroupId) throws SSException;

    /**
     * 批量删除系统用户
     *
     * @param idList
     * @throws SSException
     */
    public void deleteByIdList(List<Integer> idList) throws SSException;

    /**
     * 批量修改系统用户
     *
     * @param idList
     * @param userType
     * @param dicDepartment
     * @param dicPosition
     * @param dicImmediateS
     * @throws SSException
     */
    public void updateByIdList(List<Integer> idList, Integer userType, Integer dicDepartment, Integer dicPosition, Integer dicImmediateS) throws SSException;

    /**
     * 根据partyId查询员工姓名
     *
     * @param partyId
     * @return
     * @throws SSException
     */
    public String queryNameByPartyId(int partyId) throws SSException;

    /**
     * 根据用户姓名查询用户信息
     *
     * @param employeeSearchDto
     * @return
     * @throws SSException
     */
    public List<Employee> queryEmployeeListByName(EmployeeSearchDto employeeSearchDto) throws SSException;

    /**
     * 根据id将employee设置为删除状态
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 根据partyId获取会员组id
     *
     * @param partyId
     * @return
     * @throws SSException
     */
    public int getGroupIdByPartyId(int partyId) throws SSException;

    /**
     * 计算根据条件查询系统用户的数目
     *
     * @param employeeSearchDto
     * @return
     * @throws SSException
     */
    public int countEmployeeListByName(EmployeeSearchDto employeeSearchDto) throws SSException;

    /**
     * 根据用户id删除所分配角色
     *
     * @param idList
     * @return
     * @throws SSException
     */
    public void delRoleById(List<Integer> idList) throws SSException;

    /**
     * 根据partyId获取直接上级列表
     *
     * @param partyId
     * @return
     * @throws SSException
     */
    public List<Employee> listImmediateSByPartyId(int partyId) throws SSException;

    /**
     * 检查登录名是否重名
     *
     * @param loginName
     * @return
     * @throws SSException
     */
    public boolean checkLoginNameIsExist(String loginName) throws SSException;

    /**
     * 根据partyId获取在用的系统用户
     *
     * @param partyId
     * @return
     * @throws SSException
     */
    public Employee queryByPartyIdWithUsing(int partyId) throws SSException;

    /**
     * 根据partyId获取所有下级
     *
     * @param partyId
     * @return
     * @throws SSException
     */
    public List<Integer> listUnderlingsByPartyId(int partyId) throws SSException;

    /**
     * 根据partyId获取安全组
     *
     * @param partyId
     * @return
     * @throws SSException
     */
    public SecurityGroup querySecurityGroupByPartyId(int partyId) throws SSException;

}
