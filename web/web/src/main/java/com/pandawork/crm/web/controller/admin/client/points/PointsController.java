package com.pandawork.crm.web.controller.admin.client.points;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.dto.client.points.PointsSearchDto;
import com.pandawork.crm.common.entity.client.points.Points;
import com.pandawork.crm.common.enums.client.MemberStatusEnums;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * PointsController
 * Author： liping
 * Date: 2017/8/1
 * Time: 10:55
 */
@Controller
@Module(ModuleEnums.AdminClientPoints)
@RequestMapping(value = URLConstants.ADMIN_CLIENT_POINTS_URL)
public class PointsController extends AbstractController{

    /**
     * 去列表页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminClientPointsList)
    @RequestMapping(value = {"","list"},method = RequestMethod.GET)
    public String toList(Model model){
        int dataCount = 0;
        PointsSearchDto searchDto = new PointsSearchDto();
        try{
            dataCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE,pointsService.count(searchDto));
            model.addAttribute("dataCount",dataCount);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/client/points/list";
    }

    /**
     * searchDto查询
     *
     * @param model
     * @param searchDto
     * @return
     */
    @Module(ModuleEnums.AdminClientPointsList)
    @RequestMapping(value = {"ajax/list"},method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(Model model, PointsSearchDto searchDto, HttpSession httpSession){
        JSONObject json = new JSONObject();
        Integer pageSize = searchDto.getPageSize();
        pageSize = (pageSize == null || pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
        searchDto.setPageSize(pageSize);
        //根据page计算offset
        Integer pageNo = searchDto.getPage();
        int offset = 0;
        if (Assert.isNotNull(pageNo)) {
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * pageSize;
            searchDto.setOffset(offset);
        }
        JSONArray jsonArray = new JSONArray();
        List<Points> pointsList = new ArrayList<Points>();
        int dataCount = 0,numCount = 0;
//        //处理中文乱码
//        if(searchDto.getClientName() != null & !"".equals(searchDto.getClientName())){
//            try {
//                String str = new String((searchDto.getClientName()).getBytes("iso8859-1"),"utf-8");
//                searchDto.setClientName(str);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
        try{
            //获取操作人
            int createdPartyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
            //获取登录用户的id
            int userId = securityUserService.queryByPartyId(createdPartyId).getId();
            //根据用户id获取角色id
            int securityGroupId = securityUserGroupService.queryByUserId(userId).getGroupId();
            int memberGroupId = 0;
            if(securityGroupId !=3){
                //若为超级管理员可查看所有
                searchDto.setMemberGroupId(null);
            }else{
                //若为其他包医人只可添加自己分组
                memberGroupId = employeeService.getGroupIdByPartyId(createdPartyId);
                searchDto.setMemberGroupId(memberGroupId);
            }
            pointsList = pointsService.listByPointsSearchDto(searchDto);
            int  i = 1 + pageNo * 10;
            for(Points points : pointsList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("number",i++);
                jsonObject.put("clientId", points.getClientId());
                jsonObject.put("clientName", points.getClientName());
                jsonObject.put("clientIdcardNum",points.getClientIdcardNum());
                jsonObject.put("clientTel",points.getClientTel());
                jsonObject.put("allCost",points.getAllCost());
                jsonObject.put("times",points.getTimes()-1);
                jsonObject.put("lastVisitDate",points.getLastVisitDate());
                jsonObject.put("currentSumpoints",points.getCurrentSumpoints());
                jsonObject.put("currentPoints",points.getCurrentPoints());
                jsonArray.add(jsonObject);
            }
            //numCount = pointsList.size();
            numCount = pointsService.countByPointsSearchDto(searchDto);
            //计算总页数
            dataCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE,numCount);
            model.addAttribute("dataCount",dataCount);
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
     * 姓名搜索框模糊查询
     *
     * @param clientName
     * @return
     */
    @Module(ModuleEnums.AdminClientPointsList)
    @RequestMapping(value = {"ajax/clientName"},method = RequestMethod.GET)
    @ResponseBody
    public JSON searchByName(@RequestParam("clientName")String  clientName){
        JSONArray jsonArray = new JSONArray();
        PointsSearchDto searchDto = new PointsSearchDto();
        try{
            searchDto.setClientName(clientName);
            List<Points> pointsList = pointsService.listByPointsSearchDto(searchDto);
            for(Points points : pointsList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",points.getClientId());
                jsonObject.put("name",points.getClientName());
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
    @Module(ModuleEnums.AdminClientPointsList)
    @RequestMapping(value = {"ajax/idcard"},method = RequestMethod.GET)
    @ResponseBody
    public JSON searchByIdCard(@RequestParam("idcard")String  idcard){
        JSONArray jsonArray = new JSONArray();
        PointsSearchDto searchDto = new PointsSearchDto();
        try{
            searchDto.setClientIdcardNum(idcard);
            List<Points> pointsList = pointsService.listByPointsSearchDto(searchDto);
            for(Points points : pointsList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",points.getClientId());
                jsonObject.put("name",points.getClientIdcardNum());
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
    @Module(ModuleEnums.AdminClientPointsList)
    @RequestMapping(value = {"ajax/tel"},method = RequestMethod.GET)
    @ResponseBody
    public JSON searchByTel(@RequestParam("tel")String  tel){
        JSONArray jsonArray = new JSONArray();
        PointsSearchDto searchDto = new PointsSearchDto();
        try{
            searchDto.setClientTel(tel);
            List<Points> pointsList = pointsService.listByPointsSearchDto(searchDto);
            for(Points points : pointsList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",points.getClientId());
                jsonObject.put("name",points.getClientTel());
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
    @Module(ModuleEnums.AdminClientPointsDetailList)
    @RequestMapping(value = {"detailList"},method = RequestMethod.GET)
    public String toDetailList(Model model,@RequestParam("clientId")Integer clientId){
        int dataCount = 0;
        PointsSearchDto searchDto = new PointsSearchDto();
        searchDto.setClientId(clientId);
        try{
            dataCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE,pointsService.countDetail(searchDto));
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        model.addAttribute("dataCount",dataCount);
        model.addAttribute("clientId",clientId);
        return "admin/client/points/detailList";
    }
    /**
     * 详情页
     *
     * @param searchDto
     * @return
     */
    @Module(ModuleEnums.AdminClientPointsDetailList)
    @RequestMapping(value = {"ajax/detailList"},method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxDetailList(PointsSearchDto searchDto) {
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
        //处理接收搜索框中文的乱码问题
        if(searchDto.getEventName() != null & !"".equals(searchDto.getEventName())){
            try {
                String str = new String((searchDto.getEventName()).getBytes("iso8859-1"),"utf-8");
                searchDto.setEventName(str);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        JSONArray jsonArray = new JSONArray();
        List<Points> pointsList = new ArrayList<Points>();
        int dataCount = 0, numCount = 0;
//        Integer memberStatus = 0;
        try{
            pointsList = pointsService.listByClientId(searchDto);
            int  i = 1;
            if(pageNo != null)
                i = 1 + pageNo * 10;
            int curPoints = 0;
            int sumPoints = 0;
            String eventName = "";
            for(Points points : pointsList){
                JSONObject jsonObject = new JSONObject();
                if(Assert.isNotNull(points.getCurrentPoints())){curPoints = points.getCurrentPoints();}
                if(Assert.isNotNull(points.getCurrentSumpoints())){sumPoints = points.getCurrentSumpoints();}
                if(Assert.isNotNull(points.getEventName())){eventName = points.getEventName();}
                jsonObject.put("number",i++);
                jsonObject.put("id",points.getId());
                jsonObject.put("lastVisitDate",points.getPointsDate());
//                memberStatus = clientService.queryById(searchDto.getClientId()).getMemberStatus();
                if(Assert.isNull(points.getVisitId()) && Assert.isNull(points.getEventId())) {
                    if(points.getPointsSize() < 0){
                        jsonObject.put("pointsFrom", "会员锁定清空积分");
                    }else if(points.getPointsSize() > 0){
                        jsonObject.put("pointsFrom", "初始积分");
                    }
                }else{
                    jsonObject.put("pointsFrom", points.getPointsFrom1());
                }
                jsonObject.put("pointsSize",points.getPointsSize());
                jsonObject.put("currentPoints",curPoints);
                jsonObject.put("currentSumpoints",sumPoints);
                jsonObject.put("eventName",eventName);
                jsonArray.add(jsonObject);
                eventName = "";
            }
            numCount = pointsList.size();
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

}
