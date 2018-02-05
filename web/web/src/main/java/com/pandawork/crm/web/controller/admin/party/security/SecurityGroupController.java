package com.pandawork.crm.web.controller.admin.party.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.entity.party.dictionary.Dictionary;
import com.pandawork.crm.common.entity.party.security.SecurityGroup;
import com.pandawork.crm.common.entity.party.security.SecurityGroupPermission;
import com.pandawork.crm.common.entity.party.security.SecurityPermission;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.enums.party.dictionary.DictionaryEnums;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *安全组管理Controller
 *
 * @author Flying
 * @date 2017/7/17 10:56
 */
@Controller
@Module(ModuleEnums.AdminSAdmin)
@RequestMapping(value = URLConstants.ADMIN_PARTY_SECURITY_GROUP_URL)
public class SecurityGroupController extends AbstractController {

    /**
     * 去列表页面
     * @return
     */
    @Module(ModuleEnums.AdminPartySecurityGroupList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model) {
        int dataCount = 0;
        List<Dictionary> dictionaryList = new ArrayList<Dictionary>(); //角色的所有字典值
        List<SecurityGroup> securityGroupList = new ArrayList<SecurityGroup>(); //已添加的角色
        List<Dictionary> delDicGroup = new ArrayList<Dictionary>();//要删除的角色字典值
        try {
            securityGroupList = securityGroupService.listAll();
            dictionaryList = dictionaryService.listByPId(DictionaryEnums.DIC_SECURITY_GROUP.getId());
            for (SecurityGroup securityGroup : securityGroupList){
                for (Dictionary dictionary : dictionaryList){
                    if (dictionary.getName().equals(securityGroup.getName())){
                        delDicGroup.add(dictionary);
                    }
                }
            }
            dictionaryList.removeAll(delDicGroup);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            //return sendErrMsgAndErrCode(e);
        }
        model.addAttribute("dictionaryList", dictionaryList);
        return "admin/party/security/group/list";
    }

    /**
     * Ajax获取分页
     * @param pageNo
     * @return
     */
    @Module(ModuleEnums.AdminPartySecurityGroupList)
    @RequestMapping(value = "ajax/list", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listSecurityGroup(@RequestParam("pageNo") Integer pageNo){
        JSONObject json = new JSONObject();
        List<SecurityGroup> securityGroupList = Collections.emptyList();
        List<SecurityGroup> securityGroupList1 = Collections.emptyList();
        try {
            securityGroupList = securityGroupService.listByPage(pageNo, DEFAULT_PAGE_SIZE);
            securityGroupList1 = securityGroupService.listAll();
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        for (SecurityGroup  securityGroup: securityGroupList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", securityGroup.getId());
            jsonObject.put("name", securityGroup.getName());
            jsonObject.put("description", securityGroup.getDescription());
            jsonArray.add(jsonObject);
        }
        int numCount = 0, dataCount = 0;
        numCount = securityGroupList1.size();
        dataCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE,numCount);
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", jsonArray);
        json.put("dataCount", dataCount);
        json.put("numCount", numCount);
        return json;

    }

    /**
     * Ajax添加
     * @param securityGroup
     * @return
     */
    @Module(ModuleEnums.AdminPartySecurityGroupNew)
    @RequestMapping(value = "ajax/new", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxNewSecurityGroup(SecurityGroup securityGroup){
        try{
            securityGroupService.newSecurityGroup(securityGroup);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax修改
     * @param securityGroup
     * @return
     */
    @Module(ModuleEnums.AdminPartySecurityGroupUpdate)
    @RequestMapping(value = "ajax/update",method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject ajaxUpdateSecurityGroup(SecurityGroup securityGroup){
        try{
            securityGroupService.update(securityGroup);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax删除
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminPartySecurityGroupDel)
    @RequestMapping(value = "ajax/del",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxDelSecurityGroup(@RequestParam("id") Integer id){
        try{
            securityGroupService.delById(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 去安全组-权限列表页
     *
     * @param id
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminPartySecurityGroupPermissionList)
    @RequestMapping(value = "permission/{id}", method = RequestMethod.GET)
    public String toPermissionList(@PathVariable("id") Integer id,
                                   Model model) {
        int dataCount = 0;
        try {
            dataCount = securityGroupPermissionService.countByGroupId(id);
            dataCount = (int) Math.ceil(((double)dataCount)/DEFAULT_PAGE_SIZE);
            SecurityGroup securityGroup = securityGroupService.queryById(id);
            List<SecurityPermission> noSelectedPermissionList = securityGroupPermissionService.listNotSelectedPermission(id);
            //List<SecurityGroupPermission> securityGroupPermissionList = securityGroupPermissionService.listByGroupIdAndPage(id, 0, 10);

            model.addAttribute("securityGroup", securityGroup);
            model.addAttribute("noSelectedPermissionList", noSelectedPermissionList);
            model.addAttribute("dataCount", dataCount);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/party/security/group/permission";
    }

    /**
     * ajax 获取列表页
     *
     * @param id
     * @param pageNo
     * @return
     */
    @Module(ModuleEnums.AdminPartySecurityGroupPermissionList)
    @RequestMapping(value = "permission/{id}/ajax", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxPermissionList(@PathVariable("id") Integer id,
                                   @RequestParam("pageNo") Integer pageNo) {
        JSONObject json = new JSONObject();
        int pageSize = DEFAULT_PAGE_SIZE;
        int dataCount = 0, numCount = 0;
        List<SecurityGroupPermission> list = Collections.emptyList();
        try {
            numCount = securityGroupPermissionService.countByGroupId(id);
            dataCount = DataUtils.getPageCount(pageSize,numCount);
            list = securityGroupPermissionService.listByGroupIdAndPage(id, pageNo, pageSize);
            JSONArray jsonArray = new JSONArray();
            for (SecurityGroupPermission securityGroupPermission : list) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", securityGroupPermission.getId());
                jsonObject.put("permissionExpression", securityGroupPermission.getPermissionExpression());
                jsonObject.put("permissionDescription", securityGroupPermission.getPermissionDescription());
                jsonArray.add(jsonObject);
            }
            json.put("code", AJAX_SUCCESS_CODE);
            json.put("list", jsonArray);
            json.put("dataCount", dataCount);
            json.put("numCount", numCount);
            return json;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 添加权限
     *
     * @param groupId
     * @param permissionId
     * @return
     */
    @Module(ModuleEnums.AdminPartySecurityGroupPermissionNew)
    @RequestMapping(value = "permission", method = RequestMethod.POST)
    @ResponseBody
    public JSON newPermission(@RequestParam("groupId") Integer groupId,
                                @RequestParam("permissionId") Integer permissionId,
                                RedirectAttributes redirectAttributes) {
//        redirectAttributes.addFlashAttribute("code", 0);
//        redirectAttributes.addFlashAttribute("msg", "添加成功!");
        SecurityGroupPermission securityGroupPermission = new SecurityGroupPermission();
        securityGroupPermission.setGroupId(groupId);
        securityGroupPermission.setPermissionId(permissionId);
        try {
            securityGroupPermissionService.newSecurityGroupPermission(securityGroupPermission);
        } catch (SSException e) {
//            LogClerk.errLog.error(e);
//            redirectAttributes.addFlashAttribute("code", 1);
//            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

//        String redirectUrl = URLConstants.ADMIN_PARTY_SECURITY_GROUP_URL + "/permission/" + groupId;
//        return "redirect:/" + redirectUrl;
          return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * ajax删除权限
     *
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminPartySecurityGroupPermissionDelete)
    @RequestMapping(value = "permission/ajax/del", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxDeletePermission(@RequestParam("id") Integer id) {
        try {
            securityGroupPermissionService.delById(id);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }
}
