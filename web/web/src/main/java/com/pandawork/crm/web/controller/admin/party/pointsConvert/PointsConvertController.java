package com.pandawork.crm.web.controller.admin.party.pointsConvert;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.entity.client.visit.PointsConvert;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.URLConstants;
import com.pandawork.crm.web.controller.admin.AdminController;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * Created by shura on 2017/7/18.
 */
@Controller
@Module(ModuleEnums.AdminPartyPointsConvert)
@RequestMapping(value = URLConstants.ADMIN_PARTY_POINTSCONVERT_URL)
public class PointsConvertController extends AdminController {

    /**
     * 去积分兑换管理页
     *
     * @return
     */
    @Module(ModuleEnums.AdminPartyPointsConvertList)
    @RequestMapping(value = {"", "list"},method = RequestMethod.GET)
    public String toList(Model model){
        try{
            BigDecimal pointsConvert = visitService.queryPointsConvertById(1).getMoneyToPoints();
            model.addAttribute("pointsConvert",pointsConvert.toString());
        }catch(SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/party/pointsConvert/list";
    }

    /**
     * ajax修改转换值
     *
     * @param points
     * @return
     */
    @Module(ModuleEnums.AdminPartyPointsConvertUpdate)
    @RequestMapping(value = {"ajax/save"},method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxUpdate(@Param("points")Double points, HttpSession httpSession){
        try{
            int partyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
            PointsConvert pointsConvert = new PointsConvert();
            BigDecimal moneyToPoints = new BigDecimal(points.doubleValue());
            pointsConvert.setId(1);
            pointsConvert.setMoneyToPoints(moneyToPoints);
            pointsConvert.setCreatedPartyId(partyId);
            visitService.updatePointsConvert(pointsConvert);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e){
            LogClerk.errLog.error(e);
            return sendJsonObject(AJAX_FAILURE_CODE);
        }
    }


}
