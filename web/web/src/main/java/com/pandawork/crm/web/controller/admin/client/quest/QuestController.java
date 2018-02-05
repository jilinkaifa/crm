package com.pandawork.crm.web.controller.admin.client.quest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.dto.client.basic.ClientSearchDto;
import com.pandawork.crm.common.dto.client.quest.QuestDto;
import com.pandawork.crm.common.dto.client.quest.QuestItemSearchDto;
import com.pandawork.crm.common.dto.client.quest.QuestSearchDto;
import com.pandawork.crm.common.entity.client.basic.Client;
import com.pandawork.crm.common.entity.client.quest.Quest;
import com.pandawork.crm.common.entity.client.quest.QuestItem;
import com.pandawork.crm.common.entity.party.dictionary.Dictionary;
import com.pandawork.crm.common.entity.party.member.Member;
import com.pandawork.crm.common.enums.client.MemberEnums;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.enums.party.dictionary.DictionaryEnums;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.DateUtils;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.web.spring.AbstractController;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * QuestController
 * Created by shura
 * Date:  2017/7/27.
 * Time:  10:48
 */
@Controller
@Module(ModuleEnums.AdminClientQuest)
@RequestMapping(value = URLConstants.ADMIN_CLIENT_QUEST_URL)
public class QuestController extends AbstractController{

    /**
     * 去列表页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminClientQuestList)
    @RequestMapping(value = {"","list"},method = RequestMethod.GET)
    public String ToList(Model model,HttpSession httpSession){
        int dataCount = 0;
        QuestSearchDto searchDto = new QuestSearchDto();
        try{
            //获取操作人
            int createdPartyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
            //获取登录用户的id
            int userId = securityUserService.queryByPartyId(createdPartyId).getId();
            //根据用户id获取角色id
            int securityGroupId = securityUserGroupService.queryByUserId(userId).getGroupId();
            int memberGroupId = 0;
            dataCount = (int)Math.ceil((double)(questService.countBySearchDto(searchDto) / DEFAULT_PAGE_SIZE));
            List<Member> memberGroupList = memberGroupService.listAll();
            if(securityGroupId != 3){
                //若为超级管理员可查看所有
                model.addAttribute("memberGroupList",memberGroupList);
            }else{
                //若为其他包医人只可添加自己分组
                memberGroupId = employeeService.getGroupIdByPartyId(createdPartyId);
                memberGroupList.clear();
                memberGroupList.add(memberGroupService.queryById(memberGroupId));
                model.addAttribute("memberGroupList",memberGroupList);
            }
            model.addAttribute("dataCount",dataCount);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/client/quest/list";
    }

    /**
     * 根据searchDto搜索
     * @param searchDto
     * @return
     */
    @Module(ModuleEnums.AdminClientQuestList)
    @RequestMapping(value = {"ajax/list"},method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(QuestSearchDto searchDto,HttpSession httpSession) {
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
        List<Quest> questList = Collections.emptyList();
        int dataCount = 0, numCount = 0;
        String nextQuestTime = "";
        String level = "";
        try{
            //获取操作人
            int createdPartyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
            //获取登录用户的id
            int userId = securityUserService.queryByPartyId(createdPartyId).getId();
            //根据用户id获取角色id
            int securityGroupId = securityUserGroupService.queryByUserId(userId).getGroupId();
            int memberGroupId = 0;
            Date showQuestTime = null;
            String questTime = "";
            if(securityGroupId != 3){
                //若为超级管理员可查看所有
                searchDto.setMemberGroupId(null);
            }else{
                //若为其他包医人只可添加自己分组
                memberGroupId = employeeService.getGroupIdByPartyId(createdPartyId);
                searchDto.setMemberGroupId(memberGroupId);
            }
            questList = questService.listBySearchDto(searchDto);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            int  i = 1 + pageNo * 10;
            for(Quest quest : questList){
                JSONObject jsonObject = new JSONObject();
                //格式化时间
                if(Assert.isNotNull(quest.getNextQuestTime())){
                    nextQuestTime = sdf.format(quest.getNextQuestTime());
                }
                //转换会员等级
                if(Assert.isNotNull(quest.getIsMember())){
                    level = MemberEnums.valueOf(quest.getIsMember()).getMember();
                }
                if(Assert.isNotNull(quest.getIsRemindahead()) && quest.getIsRemindahead() == 1
                        && Assert.isNotNull(quest.getRemindaheadDays())){
                    //判断是否存在提前提醒天数
                    showQuestTime = DateUtils.aheadDay(quest.getNextQuestTime(),quest.getRemindaheadDays());
                }else {
                    //获取下次问卷时间的前一天
                    showQuestTime = DateUtils.yesterday(quest.getNextQuestTime());
                }
                questTime = DateUtils.formatDateSimple(showQuestTime);
                jsonObject.put("id",quest.getClientId());
                jsonObject.put("number",i++);
                jsonObject.put("name",quest.getClientName());
                jsonObject.put("idCardNum",quest.getIdCardNum());
                jsonObject.put("tel",quest.getTel());
                jsonObject.put("memberGroup",quest.getMemberGroupName());
                jsonObject.put("level",level);
                jsonObject.put("allCost",quest.getAllCost());
                jsonObject.put("questCount",quest.getQuestCount());
                jsonObject.put("nextQuestTime",nextQuestTime);
                jsonObject.put("showQuestTime",questTime);
                jsonArray.add(jsonObject);
            }
            searchDto.setPageSize(null);
            searchDto.setOffset(0);
            numCount = questService.listBySearchDto(searchDto).size();
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
    @Module(ModuleEnums.AdminClientQuestList)
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
     * @param idCard
     * @return
     */
    @Module(ModuleEnums.AdminClientQuestList)
    @RequestMapping(value = {"ajax/idCard"},method = RequestMethod.GET)
    @ResponseBody
    public JSON searchByIdCard(@RequestParam("idCard")String  idCard){
        JSONArray jsonArray = new JSONArray();
        ClientSearchDto searchDto = new ClientSearchDto();
        try{
            searchDto.setIsMember(1);
            searchDto.setIdCardNum(idCard);
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
     * @param phone
     * @return
     */
    @Module(ModuleEnums.AdminClientQuestList)
    @RequestMapping(value = {"ajax/tel"},method = RequestMethod.GET)
    @ResponseBody
    public JSON searchByTel(@RequestParam("tel")String  phone) {
        JSONArray jsonArray = new JSONArray();
        ClientSearchDto searchDto = new ClientSearchDto();
        try {
            searchDto.setTel(phone);
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
     * 去添加页
     *
     * @return
     */
    @Module(ModuleEnums.AdminClientQuestNew)
    @RequestMapping(value = {"add"},method = RequestMethod.GET)
    public String ToAdd(@RequestParam("id")Integer id, Model model){
        List<Dictionary> dictionaryList = new ArrayList<Dictionary>();
        List<Dictionary> unitList = new ArrayList<Dictionary>();
        try{
            dictionaryList = dictionaryService.listByPId(DictionaryEnums.DIC_QUEST_ITEM.getId());
            unitList = dictionaryService.listByPId(DictionaryEnums.DIC_DOSAGE_UNIT.getId());
            //用药情况单独显示此处应移出列表
            for(int i = 0 ; i <dictionaryList.size() ;  i++){
                if(dictionaryList.get(i).getId() == DictionaryEnums.DIC_DOSAGE.getId()){
                    dictionaryList.remove(i);
                }
            }
            //获取上次问卷时间第二天作为开始时间
            Quest quest = questService.queryByClientId(id);
            Date start  = DateUtils.tomorrow(quest.getQuestTime());
            Date today = new Date();
            //判断问卷初始化的时候可能出现上次问卷时间大于当天
            if(start.getTime() > today.getTime()){
                start = today;
            }
            String startDate = DateUtils.formatDateSimple(start);
            model.addAttribute("clientId",id);
            model.addAttribute("unitList",unitList);
            model.addAttribute("startDate",startDate);
            model.addAttribute("questItemList",dictionaryList);
            model.addAttribute("dicDosageId",DictionaryEnums.DIC_DOSAGE.getId());
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/client/quest/add";
    }


    /**
     * 添加问卷保存
     *
     * @param questDto
     * @param httpSession
     * @return
     */
    @Module(ModuleEnums.AdminClientQuestNew)
    @RequestMapping(value = {"ajax/save"},method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxAdd(@RequestBody QuestDto questDto, HttpSession httpSession){
        int createdPartyId = 0;
        try{
            //从Session获取当前用户id
            createdPartyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
            if(Assert.isNotNull(createdPartyId)){
                questDto.setCreatedPartyId(createdPartyId);
            }
            //为了方便统计问卷项个数在此处判断若不为用药情况--问卷内容为空的移除list
            Iterator<QuestItem> questItemIterator = questDto.getQuestItemList().iterator();
            while(questItemIterator.hasNext()){
                QuestItem questItem = questItemIterator.next();
                if(questItem.getDicQuestItem() != DictionaryEnums.DIC_DOSAGE.getId()
                        && (questItem.getQuestContent() == "" || Assert.isNull(questItem.getQuestContent()))){
                    questItemIterator.remove();
                }
            }
            //添加问卷以及问卷项
            QuestDto newQuestDto = questService.newQuestDto(questDto);
            //更新问卷次数
            clientService.updateQuestCount(newQuestDto.getClientId());
        }catch(SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 去问卷详情
     *
     * @param id
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminClientQuestDetail)
    @RequestMapping(value = {"detail"},method = RequestMethod.GET)
    public String ToDetail(@Param("id")Integer id, Model model){
        List<Dictionary> dictionaryList = new ArrayList<Dictionary>();
        try{
            dictionaryList = dictionaryService.listByPId(DictionaryEnums.DIC_QUEST_ITEM.getId());
            model.addAttribute("clientId",id);
            model.addAttribute("questItemList",dictionaryList);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/client/quest/detail";
    }

    /**
     * 根据searchDto搜索问卷详情
     * @param searchDto
     * @return
     */
    @Module(ModuleEnums.AdminClientQuestDetail)
    @RequestMapping(value = {"ajax/detailList"},method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxDetailList(QuestItemSearchDto searchDto) {
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
        List<QuestItem> questItemListList = Collections.emptyList();
        int dataCount = 0, numCount = 0;
        String dicQuestItem = "";
        String startDate = "";
        String endDate = "";
        try{
            questItemListList = questItemService.listBySearchDto(searchDto);
            int  i = 1 + pageNo * 10;
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            for(QuestItem questItem : questItemListList){
                dicQuestItem = dictionaryService.queryById(questItem.getDicQuestItem()).getName();
                JSONObject jsonObject = new JSONObject();
                if(Assert.isNotNull(questItem.getStartDate())){
                    startDate = sdf.format(questItem.getStartDate());
                }
                if(Assert.isNotNull(questItem.getEndDate())){
                    endDate = sdf.format(questItem.getEndDate());
                }
                jsonObject.put("number",i++);
                jsonObject.put("dicQuestItem",dicQuestItem);
                jsonObject.put("questContent",questItem.getQuestContent());
                jsonObject.put("startDate",startDate);
                jsonObject.put("endDate",endDate);
                jsonArray.add(jsonObject);
            }
            numCount = questItemService.countBySearchDto(searchDto);
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
     * 药品名称框模糊查询
     *
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminClientQuestList)
    @RequestMapping(value = {"ajax/dosage"},method = RequestMethod.GET)
    @ResponseBody
    public JSON listDosage(@RequestParam("name")String  name) {
        JSONArray jsonArray = new JSONArray();
        try {
            List<Dictionary> dictionaryList = dictionaryService.listForSearch(name,DictionaryEnums.DIC_DOSAGE_NAME.getId());
            for(Dictionary dictionary : dictionaryList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", dictionary.getId());
                jsonObject.put("name", dictionary.getName());
                jsonArray.add(jsonObject);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray);
    }

}
