package com.pandawork.crm.web.controller.admin.profile.portrayal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.dto.client.member.MemberSearchDto;
import com.pandawork.crm.common.dto.profile.LabelItemDto;
import com.pandawork.crm.common.dto.profile.ProfileDto;
import com.pandawork.crm.common.entity.client.basic.Client;
import com.pandawork.crm.common.entity.party.dictionary.Dictionary;
import com.pandawork.crm.common.entity.profile.label.LabelItem;
import com.pandawork.crm.common.entity.profile.label.LabelType;
import com.pandawork.crm.common.enums.client.GenderEnums;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.enums.party.dictionary.DictionaryEnums;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.DateUtils;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeArray.join;

/**
 * PortrayalController
 * 患者画像
 *
 * @author Flying
 * @date 2017/7/26 20:12
 */

@Controller
@Module(ModuleEnums.AdminProfilePortrayal)
@RequestMapping(value = URLConstants.ADMIN_PROFILE_PORTRAYAL_URL)
public class PortrayalController extends AbstractController {

    /**
     * 去列表页
     *
     * @param httpSession
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminProfilePortrayalList)
    @RequestMapping(value = { "" , "list" }, method = RequestMethod.GET)
    public String toList(HttpSession httpSession, Model model){
        int dateCount = 0;
        int pageCount = 0;
        List<Client> clientList = Collections.emptyList();
        try{
            List<Dictionary> dictionaryList = dictionaryService.listByPId(DictionaryEnums.DIC_CLIENT_TYPE.getId());
            Object pId = httpSession.getAttribute("partyId");
            int partyId = DataUtils.objectToInt(pId);
            int memberGroupId = employeeService.getGroupIdByPartyId(partyId);
            clientList = clientService.listByMemberGroupId(memberGroupId);
            dateCount = clientList.size();
            pageCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE, dateCount);
            model.addAttribute("pageCount", pageCount);
            model.addAttribute("dictionaryList", dictionaryList);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/profile/portrayal/list";
    }

    /**
     * ajax 获取所有会员信息
     *
     * @param pageNo
     * @param memberSearchDto
     * @param httpSession
     * @return
     */
    @Module(ModuleEnums.AdminProfilePortrayalList)
    @RequestMapping(value = "ajax/list", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@RequestParam("pageNo") Integer pageNo,
                         MemberSearchDto memberSearchDto,
                         HttpSession httpSession){
        int partyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
        JSONObject json = new JSONObject();
        int pageCount = 0;
        int dataCount = 0;
        int offset = 0;
        if(Assert.isNotNull(pageNo)){
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * DEFAULT_PAGE_SIZE;
        }
        memberSearchDto.setOffset(offset);
        memberSearchDto.setPageSize(DEFAULT_PAGE_SIZE);
        memberSearchDto.setPage(pageNo);
        //用户·性别枚举值
        String genderName = "";
        //用户类型字典值
        String typeName = "";
        //医保类型字典值
        String MCIType = "";
        List<Client> clientList = Collections.emptyList();
        JSONArray jsonArray = new JSONArray();
        try{
//            int memberGroupId = employeeService.getGroupIdByPartyId(partyId);
//            memberSearchDto.setMemberGroupId(memberGroupId);
            //获取登录用户的id
            int userId = securityUserService.queryByPartyId(partyId).getId();
            //根据用户id获取角色id
            int securityGroupId = securityUserGroupService.queryByUserId(userId).getGroupId();
            int memberGroupId = 0;
            if(securityGroupId !=3){
                //若为超级管理员可查看所有
                memberSearchDto.setMemberGroupId(null);
            }else{
                //若为其他包医人只可添加自己分组
                memberGroupId = employeeService.getGroupIdByPartyId(partyId);
                memberSearchDto.setMemberGroupId(memberGroupId);
            }
            clientList = memberService.listBySearchDto(memberSearchDto);
            for(int i = 0 ; i < clientList.size() ; i++){
                //设置患者枚举性别
                if(Assert.isNotNull(clientList.get(i).getGender())) {
                    if (clientList.get(i).getGender() == 1) {
                        genderName = GenderEnums.Male.getGender();
                    } else if (clientList.get(i).getGender() == 2) {
                        genderName = GenderEnums.Female.getGender();
                    } else if (clientList.get(i).getGender() == 3) {
                        genderName = GenderEnums.Other.getGender();
                    }
                }else {
                    genderName = "";
                }
                clientList.get(i).setGenderName(genderName);
                if (Assert.isNotNull(clientList.get(i).getDicType()) && clientList.get(i).getDicType() > 0) {
                    typeName = dictionaryService.queryById(clientList.get(i).getDicType()).getName();
                }
                if (Assert.isNotNull(clientList.get(i).getDicMCIType()) && clientList.get(i).getDicMCIType() > 0) {
                    MCIType = dictionaryService.queryById(clientList.get(i).getDicMCIType()).getName();
                }

                //设置患者类型字典值
                clientList.get(i).setDicTypeName(typeName);
                //设置医保类型字典值
                clientList.get(i).setDicMCITypeName(MCIType);
            }
            for(Client client : clientList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",client.getId());
                jsonObject.put("name",client.getName());
                jsonObject.put("gender",client.getGenderName());
                jsonObject.put("idCardNum",client.getIdCardNum());
                jsonObject.put("tel",client.getTel());
                jsonObject.put("diagnoseType",client.getDiagnoseType());
                jsonObject.put("dicType",client.getDicTypeName());
                if (Assert.isNull(client.getDicMCITypeName())){
                    jsonObject.put("dicMciType","无");
                } else {
                    jsonObject.put("dicMciType",client.getDicMCITypeName());
                }
                jsonObject.put("allCost",client.getAllCost());
                String partyName = employeeService.queryNameByPartyId(client.getCreatedPartyId());
                jsonObject.put("partyName",partyName);
                jsonObject.put("lastModifiedTime", DateUtils.formatDateSimple(client.getLastModifiedTime()));
                jsonArray.add(jsonObject);
            }
            //dataCount = clientList.size();
            dataCount = memberService.countBySearchDto(memberSearchDto);
            pageCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE, dataCount);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", jsonArray);
        json.put("dataCount", pageCount);
        json.put("numCount", dataCount);
        return json;
    }

    /**
     * ajax 姓名搜索框模糊查询
     *
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminProfilePortrayalList)
    @RequestMapping(value = "ajax/name", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSearchByName(@RequestParam("name") String name, HttpSession httpSession){
        int partyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
        JSONArray jsonArray = new JSONArray();
        MemberSearchDto searchDto = new MemberSearchDto();
        try{
            int memberGroupId = employeeService.getGroupIdByPartyId(partyId);
            searchDto.setMemberGroupId(memberGroupId);
            searchDto.setName(name);
            List<Client> clientList = memberService.listBySearchDto(searchDto);
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
     * ajax 身份证号搜索框模糊查询
     *
     * @param idCard
     * @return
     */
    @Module(ModuleEnums.AdminProfilePortrayalList)
    @RequestMapping(value = {"ajax/idCard"},method = RequestMethod.GET)
    @ResponseBody
    public JSON searchByIdCard(@RequestParam("idCard")String idCard, HttpSession httpSession){
        int partyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
        JSONArray jsonArray = new JSONArray();
        MemberSearchDto searchDto = new MemberSearchDto();
        try{
            int memberGroupId = employeeService.getGroupIdByPartyId(partyId);
            searchDto.setMemberGroupId(memberGroupId);
            searchDto.setIdCardNum(idCard);
            List<Client> clientList = memberService.listBySearchDto(searchDto);
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
     * 给单个患者画像
     *
     * @param id
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminProfilePortrayalNew)
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String toNewProfile(@RequestParam("id") Integer id,
                               Model model){
        List<LabelType> labelTypeList = Collections.emptyList();
        List<String> profileList = Collections.emptyList();
        try{
            labelTypeList = labelTypeService.listAll();
            List<ProfileDto> profileDtoList = new ArrayList<ProfileDto>();
            //处理标签类型下患者已有的画像
            for (LabelType type : labelTypeList){
                ProfileDto profileDto = new ProfileDto();
                profileDto.setTypeId(type.getId());
                profileDto.setTypeName(type.getName());
                profileDto.setIsMultiple(type.getIsMultiple());
                profileList = portrayalService.listProfileByClientIdAndTypeId(id, type.getId());
                if(Assert.isNotNull(profileList)){
                        String[] array = (String[])profileList.toArray(new String[profileList.size()]);
                        String str = join(array, ",");
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
            //model.addAttribute("labelTypeList", labelTypeList);
            model.addAttribute("profileDtoList", profileDtoList);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/profile/portrayal/new";
    }

    /**
     * 去批量修改页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminProfilePortrayalUpdate)
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String toNewProfile(Model model){
        List<LabelType> labelTypeList = Collections.emptyList();
        List<LabelItem> labelItemList = Collections.emptyList();
        List<LabelItemDto> labelItemDtoList = new ArrayList<LabelItemDto>();
        try{
            labelTypeList = labelTypeService.listAll();
            //处理每个标签类型下的标签项，将一个类型下的所有标签项都存到itemList中
            for (LabelType labelType : labelTypeList){
                //根据类型id查询所有的标签项
                labelItemList = labelItemService.listByTypeId(labelType.getId());
                LabelItemDto labelItemDto = new LabelItemDto();
                //用itemName去存所有的标签项
                List<String> itemName = new ArrayList<String>();
                if (Assert.isNotNull(labelItemList)){
                    for (LabelItem labelItem : labelItemList){
                        itemName.add(labelItem.getDicLabelName());
                    }
                }
                if (itemName == null || itemName.size() == 0 ){
                    labelItemDto.setItemList("");
                } else {
                    String str = DataUtils.listStringToString(itemName);
                    labelItemDto.setItemList(str);
                }
                //往labelItemDto中set值
                labelItemDto.setTypeId(labelType.getId());
                labelItemDtoList.add(labelItemDto);
            }
            model.addAttribute("labelTypeList", labelTypeList);
            model.addAttribute("labelItemDtoList", labelItemDtoList);
        } catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/profile/portrayal/update";
    }

    /**
     * ajax 获取选择的标签项
     *
     * @param typeId
     * @return
     */
    @Module(ModuleEnums.AdminProfilePortrayalUpdate)
    @RequestMapping(value = "ajax/select", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSelect(@RequestParam("typeId") Integer typeId){
        List<LabelItem> labelItemList = Collections.emptyList();
        JSONArray jsonArray = new JSONArray();
        try{
            if (Assert.isNull(typeId)){
                return null;
            }
            labelItemList = labelItemService.listByTypeId(typeId);
            for (LabelItem labelItem : labelItemList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("labelItemId", labelItem.getId());
                jsonObject.put("labelItemName", labelItem.getDicLabelName());
                jsonArray.add(jsonObject);
            }
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return sendJsonArray(jsonArray);
    }

    /**
     * ajax 批量修改---单选标签类型的画像信息
     *
     * @param typeId
     * @param itemId
     * @param clientId
     * @param clickNumber
     * @return
     */
    @Module(ModuleEnums.AdminProfilePortrayalUpdate)
    @RequestMapping(value = "ajax/update/select", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxUpdateSelect(@RequestParam("typeId") Integer typeId,
                                 @RequestParam("itemId") Integer itemId,
                                 @RequestParam("clientId") String clientId,
                                 @RequestParam("clickNumber") Integer clickNumber,
                                 HttpSession httpSession){
        //获取当前登录用户的partyId
        int partyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
        JSONObject json = new JSONObject();
        if (Assert.isNull(clientId) && Assert.isNull(typeId)){
            return sendMsgAndCode(AJAX_FAILURE_CODE, "操作失败");
        }
        List<Integer> clientIdList = DataUtils.stringToListInt(clientId);
        List<String> nameList = new ArrayList<String>();
        try{
            //clickNumber为1时表示前台第一次点击确认按钮
            if (clickNumber == 1){
                if (itemId == -1){
                    json.put("code", 0);
                    json.put("tipMsg", "确认清空这些患者的画像信息么？");
                    return json;
                }
                for (Integer id : clientIdList){
                    //查询当前患者在此标签类型下的画像信息（标签类型恒为单选项）
                    int count = portrayalService.countByClientIdAndTypeId(id, typeId);
                    if (count > 0){
                        //根据clientId获取患者信息
                        Client client = clientService.queryById(id);
                        nameList.add(client.getName());
                    }
                }
                if(nameList.size() == 0){
                    json.put("code", 0);
                    json.put("tipMsg", "确认为所选择的患者添入画像信息么？");
                    return json;
                }else {
                    //将name转化为String类型，发送到前台提示框
                    String[] array = (String[])nameList.toArray(new String[nameList.size()]);
                    String nameStr = org.apache.commons.lang.StringUtils.join(array, ",");
                    json.put("code", 0);
                    json.put("tipMsg", "患者：( " + nameStr + " )在该类型下已有画像信息，确认修改么？");
                    return json;
                }

            }else if(clickNumber == 2){
                //clickNumber为2时表示前台二次确认
                if (itemId == -1){
                    //如果未选择标签项，则清除所选的患者在该单选类型下的画像信息
                    portrayalService.batchDelProfile(clientIdList, typeId);
                    return sendMsgAndCode(AJAX_SUCCESS_CODE, "操作成功");
                }else {
                    //如果选择了新的标签项，则清除所选的患者在该单选类型下的画像信息，然后批量加入新选的标签项
                    portrayalService.batchDelProfile(clientIdList, typeId);
                    portrayalService.batchNewProfile(clientIdList, typeId, itemId, partyId);
                    return sendMsgAndCode(AJAX_SUCCESS_CODE, "操作成功");
                }
            }else {
                return sendMsgAndCode(AJAX_FAILURE_CODE, "操作失败");
            }
        } catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax 批量修改---多选标签类型的画像信息
     *
     * @param itemId
     * @param clientId
     * @param clickNumber
     * @return
     */
    @Module(ModuleEnums.AdminProfilePortrayalUpdate)
    @RequestMapping(value = "ajax/update/multiple", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxUpdateMultiple(@RequestParam("labelTypeId") String labelTypeId,
                                   @RequestParam("itemId") String itemId,
                                   @RequestParam("clientId") String clientId,
                                   @RequestParam("clickNumber") Integer clickNumber,
                                   HttpSession httpSession){
        //获取当前登录用户的partyId
        int partyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
        JSONObject json = new JSONObject();
        if (Assert.isNull(clientId) && Assert.isNull(labelTypeId)){
            return sendMsgAndCode(AJAX_FAILURE_CODE, "操作失败");
        }
        int typeId = Integer.parseInt(labelTypeId);
        List<Integer> clientIdList = DataUtils.stringToListInt(clientId);
        List<String> nameList = new ArrayList<String>();
        try{
            //clickNumber为1时表示前台第一次点击确认按钮
            if (clickNumber == 1){
                if (Assert.isNull(itemId)){
                    json.put("code", 0);
                    json.put("tipMsg", "确认清空这些患者的画像信息么？");
                    return json;
                }
                for (Integer id : clientIdList){
                    //查询当前患者在此标签类型下的画像信息（标签类型恒为多选项）
                    int count = portrayalService.countByClientIdAndTypeId(id, typeId);
                    if (count > 0){
                        //根据clientId获取患者信息
                        Client client = clientService.queryById(id);
                        nameList.add(client.getName());
                    }
                }
                if(nameList.size() == 0){
                    json.put("code", 0);
                    json.put("tipMsg", "确认为所选择的患者添入画像信息么？");
                    return json;
                }else {
                    //将name转化为String类型，发送到前台提示框
                    String[] array = (String[])nameList.toArray(new String[nameList.size()]);
                    String nameStr = org.apache.commons.lang.StringUtils.join(array, ",");
                    json.put("code", 0);
                    json.put("tipMsg", "患者：( " + nameStr + " )在该类型下已有画像信息，确认修改么？");
                    return json;
                }

            }else if(clickNumber == 2){
                if (Assert.isNull(itemId)){
                    //如果未选择标签项，则清除所选的患者在该单选类型下的画像信息
                    portrayalService.batchDelProfile(clientIdList, typeId);
                    return sendMsgAndCode(AJAX_SUCCESS_CODE, "操作成功");
                }else {
                    //如果选择了新的标签项，则清除所选的患者在该单选类型下的画像信息，然后批量加入新选的标签项
                    portrayalService.batchDelProfile(clientIdList, typeId);
                    List<Integer> itemIdList = DataUtils.stringToListInt(itemId);
                    for (Integer itemid : itemIdList){
                        portrayalService.batchNewProfile(clientIdList, typeId, itemid, partyId);
                    }
                    return sendMsgAndCode(AJAX_SUCCESS_CODE, "操作成功");
                }
            }else {
                return sendMsgAndCode(AJAX_FAILURE_CODE, "操作失败");
            }
        } catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

}
