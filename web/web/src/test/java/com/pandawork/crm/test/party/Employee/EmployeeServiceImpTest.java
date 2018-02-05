package com.pandawork.crm.test.party.Employee;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.employee.EmployeeDto;
import com.pandawork.crm.common.entity.party.group.employee.Employee;
import com.pandawork.crm.common.utils.CommonUtil;
import com.pandawork.crm.common.utils.JmAndKlUtils;
import com.pandawork.crm.service.party.group.employee.EmployeeService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * EmployeeServiceImpTest
 * Author： csy
 * Date: 2017/7/20
 * Time: 21:36
 */
public class EmployeeServiceImpTest extends AbstractTestCase {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 测试添加用户的测试类
     *
     * @throws SSException
     */
    @Test
    public void newEmployeeTest() throws SSException {

        EmployeeDto employeeDto = new EmployeeDto();
        Employee employee = new Employee();
        employee.setName("张三");
        employee.setPhone("11111111");
        employeeService.newEmployee(employee);
        System.out.println(employee.getName());
        System.out.println(employee.getPhone());
    }

    /**
     * 测试更新用户信息
     * @throws SSException
     */
    @Test
    public void updateTest() throws SSException{
        Employee employee = new Employee();
        employee.setName("lili");
        employee.setPhone("789977899");
        employee.setDicDepartment(999);
        Integer partyId = 18;
        employee.setAccount(partyId);
        employeeService.update(employee);
        System.out.println(employee.getName());
        System.out.println(employee.getDicDepartment());
    }

    /**
     * 测试删除用户
     * @throws SSException
     */
    @Test
    public void delByPartyIdTest()throws SSException{
        employeeService.delByPartyId(9);
    }

    /**
     * 测试修改用户状态
     * @throws SSException
     */
    @Test
    public void updateStatusTest() throws SSException {
        employeeService.updateStatus(1,1);
    }

    /**
     * 测试批量分配角色
     *
     * @throws SSException
     */
    @Test
    public void setRoleByPartyIdListTest() throws SSException{
        List<Integer> partyIdList = new ArrayList<Integer>();
        for(int i = 2;i < 4;i++){
            partyIdList.add(i);
        }
        int memberGroupId = 1;
        employeeService.setRoleByIdList(partyIdList,2, memberGroupId);
    }

    /**
     * 测试批量删除
     *
     * @throws SSException
     */
    @Test
    public void deleteByPartyIdListTest() throws SSException{
        List<Integer> partyIdList = new ArrayList<Integer>();
        for(int i = 3;i < 7;i++){
            partyIdList.add(i);
        }
        employeeService.deleteByIdList(partyIdList);
    }

    /**
     * 批量修改用户信息
     *
     * @throws SSException
     */
    @Test
    public void updateByPartyIdListTest() throws SSException{
        List<Integer> partyIdList = new ArrayList<Integer>();
        for(int i = 1;i < 3;i++){
            partyIdList.add(i);
        }
        Integer userType = -1;
        Integer dicDepartment = 1;
        Integer dicPosition = 1;
        Integer dicImmediate = 1;

        employeeService.updateByIdList(partyIdList,userType,dicDepartment,dicPosition,dicImmediate);
    }

    /**
     * 根据partyId 查询用户姓名
     *
     * @throws SSException
     */
    @Test
    public void queryNameByPartyIdTest()throws SSException{
        System.out.println(employeeService.queryNameByPartyId(1));
    }


    @Test
    public void getGroupIdByPartyId() throws Exception{
        try{
            int group = employeeService.getGroupIdByPartyId(1);
            System.out.println(group);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void delRoleById() throws Exception{
        List<Integer> idList = new ArrayList<Integer>();
        idList.add(23);
        idList.add(24);
        try{
            employeeService.delRoleById(idList);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void listImmediateSByPartyId() throws Exception{
        int partyId = 3;
        try{
            employeeService.listImmediateSByPartyId(partyId);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void checkLoginNameIsExistTest() throws Exception{
        String name1 = "csy";
        String name2 = "lly";
        String name3 = "kerkg";

        employeeService.checkLoginNameIsExist(name1);
        System.out.println(1);
        employeeService.checkLoginNameIsExist(name2);
        System.out.println(2);
        employeeService.checkLoginNameIsExist(name3);
        System.out.println(3);
    }

    @Test
    public void batchSetRoleById() throws Exception{
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(50);
        ids.add(51);
        int roleId = 36;
        try{
            employeeService.setRoleByIdList(ids,roleId,1);
        }catch (Exception e){
            throw new Exception(e);
        }

    }

    @Test
    public void delByIdTest() throws Exception{

        try{
            employeeService.delById(62);
            List<Integer> idList = new ArrayList<Integer>();
            idList.add(63);
            idList.add(64);
            employeeService.deleteByIdList(idList);
            System.out.println("555");
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void queryByPartyIdWithUsing() throws Exception{
        int partyId = 1;
        try{
            employeeService.queryByPartyIdWithUsing(partyId);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void listUnderlingsByPartyId() throws Exception{
        int roleId = 1;
        try{
            employeeService.listUnderlingsByPartyId(roleId);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void querySecurityGroupByPartyId()throws Exception{
        int partyId = 1;
        try{
            employeeService.querySecurityGroupByPartyId(partyId);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void updateByIdList() throws Exception{
        List<Integer> idList = new ArrayList<Integer>();
        idList.add(100);
        idList.add(101);
        Integer userType = null;
        Integer dicPosition = null;
        Integer dicDepartment = null;
        Integer dicImmediateS = null;
        try{
            employeeService.updateByIdList(idList,userType,dicDepartment,dicPosition,dicImmediateS);
        }catch (Exception e){
            throw new Exception(e);
        }

    }

}