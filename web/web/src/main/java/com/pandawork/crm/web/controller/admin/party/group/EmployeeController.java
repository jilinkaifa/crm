package com.pandawork.crm.web.controller.admin.party.group;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.dto.employee.EmployeeSearchDto;
import com.pandawork.crm.common.entity.party.dictionary.Dictionary;
import com.pandawork.crm.common.entity.party.group.employee.Employee;
import com.pandawork.crm.common.entity.party.member.Member;
import com.pandawork.crm.common.entity.party.security.SecurityGroup;
import com.pandawork.crm.common.entity.party.security.SecurityUser;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.enums.party.UserStatusEnums;
import com.pandawork.crm.common.enums.party.dictionary.DictionaryEnums;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.JmAndKlUtils;
import com.pandawork.crm.common.utils.SessionUserListener;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.service.party.dictionary.DictionaryService;
import com.pandawork.crm.service.party.group.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pandawork.crm.web.spring.AbstractController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author Flying
 * @date 2017/7/17 11:00
 */
@Controller
@Module(ModuleEnums.AdminUserManagementEmployee)
@RequestMapping(value = URLConstants.ADMIN_PARTY_GROUP_EMPLOYEE_URL)
public class EmployeeController extends AbstractController{

    private int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 去往系统用户列表页
     *
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeList)
    @RequestMapping(value = {"","list"},method = RequestMethod.GET)
    public String toList(Model model){
        int dataCount = 0;
        List<Dictionary> departments = Collections.emptyList();
        List<Dictionary> positions = Collections.emptyList();
        List<SecurityGroup> securityGroups = Collections.emptyList();
        List<Member> memberGroups = Collections.emptyList();
        try{
            dataCount = DataUtils.getPageCount(pageSize,employeeService.count());
            departments = dictionaryService.listByPId(DictionaryEnums.DIC_PARTY_DEPARTMENT.getId());
            positions = dictionaryService.listByPId(DictionaryEnums.DIC_PARTY_POSITION.getId());
            securityGroups = securityGroupService.listAll();
            memberGroups = memberGroupService.listAll();
        }catch (Exception e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        model.addAttribute("dataCount",dataCount);
        model.addAttribute("departments",departments);
        model.addAttribute("positions",positions);
        //直接上级以及角色均为从系统角色中获取
        model.addAttribute("immediateSs",securityGroups);
        model.addAttribute("roles",securityGroups);
        model.addAttribute("memberGroups",memberGroups);
        return "/admin/party/employee/list";
    }

    /**
     * 去往新增系统用户页
     *
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeNew)
    @RequestMapping(value = "new",method = RequestMethod.GET)
    public String toNew(Model model){
        List<Dictionary> departments = Collections.emptyList();
        List<Dictionary> positions = Collections.emptyList();
        List<SecurityGroup> immediateSs = Collections.emptyList();

        try{
            departments = dictionaryService.listByPId(DictionaryEnums.DIC_PARTY_DEPARTMENT.getId());
            positions = dictionaryService.listByPId(DictionaryEnums.DIC_PARTY_POSITION.getId());
            immediateSs =  securityGroupService.listAll();
        }catch (Exception e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        model.addAttribute("departments",departments);
        model.addAttribute("positions",positions);
        //直接上级以及角色均为从系统角色中获取
        model.addAttribute("immediateSs",immediateSs);
        return "/admin/party/employee/new";
    }

    /**
     * 去往编辑页
     *
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeUpdate)
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String toEdit(@RequestParam("id") int id,Model model){
        Employee employee = new Employee();
        List<Dictionary> departments = Collections.emptyList();
        List<Dictionary> positions = Collections.emptyList();
        List<SecurityGroup> immediateSs = Collections.emptyList();
        List<Member> memberGroups = Collections.emptyList();
        try{
            employee = employeeService.queryById(id);
            employee.setPassword(JmAndKlUtils.JM(employee.getPassword()));
            departments = dictionaryService.listByPId(DictionaryEnums.DIC_PARTY_DEPARTMENT.getId());
            positions = dictionaryService.listByPId(DictionaryEnums.DIC_PARTY_POSITION.getId());
            immediateSs = securityGroupService.listAll();
            memberGroups = memberGroupService.listAll();
        }catch (Exception e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        model.addAttribute("employee",employee);
        model.addAttribute("departments",departments);
        model.addAttribute("positions",positions);
        model.addAttribute("immediateSs",immediateSs);
        model.addAttribute("memberGroups",memberGroups);
        return "/admin/party/employee/edit";
    }

    /**
     * 去往详情页
     *
     * @param id
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeDetail)
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String toDetail(@RequestParam("id") int id,Model model){
        Employee employee = new Employee();
        List<Dictionary> departments = Collections.emptyList();
        List<Dictionary> positions = Collections.emptyList();
        List<SecurityGroup> immediateSs = Collections.emptyList();
        try{
            employee = employeeService.queryById(id);
            employee.setPassword(JmAndKlUtils.JM(employee.getPassword()));
            model.addAttribute("employee",employee);
            departments = dictionaryService.listByPId(DictionaryEnums.DIC_PARTY_DEPARTMENT.getId());
            positions = dictionaryService.listByPId(DictionaryEnums.DIC_PARTY_POSITION.getId());
            immediateSs = securityGroupService.listAll();
            model.addAttribute("departments",departments);
            model.addAttribute("positions",positions);
            model.addAttribute("immediateSs",immediateSs);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return "/admin/party/employee/detail";
    }

    /**
     * 角色分配
     *
     * @param idList
     * @param role
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeSetRole)
    @RequestMapping(value = "ajax/setRole", method = RequestMethod.GET)
    @ResponseBody
    public JSON toDistribution(@RequestParam("idList") String idList,
                               @RequestParam("role") Integer role,
                               @RequestParam(value = "memberGroupId",required = false) Integer memberGroupId,
                               Model model){
        JSONObject json = new JSONObject();
        List<Integer> list = new ArrayList<Integer>();
        int code = AJAX_SUCCESS_CODE;
        try{
            if(Assert.isNull(memberGroupId)){
                memberGroupId = 0;
            }
            //查看设置角色的用户是否为登录状态
            if(Assert.isNotNull(idList)){
                list = DataUtils.stringToListInt(idList);
                for(int id : list){
                    int partyId = employeeService.queryById(id).getPartyId();
                    if(SessionUserListener.checkIfHasLogin(partyId)){
                        code = 2;
                        break;
                    }
                }
            }
            if(Assert.isNotNull(role) && Assert.isNotNull(idList)){
                employeeService.setRoleByIdList(list, role, memberGroupId);
            }
            if(Assert.isNull(role) && Assert.isNotNull(idList)){
                employeeService.delRoleById(list);
            }
            json.put("code",code);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return json;
    }

    /**
     * 批量删除
     *
     * @param idStr
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeDelete)
    @RequestMapping(value = "ajax/batchDelete", method = RequestMethod.GET)
    @ResponseBody
    public JSON toBatchDelete(@RequestParam("idList") String idStr){
        JSONObject json = new JSONObject();
        try{
            employeeService.deleteByIdList(DataUtils.stringToListInt(idStr));
            json.put("code",AJAX_SUCCESS_CODE);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return json;
    }


    /**
     * 批量修改系统用户信息
     *
     * @param idStr
     * @param userType
     * @param dicDepartment
     * @param dicPosition
     * @param dicImmediateS
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeUpdate)
    @RequestMapping(value = "ajax/batchUpdate", method = RequestMethod.POST)
    @ResponseBody
    public JSON toBatchUpdate(@RequestParam("idList") String idStr,
                              @RequestParam("userType") Integer userType,
                              @RequestParam("dicDepartment") Integer dicDepartment,
                              @RequestParam("dicPosition") Integer dicPosition,
                              @RequestParam("dicImmediateS") Integer dicImmediateS){
        List<Integer> idList = DataUtils.stringToListInt(idStr);
        try{
            employeeService.updateByIdList(idList, userType, dicDepartment, dicPosition,dicImmediateS);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }


    /**
     * ajax根据分页获取系统用户信息
     *
     * @param curPage
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeList)
    @RequestMapping(value = "ajax/list", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxListEmployeeInfo(@RequestParam("pageNo") Integer curPage){
        List<Employee> employeeList = Collections.emptyList();
        JSONObject jsonObject = new JSONObject();
        int dataCount = 0, numCount = 0;
        try{
            employeeList = employeeService.listAllNotDeleteByPage(curPage,pageSize);
            numCount = employeeService.count();
            dataCount =DataUtils.getPageCount(pageSize,numCount);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        jsonObject.put("code",AJAX_SUCCESS_CODE);
        jsonObject.put("list",employeeList);
        jsonObject.put("dataCount",dataCount);
        jsonObject.put("numCount", numCount);
        return jsonObject;
    }

    /**
     * 新增系统用户信息
     *
     * @param employee
     * @param httpSession
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeNew)
    @RequestMapping(value = "ajax/save", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject newEmployeeInfo(Employee employee,
                                      HttpSession httpSession){
       try{
           if(Assert.isNotNull(httpSession.getAttribute("partyId"))){
               int partyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
               employee.setAccount(partyId);
               employeeService.newEmployee(employee);
           }
       }catch (SSException e){
           LogClerk.errLog.error(e);
           return sendErrMsgAndErrCode(e);
       }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 更新系统用户信息
     *
     * @param employee
     * @param httpSession
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeUpdate)
    @RequestMapping(value = "ajax/update",method = RequestMethod.POST)
    @ResponseBody
    public JSON updateEmployeeInfo(Employee employee,
                                   HttpSession httpSession){
        try{
            if(Assert.isNotNull(httpSession.getAttribute("partyId"))){
                int partyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
                employee.setAccount(partyId);
                employeeService.update(employee);
            }
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * ajax删除系统用户
     *
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeDelete)
    @RequestMapping(value = "ajax/del",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delEmployee(@RequestParam("id") Integer id){
        try {
            if(Assert.isNotNull(id)){
                employeeService.delById(id);
            }
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 修改用户状态
     *
     * @param partyId
     * @param status
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeUpdate)
    @RequestMapping(value = "ajax/status",method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateEmployeeStatus(@RequestParam("partyId")Integer partyId,
                                           @RequestParam("status")Integer status){
        try {
            if(status==1) {
                employeeService.updateStatus(partyId, UserStatusEnums.Enabled.getId());
            }
            if(status==2) {
                employeeService.updateStatus(partyId, UserStatusEnums.Disabled.getId());
            }
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 根据用户姓名模糊查询用户信息
     *
     * @param name
     * @param curPage
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeList)
    @RequestMapping(value = "ajax/search",method = RequestMethod.GET)
    @ResponseBody
    public JSON searchByName(@RequestParam(value = "name",required = false) String name ,
                             @RequestParam("pageNo") Integer curPage) {
        JSONObject json = new JSONObject();
        int offset = (curPage - 1) * pageSize;
        EmployeeSearchDto employeeSearchDto = new EmployeeSearchDto(name,offset,pageSize);
        List<Employee> employeeList = new ArrayList<Employee>();
        int dataCount = 0 , numCount = 0;
        try{
            employeeList = employeeService.queryEmployeeListByName(employeeSearchDto);
            numCount = employeeService.countEmployeeListByName(employeeSearchDto);
            dataCount = DataUtils.getPageCount(pageSize,numCount);
            json.put("code",AJAX_SUCCESS_CODE);
            json.put("list",employeeList);
            json.put("dataCount",dataCount);
            json.put("numCount", numCount);
            return json;
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }


    /**
     * 检查登录名重名
     *
     * @param userName
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeUpdate)
    @RequestMapping(value = "ajax/check/loginname",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject checkLoginName(@RequestParam("userName")String userName){
        JSONObject jsonObject = new JSONObject();
        try{
            Boolean resault = employeeService.checkLoginNameIsExist(userName);
            if(resault == true){
                jsonObject.put("code",AJAX_SUCCESS_CODE);
                return jsonObject;
            }else {
                jsonObject.put("code",AJAX_FAILURE_CODE);
                return jsonObject;
            }
        }catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

}
