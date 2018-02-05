package com.pandawork.crm.web.controller.admin.profile.statistics;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.dto.client.basic.ClientSearchDto;
import com.pandawork.crm.common.dto.client.visit.VisitSearchDto;
import com.pandawork.crm.common.entity.client.basic.Client;
import com.pandawork.crm.common.entity.client.visit.Visit;
import com.pandawork.crm.common.entity.party.dictionary.Dictionary;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.enums.party.dictionary.DictionaryEnums;
import com.pandawork.crm.common.utils.DateUtils;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.service.client.basic.ClientService;
import com.pandawork.crm.service.client.visit.VisitService;
import com.pandawork.crm.service.party.dictionary.DictionaryService;
import com.pandawork.crm.service.party.member.MemberGroupService;
import com.pandawork.crm.web.spring.AbstractController;
import com.pandawork.crm.common.entity.party.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Statistics
 * Author： csy
 * Date: 2017/8/3
 * Time: 19:32
 */

@Controller
@Module(ModuleEnums.AdminProfileStatistics)
@RequestMapping(value = URLConstants.ADMIN_PROFILE_STATISTICS_URL)
public class StatisticsController extends AbstractController {


    /**
     * 去往行为分析页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminProfileStatistics)
    @RequestMapping(value = { "" , "chart" }, method = RequestMethod.GET)
    public String toChart(Model model){

        List<Client> clientList1 = new ArrayList<Client>();
        VisitSearchDto visitSearchDto = new VisitSearchDto();
        List<Dictionary> dictionaryList = Collections.emptyList();
        JSONArray jsonArrayPie = new JSONArray();
        JSONArray typeName = new JSONArray();
        ClientSearchDto clientSearchDto1 = new ClientSearchDto();

        try{
            //饼图
            dictionaryList = dictionaryService.listByPId(DictionaryEnums.DIC_CLIENT_TYPE.getId());
            for(Dictionary dictionary : dictionaryList) {
                JSONObject json = new JSONObject();
                clientSearchDto1.setDicType(dictionary.getId());
                clientList1 = clientService.listBySearchDto(clientSearchDto1);
                int value = clientList1.size();
                String name = dictionary.getName();
                typeName.add(name);
                json.put("value",value);
                json.put("name",name);
                jsonArrayPie.add(json);
            }
            model.addAttribute("typeName",typeName);
            model.addAttribute("jsonArrayPie",jsonArrayPie);

            //折线图
            JSONArray jsonArrayMemberGroup = new JSONArray();
            List<Member> memberList = Collections.emptyList();
            memberList = memberGroupService.listAll();

            List<BigDecimal> costListA = new ArrayList<BigDecimal>();
            List<BigDecimal> costListB = new ArrayList<BigDecimal>();
            List<BigDecimal> costListC = new ArrayList<BigDecimal>();
            List<BigDecimal> costListD = new ArrayList<BigDecimal>();
            JSONArray jsonArray = new JSONArray();

            List<BigDecimal> allCost = new ArrayList<BigDecimal>();
            for(int i = 0; i <= memberList.size()-1; i++) {
//            for(int i = 1; i <= 5; i++) {
                JSONObject jsonObject = new JSONObject();
                Member member = new Member();
                allCost.clear();
                List<Member> memberList1 = memberGroupService.isNotDeleted();
                member.setName(memberList1.get(i).getName());
                for (int j = 6; j >= 0; j--) {
                    //获取每天的日期

                    String date = DateUtils.getLastWeekLastDay();
                    String day = DateUtils.formatDateSimple(DateUtils.aheadDay(DateUtils.formatDateSimpleAnother(date),j));
                    BigDecimal cost = visitService.countCostByDate(day,i);
                    if(Assert.isNull(cost)){
                        cost = BigDecimal.valueOf(0.0);
                    }
                    allCost.add(cost);
                }
                jsonObject.put("name",member.getName());
                jsonObject.put("type","line");
                jsonObject.put("tiled","总量");
                jsonObject.put("data",allCost);
                jsonArray.add(jsonObject);

                String memberGroup = member.getName();
                jsonArrayMemberGroup.add(memberGroup);
            }
//            model.addAttribute("jsonArrayLineA",jsonArrayLineA);
//            model.addAttribute("jsonArrayLineB",jsonArrayLineB);
//            model.addAttribute("jsonArrayLineC",jsonArrayLineC);
//            model.addAttribute("jsonArrayLineD",jsonArrayLineD);
            model.addAttribute("jsonArrayMemberGroup",jsonArrayMemberGroup);
            model.addAttribute("jsonArray",jsonArray);

            //柱状图
//            ClientSearchDto clientSearchDto = new ClientSearchDto();
//            JSONArray jsonClientCount = new JSONArray();
//            String lastMonthLastDay = DateUtils.getLastMonthLastDay();
//            String lastMonthFirstDay = DateUtils.getLastMonthFirstDay();
//
//            clientSearchDto.setStartDate(DateUtils.formatDateSimpleAnother(lastMonthFirstDay));
//            clientSearchDto.setEndDate(DateUtils.formatDateSimpleAnother(lastMonthLastDay));
//            List<Client> clientList = clientService.listBySearchDto(clientSearchDto);
//           // int count = clientList.size();
//           // jsonClientCount.add(count);
//
//            for(int i = 0 ; i <= 6 ; i++ ){
//                List<Client> clientListL = new ArrayList<Client>();
//                clientListL.clear();
//                if(i == 0){
//                    lastMonthLastDay = DateUtils.formatDate(DateUtils.formatDateSimpleAnother(lastMonthLastDay));
//                    lastMonthFirstDay = DateUtils.formatDate(DateUtils.formatDateSimpleAnother(lastMonthFirstDay));
//                }else {
//                    lastMonthLastDay = DateUtils.formatDate(DateUtils.aheadDay(DateUtils.formatDateSimpleAnother(lastMonthLastDay),30));
//                    lastMonthFirstDay = DateUtils.formatDate(DateUtils.aheadDay(DateUtils.formatDateSimpleAnother(lastMonthFirstDay),30));
//                }
//                clientSearchDto.setStartDate(DateUtils.formatDateSimpleAnother(lastMonthFirstDay));
//                clientSearchDto.setEndDate(DateUtils.formatDateSimpleAnother(lastMonthLastDay));
//                clientListL = clientService.listBySearchDto(clientSearchDto);
//                int count = clientListL.size();
//                    jsonClientCount.add(count);
//            }
            JSONArray jsonClientCount = new JSONArray();
            List<Integer> numberOfMembersList = visitService.getNumberOfMembers();
            for(Integer num:numberOfMembersList){
                jsonClientCount.add(num);
            }
            model.addAttribute("jsonClientCount",jsonClientCount);

            //计算消费总金额
            //String date = DateUtils.getCurrentMonthFirstDay();
            //JSONArray jsonCostAll = new JSONArray();
            //BigDecimal sumEveryDay = BigDecimal.valueOf(0);
            //BigDecimal sumEveryMonth = BigDecimal.valueOf(0);

//            for (int i = 0; i <= 6; i++) {
//                for (int j = 30; j >= 0; j--) {
//                    //当前日期往前一天
//                    Date dd = DateUtils.formatDateSimpleAnother(date);
//                    Date day = DateUtils.aheadDay(dd, 1);
//
//                    for(int k = 0;k <= memberList.size()-1; k++){
//                        BigDecimal cost = visitService.countCostByDate(DateUtils.formatDateSimple(day),memberList.get(k).getId());
//                        if(Assert.isNull(cost))
//                            cost = BigDecimal.valueOf(0.00);
//                        sumEveryDay = sumEveryDay.add(cost);
//                    }
//                    sumEveryMonth = sumEveryMonth.add(sumEveryDay);
//                    date = DateUtils.formatDateSimple(day);
//                }
//                jsonCostAll.add(sumEveryMonth);
//            }
            JSONArray jsonCostAll = new JSONArray();
            List<BigDecimal> grossSalesList = visitService.getGrossSales();
            for(BigDecimal cost:grossSalesList){
                jsonCostAll.add(cost);
            }
            model.addAttribute("jsonCostAll",jsonCostAll);

            //计算月份
//          List<String> strList = new ArrayList<String>();
//            String day = DateUtils.getLastMonthLastDay();
//            String Month = day.substring(5,7);
//            strList.add(Month);
//            for(int i = 0 ; i <= 5 ; i++ ){
//                String date1 = DateUtils.formatDate(DateUtils.aheadDay(DateUtils.formatDateSimpleAnother(day),31));
//                String Month1 = date1.substring(5,7);
//                strList.add(Month1);
//                day = date1;
//            }
//          // Collections.reverse(strList);
//            for(String str : strList){
//                jsonMonth.add(str);
//            }
            JSONArray jsonMonth = new JSONArray();
            List<String> strList =visitService.getMonth();
            for(String str : strList){
                jsonMonth.add(str);
            }
            model.addAttribute("jsonMonth",jsonMonth);

        }catch(SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "/admin/profile/statistics/chart";
    }
}
