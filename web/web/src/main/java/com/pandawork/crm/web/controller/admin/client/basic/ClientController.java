package com.pandawork.crm.web.controller.admin.client.basic;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.crm.common.annotation.Module;
import com.pandawork.crm.common.dto.client.basic.ClientSearchDto;
import com.pandawork.crm.common.dto.client.basic.GenderDto;
import com.pandawork.crm.common.entity.client.basic.Client;
import com.pandawork.crm.common.entity.client.visit.Visit;
import com.pandawork.crm.common.entity.party.dictionary.Dictionary;
import com.pandawork.crm.common.enums.client.GenderEnums;
import com.pandawork.crm.common.enums.other.ModuleEnums;
import com.pandawork.crm.common.enums.party.dictionary.DictionaryEnums;
import com.pandawork.crm.common.utils.*;
import com.pandawork.crm.web.spring.AbstractController;
import org.apache.http.protocol.HTTP;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;


/**
 * ClientController
 * Created by shura
 * Date:  2017/7/21.
 * Time:  14:23
 */
@Controller
@Module(ModuleEnums.AdminClientBasic)
@RequestMapping(value = URLConstants.ADMIN_CLIENT_BASIC_URL)
public class ClientController extends AbstractController{

    /**
     * 去列表页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminClientBasicList)
    @RequestMapping(value = {"","list"},method = RequestMethod.GET)
    public String ToList(Model model){
        //用户类型字典值
        String typeName = "";
        //医保类型字典值
        String MCIType = "";
        int dataCount = 0;
        ClientSearchDto searchDto = new ClientSearchDto();
        try{
            dataCount = DataUtils.getPageCount(DEFAULT_PAGE_SIZE,clientService.countBySearchDto(searchDto));
            List<Dictionary> clientTypeList = dictionaryService.listByPId(DictionaryEnums.DIC_CLIENT_TYPE.getId());
            List<Dictionary> MCIList = dictionaryService.listByPId(DictionaryEnums.DIC_CLIENT_MCI_TYPE.getId());
            model.addAttribute("dicTypeList",clientTypeList);
            model.addAttribute("mciTypeList",MCIList);
            model.addAttribute("dataCount",dataCount);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/client/basic/list";
    }


    /**
     * Ajax根据searchDto搜索
     *
     * @param searchDto
     * @return
     */
    @Module(ModuleEnums.AdminClientBasicList)
    @RequestMapping(value = {"ajax/list"},method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(ClientSearchDto searchDto, HttpSession httpSession) {
        JSONObject json = new JSONObject();
       //分页偏移量处理
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
        //用户·性别枚举值
        String genderName = "";
        //用户类型字典值
        String typeName = "";
        //医保类型字典值
        String MCIType = "";
        List<Client> clientList = Collections.emptyList();
        int dataCount = 0,numCount = 0;
        try{
            clientList = clientService.listBySearchDto(searchDto);
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
                if(Assert.isNotNull(clientList.get(i).getDicType()) && clientList.get(i).getDicType() > 0) {
                    typeName = dictionaryService.queryById(clientList.get(i).getDicType()).getName();
                }
                if(Assert.isNotNull(clientList.get(i).getDicMCIType()) && clientList.get(i).getDicMCIType() > 0) {
                    MCIType = dictionaryService.queryById(clientList.get(i).getDicMCIType()).getName();
                }
                //设置患者类型字典值
                clientList.get(i).setDicTypeName(typeName);
                //设置医保类型字典值
                clientList.get(i).setDicMCITypeName(MCIType);
            }
            int  i = 1 + pageNo * 10;
            for(Client client : clientList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("number",i++);
                jsonObject.put("id",client.getId());
                jsonObject.put("name",client.getName());
                jsonObject.put("gender",client.getGenderName());
                jsonObject.put("idCardNum",client.getIdCardNum());
                jsonObject.put("tel",client.getTel());
                jsonObject.put("diagnoseType",client.getDiagnoseType());
                jsonObject.put("dicType",client.getDicTypeName());
                jsonObject.put("dicMciType",client.getDicMCITypeName());
                jsonObject.put("allCost",client.getAllCost());
                jsonArray.add(jsonObject);
            }
            numCount = clientService.countBySearchDto(searchDto);
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
     * Ajax姓名搜索框模糊查询
     *
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminClientBasicList)
    @RequestMapping(value = {"ajax/name"},method = RequestMethod.GET)
    @ResponseBody
    public JSON searchByName(@RequestParam("name")String  name){
        JSONArray jsonArray = new JSONArray();
        ClientSearchDto searchDto = new ClientSearchDto();
        try{
            searchDto.setName(name);
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
     * Ajax身份证号搜索框模糊查询
     *
     * @param idCard
     * @return
     */
    @Module(ModuleEnums.AdminClientBasicList)
    @RequestMapping(value = {"ajax/idCard"},method = RequestMethod.GET)
    @ResponseBody
    public JSON searchByIdCard(@RequestParam("idCard")String  idCard){
        JSONArray jsonArray = new JSONArray();
        ClientSearchDto searchDto = new ClientSearchDto();
        try{
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
     * Ajax电话搜索框模糊查询
     *
     * @param phone
     * @return
     */
    @Module(ModuleEnums.AdminClientBasicList)
    @RequestMapping(value = {"ajax/tel"},method = RequestMethod.GET)
    @ResponseBody
    public JSON searchByTel(@RequestParam("tel")String  phone) {
        JSONArray jsonArray = new JSONArray();
        ClientSearchDto searchDto = new ClientSearchDto();
        try {
            searchDto.setTel(phone);
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
     * Ajax删除患者信息
     *
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminClientBasicDelete)
    @RequestMapping(value = {"ajax/del"},method = RequestMethod.POST)
    @ResponseBody
    public JSON delClient(@RequestParam("id") int id,Model model){
            try{
                   clientService.delClient(id);
            }catch(SSException e){
                LogClerk.errLog.error(e);
                return sendErrMsgAndErrCode(e);
            }
            return sendMsgAndCode(AJAX_SUCCESS_CODE,"删除成功");
    }

    /**
     * 去添加页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminClientBasicList)
    @RequestMapping(value = {"add"},method = RequestMethod.GET)
    public String ToAdd(Model model){
        try{
            List<Dictionary> secRelationList = dictionaryService.listByPId(DictionaryEnums.DIC_DEC_RELATION.getId());
            List<Dictionary> nationList = dictionaryService.listByPId(DictionaryEnums.DIC_NATION.getId());
            List<Dictionary> MCIList = dictionaryService.listByPId(DictionaryEnums.DIC_CLIENT_MCI_TYPE.getId());
            Dictionary dicType = dictionaryService.queryById(DictionaryEnums.DIC_CLIENT_TYPE_NORMAL.getId());
            model.addAttribute("dicType",dicType);
            model.addAttribute("dicMCITypeList",MCIList);
            model.addAttribute("dicNationList",nationList);
            model.addAttribute("dicSecRelationList",secRelationList);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/client/basic/add";
    }

    /**
     * Ajax保存患者添加信息
     * @param client
     * @return
     */
    @Module(ModuleEnums.AdminClientBasicNew)
    @RequestMapping(value = {"ajax/save"},method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxAdd(Client client, HttpSession httpSession){
        int createdPartyId = 0;
        String error = "";
        try{
            //身份证校验
            if(Assert.isNotNull(client.getIdCardNum())){
                error = IdCardUtil.IDCardValidate(client.getIdCardNum());
            }
            if(error !="" || Assert.isNotNull(error)){
                return sendMsgAndCode(AJAX_FAILURE_CODE,error);
            }
            //从Session获取当前用户id
            createdPartyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
            client.setCreatedPartyId(createdPartyId);
            Client newClient = clientService.newClient(client);
        }catch(SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 新增Ajax校验身份证号
     * @param idCard
     * @return
     */
    @Module(ModuleEnums.AdminClientBasicNew)
    @RequestMapping(value = {"ajax/checkIdCard"},method = RequestMethod.GET)
    @ResponseBody
    public JSON checkIdCard(@RequestParam("idCard") String idCard){
        try{
            int count = 0;
            count = clientService.checkIdCardNumIsExist(idCard);
            if(count == 0){
                return sendJsonObject(AJAX_SUCCESS_CODE);
            }else{
                return sendJsonObject(AJAX_FAILURE_CODE);
            }
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 去详情页
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminClientBasicDetail)
    @RequestMapping(value = {"detail"},method = RequestMethod.GET)
    public String ToDetail(@RequestParam("id")Integer id,Model model){
        try{
            int age = 0;
            String genderName = "";
            String dicType = "";
            String dicMCIType = "";
            String dicNation = "";
            String dicSecRelation ="";
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
            //判断空值不做查询
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
            if(Assert.isNotNull(client.getIdCardNum())){
                age = DataUtils.getAgeByIdCard(client.getIdCardNum());
            }
            model.addAttribute("id",client.getId());
            model.addAttribute("name",client.getName());
            model.addAttribute("age",age);
            model.addAttribute("tel",client.getTel());
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
            model.addAttribute("isMember",client.getIsMember());
            int eventCount  = processingEventService.countEventByClientId(client.getId());
            model.addAttribute("eventCount",eventCount);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/client/basic/detail";
    }

    /**
     * Ajax加入会员
     *
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminClientBasicAddMember)
    @RequestMapping(value = {"ajax/addMember"},method = RequestMethod.GET)
    @ResponseBody
    public JSON addMember(@RequestParam("id") Integer id){
            return sendJsonObject(AJAX_SUCCESS_CODE);
    }


    /**
     *  去修改页
     *
     * @param id
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminClientBasicUpdate)
    @RequestMapping(value = {"update"},method = RequestMethod.GET)
    public String ToUpdate(@RequestParam("id")Integer id,Model model){
        try{
            int age = 0;
            Client client = clientService.queryById(id);
            if(Assert.isNotNull(client.getIdCardNum())){
                age = DataUtils.getAgeByIdCard(client.getIdCardNum());
            }
            //渲染下拉框
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
            List<Dictionary> clientTypeList = dictionaryService.listByPId(DictionaryEnums.DIC_CLIENT_TYPE.getId());
            List<Dictionary> MCIList = dictionaryService.listByPId(DictionaryEnums.DIC_CLIENT_MCI_TYPE.getId());
            model.addAttribute("genderList",genderList);
            model.addAttribute("dicTypeList",clientTypeList);
            model.addAttribute("dicMCITypeList",MCIList);
            model.addAttribute("dicNationList",nationList);
            model.addAttribute("dicSecRelationList",secRelationList);
            //渲染输入框
            model.addAttribute("id",client.getId());
            model.addAttribute("name",client.getName());
            model.addAttribute("tel",client.getTel());
            model.addAttribute("age",age);
            model.addAttribute("gender",client.getGender());
            model.addAttribute("height",client.getHeight());
            model.addAttribute("address",client.getAddress());
            model.addAttribute("secContact",client.getSecContact());
            model.addAttribute("dicType",client.getDicType());
            model.addAttribute("normalClient",DictionaryEnums.DIC_CLIENT_TYPE_NORMAL.getId());
            model.addAttribute("dicMCIType",client.getDicMCIType());
            model.addAttribute("dicNation",client.getDicNation());
            model.addAttribute("dicSecRelation",client.getDicSecRelation());
            model.addAttribute("MCINum",client.getMCINum());
            model.addAttribute("diagnoseType",client.getDiagnoseType());
            model.addAttribute("idCardNum",client.getIdCardNum());
            model.addAttribute("weight",client.getWeight());
            model.addAttribute("company",client.getCompany());
            model.addAttribute("secTel",client.getSecTel());
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/client/basic/update";
    }

    /**
     * Ajax保存患者修改信息
     *
     * @param client
     * @return
     */
    @Module(ModuleEnums.AdminClientBasicUpdate)
    @RequestMapping(value = {"ajax/update"},method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxUpdate(Client client){
        try{
            String error = "";
            if(Assert.isNotNull(client.getIdCardNum())){
                error = IdCardUtil.IDCardValidate(client.getIdCardNum());
            }
            if(error !="" || Assert.isNotNull(error)){
                return sendMsgAndCode(AJAX_FAILURE_CODE,error);
            }
            Client updateClient = clientService.queryById(client.getId());
            //修改患者信息为了防止空值替换会员信息故在此判空
            updateClient.setName(client.getName());
            updateClient.setTel(client.getTel());
            updateClient.setGender(client.getGender());
            updateClient.setHeight(client.getHeight());
            updateClient.setIdCardNum(client.getIdCardNum());
            updateClient.setDicNation(client.getDicNation());
            updateClient.setAddress(client.getAddress());
            updateClient.setSecContact(client.getSecContact());
            updateClient.setDicSecRelation(client.getDicSecRelation());
            updateClient.setDiagnoseType(client.getDiagnoseType());
            updateClient.setSecTel(client.getSecTel());
            updateClient.setDicMCIType(client.getDicMCIType());
            updateClient.setMCINum(client.getMCINum());
            updateClient.setDicType(client.getDicType());
            clientService.updateClient(updateClient);
        }catch(SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }


    /**
     * 修改Ajax校验身份证号
     * @param idCard
     * @return
     */
    @Module(ModuleEnums.AdminClientBasicUpdate)
    @RequestMapping(value = {"ajax/checkUpdateIdCard"},method = RequestMethod.GET)
    @ResponseBody
    public JSON checkUpdatedCard(@RequestParam("idCard") String idCard){
        try{
            int count = 0;
            if(Assert.isNull(idCard) || idCard==""){
                return sendJsonObject(AJAX_SUCCESS_CODE);
            }
            count = clientService.checkIdCardNumIsExist(idCard);
            //修改校验因为自身存在数据库故此处大于1
            if(count > 1){
                return sendJsonObject(AJAX_FAILURE_CODE);
            }else{
                return sendJsonObject(AJAX_SUCCESS_CODE);
            }
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 下载模板
     */
    @Module(ModuleEnums.AdminClientBasicNew)
    @RequestMapping(value = {"exportTemplate"},method = RequestMethod.GET)
    public void exportTemplate(){
        try{
            clientService.downLoadTemplate(getResponse());
            sendErrMsg("导出成功");
        }catch(SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
    }

    /**
     * 批量导入
     *
     * @param fileToUpload
     * @param request
     * @param response
     * @return
     */
    @Module(ModuleEnums.AdminClientBasicNew)
    @RequestMapping(value = "ajax/import", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String importExcel(@RequestParam("fileToUpload") MultipartFile fileToUpload,HttpServletRequest request, HttpServletResponse response,
                            HttpSession httpSession){
        try{
            response.setContentType("text/xml;charset=UTF-8");
            int createdPartyId = DataUtils.objectToInt(httpSession.getAttribute("partyId"));
            int count =0;
            int error =0;
            int success = 0;
            String errorInfo = "";
            int nullLine = 0;
            String errorStr = "";
            List<Client> clientList = new ArrayList<Client>();
            //Client client = new Client();
            ExcelReaderUtil readExcel = new ExcelReaderUtil(fileToUpload.getInputStream(),fileToUpload.getOriginalFilename());
            readExcel.setSheetNum(0); // 设置读取索引为0的工作表
            Map<String,String> checkRepeatMap=new HashMap<String,String>();//检验数据是否正常的集合
            List<String> tabIdList=new ArrayList<String>();//最终得到的tableid集合
            /*这里为循环校验数据*/
            for (int i = 2; i < readExcel.getRowCount()+1; i++) {
                //库名,先判断是否在合并单元格内，若有则取合并单元格的值
                String id=readExcel.getCellValue(i, 0).trim();//序号
                String name=readExcel.getCellValue(i, 1).trim();//姓名
                String dicType=readExcel.getCellValue(i, 2).trim();//用户类型
                String gender=readExcel.getCellValue(i, 3).trim();//性别
                String tel=readExcel.getCellValue(i, 4).trim();//电话
                String idcardNum=readExcel.getCellValue(i, 5).trim();//身份证号
                String dicMciType=readExcel.getCellValue(i, 6).trim();//医保类型
                String mciNum=readExcel.getCellValue(i, 7).trim();//医保卡号
                String secContact=readExcel.getCellValue(i, 8).trim();//第二联系人
                String dicSecRelation=readExcel.getCellValue(i, 9).trim();//第二联系人关系
                String secTel=readExcel.getCellValue(i, 10).trim();//第二联系人方式
                String company=readExcel.getCellValue(i, 11).trim();//公司
                String diagnoseType=readExcel.getCellValue(i, 12).trim();//诊断类型
                String height=readExcel.getCellValue(i, 13).trim();//身高
                String weight=readExcel.getCellValue(i, 14).trim();//体重
                String dicNation=readExcel.getCellValue(i, 15).trim();//民族
                String address=readExcel.getCellValue(i, 16).trim();//地址
                //空行校验
                if(Assert.isNull(id) && Assert.isNull(name) && Assert.isNull(dicType)
                        &&Assert.isNull(gender) && Assert.isNull(tel)&&Assert.isNull(idcardNum) && Assert.isNull(dicMciType)&& Assert.isNull(mciNum)
                        &&Assert.isNull(secContact) && Assert.isNull(dicSecRelation)&&Assert.isNull(secTel)
                        &&Assert.isNull(company) && Assert.isNull(diagnoseType)&&Assert.isNull(height)
                        &&Assert.isNull(weight)&&Assert.isNull(dicNation)&&Assert.isNull(address))
                {
                    nullLine++;
                    continue;
                }
                String mapValue=id+name+dicType+gender+tel+idcardNum+dicMciType+mciNum+secContact+dicSecRelation+secTel+company+diagnoseType+height
                        +weight+dicNation+address;
                if(checkRepeatMap.containsValue(mapValue)){
                    errorInfo ="\n第"+(i+1)+"行：该行数据为重复数据，请删除该行后重新提交。\n";
                    errorStr+=errorInfo;
                    continue;
                }else{
                    checkRepeatMap.put((i+1)+"",mapValue);
                }
                //信息校验
                if(Assert.isNull(name)){
                    error++;
                    errorStr = errorStr + "\n第" + (int)Double.parseDouble(id) +"条，姓名不能为空";
                    continue;
                }
                if(DataUtils.isNumber(dicMciType) && Assert.isNotNull(dicMciType)){
                    error++;
                    errorStr = errorStr + "\n第"+(int)Double.parseDouble(id) +"条"+" 医保类型为字典值只能填数字";
                    continue;
                }
                if(DataUtils.isNumber(dicNation) && Assert.isNotNull(dicNation)){
                    error++;
                    errorStr = errorStr + "\n第"+(int)Double.parseDouble(id) +"条"+" 民族为字典值只能填数字";
                    continue;
                }
                if(DataUtils.isNumber(dicSecRelation) && Assert.isNotNull(dicSecRelation)){
                    error++;
                    errorStr = errorStr + "\n第"+(int)Double.parseDouble(id) +"条"+" 第二联系人关系为字典值只能填数字";
                    continue;
                }
                if(Assert.isNull(tel)){
                    error++;
                    errorStr = errorStr + "\n第"+(int)Double.parseDouble(id) +"条"+" 联系方式不能为空";
                    continue;
                }
                if(!DataUtils.isMobile(tel)){
                    error++;
                    errorStr = errorStr + "\n第"+(int)Double.parseDouble(id) +"条"+" 电话号码错误";
                    continue;
                }
                if(Assert.isNotNull(idcardNum)) {
                    if (IdCardUtil.IDCardValidate(idcardNum) != "") {
                        error++;
                        errorStr = errorStr + "\n第" + (int) Double.parseDouble(id) + "条" + IdCardUtil.IDCardValidate(idcardNum);
                        errorStr = errorStr + errorInfo;
                    }
                    if (Assert.isNull(idcardNum)) {
                        if (clientService.checkIdCardNumIsExist(idcardNum) > 0) {
                            error++;
                            errorStr = errorStr + "\n第" + (int) Double.parseDouble(id) + "条" + "身份证号已经存在";
                            continue;
                        }
                    }
                }
                if(Assert.isNotNull(gender)){
                    if(!(gender.equals(GenderEnums.Male.getGender()) || gender.equals(GenderEnums.Female.getGender()))
                            || gender.equals(GenderEnums.Other.getGender())){
                        error++;
                        errorStr = errorStr + "\n第" + (int) Double.parseDouble(id) + "条" + "性别填写有误";
                    }
                }
                //通过验证后存入list
                Client client = new Client();
                client.setName(name);
                client.setDicType(DictionaryEnums.DIC_CLIENT_TYPE_NORMAL.getId());//患者导入默认类型为普通会员
                //不为空才进行数据类型转换
                if(Assert.isNotNull(gender)){
                        for(GenderEnums sex : GenderEnums.values()){
                        if(sex.getGender().equals(gender)){
                            client.setGender(sex.getId());
                        }
                    }
                }
                client.setTel(tel);
                client.setIdCardNum(idcardNum);
                if(Assert.isNotNull(dicMciType)) {
                    client.setDicMCIType((int)Double.parseDouble(dicMciType));
                }
                client.setMCINum(mciNum);
                client.setSecContact(secContact);
                if(Assert.isNotNull(dicSecRelation)){
                    client.setDicSecRelation((int)Double.parseDouble(dicSecRelation));
                }
                client.setSecTel(secTel);
                client.setCompany(company);
                client.setDiagnoseType(diagnoseType);
                if(Assert.isNotNull(height)) {
                    BigDecimal heightB = new BigDecimal(height);
                    client.setHeight(heightB.setScale(2, BigDecimal.ROUND_HALF_UP));
                }
                if(Assert.isNotNull(weight)) {
                    BigDecimal weightB = new BigDecimal(weight);
                    client.setWeight(weightB.setScale(2, BigDecimal.ROUND_HALF_UP));
                }
                if(Assert.isNotNull(dicNation)) {
                    client.setDicNation((int) Double.parseDouble(dicNation));
                }
                client.setAddress(address);
                client.setCreatedPartyId(createdPartyId);
                clientList.add(client);
                success++;
                count++;
            }
            if (Assert.isNotNull(errorStr)) {
                return sendMsgAndCodeStr(AJAX_FAILURE_CODE, "导入失败！有"+error+"条数据存在问题，请修正后再次导入：\n"+errorStr);
            } else {
                /*保存操作*/
                try{
                    for(Client newClient : clientList){
                        clientService.newClient(newClient);
                    }
                }catch(SSException e){
                    LogClerk.errLog.error(e);
                    return sendMsgAndCodeStr(AJAX_FAILURE_CODE,e.getMessage());
                }
            }
            count = readExcel.getRowCount() - nullLine -1;
            String returnMsg = "导入成功！共"+count+"条，成功导入"+success+"条";
            return sendMsgAndCodeStr(AJAX_SUCCESS_CODE,returnMsg);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            try {
                return sendMsgAndCodeStr(AJAX_FAILURE_CODE, e.getMessage());
            }catch(Exception e1){
                return "导入失败";
            }
        }
    }



}
