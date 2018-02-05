package com.pandawork.crm.web.controller.admin.profile.label;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.entity.party.dictionary.Dictionary;
import com.pandawork.crm.common.entity.profile.label.LabelItem;
import com.pandawork.crm.common.entity.profile.label.LabelType;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.enums.party.dictionary.DictionaryEnums;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeArray.join;

/**
 * LabelController
 * Author： Daydreamer
 * Date: 2017/7/19
 * Time: 13:47
 */
@Controller
@Module(ModuleEnums.AdminProfileLabel)
@RequestMapping(value = URLConstants.ADMIN_PROFILE_LABEL_URL)
public class LabelController extends AbstractController{

    /**
     * 去列表页面
     *
     * @return
     */
    @Module(ModuleEnums.AdminProfileLabelTypeList)//权限管理、控制
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(HttpSession request, Model model) {
        int dataCount = 0;
        String pId = String.valueOf(request.getAttribute("partyId"));
        int createdPartyId = Integer.parseInt(pId);
        String createdName = "";
        String key = "";

        List<LabelType> labelTypes = new ArrayList<LabelType>();//所有标签类型列表
        List<Dictionary> delDicTypes = new ArrayList<Dictionary>();//要删除的标签类型字典
        List<Dictionary> dicTypes = new ArrayList<Dictionary>();//标签类型的所有字典值

        try{
            labelTypes = labelTypeService.listAll();
            dicTypes = dictionaryService.listByPId(DictionaryEnums.DIC_LABEL_TYPE.getId());

            for (LabelType labelType : labelTypes){
                for (Dictionary dicType : dicTypes){
                    if ((dicType.getName()).equals(labelType.getName())){
                        delDicTypes.add(dicType);
                    }
                }
            }
            dicTypes.removeAll(delDicTypes);

//            dataCount = labelTypeService.count(key);
//            dataCount = (int) Math.ceil(((double)dataCount)/DEFAULT_PAGE_SIZE);
            createdName = employeeService.queryNameByPartyId(createdPartyId);
        }catch (SSException e){
            LogClerk.errLog.error(e);
        }

        model.addAttribute("createdPartyId", createdPartyId);
        model.addAttribute("createdName", createdName);
        model.addAttribute("dicTypes",dicTypes);

        return "/admin/profile/label/list";
    }

    /**
     * 根据输入关键词搜索标签类型
     *
     * @param key
     * @param pageNo
     * @return
     * @throws SSException
     */
    @Module(ModuleEnums.AdminProfileLabelTypeList)
    @RequestMapping(value = "type/ajax/query/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject queryByKeyWord(@RequestParam(value = "tagType", required = false) String key,
                                     @RequestParam("pageNo") Integer pageNo) throws SSException{
        List<LabelType> labelTypes = Collections.emptyList();
        JSONObject json = new JSONObject();
        int dataCount = 0, numCount = 0;
        try {
            labelTypes = labelTypeService.queryByKeyWord(key, pageNo, DEFAULT_PAGE_SIZE);
            numCount =labelTypeService.countByKeyWord(key);
            //numCount = labelTypes.size();
            dataCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE,numCount);
            //处理标签类型下的标签项
            for (LabelType labelType : labelTypes){
                List<LabelItem> labelItemList = labelItemService.listByTypeId(labelType.getId());
                List<String> items = new ArrayList<String>();
                for (LabelItem labelItem : labelItemList){
                    items.add(labelItem.getDicLabelName());
                }
                if (items != null && !items.isEmpty()){
                    String str = DataUtils.listStringToString(items);
                    labelType.setLabelText(str);
                }
            }
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        int i = 1 + (pageNo - 1) * DEFAULT_PAGE_SIZE;
        JSONArray jsonArray = new JSONArray();
        for (LabelType labelType : labelTypes){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("number",i++);
            jsonObject.put("id", labelType.getId());
            jsonObject.put("name", labelType.getName());

            int isMultiple = labelType.getIsMultiple();
            String isMultiplea = null;
            if (isMultiple == 1){
                isMultiplea = "是";
            }
            if (isMultiple == 0){
                isMultiplea = "否";
            }
            jsonObject.put("isMultiple",isMultiple);
            jsonObject.put("isMultiplea",isMultiplea);
            jsonObject.put("labelCount",labelType.getLabelCount());

            if (Assert.isNull(labelType.getLabelText())){
                jsonObject.put("labelText","");
            } else {
                jsonObject.put("labelText",labelType.getLabelText());
            }
            jsonObject.put("labelComment",labelType.getLabelComment());
            jsonObject.put("createdPartyId",labelType.getCreatedPartyId());
            jsonObject.put("createdName",labelType.getCreatedPartyName());

            Date time = labelType.getCreatedTime();
            SimpleDateFormat formatter = new SimpleDateFormat(" yyyy-MM-dd");
            String createdTime = formatter.format(time);

            jsonObject.put("createdTime",createdTime);
            jsonObject.put("lastModifiedTime",labelType.getLastModifiedTime());
            jsonObject.put("deleted",labelType.getDeleted());
            jsonArray.add(jsonObject);
        }
        json.put("code", AJAX_SUCCESS_CODE);
        json.put("list", jsonArray);
        json.put("dataCount", dataCount);
        json.put("numCount", numCount);
        return json;
    }

    /**
     * AJAX增加标签类型
     *
     * @param labelType
     * @return
     * @throws SSException
     */
    @Module(ModuleEnums.AdminProfileLabelTypeNew)
    @RequestMapping(value = "type/ajax/new", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxNewLabelType(LabelType labelType) throws SSException{
        try{
            labelType.setDeleted(0);
            labelType.setName(labelType.getName());
            labelTypeService.newLabelType(labelType);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * AJAX修改标签类型
     *
     * @param labelType
     * @return
     * @throws SSException
     */
    @Module(ModuleEnums.AdminProfileLabelTypeUpdate)
    @RequestMapping(value = "type/ajax/update",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxUpdateLabelType(LabelType labelType){
        try {
            labelTypeService.updateLabelType(labelType);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax批量删除列表
     *
     * @param idStr
     * @return
     * @throws SSException
     */
    @Module(ModuleEnums.AdminProfileLabelTypeDelete)
    @RequestMapping(value = "type/ajax/delSeveral",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxDelLabelTypes(@RequestParam("idStr") String idStr) throws SSException{
        try {
            List<Integer> idList = DataUtils.stringToListInt(idStr);
            for (int id : idList) {
                labelTypeService.delById(id);
            }
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

    }

    /**
     * Ajax获取标签类型下标签项
     *
     * @param typeId
     * @return
     * @throws SSException
     */
    @Module(ModuleEnums.AdminProfileLabelItemList)
    @RequestMapping(value = "item/ajax/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject listLabelItem(@RequestParam("typeId") Integer typeId) throws SSException{
        List<LabelItem> Items = Collections.emptyList();
        List<LabelItem> labelItems = new ArrayList<LabelItem>();//所有标签项列表
        List<Dictionary> delDicItems = new ArrayList<Dictionary>();//要删除的标签项字典
        List<Dictionary> dicItems = new ArrayList<Dictionary>();//标签项的所有字典值
        JSONObject json = new JSONObject();
        try{
            Items = labelItemService.listByTypeId(typeId);

            labelItems = labelItemService.listByTypeId(typeId);
            //根据typeId得到名字
            LabelType labelType = labelTypeService.queryById(typeId);
            String labelTypeName = labelType.getName();
            //根据pid0和名字得到标签类型字典值名称和id
            Dictionary dicType = dictionaryService.queryDictionaryByPIdAndName(DictionaryEnums.DIC_LABEL_TYPE.getId(), labelTypeName);
            int dicTypeId = dicType.getId();
            //根据标签类型id获取标签项字典值列表
            dicItems = dictionaryService.listByPId(dicTypeId);
            for (LabelItem item : labelItems){
                for (Dictionary dicItem : dicItems){
                    if ((dicItem.getName()).equals(item.getDicLabelName())){
                        delDicItems.add(dicItem);
                    }
                }
            }
            dicItems.removeAll(delDicItems);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        JSONArray jsonArray = new JSONArray();
        for (LabelItem labelItem : Items){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", labelItem.getId());
            jsonObject.put("labelTypeId", typeId);
            jsonObject.put("labelType",labelTypeService.queryById(typeId));
            jsonObject.put("dicLabelId", labelItem.getDicLabelId());
            jsonObject.put("dicLabelName", labelItem.getDicLabelName());

            jsonArray.add(jsonObject);
        }

        json.put("code",AJAX_SUCCESS_CODE);
        json.put("list",jsonArray);
        json.put("selectList",dicItems);
        return json;
    }

    /**
     * Ajax新增标签项
     *
     * @param labelItem
     * @return
     * @throws SSException
     */
    @Module(ModuleEnums.AdminProfileLabelItemNew)
    @RequestMapping(value = "item/ajax/new", method = RequestMethod.POST)
    @ResponseBody

    public JSONObject ajaxNewLabelItem(LabelItem labelItem) throws SSException{
        try {
            if (Assert.isNotNull(labelItem.getLabelTypeId())){
                LabelType labelType = labelTypeService.queryById(labelItem.getLabelTypeId());
                //处理标签项表
                labelItem.setDeleted(0);
                labelItemService.newLabelItem(labelItem);
                //处理当前标签类型下的标签项数量+1
                labelType.setLabelCount(labelType.getLabelCount() + 1);
                labelType.setId(labelType.getId());
                labelTypeService.updateLabelType(labelType);
                return sendJsonObject(AJAX_SUCCESS_CODE);
            } else {
                return sendJsonObject(AJAX_FAILURE_CODE);
            }
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax删除标签项
     *
     * @param tagId
     * @return
     * @throws SSException
     */
    @Module(ModuleEnums.AdminProfileLabelItemDelete)
    @RequestMapping(value = "item/ajax/del",method = RequestMethod.POST)
    @ResponseBody

    public JSONObject ajaxDelLabelItem(@RequestParam("tagId") int tagId)throws SSException{
        try {
            //处理此类型下的标签项数量-1
            if (Assert.isNotNull(tagId)){
                LabelItem labelItem = labelItemService.queryById(tagId);
                LabelType labelType = labelTypeService.queryById(labelItem.getLabelTypeId());
                if (labelType.getLabelCount() > 0){
                    LabelType labelType1 = new LabelType();
                    labelType1.setId(labelType.getId());
                    labelType1.setLabelCount(labelType.getLabelCount() - 1);
                    labelTypeService.updateLabelType(labelType1);
                }
                labelItemService.delLabelItem(tagId);
                return sendJsonObject(AJAX_SUCCESS_CODE);
            } else {
                return sendJsonObject(AJAX_FAILURE_CODE);
            }
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

}
