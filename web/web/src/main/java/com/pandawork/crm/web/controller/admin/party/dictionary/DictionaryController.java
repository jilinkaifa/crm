package com.pandawork.crm.web.controller.admin.party.dictionary;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.entity.party.dictionary.Dictionary;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.web.controller.admin.AdminController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * Created by shura on 2017/7/18.
 */
@Controller
@Module(ModuleEnums.AdminPartyDictionary)
@RequestMapping(value = URLConstants.ADMIN_PARTY_DICTIONARY_URL)
public class  DictionaryController extends AdminController{

    /**
     * 去字典值管理页
     *
     * @return
     */
    @Module(ModuleEnums.AdminPartyDictionaryList)
    @RequestMapping(value = {"", "list"},method = RequestMethod.GET)
    public String toList(){
        return "admin/party/dictionary/list";
    }

    /**
     * 返回字典树
     *
     * @return
     */
    @Module(ModuleEnums.AdminPartyDictionaryList)
    @RequestMapping(value = {"ajax/list"},method = RequestMethod.GET)
    @ResponseBody
    public JSONArray ajaxList(){
        JSONArray jsonArray = new JSONArray();
        try{
            List<Dictionary> dictionaryList = dictionaryService.listAllDictionary();
            for(Dictionary dictionary:dictionaryList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",dictionary.getId());
                jsonObject.put("name",dictionary.getName());
                jsonObject.put("pId",dictionary.getParentId());
                jsonArray.add(jsonObject);
            }
        } catch (SSException e){
            LogClerk.errLog.error(e);
            jsonArray.clear();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", AJAX_FAILURE_CODE);
            jsonObject.put("errMsg", e.getMessage());
            jsonArray.add(jsonObject);
            return jsonArray;
        } catch (Exception e){
            LogClerk.errLog.error(e);
            jsonArray.clear();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", AJAX_FAILURE_CODE);
            jsonObject.put("errMsg", e.getMessage());
            jsonArray.add(jsonObject);
            return jsonArray;
        }
        return jsonArray;
    }

    /**
     * ajax添加字典值
     *
     * @param pId
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminPartyDictionaryNew)
    @RequestMapping(value = {"ajax/new"},method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxNewDictionary(@RequestParam("pId")Integer pId, @RequestParam("name")String name, HttpSession httpSession){
        int createdPartyId = 0;
        JSONObject jsonObject = new JSONObject();
        try{
            createdPartyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
            Dictionary dictionary = new Dictionary();
            dictionary.setParentId(pId);
            dictionary.setName(name);
            dictionary.setCreatedPartyId(createdPartyId);
            Dictionary newDictionary = dictionaryService.newDictionary(dictionary);
            jsonObject.put("id",newDictionary.getId());
            jsonObject.put("code",AJAX_SUCCESS_CODE);
            return jsonObject;
        } catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            return sendJsonObject(AJAX_FAILURE_CODE);
        }
    }

    /**
     * ajax删除字典值
     *
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminPartyDictionaryDelete)
    @RequestMapping(value = {"ajax/del"},method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxDelDictionary(@RequestParam("id") Integer id){
        try{
           dictionaryService.delDictionary(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            return sendJsonObject(AJAX_FAILURE_CODE);
        }
    }

    /**
     * ajax修改字典值
     *
     * @param id
     * @param pId
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminPartyDictionaryUpdate)
    @RequestMapping(value = {"ajax/update"},method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxUpdateDictionary(@RequestParam("id")Integer id,@RequestParam("pId")Integer pId, @RequestParam("name")String name){
        try{
            Dictionary dictionary = dictionaryService.queryById(id);
            dictionary.setName(name);
            dictionaryService.updateDictionary(dictionary);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            return sendJsonObject(AJAX_FAILURE_CODE);
        }
    }


}
