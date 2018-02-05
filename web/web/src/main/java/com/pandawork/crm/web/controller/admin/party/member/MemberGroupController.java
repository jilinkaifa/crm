package com.pandawork.crm.web.controller.admin.party.member;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.entity.party.member.Member;
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
 * @author Flying
 * @date 2017/7/17 10:59
 */
@Controller
@Module(ModuleEnums.AdminPartyMemberGroup)
@RequestMapping(value = URLConstants.ADMIN_PARTY_MEMBER_GROUP_URL)
public class MemberGroupController extends AbstractController {

    /**
     * 去列表页面
     * @return
     */
    @Module(ModuleEnums.AdminPartyMemberGroupList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model){
        int dataCount = 0;
        try{
            dataCount = memberGroupService.count();
            dataCount = (int) Math.ceil(((double)dataCount)/DEFAULT_PAGE_SIZE);
        }catch(SSException e){
            LogClerk.errLog.error(e);
        }
        model.addAttribute("dataCount", dataCount);
        return "admin/party/member/group/list";
    }

    /**
     * Ajax获取分页
     *
     * @param pageNo
     * @return
     */
    @Module(ModuleEnums.AdminPartyMemberGroupList)
    @RequestMapping(value = "ajax/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject listMember(@RequestParam("pageNo") Integer pageNo){
        JSONObject json = new JSONObject();
        int dataCount = 0, numCount = 0;
        List<Member> memberList = Collections.emptyList();
        try {
            numCount = memberGroupService.count();
            dataCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE,numCount);
            memberList = memberGroupService.listByPage(pageNo, DEFAULT_PAGE_SIZE);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        JSONArray jsonArray = new JSONArray();
        for (Member member : memberList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", member.getId());
            jsonObject.put("name", member.getName());
            jsonObject.put("comment", member.getComment());
            jsonObject.put("createdPartyId", member.getCreatedPartyId());
            jsonObject.put("createTime", member.getCreateTime());
            jsonObject.put("lastModifiedTime", member.getLastModifiedTime());
            jsonArray.add(jsonObject);
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", jsonArray);
        json.put("dataCount", dataCount);
        json.put("numCount", numCount);
        return json;
    }

    /**
     * Ajax增加
     *
     * @param member
     * @return
     */
    @Module(ModuleEnums.AdminPartyMemberGroupNew)
    @RequestMapping(value = "ajax/new", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxNewMemberGroup(Member member){
        try{
            memberGroupService.newMember(member);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * AJAX修改
     *
     * @param member
     * @return
     */
    @Module(ModuleEnums.AdminPartyMemberGroupUpdate)
    @RequestMapping(value = "ajax/update",method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject ajaxUpdateMemberGroup(Member member){
        try{
            memberGroupService.updateMember(member);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax删除
     *
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminPartyMemberGroupDel)
    @RequestMapping(value = "ajax/del",method = RequestMethod.POST)
    @ResponseBody
        public JSONObject ajaxDelMemberGroup(@RequestParam("id") Integer id){
        try{
            memberGroupService.delById(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
