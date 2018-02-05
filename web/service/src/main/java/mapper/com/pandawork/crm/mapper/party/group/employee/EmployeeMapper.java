package com.pandawork.crm.mapper.party.group.employee;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.employee.EmployeeDto;
import com.pandawork.crm.common.dto.employee.EmployeeSearchDto;
import com.pandawork.crm.common.entity.party.group.employee.Employee;
import com.pandawork.crm.common.entity.party.group.employee.EmployeeRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author xiaozl
 * @date 2015/10/23
 * @time 10:41
 */
public interface EmployeeMapper {


    /**
     * 查询所有员工
     *
     * @param
     * @return List<Employee>
     */
    public List<Employee> listAll() throws Exception;

    /**
     * 根据partyId查询用户角色
     *
     * @param partyId
     * @return
     * @throws Exception
     */
    public List<Integer> queryRoleByPartyId(@Param("partyId") int partyId) throws Exception;

    /**
     * 检查注册用户是否重名
     *
     * @param Name
     * @return
     * @throws Exception
     */
    public Employee queryByName(@Param("employeeName") String Name) throws Exception;

    /**
     * 根据partyId更改员工状态
     *
     * @param partyId
     * @param status
     */
    public void updateStatusByPartyId(@Param("partyId") int partyId, @Param("status") int status);

    /**
     * 根据partyId更改员工登陆状态
     *
     * @param partyId
     * @param status
     */
    public void updateUserStatusByPartyId(@Param("partyId") int partyId, @Param("status") int status);

    /**
     * 根据ID查询员工
     * @param  partyId
     *
     * @return
     * @throws Exception
     */
    public List<EmployeeRole> listRoleByPartyId(@Param("partyId") int partyId) throws Exception;

    /**
     * 根据partyId删除员工角色
     *
     * @param  partyId
     * @throws Exception
     */
    public void delRoleByPartyId(@Param("partyId") int partyId) throws Exception;

    /**
     * 根据partyId查询员工信息t_party_employee
     *
     * @param partyId
     * @throws Exception
     */
    public Employee queryByPartyId(@Param("partyId") int partyId) throws  Exception;

    /**
     * 根据partyId查询员工信息t_party_employee(忽略删除，即即使已删除仍可查出)
     *
     * @param partyId
     * @throws Exception
     */
    public Employee queryByPartyIdWithoutDelete(@Param("partyId") int partyId) throws Exception;

    /**
     * 根据获取对应角色的partyId
     *
     * @param roles
     * @return
     * @throws Exception
     */
    public List<Integer> listPartIdByRoles(@Param("roles") List<Integer> roles) throws Exception;

    /**
     * 根据员工编号查询员工
     *
     * @param employeeNumber
     * @return
     * @throws Exception
     */
    public Employee queryByNumber(@Param("employeeNumber") String employeeNumber) throws Exception;

    /**
     * 判断员工电话是否重复
     *
     * @param phone
     * @return
     * @throws Exception
     */
    public Employee queryByPhone(@Param("phone") String phone) throws Exception;

    /**
     * 根据分页显示员工
     *
     * @param curPage
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<EmployeeDto> listByPage(@Param("curPage") Integer curPage,
                                        @Param("pageSize") Integer pageSize) throws Exception;

    /**
     * 计算所有员工数量
     *
     * @return
     * @throws Exception
     */
    public Integer count() throws Exception;

    /**
     * 根据分页获取未删除的所有用户
     *
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<Employee> listAllNotDeleteByPage(@Param("offset") Integer offset,
                                           @Param("pageSize") Integer pageSize) throws Exception;

    /**
     * 根据id分配角色
     *
     * @param partyId
     * @param roleId
     * @throws Exception
     */
    public void setRoleByPartyId(@Param("partyId") Integer partyId,
                            @Param("roleId") Integer roleId) throws Exception;

    /**
     * 根据用户id批量分配角色
     *
     * @param idList
     * @param roleId
     * @throws Exception
     */
    public void batchSetRoleById(@Param("idList") List<Integer> idList,
                                 @Param("roleId") Integer roleId)throws Exception;

    /**
     * 根据partyId批量删除
     *
     * @param idList
     * @throws Exception
     */
    public void batchDeleteById(@Param("idList") List<Integer> idList) throws Exception;

    /**
     * 根据partyId批量修改
     *
     * @param idList
     * @param userType
     * @param dicDepartment
     * @param dicPosition
     * @param dicImmediateS
     * @throws Exception
     */
    public void batchUpdateById(@Param("idList") List<Integer> idList,
                                     @Param("userType") Integer userType,
                                     @Param("dicDepartment") Integer dicDepartment,
                                     @Param("dicPosition") Integer dicPosition,
                                     @Param("dicImmediateS") Integer dicImmediateS) throws Exception;

    /**
     * 根据partyId查询用户姓名
     *
     * @param partyId
     * @throws Exception
     */
    public String queryNameByPartyId(@Param("partyId") Integer partyId) throws Exception;

    /**
     * 根据用户姓名模糊查询用户信息
     *
     * @param employeeSearchDto
     * @return
     * @throws Exception
     */
    public List<Employee> queryEmployeeListByName(@Param("employeeSearchDto")EmployeeSearchDto employeeSearchDto) throws Exception;

    /**
     * 根据id将employee逻辑删除
     *
     * @param id
     * @throws Exception
     */
    public void delById(@Param("id") Integer id) throws Exception;

    /**
     * 根据partyId获取会员组id
     *
     * @param partyId
     * @return
     * @throws Exception
     */
    public Integer getGroupIdByPartyId(@Param("partyId") Integer partyId) throws Exception;

    /**
     * 根据条件dto获取查询数目
     *
     * @param employeeSearchDto
     * @return
     * @throws Exception
     */
    public Integer countEmployeeListByName(@Param("employeeSearchDto") EmployeeSearchDto employeeSearchDto) throws Exception;

    /**
     * 根据用户id删除用户
     *
     * @param idList
     * @throws Exception
     */
    public void delRoleById(@Param("idList") List<Integer> idList) throws Exception;

    /**
     * 用户名重名的个数
     *
     * @param loginName
     * @return
     * @throws Exception
     */
    public Integer checkLoginNameIsExist(@Param("loginName") String loginName) throws Exception;

    /**
     * 根据partyID更改登录状态
     *
     * @param partyId
     * @throws Exception
     */
    public void delLoginStatusByPartyId(@Param("partyId") Integer partyId) throws Exception;

    /**
     * 根据partyId获取在用的系统用户
     *
     * @param partyId
     * @return
     * @throws Exception
     */
    public Employee queryByPartyIdWithUsing(@Param("partyId") Integer partyId) throws Exception;

    /**
     * 根据immediateSId获取所有下级
     *
     * @param immediateSId
     * @return
     * @throws Exception
     */
    public List<Integer> listUnderlingsByImmediateS(@Param("immediateSId") int immediateSId) throws Exception;

}
