package com.pandawork.crm.service.party.group.employee.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.dto.employee.EmployeeDto;
import com.pandawork.crm.common.dto.employee.EmployeeSearchDto;
import com.pandawork.crm.common.entity.party.group.Party;
import com.pandawork.crm.common.entity.party.group.employee.Employee;
import com.pandawork.crm.common.entity.party.member.Member;
import com.pandawork.crm.common.entity.party.security.SecurityGroup;
import com.pandawork.crm.common.entity.party.security.SecurityUser;
import com.pandawork.crm.common.entity.party.security.SecurityUserGroup;
import com.pandawork.crm.common.enums.party.EnableEnums;
import com.pandawork.crm.common.enums.party.PartyTypeEnums;
import com.pandawork.crm.common.enums.party.UserStatusEnums;
import com.pandawork.crm.common.enums.party.group.employee.EmployeeRoleEnums;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.common.exception.PartyException;
import com.pandawork.crm.common.utils.CommonUtil;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.JmAndKlUtils;
import com.pandawork.crm.mapper.party.group.employee.EmployeeMapper;
import com.pandawork.crm.service.party.dictionary.DictionaryService;
import com.pandawork.crm.service.party.group.PartyService;
import com.pandawork.crm.service.party.group.employee.EmployeeService;
import com.pandawork.crm.service.party.security.SecurityGroupService;
import com.pandawork.crm.service.party.security.SecurityUserGroupService;
import com.pandawork.crm.service.party.security.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 用户管理service接口实现
 * @author xiaozl
 * @date 2015/10/23
 * @time 10:37
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private PartyService partyService;

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private SecurityUserGroupService securityUserGroupService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private SecurityGroupService securityGroupService;


    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;//core包

    /**
     * 获取所有员工信息
     * @return
     * @throws SSException
     */
    @Override
    public List<EmployeeDto> listAll() throws SSException {
        try {
            List<EmployeeDto> employeeDtos = new ArrayList<EmployeeDto>();
            //根据取出启用的员工信息t_party_employee
            List<Employee> employees = Collections.emptyList();
            employees = employeeMapper.listAll();
           /* //获取所有启用的员工登录名
            List<SecurityUser> securityUsers = Collections.emptyList();
            securityUsers = securityUserService.listByPartyId(partyId);*/
            for (Employee employee : employees) {
                EmployeeDto employeeDto = new EmployeeDto();
                employeeDto.setEmployee(employee);
                List<Integer> roles = Collections.emptyList();
                roles = employeeMapper.queryRoleByPartyId(employee.getPartyId());//查询每个用户的对应的所有角色

                List<String> roleNames = new ArrayList<String>();//根据取出的角色标识，赋予角色名
                    //获得用户角色名
                    for (int r : roles) {
                        roleNames.add(EmployeeRoleEnums.getDescriptionById(r));
                    }
                    //数据存入dto
                employeeDto.setRole(roles);
                employeeDto.setRoleName(roleNames);
                if(employee.getStatus()==1) {
                    employeeDto.setStatus(UserStatusEnums.Enabled.getStatus());
                }
                if(employee.getStatus()==2){
                    employeeDto.setStatus(UserStatusEnums.Disabled.getStatus());
                }
                SecurityUser securityUser = securityUserService.queryByPartyId(employee.getPartyId());
                employeeDto.setLoginName(securityUser.getLoginName());

                employeeDtos.add(employeeDto);
                }
                return employeeDtos;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEmployeeInfoFail, e);
        }
    }

    /**
     * 通过角色查询所有用户
     * @param roleList
     * @return
     * @throws SSException
     */
    @Override
    public List<EmployeeDto> listByRoles(List<Integer> roleList) throws SSException {
        //存放所有员工的所有信息
        List<EmployeeDto> employeeDtos = new ArrayList<EmployeeDto>();
        //存放所有员工t_party_employee中的信息
        List<Employee> employees = new ArrayList<Employee>();
        try {
            //获取具有该角色的所有当事人partId
            List<Integer> partyIdList =Collections.emptyList();
            partyIdList = employeeMapper.listPartIdByRoles(roleList);

            //获取指定partId的员工t_party_employee中的信息，
            for (Integer id : partyIdList) {
                Employee employee = employeeMapper.queryByPartyId(id);
                //不获取删除的员工
                if(employee.getStatus()!=3) {
                    employees.add(employee);
                }
            }

            //根据获取的员工t_party_employee表中的信息获取角色等信息
            for (Employee employee : employees) {
                EmployeeDto employeeDto = new EmployeeDto();
                //数据存入Dto
                employeeDto.setEmployee(employee);

                //查询每个用户的对应的所有角色
                List<Integer> roles = Collections.emptyList();
                roles = employeeMapper.queryRoleByPartyId(employee.getPartyId());

                //根据取出的角色标识，赋予角色名
                List<String> roleNames = new ArrayList<String>();
                for (int r : roles) {
                        roleNames.add(EmployeeRoleEnums.getDescriptionById(r));
                }
                //员工信息存入dto
                employeeDto.setRole(roles);
                employeeDto.setRoleName(roleNames);
                //获取员工状态信息
                if(employee.getStatus()==1){
                    employeeDto.setStatus(UserStatusEnums.Enabled.getStatus());
                }else if(employee.getStatus()==2){
                    employeeDto.setStatus(UserStatusEnums.Disabled.getStatus());
                }
                //获取员工登录名
                SecurityUser securityUser = securityUserService.queryByPartyId(employee.getPartyId());
                employeeDto.setLoginName(securityUser.getLoginName());

                employeeDtos.add(employeeDto);
                }
                return employeeDtos;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEmployeeInfoFail, e);
        }
    }


    /**
     * 检查注册员工是否姓名重名
     * @param employeeName
     * @return
     * @throws SSException
     */
    @Override
    public boolean checkNameIsExist(String employeeName) throws SSException {
        try {
            if(Assert.isNull(employeeName)){
                throw SSException.get(CrmException.EmployeeNameNotNull);
            }
            if(employeeMapper.queryByName(employeeName) != null) {
                return true;
            } else {
                return  false;
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.SystemException, e);
        }
    }

    /**
     * 检查员工编号是否存在
     * @param employeeNumber
     * @return
     * @throws SSException
     */
    @Override
    public boolean checkNumberIsExist(String employeeNumber) throws SSException {
        try{
            if(Assert.isNull(employeeNumber)){
                throw SSException.get(CrmException.EmployeeNumberNotNull);
            }
            if(employeeMapper.queryByNumber(employeeNumber)!= null) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.SystemException, e);
        }
    }

    /**
     * 检查电话是否存在
     * @param phone
     * @return
     * @throws SSException
     */
    @Override
    public boolean checkPhoneIsExist(String phone) throws SSException {
        try{
            if(employeeMapper.queryByPhone(phone) != null) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.SystemException, e);
        }
    }

    /**
     * 新增一条系统用户信息
     * @param employee
     * @return
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public Employee newEmployee(Employee employee) throws SSException {

        try {
            Party party = new Party();
            //向t_security_user表中添加用户信息
            party.setPartyTypeId(PartyTypeEnums.Employee.getId());
            //新建一个party_id
            Integer partyId = partyService.newParty(party).getId();
            employee.setPartyId(partyId);
            commonDao.insert(employee);
            SecurityUser securityUser = new SecurityUser();
            securityUser.setPartyId(partyId);
            securityUser.setStatus(1);
            securityUser.setUpdatePassword(0);
            if(Assert.isNotNull(employee.getLoginName())){
                securityUser.setLoginName(employee.getLoginName());
            }
            if(Assert.isNotNull(employee.getPassword())){
                String password = JmAndKlUtils.KL(employee.getPassword());
                securityUser.setPassword(password);
            }
            commonDao.insert(securityUser);
            return employee;

        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEmployeeException, e);
        }
    }


    /**
     * 更新系统用户信息
     *
     * @param employee
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void update(Employee employee) throws SSException {
        try {
            if(Assert.isNull(employee)){
                return;
            }else {
                int partyId = employee.getPartyId();
                SecurityUser securityUser = securityUserService.queryByPartyId(partyId);
                securityUser.setPartyId(partyId);
                securityUser.setStatus(1);
                securityUser.setUpdatePassword(0);
                if(Assert.isNotNull(employee.getLoginName())){
                    securityUser.setLoginName(employee.getLoginName());
                }
                if(Assert.isNotNull(employee.getPassword())){
                    String password = JmAndKlUtils.KL(employee.getPassword());
                    securityUser.setPassword(password);
                }
                commonDao.update(employee);
                commonDao.update(securityUser);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    /**
     * 修改密码时检查原密码是否正确
     * @param securityUserId
     * @param oldPwd
     * @return
     * @throws SSException
     */
    @Override
    public boolean checkOldPwd(int securityUserId, String oldPwd) throws SSException {
        try {
            String opd = JmAndKlUtils.KL(oldPwd);
            SecurityUser securityUser = commonDao.queryById(SecurityUser.class, securityUserId);
            if(securityUser != null && securityUser.getPassword().equals(opd)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.SystemException, e);
        }

    }

    /**
     *修改密码
     * @param securityUserId
     * @param oldPwd
     * @param newPwd
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updatePwd(int securityUserId, String oldPwd, String newPwd) throws SSException {
        try {
            if(checkOldPwd(securityUserId, JmAndKlUtils.KL(oldPwd))) {
                String npd = JmAndKlUtils.KL(newPwd);
                SecurityUser securityUser = new SecurityUser();
                securityUser.setId(securityUserId);
                securityUser.setPassword(npd);
                commonDao.updateFieldsById(securityUser, "password");
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.UpdateUserPwdFail, e);
        }
    }

    /**
     * 更新用户状态
     * @param partyId
     * @param status
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStatus(int partyId, Integer status) throws SSException {
        try {
            //User user = commonDao.queryById(User.class, uid);
            //修改t_party_employee表中员工状态
            employeeMapper.updateStatusByPartyId(partyId, status);

            SecurityUser securityUser = securityUserService.queryByPartyId(partyId);
            securityUser.setStatus(status);
            //根据securityUserId修改员工状态t_party_security_user
            if(status == 1) {
                securityUserService.updateStatusById(securityUser.getId(), EnableEnums.Enabled);
            }
            if(status == 2) {
                securityUserService.updateStatusById(securityUser.getId(), EnableEnums.Disabled);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.UpdateEmployeeStateFail, e);
        }
    }

    /**
     * 根据partyId删除用户
     * @param partyId
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delByPartyId(int partyId) throws SSException {
        //假删除，在数据库表中并没有删除，只是把status字段设为“删除”
        try {
            //修改t_party_employee员工状态
            employeeMapper.updateStatusByPartyId(partyId, UserStatusEnums.Deleted.getId());
            //删除员工和角色关联的信息



            //修改t_party_security_user员工状态
            SecurityUser securityUser = securityUserService.queryByPartyId(partyId);
            securityUser.setStatus(EnableEnums.Disabled.getId());
            securityUserService.updateSecurityUser(securityUser);
            // 从SecurityUserGroup中删除信息
            securityUserGroupService.delByUserId(securityUser.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.DeleteEmployeeFail, e);
        }
    }

    /**
     * 通过partyId查询用户
     * @param partyId
     * @return
     * @throws SSException
     */
    @Override
    public Employee queryByPartyId(int partyId) throws SSException {
        Employee employee = null;
        if (Assert.lessOrEqualZero(partyId)) {
            return employee;
        }
        try {
            employee = employeeMapper.queryByPartyId(partyId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEmployeeException, e);
        }

        return employee;
    }

    /**
     * 查询不是删除状态的用户
     * @param partyId
     * @return
     * @throws SSException
     */
    @Override
    public Employee queryByPartyIdWithoutDelete(int partyId) throws SSException {
        Employee employee = null;
        if (Assert.lessOrEqualZero(partyId)) {
            return employee;
        }

        try {
            employee = employeeMapper.queryByPartyIdWithoutDelete(partyId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEmployeeException, e);
        }

        return employee;
    }


    /**
     * 通过PartyId查询EmployeeDto
     * @param partyId
     * @return
     * @throws SSException
     */
    public EmployeeDto queryEmployeeDtoByPartyId(int partyId) throws SSException{
        EmployeeDto employeeDto = new EmployeeDto();
        try{
            if (Assert.lessOrEqualZero(partyId)) {
                return null;
            }
            Employee employee = queryByPartyId(partyId);

            employeeDto.setEmployee(employee);
            //获取指定partyId的用户角色
            List<Integer> roles = Collections.emptyList();
            roles = employeeMapper.queryRoleByPartyId(employee.getPartyId());//查询每个用户的对应的所有角色
            List<String> roleNames = new ArrayList<String>();//根据取出的角色标识，赋予角色名

            //获得用户角色名
            for (int r : roles) {
                    roleNames.add(EmployeeRoleEnums.getDescriptionById(r));
            }
            //数据存入dto
            employeeDto.setRole(roles);
            employeeDto.setRoleName(roleNames);
            if(employee.getStatus()==1) {
                employeeDto.setStatus(UserStatusEnums.Enabled.getStatus());
            }
            if(employee.getStatus()==2){
                employeeDto.setStatus(UserStatusEnums.Disabled.getStatus());
            }

            SecurityUser securityUser = securityUserService.queryByPartyId(employee.getPartyId());
            employeeDto.setLoginName(securityUser.getLoginName());

            return employeeDto;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEmployeeException, e);
        }
    }

    /**
     * 根据员工编号查询用户
     * @param employeeNumber
     * @return
     * @throws SSException
     */
    @Override
    public Employee queryByNumber(String employeeNumber) throws SSException {
        try {
              if (Assert.isNull(employeeNumber)){
                  throw SSException.get(CrmException.EmployeeNumberNotNull);
              }
            return employeeMapper.queryByNumber(employeeNumber);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEmployeeException, e);
        }
    }

    /**
     * 根据电话号码查询用户
     * @param phone
     * @return
     * @throws SSException
     */
    @Override
    public Employee queryByPhone(String phone) throws SSException {
        try {
            if (Assert.isNull(phone)){
                throw SSException.get(CrmException.CheckEmployeePhoneFail);
            }
            return employeeMapper.queryByPhone(phone);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEmployeeException, e);
        }
    }

    /**
     * 根据分页显示所有员工共信息
     * @param curPage
     * @param pageSize
     * @return
     * @throws SSException
     */
    @Override
    public List<EmployeeDto> listByPage(int curPage ,int pageSize) throws SSException{

        //存放员工信息
        List<Employee> employees = Collections.emptyList();
        //存放employeeDto信息
        List<EmployeeDto> employeeDtos = Collections.emptyList();
        try{
            employeeDtos = employeeMapper.listByPage(curPage , pageSize);
            return employeeDtos;
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEmployeeException, e);
        }

    }

    /**
     * 计算所有员工数量
     * @return
     * @throws SSException
     */
    @Override
    public int count() throws SSException{
        try{
            return employeeMapper.count();
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEmployeeException, e);
        }
    }

    /**
     * 根据分页获取未删除的系统用户信息
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    @Override
    public List<Employee> listAllNotDeleteByPage(int offset, int pageSize) throws SSException{
        List<Employee> employeeList = Collections.emptyList();
        try{
            employeeList = employeeMapper.listAllNotDeleteByPage(offset,pageSize);
            if(employeeList.size() > 0){
                for(Employee employee : employeeList){
                    setExtraValue(employee);
                }
            }
            return employeeList;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryArchivedEventByIdFail, e);
        }
    }

    /**
     * 根据Id查询系统用户
     *
     * @param id
     * @return
     * @throws SSException
     */
    @Override
    public Employee queryById(int id) throws SSException{
        Employee employee = new Employee();
        try{
            employee = commonDao.queryById(Employee.class,id);
            setExtraValue(employee);
            return employee;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryByEmployeeIdFail, e);
        }
    }

    /**
     * 根据partyd批量分配角色
     *
     * @param idList
     * @param roleId
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void setRoleByIdList(List<Integer> idList, int roleId,int memberGroupId) throws SSException{
        try {
            List<Integer> userIds = new ArrayList<Integer>();
            if (Assert.isNotNull(idList)) {
                for(Integer id : idList){
                    if(Assert.isNotNull(commonDao.queryById(Employee.class,id))){
                        int partyId = commonDao.queryById(Employee.class,id).getPartyId();
                         if(Assert.isNotNull(securityUserService.queryByPartyId(partyId))){
                             int userId = securityUserService.queryByPartyId(partyId).getId();
                             userIds.add(userId);
                             List<SecurityUserGroup> securityUserGroups = Collections.emptyList();
                             securityUserGroups = securityUserGroupService.listByUserId(userId);
                             //检查是否已被分配角色，若已分配，则更新安全用户组记录；否则，新增安全用户组记录
                             if(securityUserGroups.size() > 0){
                                 employeeMapper.batchSetRoleById(userIds,roleId);
                             }else {
                                 SecurityUserGroup securityUserGroup = new SecurityUserGroup();
                                 securityUserGroup.setGroupId(roleId);
                                 securityUserGroup.setUserId(userId);
                                 commonDao.insert(securityUserGroup);
                             }
                         }
                    }
                }
               if(Assert.isNotNull(memberGroupId) && memberGroupId > 0){
                    for(Integer id : idList){
                        if(Assert.isNotNull(commonDao.queryById(Employee.class,id))){
                            Employee employee = commonDao.queryById(Employee.class,id);
                            employee.setGroupId(memberGroupId);
                            commonDao.update(employee);
                        }
                    }
               }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryByEmployeeIdFail, e);
        }

    }

    /**
     *批量删除
     *
     * @param idList
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void deleteByIdList(List<Integer> idList) throws SSException{
        try{
            if(Assert.isNotNull(idList)) {
                employeeMapper.batchDeleteById(idList);
                for(Integer id : idList){
                    employeeMapper.delLoginStatusByPartyId(queryById(id).getPartyId());
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.DeleteEmployeeFail, e);
        }
    }

    /**
     * 根据partyId批量修改
     *
     * @param idList
     * @param userType
     * @param dicDepartment
     * @param dicPosition
     * @param dicImmediateS
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateByIdList(List<Integer> idList ,Integer userType, Integer dicDepartment, Integer dicPosition, Integer dicImmediateS) throws SSException {
        try{
            if(Assert.isNotNull(idList)) {
                employeeMapper.batchUpdateById(idList,userType,dicDepartment,dicPosition,dicImmediateS);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.UpdateByEmployeePartyIdFail, e);
        }
    }

    /**
     * 根据partyId查询用户姓名
     *
     * @param partyId
     * @return
     * @throws SSException
     */
    @Override
    public String queryNameByPartyId(int partyId) throws SSException{
        try{
              return employeeMapper.queryNameByPartyId(partyId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryNameByPartyIdFail, e);
        }

    }

    /**
     * 根据用户姓名查询
     *
     * @param employeeSearchDto
     * @return
     * @throws SSException
     */
    @Override
    public List<Employee> queryEmployeeListByName(EmployeeSearchDto employeeSearchDto) throws SSException{
        List<Employee> employees = Collections.emptyList();
        try{
            employees =  employeeMapper.queryEmployeeListByName(employeeSearchDto);
            for(Employee employee : employees){
                setExtraValue(employee);
            }
            return employees;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEmployeeListByNameFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delById(int id) throws SSException{
        try{
             if(!Assert.lessOrEqualZero(id)){
                 employeeMapper.delById(id);
                 employeeMapper.delLoginStatusByPartyId(queryById(id).getPartyId());
             }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.DelByEmployeeIdFail, e);
        }
    }

    @Override
    public int getGroupIdByPartyId(int partyId) throws SSException{
       try{
         return employeeMapper.getGroupIdByPartyId(partyId);
       }catch (Exception e){
           LogClerk.errLog.error(e);
           throw SSException.get(CrmException.GetGroupIdByPartyIdFail, e);
       }
    }

    @Override
    public int countEmployeeListByName(EmployeeSearchDto employeeSearchDto) throws SSException{
        try{
            return employeeMapper.countEmployeeListByName(employeeSearchDto);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountEmployeeListByNameFail, e);
        }
    }

    @Override
    public void delRoleById(List<Integer> idList) throws SSException{
        try{
            if(Assert.isNotNull(idList)){
             employeeMapper.delRoleById(idList);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.DelRoleByIdFail, e);
        }

    }

    @Override
    public List<Employee> listImmediateSByPartyId(int partyId) throws SSException{
        //存放所有直接上级的实体employee
        List<Employee> employees = new ArrayList<Employee>();
        try{
            if(Assert.isNotNull(partyId)){
                //根据partyId获取employee
                Employee employee = new Employee();
                if(Assert.isNotNull(employeeMapper.queryByPartyId(partyId))){
                    employee = employeeMapper.queryByPartyId(partyId);
                }

                if(Assert.isNotNull(employee.getDicImmediateS())){
                    //获取该employee的直接上级
                    int dicImmediateS = employee.getDicImmediateS();
                    if(Assert.isNotNull( securityGroupService.queryById(dicImmediateS))){
                        //获取employee的直接上级的角色实体
                        SecurityGroup securityGroup = securityGroupService.queryById(dicImmediateS);
                        //根据角色id获取所有有该角色的用户组关系实体
                        List<SecurityUserGroup> securityUserGroups = securityUserGroupService.listByGroupId(securityGroup.getId());
                        //循环遍历获取所有的直接上级的实体employee
                        for(SecurityUserGroup securityUserGroup: securityUserGroups){
                            if(Assert.isNotNull(securityUserGroup.getUserId())){
                                int userId = securityUserGroup.getUserId();
                                if(Assert.isNotNull(securityUserService.queryById(userId))){
                                    int partyId_ = securityUserService.queryById(userId).getPartyId();
                                    Employee employee1 = this.queryByPartyIdWithUsing(partyId_);
                                    if(Assert.isNotNull(employee1)){
                                        employees.add(employee1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return employees;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListImmediateSByPartyIdFail, e);
        }
    }

    /**
     * 检查登录名是否重名
     *
     * @param loginName
     * @return
     * @throws SSException
     */
    @Override
    public boolean checkLoginNameIsExist(String loginName) throws SSException{
        try{
             int count = employeeMapper.checkLoginNameIsExist(loginName);
             if(count == 0){
                 return true;
             }else {
                 return false;
             }
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListImmediateSByPartyIdFail, e);
        }
    }

    @Override
    public Employee queryByPartyIdWithUsing(int partyId) throws SSException{
        Employee employee = new Employee();
        try{
            if(Assert.isNotNull(partyId)){
                employee =  employeeMapper.queryByPartyIdWithUsing(partyId);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEmployeeByPartyIdWithUsingFail, e);
        }
        return employee;
    }

    @Override
    public List<Integer> listUnderlingsByPartyId(int partyId) throws SSException{
        List<Integer> partyIdList = Collections.emptyList();
        try{
            if(Assert.isNotNull("partyId")){
                //根据partyId获取employee
                Employee employee = new Employee();
                if (Assert.isNotNull(securityUserService.queryByPartyId(partyId))) {
                    SecurityUser securityUser = securityUserService.queryByPartyId(partyId);
                    int userId = securityUser.getId();
                    //获取角色
                    SecurityUserGroup securityUserGroup = securityUserGroupService.queryByUserId(userId);
                    SecurityGroup securityGroup = securityGroupService.queryById(securityUserGroup.getGroupId());

                    //获取角色id
                    int roleId = securityGroup.getId();
                    partyIdList = employeeMapper.listUnderlingsByImmediateS(roleId);
                }

            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEmployeeByPartyIdWithUsingFail, e);
        }
        return partyIdList;
    }

    @Override
    public SecurityGroup querySecurityGroupByPartyId(int partyId) throws SSException{
        SecurityGroup securityGroup = new SecurityGroup();
        try{
          if(Assert.isNotNull(partyId)){
              SecurityUser securityUser = securityUserService.queryByPartyId(partyId);
              SecurityUserGroup securityUserGroup = securityUserGroupService.queryByUserId(securityUser.getId());
              securityGroup = securityGroupService.queryById(securityUserGroup.getGroupId());
          }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QuerySecurityGroupByPartyIdFail, e);
        }
        return securityGroup;
    }





    /*******************************私有方法**************************************/

    /**
     * 设置employee用于显示列表的名称
     *
     * @param employee
     * @return
     * @throws SSException
     */
    private Employee setExtraValue(Employee employee) throws Exception{
        if (Assert.isNotNull(employee)){
            SecurityUser securityUser = new SecurityUser();
            try{
                if(Assert.isNotNull(employee.getPartyId())){
                    securityUser = securityUserService.queryByPartyId(employee.getPartyId());
                    if(Assert.isNotNull(securityUser)){
                        employee.setLoginName(securityUser.getLoginName());
                        employee.setPassword(securityUser.getPassword());
                    }
                }
                if(Assert.isNotNull(employee.getDicDepartment()) && Assert.isNotNull(dictionaryService.queryById(employee.getDicDepartment()))){
                    employee.setDepartment(dictionaryService.queryById(employee.getDicDepartment()).getName());
                }else{
                    employee.setDepartment("无");
                }
                if(Assert.isNotNull(employee.getDicPosition()) && Assert.isNotNull(dictionaryService.queryById(employee.getDicPosition()))){
                    employee.setPosition(dictionaryService.queryById(employee.getDicPosition()).getName());
                }else{
                    employee.setPosition("无");
                }
                if(Assert.isNotNull(employee.getDicImmediateS()) && Assert.isNotNull(securityGroupService.queryById(employee.getDicImmediateS()))){
                    employee.setImmediateS(securityGroupService.queryById(employee.getDicImmediateS()).getName());
                }else{
                    employee.setImmediateS("无");
                }
                if(Assert.isNotNull(employee.getUserType())){
                    if(employee.getUserType() == 1){
                        employee.setUserTypeValue("超级管理员");
                    }else if(employee.getUserType() == 2){
                        employee.setUserTypeValue("系统用户");
                    }
                }else {
                    employee.setUserTypeValue("无");
                }

                if(Assert.isNotNull(employee.getAccount())){
                    if(Assert.isNotNull(employeeMapper.queryByPartyId(employee.getAccount()))){
                        employee.setAccountValue(employeeMapper.queryByPartyId(employee.getAccount()).getName());
                    }
                }
                if(Assert.isNotNull(employee.getStatus())){
                    if(employee.getStatus() == 1){
                        employee.setStatusValue("启用");
                    }else if(employee.getStatus() == 2){
                        employee.setStatusValue("禁用");
                    }
                }
                //设置角色名称
                if(Assert.isNotNull(employee.getId())){
                    int partyId = employee.getPartyId();
                    if(Assert.isNotNull(securityUserService.queryByPartyId(partyId))){
                        int userId = securityUserService.queryByPartyId(partyId).getId();
                        List<SecurityUserGroup> securityUserGroups = Collections.emptyList();
                        securityUserGroups = securityUserGroupService.listByUserId(userId);
                        //检查是否已被分配角色，若已分配，显示角色名称，否则显示未分配
                        if( securityUserGroups.size() > 0){
                            int roleId = securityUserGroups.get(0).getGroupId();
                            employee.setRoleName((commonDao.queryById(SecurityGroup.class,roleId)).getName());
                        }else{
                            employee.setRoleName("未分配");
                        }
                    }

                }
                //设置管理会员组名称
                if(Assert.isNotNull(employee.getGroupId())){
                    employee.setMemberGroupName(commonDao.queryById(Member.class,employee.getGroupId()).getName());
                }
            }catch(Exception e){
                LogClerk.errLog.error(e);
                throw SSException.get(CrmException.SetExtraEmployeeDataFail, e);
            }
        }
        return employee;
    }

}
