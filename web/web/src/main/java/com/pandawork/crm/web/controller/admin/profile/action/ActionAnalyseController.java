package com.pandawork.crm.web.controller.admin.profile.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.dto.client.basic.ClientSearchDto;
import com.pandawork.crm.common.dto.client.member.MemberSearchDto;
import com.pandawork.crm.common.dto.client.visit.VisitSearchDto;
import com.pandawork.crm.common.dto.event.EventSearchDto;
import com.pandawork.crm.common.dto.profile.action.ActionSearchDto;
import com.pandawork.crm.common.entity.client.basic.Client;
import com.pandawork.crm.common.entity.client.visit.Visit;
import com.pandawork.crm.common.entity.event.Event;
import com.pandawork.crm.common.entity.party.dictionary.Dictionary;
import com.pandawork.crm.common.entity.party.member.Member;
import com.pandawork.crm.common.entity.profile.analysis.AnalysisResult;
import com.pandawork.crm.common.entity.profile.label.LabelItem;
import com.pandawork.crm.common.entity.profile.label.LabelType;
import com.pandawork.crm.common.entity.profile.portrayal.Profile;
import com.pandawork.crm.common.enums.client.GenderEnums;
import com.pandawork.crm.common.enums.event.EventTypeEnums;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.enums.party.MemberGroupTypeEnums;
import com.pandawork.crm.common.enums.party.dictionary.DictionaryEnums;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.DateUtils;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.service.client.member.MemberService;
import com.pandawork.crm.service.event.prepare.EventService;
import com.pandawork.crm.service.event.processing.ProcessingEventService;
import com.pandawork.crm.service.party.member.MemberGroupService;
import com.pandawork.crm.service.profile.action.ActionAnalyseService;
import com.pandawork.crm.service.profile.analysis.AnalysisResultService;
import com.pandawork.crm.service.profile.label.LabelItemService;
import com.pandawork.crm.service.profile.portrayal.PortrayalService;
import com.pandawork.crm.web.spring.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ActionAnalyseController
 *
 * @author Daydreamer
 * @date 2017/8/3 9:42
 */
@Controller
@Module(ModuleEnums.AdminProfileAction)
@RequestMapping(value = URLConstants.ADMIN_PROFILE_ACTION_URL )
public class ActionAnalyseController extends AbstractController {

    private int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 去列表页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminProfileActionList)
    @RequestMapping(value = {"","list"}, method = RequestMethod.GET)
    public String toList(Model model) {
        int dataCount = 0;

        List<Client> clients0 = new ArrayList<Client>();

        //性别的value和name
        List<Client> clients1 = new ArrayList<Client>();
        //民族的value和name
        List<Dictionary> clients2 = new ArrayList<Dictionary>();
        //医保类型的value和name
        List<Dictionary> clients3 = new ArrayList<Dictionary>();
        //用户类型的value和name
        List<Dictionary> clients4 = new ArrayList<Dictionary>();

        //三组标签的value和name
        List<LabelType> labelTypes = new ArrayList<LabelType>();
        EventSearchDto eventSearchDto = new EventSearchDto();
        eventSearchDto.setType(EventTypeEnums.Promotion.getId());

        try {
//            int count = actionAnalyseService.queryCountAll();
//            dataCount = DataUtils.getPageCount(pageSize, count);

            clients0 = actionAnalyseService.queryClient();
            //clients1 = actionAnalyseService.queryGenderAll();
            Client client0 = new Client();
            Client client1 = new Client();
            Client client2 = new Client();
            client0.setGenderName("男");
            client0.setGender(1);
            client1.setGenderName("女");
            client1.setGender(2);
            client2.setGenderName("其他");
            client2.setGender(3);
            clients1.add(client0);
            clients1.add(client1);
            clients1.add(client2);
            clients2 = dictionaryService.listByPId(DictionaryEnums.DIC_NATION.getId());
            clients3 = dictionaryService.listByPId(DictionaryEnums.DIC_CLIENT_MCI_TYPE.getId());
            clients4 = dictionaryService.listByPId(DictionaryEnums.DIC_CLIENT_TYPE.getId());
            List<Event> events = eventService.listByEventSearchDto(eventSearchDto);

            //labelTypes = labelTypeService.listAll();
            labelTypes = labelTypeService.listExistLabelItem();

            model.addAttribute("dataCount", dataCount);
            model.addAttribute("clients0", clients0);
            model.addAttribute("clients1", clients1);
            model.addAttribute("clients2", clients2);
            model.addAttribute("clients3", clients3);
            model.addAttribute("clients4", clients4);
            model.addAttribute("labelTypes", labelTypes);
            model.addAttribute("events", events);

            List<Client> clientList1 = new ArrayList<Client>();
            List<Client> clients = new ArrayList<Client>();
            VisitSearchDto visitSearchDto = new VisitSearchDto();
            ClientSearchDto clientSearchDto = new ClientSearchDto();
            ClientSearchDto clientSearchDto1 = new ClientSearchDto();
            List<Dictionary> dictionaryList = Collections.emptyList();
            JSONArray jsonArrayPie = new JSONArray();
            JSONArray typeName = new JSONArray();
            JSONArray YBJsonArrayPie = new JSONArray();
            JSONArray YBTypeName = new JSONArray();
            JSONArray HYJsonArrayPie = new JSONArray();
            JSONArray HYTypeName = new JSONArray();

            try {
                //饼图：用户类型
                dictionaryList = dictionaryService.listByPId(DictionaryEnums.DIC_CLIENT_TYPE.getId());
                for (Dictionary dictionary : dictionaryList) {
                    JSONObject json = new JSONObject();
                    clientSearchDto1.setDicType(dictionary.getId());
                    clientList1 = clientService.listBySearchDto(clientSearchDto1);
                    int value = clientList1.size();
                    String name = dictionary.getName();
                    typeName.add(name);
                    json.put("value", value);
                    json.put("name", name);
                    jsonArrayPie.add(json);
                }
                model.addAttribute("typeName", typeName);
                model.addAttribute("jsonArrayPie", jsonArrayPie);

                //饼图：医保类型
                dictionaryList = dictionaryService.listByPId(DictionaryEnums.DIC_CLIENT_MCI_TYPE.getId());
                for (Dictionary dictionary : dictionaryList) {
                    JSONObject json = new JSONObject();
                    clientSearchDto.setDicMCIType(dictionary.getId());
                    clients = clientService.listBySearchDto(clientSearchDto);
                    int value = clients.size();
                    String name = dictionary.getName();
                    YBTypeName.add(name);
                    json.put("value", value);
                    json.put("name", name);
                    YBJsonArrayPie.add(json);
                }
                model.addAttribute("YBTypeName", YBTypeName);
                model.addAttribute("YBJsonArrayPie", YBJsonArrayPie);

                //饼图：行业占比
                LabelType hangye = labelTypeService.queryByName("职业");
                if(Assert.isNotNull(hangye)) {
                    List<LabelItem> labelItemList = labelItemService.listByTypeId(hangye.getId());
                    for (int i = 0; i <= labelItemList.size() - 1; i++) {
                        JSONObject json = new JSONObject();
                        //根据标签项id获取患者列表
                        List<Profile> profileList = portrayalService.listByItemId(labelItemList.get(i).getId());
                        List<Integer> clientIdList = Collections.emptyList();
                        int value = 0;
                        if (Assert.isNull(profileList)) {
                            value = 0;
                        } else {
                            value = profileList.size();
                        }
                        String name = labelItemList.get(i).getDicLabelName();
                        HYTypeName.add(name);
                        json.put("value", value);
                        json.put("name", name);
                        HYJsonArrayPie.add(json);
                    }
                }
                model.addAttribute("HYTypeName", HYTypeName);
                model.addAttribute("HYJsonArrayPie", HYJsonArrayPie);

            } catch (SSException e) {
                LogClerk.errLog.error(e);
                sendErrMsg(e.getMessage());
                return ADMIN_SYS_ERR_PAGE;
            }

        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return "admin/profile/action/list";
    }

    /**
     * 根据条件查询行为分析列表
     *
     * @param actionSearchDto
     * @return
     */
    @Module(ModuleEnums.AdminProfileActionList)
    @RequestMapping(value = "ajax/list", method = RequestMethod.GET)
    @ResponseBody
    public JSON getList(@RequestParam("pageNo") Integer pageNo,
                        ActionSearchDto actionSearchDto,
                        Model model) {
        JSONObject json = new JSONObject();
        int numCount = 0, dataCount = 0;
        int offset = 0;
        if (Assert.isNotNull(pageNo)) {
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * DEFAULT_PAGE_SIZE;
        }
        actionSearchDto.setOffset(offset);
        actionSearchDto.setPageNo(pageNo);
        actionSearchDto.setPageSize(DEFAULT_PAGE_SIZE);

        //患者信息内容列表
        try {
            JSONArray jsonArray = new JSONArray();
            List<Client> clientSame = actionAnalyseService.listActionListDtoBySearchDto(actionSearchDto);
            //如果查询条件为空，患者信息列表什么也不显示
//            if (Assert.isNull(actionSearchDto.getLabelItemFirst()) && Assert.isNull(actionSearchDto.getLabelItemSecond())
//                    && Assert.isNull(actionSearchDto.getLabelItemThird()) && Assert.isNull(actionSearchDto.getGender())
//                    && Assert.isNull(actionSearchDto.getDicMciType()) && Assert.isNull(actionSearchDto.getDicNation())
//                    && Assert.isNull(actionSearchDto.getUserType())) {
//                clientSame.clear();
//            }
            String str = "";
            //如果查询条件不为空，则查询结果正常显示
            for (Client client : clientSame) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", client.getId());
                jsonObject.put("isMember", client.getIsMember());
                jsonObject.put("name", client.getName());
                if(Assert.isNull(client.getIdCardNum())){
                    jsonObject.put("idCardNum", str);
                }else {
                    jsonObject.put("idCardNum", client.getIdCardNum());
                }
                if(Assert.isNull(client.getGender())){
                    jsonObject.put("gender", str);
                }else {
                    jsonObject.put("gender", GenderEnums.valueOf(client.getGender()).getGender());
                }if(Assert.isNull(client.getTel())){
                    jsonObject.put("tel", str);
                }else {
                    jsonObject.put("tel", client.getTel());
                }
                if(Assert.isNull(client.getDicType())){
                    jsonObject.put("dicTypeName", str);
                }else {
                    jsonObject.put("dicTypeName", DictionaryEnums.valueOf(client.getDicType()).getDictionary());
                }
                if(Assert.isNull(client.getDicMCIType())){
                    jsonObject.put("dicMCITypeName", str);
                }else {
                    jsonObject.put("dicMCITypeName", dictionaryService.queryById(client.getDicMCIType()).getName());
                }
                if(Assert.isNull(client.getAllCost())){
                    jsonObject.put("allCost", 0);
                }else {
                    jsonObject.put("allCost", client.getAllCost());
                }
                if(Assert.isNull(client.getId())){
                    jsonObject.put("countEvent", 0);
                }else {
                    jsonObject.put("countEvent", processingEventService.countEventByClientId(client.getId()));
                }
                jsonArray.add(jsonObject);
            }
            numCount = actionAnalyseService.countActionListDtoBySearchDto(actionSearchDto);
//            if (Assert.isNull(actionSearchDto.getLabelItemFirst()) && Assert.isNull(actionSearchDto.getLabelItemSecond())
//                    && Assert.isNull(actionSearchDto.getLabelItemThird()) && Assert.isNull(actionSearchDto.getGender())
//                    && Assert.isNull(actionSearchDto.getDicMciType()) && Assert.isNull(actionSearchDto.getDicNation())
//                    && Assert.isNull(actionSearchDto.getUserType())){
//                dataCount = 0;
//                numCount = 0;
//            }else {
                dataCount = DataUtils.getPageCount(pageSize, numCount);
//            }
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
     * ajax显示标签列表
     *
     * @param tagTypeId
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminProfileActionList)
    @RequestMapping(value = "labelItem/list", method = RequestMethod.GET)
    @ResponseBody
    public JSON toLabelItemList(@RequestParam("tagTypeId") Integer tagTypeId,
                                Model model) {
        JSONObject jsonObject = new JSONObject();
        List<LabelItem> labelItems1 = new ArrayList<LabelItem>();
        try {
            if (Assert.isNotNull(tagTypeId)) {
                labelItems1 = labelItemService.listByTypeId(tagTypeId);
                jsonObject.put("code", AJAX_SUCCESS_CODE);
                jsonObject.put("list", labelItems1);
            } else {
                jsonObject.put("code", AJAX_FAILURE_CODE);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return jsonObject;
    }

    /**
     * 给营销型活动添加参与人员
     *
     * @param eventId
     * @param clientId
     * @param httpSession
     * @return
     */
    @Module(ModuleEnums.AdminProfileActionList)
    @RequestMapping(value = "ajax/selectEvent", method = RequestMethod.POST)
    @ResponseBody
    public JSON toSelectEvent(@RequestParam("id") Integer eventId,
                                    @RequestParam("clientId") String clientId,
                                    HttpSession httpSession){
        List<Integer> clientIds = new ArrayList<Integer>();
        JSONObject json = new JSONObject();
        try {
            if(Assert.isNotNull(clientId)){
                clientIds = DataUtils.stringToListInt(clientId);
            }
            int partyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
            if (Assert.isNotNull(eventId) && Assert.isNotNull(clientIds)){
                actionAnalyseService.selectEventCase(eventId, clientIds, partyId);
                return sendJsonObject(AJAX_SUCCESS_CODE);
            } else {
                return sendJsonObject(AJAX_FAILURE_CODE);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
