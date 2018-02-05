package com.pandawork.crm.web.controller.admin.client.visit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.dto.client.visit.VisitDetailSearchDto;
import com.pandawork.crm.common.dto.client.visit.VisitSearchDto;
import com.pandawork.crm.common.entity.client.visit.Visit;
import com.pandawork.crm.common.entity.party.dictionary.Dictionary;
import com.pandawork.crm.common.enums.client.GenderEnums;
import com.pandawork.crm.common.enums.client.VisitFromEnums;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.enums.party.dictionary.DictionaryEnums;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.DateUtils;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

/**
 * VisitController
 * Author： liping
 * Date: 2017/7/24
 * Time: 9:49
 */
@Controller
@Module(ModuleEnums.AdminClientVisit)
@RequestMapping(value = URLConstants.ADMIN_CLIENT_VISIT_URL)
public class VisitController extends AbstractController{

    /**
     * 去列表页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminClientVisitList)
    @RequestMapping(value = {"","list"},method = RequestMethod.GET)
    public String toList(Model model){
        //用户类型字典值
        String typeName = "";

        int dataCount = 0;
        try{
            dataCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE,visitService.count());
            List<Dictionary> clientTypeList = dictionaryService.listByPId(DictionaryEnums.DIC_CLIENT_TYPE.getId());
            model.addAttribute("dicTypeList",clientTypeList);
            model.addAttribute("dataCount",dataCount);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/client/visit/list";
    }

    /**
     * 查询条件searchDto列表页
     *
     * @param searchDto
     * @return
     */
    @Module(ModuleEnums.AdminClientVisitList)
    @RequestMapping(value = {"ajax/list"},method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(Model model,VisitSearchDto searchDto,HttpSession httpSession) {
        JSONObject json = new JSONObject();
        Integer pageSize = searchDto.getPageSize();
        pageSize = (pageSize == null || pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
        searchDto.setPageSize(pageSize);
        //根据page计算offset
        Integer pageNo = searchDto.getPage();
        int offset = 0, numCount = 0;
        if (Assert.isNotNull(pageNo)) {
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * pageSize;
            searchDto.setOffset(offset);
        }
        JSONArray jsonArray = new JSONArray();
        //用户·性别枚举值
        String genderName = "";
        //用户类型字典值
        String typeName = "";
        List<Visit> visitList = Collections.emptyList();
        int dataCount = 0;
        //处理中文乱码
//        if(searchDto.getClientName() != null & !"".equals(searchDto.getClientName())){
//            try {
//                String str = new String((searchDto.getClientName()).getBytes("iso8859-1"),"utf-8");
//                searchDto.setClientName(str);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
        //查询来访次数--默认算入来访信息初始化记录
        if(Assert.isNotNull(searchDto.getVisitTimes())){
            searchDto.setVisitTimes(searchDto.getVisitTimes() + 1);
        }
        try{
            int createdPartyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
            //获取登录用户的id
            int userId = securityUserService.queryByPartyId(createdPartyId).getId();
            //根据用户id获取角色id
            int memberGroupId = 0;
            int securityGroupId = securityUserGroupService.queryByUserId(userId).getGroupId();
            if(securityGroupId  != 3){
                //若为超级管理员可查看所有
                searchDto.setMemberGroupId(null);
            }else{
                //若为其他包医人只可查看自己分组
                memberGroupId = employeeService.getGroupIdByPartyId(createdPartyId);
                searchDto.setMemberGroupId(memberGroupId);
            }
            visitList = visitService.listByVisitSearchDto(searchDto);
            for(int i = 0 ; i < visitList.size() ; i++){
                //设置患者枚举性别
                if(visitList.get(i).getGender() == 1){
                    genderName = GenderEnums.Male.getGender();
                }else if(visitList.get(i).getGender() == 2){
                    genderName = GenderEnums.Female.getGender();
                }else if(visitList.get(i).getGender() == 3){
                    genderName = GenderEnums.Other.getGender();
                }
                visitList.get(i).setGenderName(genderName);
                typeName = dictionaryService.queryById(visitList.get(i).getDicType()).getName();
                //设置患者类型字典值
                visitList.get(i).setTypeName(typeName);
            }
            int  i = 1 + pageNo * 10;
            String firstVisitType = "";
            String visitTime = "";
            String dicType = "";
            for(Visit visit : visitList){
                JSONObject jsonObject = new JSONObject();
                //判断是否首诊  PS：因为list方法中要计算来访次数故此处也只能将sql语句分离成两个方法
                Visit visitType = visitService.getLastVisitByClientId(visit.getClientId());
                if(Assert.isNotNull(visitType.getFirstVisitType()) && Assert.isNotNull(visitType.getVisitFrom())){
                    firstVisitType = visitType.getFirstVisitType()+VisitFromEnums.valueOf(visitType.getVisitFrom()).getVisitFrom();
                }else{
                    firstVisitType = "";
                }
                //来访时间
                if(Assert.isNotNull(visit.getVisitTime())){
                    visitTime = DateUtils.formatDateSimple(visit.getVisitTime());
                }
                dicType = dictionaryService.queryById(visit.getDicType()).getName();
                jsonObject.put("number",i++);
                jsonObject.put("clientId", visit.getClientId());
                jsonObject.put("clientName", visit.getClientName());
                jsonObject.put("genderName",visit.getGenderName());
                jsonObject.put("clientIdcardNum",visit.getClientIdcardNum());
                jsonObject.put("clientTel",visit.getClientTel());
                jsonObject.put("clientType",dicType);
                jsonObject.put("firstVisitType",firstVisitType);
                jsonObject.put("visitTimes",visit.getVisitTimes()-1);
                jsonObject.put("visitTime",visitTime);
                jsonObject.put("cost",visit.getCost());
                jsonArray.add(jsonObject);
            }
            //计算总页数
            searchDto.setPageSize(null);
            List<Visit> list = Collections.emptyList();
            list = visitService.listByVisitSearchDto(searchDto);
            numCount = list.size();
            dataCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE,numCount);
            model.addAttribute("dataCount",dataCount);
        }catch(SSException e){
                LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }catch (Exception e){
            return json;
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", jsonArray);
        json.put("dataCount", dataCount);
        json.put("numCount", numCount);
        return json;
    }

    /**
     * 姓名搜索框模糊查询
     *
     * @param clientName
     * @return
     */
    @Module(ModuleEnums.AdminClientVisitList)
    @RequestMapping(value = {"ajax/clientName"},method = RequestMethod.GET)
    @ResponseBody
    public JSON searchByName(@RequestParam("clientName")String  clientName){
        JSONArray jsonArray = new JSONArray();
        VisitSearchDto searchDto = new VisitSearchDto();
        try{
            searchDto.setClientName(clientName);
            List<Visit> visitList = visitService.listByVisitSearchDto(searchDto);
            for(Visit visit : visitList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",visit.getClientId());
                jsonObject.put("name",visit.getClientName());
                jsonArray.add(jsonObject);
            }
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray);
    }

    /**
     * 身份证号搜索框模糊查询
     *
     * @param idcard
     * @return
     */
    @Module(ModuleEnums.AdminClientVisitList)
    @RequestMapping(value = {"ajax/idcard"},method = RequestMethod.GET)
    @ResponseBody
    public JSON searchByIdCard(@RequestParam("idcard")String  idcard){
        JSONArray jsonArray = new JSONArray();
        VisitSearchDto searchDto = new VisitSearchDto();
        try{
            searchDto.setClientIdcardNum(idcard);
            List<Visit> visitList = visitService.listByVisitSearchDto(searchDto);
            for(Visit visit : visitList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",visit.getClientId());
                jsonObject.put("name",visit.getClientIdcardNum());
                jsonArray.add(jsonObject);
            }
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray);
    }

    /**
     * 电话搜索框模糊查询
     *
     * @param tel
     * @return
     */
    @Module(ModuleEnums.AdminClientVisitList)
    @RequestMapping(value = {"ajax/tel"},method = RequestMethod.GET)
    @ResponseBody
    public JSON searchByTel(@RequestParam("tel")String  tel){
        JSONArray jsonArray = new JSONArray();
        VisitSearchDto searchDto = new VisitSearchDto();
        try{
            searchDto.setClientTel(tel);
            List<Visit> visitList = visitService.listByVisitSearchDto(searchDto);
            for(Visit visit : visitList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",visit.getClientId());
                jsonObject.put("name",visit.getClientTel());
                jsonArray.add(jsonObject);
            }
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray);
    }

    /**
     * 去详情列表页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminClientVisitDetailList)
    @RequestMapping(value = {"detailList"},method = RequestMethod.GET)
    public String toDetailList(Model model,@RequestParam("clientId")Integer clientId){
        int dataCount = 0;
        VisitDetailSearchDto searchDto = new VisitDetailSearchDto();
        searchDto.setClientId(clientId);
        try{
            dataCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE,visitService.countByVisitDetailSearchDto(searchDto));
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        model.addAttribute("dataCount",dataCount);
        model.addAttribute("clientId",clientId);
        return "admin/client/visit/detailList";
    }

    /**
     * 详情页
     *
     * @param searchDto
     * @return
     */
    @Module(ModuleEnums.AdminClientVisitDetailList)
    @RequestMapping(value = {"ajax/detailList"},method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(VisitDetailSearchDto searchDto) {
        JSONObject json = new JSONObject();
        Integer pageSize = searchDto.getPageSize();
        pageSize = (pageSize == null || pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
        searchDto.setPageSize(pageSize);
        Integer pageNo = searchDto.getPage();
        int offset = 0;
        if (Assert.isNotNull(pageNo)) {
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * pageSize;
            searchDto.setOffset(offset);
        }
        if(searchDto.getVisitContent() != null & !"".equals(searchDto.getVisitContent())){
            try {
                String str = new String((searchDto.getVisitContent()).getBytes("iso8859-1"),"utf-8");
                searchDto.setVisitContent(str);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        JSONArray jsonArray = new JSONArray();
        List<Visit> visitList = Collections.emptyList();
        int dataCount = 0, numCount = 0;
        String visitFromName = "";
        try{
            visitList = visitService.listByVisitDetailSearchDto(searchDto);
            int  i = 1;
            if(pageNo != null)
            i = 1 + pageNo * 10;
            for(Visit visit : visitList){
                JSONObject jsonObject = new JSONObject();
                if(Assert.isNotNull(visit.getVisitFrom())){
                    visitFromName = VisitFromEnums.valueOf(visit.getVisitFrom()).getVisitFrom();
                }
                jsonObject.put("number",i++);
                jsonObject.put("id",visit.getId());
                jsonObject.put("clientId", visit.getClientId());
                jsonObject.put("clientName", visit.getClientName());
                jsonObject.put("visitFrom",visitFromName);
                jsonObject.put("visitTime",visit.getVisitTime());
                jsonObject.put("visitContent",visit.getVisitContent());
                jsonObject.put("cost",visit.getCost());
                jsonObject.put("partyName",visit.getPartyName());
                jsonObject.put("lastModifiedTime",visit.getLastModifiedTime());
                jsonArray.add(jsonObject);
            }
            if(Assert.isNull(searchDto.getVisitFrom())){
                numCount = visitService.countByVisitDetailSearchDto(searchDto) - 1;
            }else{
                numCount = visitService.countByVisitDetailSearchDto(searchDto);
            }
            if(numCount < 0){
                numCount = 0;
            }
            dataCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE,numCount);
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", jsonArray);
        json.put("dataCount", dataCount);
        json.put("numCount", numCount);
        return json;
    }

    /**
     * 删除一条来访详情
     *
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminClientVisitDetailDelete)
    @RequestMapping(value = "ajax/del",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delVisitDetail(@RequestParam("id") Integer id){
        try{
            visitService.delById(id);
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 添加来访详情页
     *
     * @param visit
     * @return
     */
    @Module(ModuleEnums.AdminClientVisitDetailNew)
    @RequestMapping(value = {"ajax/add"},method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxAdd(Visit visit){
        try{
            //获得登录的partyId
            HttpSession session = getRequest().getSession();
            Object o = session.getAttribute("partyId");
            Integer partyId = new Integer(o.toString());
            visit.setCreatedPartyId(partyId);
            //判断是否为首诊
            VisitDetailSearchDto searchDto = new VisitDetailSearchDto();
            searchDto.setClientId(visit.getClientId());
            List<Visit> visitList = visitService.listByVisitDetailSearchDto(searchDto);
            if (visitList.size() > 2) {
                visit.setFirstVisitType("非首诊");
            }else{
                visit.setFirstVisitType("首诊");
            }
            visitService.newVisit(visit);
        }catch(SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 修改来访详情页
     *
     * @param visit
     * @return
     */
    @Module(ModuleEnums.AdminClientVisitDetailUpdate)
    @RequestMapping(value = {"ajax/update"},method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxUpdate(Visit visit){
        try{
            //获得登录的partyId
            HttpSession session = getRequest().getSession();
            Object o = session.getAttribute("partyId");
            Integer partyId = new Integer(o.toString());
            visit.setCreatedPartyId(partyId);
            visitService.updateVisit(visit);
        }catch(SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }
}
