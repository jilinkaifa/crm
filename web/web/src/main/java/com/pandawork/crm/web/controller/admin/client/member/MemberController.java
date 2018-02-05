package com.pandawork.crm.web.controller.admin.client.member;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.dto.client.basic.ClientSearchDto;
import com.pandawork.crm.common.dto.client.basic.GenderDto;
import com.pandawork.crm.common.dto.client.member.MemberDto;
import com.pandawork.crm.common.dto.client.member.MemberSearchDto;
import com.pandawork.crm.common.dto.profile.ProfileDto;
import com.pandawork.crm.common.entity.client.basic.Client;
import com.pandawork.crm.common.entity.client.points.Points;
import com.pandawork.crm.common.entity.client.quest.Quest;
import com.pandawork.crm.common.entity.client.visit.Visit;
import com.pandawork.crm.common.entity.party.dictionary.Dictionary;
import com.pandawork.crm.common.entity.party.member.Member;
import com.pandawork.crm.common.entity.profile.label.LabelItem;
import com.pandawork.crm.common.entity.profile.label.LabelType;
import com.pandawork.crm.common.enums.client.GenderEnums;
import com.pandawork.crm.common.enums.client.IsFixedEnums;
import com.pandawork.crm.common.enums.client.MemberEnums;
import com.pandawork.crm.common.enums.client.MemberStatusEnums;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.enums.party.dictionary.DictionaryEnums;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.ListSortUtil;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.service.party.dictionary.DictionaryService;
import com.pandawork.crm.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * MemberController
 * Created by shura
 * Date:  2017/7/25.
 * Time:  13:29
 */
@Controller
@Module(ModuleEnums.AdminClientMember)
@RequestMapping(value = URLConstants.ADMIN_CLIENT_MEMBER_URL)
public class MemberController extends AbstractController {


    /**
     * 去列表页
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminClientMemberList)
    @RequestMapping(value = {"","list"},method = RequestMethod.GET)
    public String ToList(Model model,HttpSession httpSession){
        int pageCount = 0;
        try{
            MemberSearchDto searchDto = new MemberSearchDto();
            //获取操作人
            int createdPartyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
            //获取登录用户的id
            int userId = securityUserService.queryByPartyId(createdPartyId).getId();
            //根据用户id获取角色id
            int securityGroupId = securityUserGroupService.queryByUserId(userId).getGroupId();
            int memberGroupId = 0;
            List<Member> memberList = memberGroupService.listAll();
            model.addAttribute("levelList",dictionaryService.listByPId(DictionaryEnums.DIC_CLIENT_TYPE.getId()));
            model.addAttribute("normalClient",DictionaryEnums.DIC_CLIENT_TYPE_NORMAL.getId());
            if(securityGroupId != 3){
                //若为超级管理员可查看所有
                model.addAttribute("memberGroupList",memberList);
            }else{
                //若为其他包医人只可添加自己分组
                memberGroupId = employeeService.getGroupIdByPartyId(createdPartyId);
                memberList.clear();
                memberList.add(memberGroupService.queryById(memberGroupId));
                model.addAttribute("memberGroupList",memberList);
            }
            pageCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE,memberService.countBySearchDto(searchDto));
            model.addAttribute("pageCount",pageCount);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/client/member/list";
    }

    /**
     * 根据searchDto搜索
     * @param searchDto
     * @return
     */
    @Module(ModuleEnums.AdminClientMemberList)
    @RequestMapping(value = {"ajax/list"},method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(MemberSearchDto searchDto,HttpSession httpSession) {
        JSONObject json = new JSONObject();
        Integer pageSize = searchDto.getPageSize();
        pageSize = (pageSize == null || pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
        Integer pageNo = searchDto.getPage();
        int offset = 0;
        if (Assert.isNotNull(pageNo)) {
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * pageSize;
        }
        searchDto.setOffset(offset);
        searchDto.setPageSize(pageSize);
        JSONArray jsonArray = new JSONArray();
        List<Client> clientList = Collections.emptyList();
        int dataCount = 0, numCount = 0;
        String memberGroupName = "";
        String level = "";
        String memberStatus = "";
        String recordDate = "";
        String deadLine = "";
        String isFixed = "";
        try{
            //获取操作人
            int createdPartyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
            //获取登录用户的id
            int userId = securityUserService.queryByPartyId(createdPartyId).getId();
            //根据用户id获取角色id
            int securityGroupId = securityUserGroupService.queryByUserId(userId).getGroupId();
            int memberGroupId = 0;
            if(securityGroupId != 3){
                //若为超级管理员可查看所有
//                searchDto.setMemberGroupId(null);
            }else{
                //若为其他包医人只可添加自己分组
                memberGroupId = employeeService.getGroupIdByPartyId(createdPartyId);
                searchDto.setMemberGroupId(memberGroupId);
            }
            clientList = memberService.listBySearchDto(searchDto);
            //按照会员档案号排序
            ListSortUtil.sortByStringNumber(clientList);
            int  i = 1 + pageNo * 10;
            for(Client client : clientList){
                //遍历会员组名称加入实体
                if(client.getMemberGroupId() > 0){
                    memberGroupName = memberGroupService.queryById(client.getMemberGroupId()).getName();
                }
                //level = DictionaryEnums.valueOf(client.getDicType()).getDictionary();//不可为空
                level = dictionaryService.queryById(client.getDicType()).getName();
                memberStatus = MemberStatusEnums.valueOf(client.getMemberStatus()).getMemberStatus();//不可为空
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//格式化时间
                if(Assert.isNotNull(client.getRecordDate())){
                    recordDate = sdf.format(client.getRecordDate());
                }else{
                    recordDate = "";
                }
                if(Assert.isNotNull(client.getMemberDeadline())){
                    deadLine   = sdf.format(client.getMemberDeadline());
                }else{
                    deadLine = "";
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("number",i++);
                jsonObject.put("id",client.getId());
                jsonObject.put("name",client.getName());
                jsonObject.put("idCardNum",client.getIdCardNum());
                jsonObject.put("tel",client.getTel());
               // jsonObject.put("memberGroup",memberGroupName);
                jsonObject.put("recordId",client.getRecordId());
                jsonObject.put("level",level);
                jsonObject.put("allCost",client.getAllCost());
                jsonObject.put("recordDate",recordDate);
                jsonObject.put("memberStatus",memberStatus);
                jsonObject.put("deadLine",deadLine);
                if(Assert.isNotNull(client.getIsFixed())) {
                    isFixed = IsFixedEnums.valueOf(client.getIsFixed()).getIsFixed();
                }
                jsonObject.put("isFixed",isFixed);
                jsonArray.add(jsonObject);
            }
            numCount = memberService.countBySearchDto(searchDto);
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
     * 姓名搜索框模糊查询
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminClientMemberList)
    @RequestMapping(value = {"ajax/name"},method = RequestMethod.GET)
    @ResponseBody
    public JSON searchByName(@RequestParam("name")String  name){
        JSONArray jsonArray = new JSONArray();
        ClientSearchDto searchDto = new ClientSearchDto();
        try{
            searchDto.setName(name);
            searchDto.setIsMember(1);
            List<Client> clientList = clientService.listForSearch(searchDto);
            for(Client client : clientList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",client.getId());
                jsonObject.put("name",client.getName());
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
     * @param idCard
     * @return
     */
    @Module(ModuleEnums.AdminClientMemberList)
    @RequestMapping(value = {"ajax/idCard"},method = RequestMethod.GET)
    @ResponseBody
    public JSON searchByIdCard(@RequestParam("idCard")String  idCard){
        JSONArray jsonArray = new JSONArray();
        ClientSearchDto searchDto = new ClientSearchDto();
        try{
            searchDto.setIdCardNum(idCard);
            searchDto.setIsMember(1);
            List<Client> clientList = clientService.listForSearch(searchDto);
            for(Client client : clientList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",client.getId());
                jsonObject.put("name",client.getIdCardNum());
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
     * @param tel
     * @return
     */
    @Module(ModuleEnums.AdminClientMemberList)
    @RequestMapping(value = {"ajax/tel"},method = RequestMethod.GET)
    @ResponseBody
    public JSON searchByTel(@RequestParam("tel")String  tel) {
        JSONArray jsonArray = new JSONArray();
        ClientSearchDto searchDto = new ClientSearchDto();
        try {
            searchDto.setTel(tel);
            searchDto.setIsMember(1);
            List<Client> clientList = clientService.listForSearch(searchDto);
            for (Client client : clientList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", client.getId());
                jsonObject.put("name", client.getTel());
                jsonArray.add(jsonObject);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray);
    }

    /**
     * 去修改页
     * @param id
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminClientMember,extModule = ModuleEnums.AdminClientMemberUpdate)
    @RequestMapping(value = {"update"},method = RequestMethod.GET)
    public String ToUpdate(@RequestParam("id")Integer id,Model model,HttpSession httpSession){
        try{
            //渲染患者基本信息
            String genderName = "";
            String dicType = "";
            String dicMCIType = "";
            String dicNation = "";
            String dicSecRelation ="";
            List<LabelType> labelTypeList = Collections.emptyList();
            List<String> profileList = Collections.emptyList();
            int questCount = 0;
            int age = 0;
            Client client = clientService.queryById(id);
            if(client.getDicType() != null){
                dicType = dictionaryService.queryById(client.getDicType()).getName();
            }
            if(client.getDicMCIType() != null){
                dicMCIType = dictionaryService.queryById(client.getDicMCIType()).getName();
            }
            if(client.getDicNation() != null){
                dicNation = dictionaryService.queryById(client.getDicNation()).getName();
            }
            if(client.getDicSecRelation()!=null){
                dicSecRelation = dictionaryService.queryById(client.getDicSecRelation()).getName();
            }
            List<GenderDto> genderList = new ArrayList<GenderDto>();
            //将性别枚举转化为list
            GenderDto male = new GenderDto();
            GenderDto female = new GenderDto();
            GenderDto other = new GenderDto();
            male.setId(GenderEnums.Male.getId());
            male.setName(GenderEnums.Male.getGender());
            female.setId(GenderEnums.Female.getId());
            female.setName(GenderEnums.Female.getGender());
            other.setId(GenderEnums.Other.getId());
            other.setName(GenderEnums.Other.getGender());
            genderList.add(male);
            genderList.add(female);
            genderList.add(other);
            List<Dictionary> secRelationList = dictionaryService.listByPId(DictionaryEnums.DIC_DEC_RELATION.getId());
            List<Dictionary> nationList = dictionaryService.listByPId(DictionaryEnums.DIC_NATION.getId());
            List<Dictionary> MCIList = dictionaryService.listByPId(DictionaryEnums.DIC_CLIENT_MCI_TYPE.getId());
            model.addAttribute("genderList",genderList);
            model.addAttribute("dicMCITypeList",MCIList);
            model.addAttribute("dicNationList",nationList);
            model.addAttribute("dicSecRelationList",secRelationList);
            //渲染患者信息
            age = DataUtils.getAgeByIdCard(client.getIdCardNum());
            model.addAttribute("id",client.getId());
            model.addAttribute("name",client.getName());
            model.addAttribute("age",age);
            model.addAttribute("tel",client.getTel());
            model.addAttribute("gender",client.getGender());
            model.addAttribute("height",client.getHeight());
            model.addAttribute("address",client.getAddress());
            model.addAttribute("secContact",client.getSecContact());
            model.addAttribute("secTel",client.getSecTel());
            model.addAttribute("dicType",dicType);
            model.addAttribute("dicMCIType",client.getDicMCIType());
            model.addAttribute("dicNation",client.getDicNation());
            model.addAttribute("dicSecRelation",client.getDicSecRelation());
            model.addAttribute("MCINum",client.getMCINum());
            model.addAttribute("diagnoseType",client.getDiagnoseType());
            model.addAttribute("idCardNum",client.getIdCardNum());
            model.addAttribute("weight",client.getWeight());
            model.addAttribute("company",client.getCompany());
            model.addAttribute("secTel",client.getSecTel());
            model.addAttribute("allCost",client.getAllCost());

            //活动
            int eventCount  = processingEventService.countEventByClientId(client.getId());
            model.addAttribute("eventCount",eventCount);
            //渲染会员详情
            String recordDate = "";
            String deadLine = "";
            String nextQuestTimeStr = "";
            int memberGroupId = client.getMemberGroupId();
            Date nextQuestTime = null;
            List<Member> memberGroupList = new ArrayList<Member>();
            //判断操作人分组
            //获取操作人
            int createdPartyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
            //获取登录用户的id
            int userId = securityUserService.queryByPartyId(createdPartyId).getId();
            //根据用户id获取角色id
            int securityGroupId = securityUserGroupService.queryByUserId(userId).getGroupId();
           if(securityGroupId != 3){
               //若为超级管理员可查看所有
               memberGroupList = memberGroupService.listAll();
           }else{
               //若为其他包医人只可添加自己分组
               memberGroupId = employeeService.getGroupIdByPartyId(createdPartyId);
               memberGroupList.clear();
               memberGroupList.add(memberGroupService.queryById(memberGroupId));
           }
            //格式化时间
            Quest quest = questService.queryByClientId(client.getId());
            if(Assert.isNotNull(quest)){
                nextQuestTime = quest.getNextQuestTime();
            }
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            //建档日期若为空则为初次加入会员默认当天为建档日期
            if(Assert.isNotNull(client.getRecordDate())){
                recordDate = sdf.format(client.getRecordDate());
            }else{
                recordDate = sdf.format(new Date());
            }
            if(Assert.isNotNull(client.getMemberDeadline())){
                deadLine   = sdf.format(client.getMemberDeadline());
            }
            if(Assert.isNotNull(nextQuestTime)){
                nextQuestTimeStr = sdf.format(nextQuestTime);
            }
            int memberStatus = client.getMemberStatus();
            int level = client.getDicType();
            List<Dictionary> levelList = dictionaryService.listByPId(DictionaryEnums.DIC_CLIENT_TYPE.getId());
            model.addAttribute("recordId",client.getRecordId());
            model.addAttribute("isMember",client.getIsMember());
            model.addAttribute("memberStatus",memberStatus);
            model.addAttribute("recordDate",recordDate);
            model.addAttribute("deadLine",deadLine);
            model.addAttribute("memberPoints", client.getMemberPoints());
            model.addAttribute("allPoints",client.getAllPoints());
            model.addAttribute("level",level);
            model.addAttribute("normalClient",DictionaryEnums.DIC_CLIENT_TYPE_NORMAL.getId());
            model.addAttribute("questCount",client.getQuestCount());
            model.addAttribute("memberGroupId",memberGroupId);
            model.addAttribute("levelList",levelList);
            model.addAttribute("isFixed",client.getIsFixed());
            model.addAttribute("memberGroupList",memberGroupList);
            model.addAttribute("nextQuestTime",nextQuestTimeStr);
            model.addAttribute("hospitalization",client.getHospitalization());
            //渲染画像信息
            labelTypeList = labelTypeService.listAll();
            List<ProfileDto> profileDtoList = new ArrayList<ProfileDto>();
            for (LabelType type : labelTypeList){
                ProfileDto profileDto = new ProfileDto();
                profileDto.setTypeId(type.getId());
                profileDto.setTypeName(type.getName());
                profileDto.setIsMultiple(type.getIsMultiple());
                profileList = portrayalService.listProfileByClientIdAndTypeId(id, type.getId());
                if(Assert.isNotNull(profileList)){
                    String[] array = (String[])profileList.toArray(new String[profileList.size()]);
                    String str = org.apache.commons.lang.StringUtils.join(array, ",");
                    profileDto.setItemName(str);
                }
                //处理每个标签类型下的标签项，存为itemList
                List<LabelItem> labelItemList = labelItemService.listByTypeId(type.getId());
                //用itemName去存所有的标签项
                List<String> itemName = new ArrayList<String>();
                for (LabelItem labelItem : labelItemList){
                    itemName.add(labelItem.getDicLabelName());
                }
                //若itemName不为空，则将List强转为String字符串，中间用“，”分隔
                if (itemName == null || itemName.size() == 0 ){
                    profileDto.setItemList("");
                } else {
                    String str = DataUtils.listStringToString(itemName);
                    profileDto.setItemList(str);
                }
                profileDtoList.add(profileDto);
            }
            model.addAttribute("profileDtoList", profileDtoList);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/client/member/update";
    }

    /**
     * 去详情页
     * @param id
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminClientMemberDetail)
    @RequestMapping(value = {"detail"},method = RequestMethod.GET)
    public String ToDetail(@RequestParam("id")Integer id,Model model){
        try{
            //渲染患者基本信息
            String genderName = "";
            String dicType = "";
            String dicMCIType = "";
            String dicNation = "";
            String dicSecRelation ="";
            List<LabelType> labelTypeList = Collections.emptyList();
            List<String> profileList = Collections.emptyList();
            int questCount = 0;
            int age = 0;
            Client client = clientService.queryById(id);
            if(Assert.isNotNull(client.getGender())) {
                if (client.getGender() == 1) {
                    genderName = GenderEnums.Male.getGender();
                } else if (client.getGender() == 2) {
                    genderName = GenderEnums.Female.getGender();
                } else if (client.getGender() == 3) {
                    genderName = GenderEnums.Other.getGender();
                }
            }else{
                genderName = "";
            }
            client.setGenderName(genderName);
            if(client.getDicType() != null){
                dicType = dictionaryService.queryById(client.getDicType()).getName();
            }
            if(client.getDicMCIType() != null){
                dicMCIType = dictionaryService.queryById(client.getDicMCIType()).getName();
            }
            if(client.getDicNation() != null){
                dicNation = dictionaryService.queryById(client.getDicNation()).getName();
            }
            if(client.getDicSecRelation()!=null){
                dicSecRelation = dictionaryService.queryById(client.getDicSecRelation()).getName();
            }
            age = DataUtils.getAgeByIdCard(client.getIdCardNum());
            model.addAttribute("name",client.getName());
            model.addAttribute("id",client.getId());
            model.addAttribute("tel",client.getTel());
            model.addAttribute("age",age);
            model.addAttribute("gender",client.getGenderName());
            model.addAttribute("height",client.getHeight());
            model.addAttribute("address",client.getAddress());
            model.addAttribute("secContact",client.getSecContact());
            model.addAttribute("dicType",dicType);
            model.addAttribute("dicMCIType",dicMCIType);
            model.addAttribute("dicNation",dicNation);
            model.addAttribute("dicSecRelation",dicSecRelation);
            model.addAttribute("MCINum",client.getMCINum());
            model.addAttribute("diagnoseType",client.getDiagnoseType());
            model.addAttribute("idCardNum",client.getIdCardNum());
            model.addAttribute("weight",client.getWeight());
            model.addAttribute("company",client.getCompany());
            model.addAttribute("secTel",client.getSecTel());
            model.addAttribute("allCost",client.getAllCost());
            int eventCount  = processingEventService.countEventByClientId(client.getId());
            model.addAttribute("eventCount",eventCount);
            //渲染会员详情
            String recordDate = "";
            String deadLine = "";
            String isFixed = "";
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            if(Assert.isNotNull(client.getRecordDate())){
                recordDate = sdf.format(client.getRecordDate());
            }
            if(Assert.isNotNull(client.getMemberDeadline())){
                deadLine   = sdf.format(client.getMemberDeadline());
            }
            String memberGroupName = "";
            if(client.getMemberGroupId() > 0 && Assert.isNotNull(client.getMemberGroupId())){
                memberGroupName = memberGroupService.queryById(client.getMemberGroupId()).getName();
            }
            String level = "";
            if(Assert.isNotNull(client.getDicType())){
                level = DictionaryEnums.valueOf(client.getDicType()).getDictionary();
            }
            if(Assert.isNotNull(client.getIsFixed())){
                isFixed = IsFixedEnums.valueOf(client.getIsFixed()).getIsFixed();
            }
            String memberStatus = MemberStatusEnums.valueOf(client.getMemberStatus()).getMemberStatus();
            model.addAttribute("recordId",client.getRecordId());
            model.addAttribute("memberGroup",memberGroupName);
            model.addAttribute("recordDate",recordDate);
            model.addAttribute("deadLine",deadLine);
            model.addAttribute("memberPoints", client.getMemberPoints());
            model.addAttribute("allCost",client.getAllCost());
            model.addAttribute("level",level);
            model.addAttribute("isFixed",isFixed);
            model.addAttribute("memberStatus",memberStatus);
            model.addAttribute("allPoints",client.getAllPoints());
            model.addAttribute("questCount",client.getQuestCount());
            model.addAttribute("hospitalization",client.getHospitalization());
            //渲染画像信息
            labelTypeList = labelTypeService.listAll();
            List<ProfileDto> profileDtoList = new ArrayList<ProfileDto>();
            for (LabelType type : labelTypeList){
                ProfileDto profileDto = new ProfileDto();
                profileDto.setTypeId(type.getId());
                profileDto.setTypeName(type.getName());
                profileDto.setIsMultiple(type.getIsMultiple());
                profileList = portrayalService.listProfileByClientIdAndTypeId(id, type.getId());
                if(Assert.isNotNull(profileList)){
                    String[] array = (String[])profileList.toArray(new String[profileList.size()]);
                    String str = org.apache.commons.lang.StringUtils.join(array, ",");
                    profileDto.setItemName(str);
                }
                //处理每个标签类型下的标签项，存为itemList
                List<LabelItem> labelItemList = labelItemService.listByTypeId(type.getId());
                //用itemName去存所有的标签项
                List<String> itemName = new ArrayList<String>();
                for (LabelItem labelItem : labelItemList){
                    itemName.add(labelItem.getDicLabelName());
                }
                //若itemName不为空，则将List强转为String字符串，中间用“，”分隔
                if (itemName == null || itemName.size() == 0 ){
                    profileDto.setItemList("");
                } else {
                    String str = DataUtils.listStringToString(itemName);
                    profileDto.setItemList(str);
                }
                profileDtoList.add(profileDto);
            }
            model.addAttribute("profileDtoList", profileDtoList);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/client/member/detail";
    }

    /**
     * 保存会员修改信息
     *
     * @param memberDto
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminClientMemberUpdate)
    @RequestMapping(value = {"ajax/update"},method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxUpdate(MemberDto memberDto, Model model, HttpSession httpSession){
        try{
            Client client = clientService.queryById(memberDto.getId());
            int createdPartyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
            //若数据库建档时间为空则为第一次创建会员
            if(client.getIsMember() == 0){
                memberDto.setRecordDate(new Date());
                //添加会员 添加相应的来访记录
                Visit visit = new Visit();
                visit.setClientId(client.getId());
                //来访信息初始化
                visit.setCreatedPartyId(createdPartyId);
                //来访信息初始化
                visit.setVisitTime(new Date());
                visit.setVisitContent("来访信息初始化");
                visitService.newVisit(visit);
                //加入会员初始积分处理
                if(Assert.isNotNull(memberDto.getAllPoints()) &&Assert.isNotNull(memberDto.getMemberPoints())) {
                    //新增积分修改积分记录
                    Points points = new Points();
                    points.setClientId(memberDto.getId());
                    int pointSize = memberDto.getMemberPoints();
                    points.setPointsSize(pointSize);
                    points.setCurrentPoints(memberDto.getMemberPoints());
                    points.setCurrentSumpoints(memberDto.getAllPoints());
                    points.setLastVisitDate(new Date());
                    points.setCreatedPartyId(visit.getCreatedPartyId());
                    //设置积分调整日期为当天
                    points.setPointsDate(new Date());
                    //积分来源设置来访
                    points.setPointsFrom(0);
                    points.setPointsText("初始积分");
                    //产生积分记录
                    pointsService.newPoints(points);
                    //更新会员当前积分
                    clientService.updateAllPointsAndMemberPoints(visit.getClientId());
                }
            }
            //添加会员 需要进行问卷初始化
            if(Assert.isNotNull(memberDto.getNextQuestTime())){
                Quest quest = new Quest();
                quest.setClientId(memberDto.getId());
                quest.setNextQuestTime(memberDto.getNextQuestTime());
                quest.setCreatedPartyId(createdPartyId);
                quest.setQuestTime(new Date());
                quest.setComment("问卷初始化");
                quest.setMemberGroupId(memberDto.getMemberGroupId());
                questService.newQuest(quest);
                clientService.updateQuestCount(memberDto.getId());
            }
            //加入会员 需要加入患者所属组内的待开展关怀活动
            if(Assert.isNull(client.getMemberGroupId())){
                eventRecordNoticeService.newByClientIdAndGroupId(memberDto.getId(),memberDto.getMemberGroupId());
            }
            memberService.updateMember(memberDto);
            clientService.addMember(client.getId());

            //若会员状态锁定后清空积分
            Client updateMember = clientService.queryById(memberDto.getId());
            if(updateMember.getMemberStatus() == MemberStatusEnums.Locked.getId()){
                //会员到期清空当前积分
               memberService.clearPoints(memberDto.getId());
            }
            model.addAttribute("msg",NEW_SUCCESS_MSG);
        }catch(SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

}
