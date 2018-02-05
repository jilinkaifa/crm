package com.pandawork.crm.web.controller.admin.party.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.entity.party.security.SecurityPermission;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * 权限Controller
 *
 * @author Flying
 * @date 2017/7/20 16:04
 */
@Controller
@Module(ModuleEnums.AdminSAdmin)
@RequestMapping(value = URLConstants.ADMIN_PARTY_SECURITY_PERMISSION_URL)
public class SecurityPermissionController extends AbstractController {

    /**
     * 去列表
     *
     * @return
     */
    @Module(ModuleEnums.AdminPartySecurityPermissionList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model) {
        return "admin/party/security/permission/list";
    }

    /**
     * ajax获取分页数据
     *
     * @param pageNo
     * @return
     */
    @Module(ModuleEnums.AdminPartySecurityPermissionList)
    @RequestMapping(value = "ajax/list", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@RequestParam("pageNo") Integer pageNo) {
        JSONObject json = new JSONObject();
        int dataCount = 0, numCount = 0;
        pageNo = pageNo > 0 ?  pageNo - 1 : pageNo;
        List<SecurityPermission> list = Collections.emptyList();
        try {
            numCount = securityPermissionService.count();
            dataCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE,numCount);
            list = securityPermissionService.listByPage(pageNo, DEFAULT_PAGE_SIZE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        for (SecurityPermission securityPermission : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", securityPermission.getId());
            jsonObject.put("expression", securityPermission.getExpression());
            jsonObject.put("description", securityPermission.getDescription());
            jsonArray.add(jsonObject);
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", jsonArray);
        json.put("dataCount", dataCount);
        json.put("numCount", numCount);
        return json;
    }

    /**
     * ajax添加
     *
     * @param securityPermission
     * @return
     */
    @Module(ModuleEnums.AdminPartySecurityPermissionNew)
    @RequestMapping(value = "ajax/new", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxNew(SecurityPermission securityPermission) {
        try {
            securityPermissionService.newPermission(securityPermission);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * ajax更新
     *
     * @param securityPermission
     * @return
     */
    @Module(ModuleEnums.AdminPartySecurityPermissionUpdate)
    @RequestMapping(value = "ajax/update", method = RequestMethod.PUT)
    @ResponseBody
    public JSON ajaxUpdate(SecurityPermission securityPermission) {
        try {
            securityPermissionService.update(securityPermission);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * ajax删除
     *
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminPartySecurityPermissionDelete)
    @RequestMapping(value = "ajax/del", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxDelete(@RequestParam("id") Integer id) {
        try {
            securityPermissionService.delById(id);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }
}
