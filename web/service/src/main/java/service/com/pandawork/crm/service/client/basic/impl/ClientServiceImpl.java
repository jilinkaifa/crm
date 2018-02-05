package com.pandawork.crm.service.client.basic.impl;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.common.util.IOUtil;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.core.pweio.excel.ExcelReader;
import com.pandawork.crm.common.dto.client.basic.ClientSearchDto;
import com.pandawork.crm.common.dto.client.quest.QuestDto;
import com.pandawork.crm.common.entity.client.basic.Client;
import com.pandawork.crm.common.entity.client.points.Points;
import com.pandawork.crm.common.entity.client.visit.Visit;
import com.pandawork.crm.common.enums.DeletedEnums;
import com.pandawork.crm.common.enums.ExcelExportTemplateEnums;
import com.pandawork.crm.common.enums.client.MemberEnums;
import com.pandawork.crm.common.enums.client.MemberStatusEnums;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.mapper.client.basic.ClientMapper;
import com.pandawork.crm.service.client.basic.ClientService;
import com.pandawork.crm.service.client.points.PointsService;
import com.pandawork.crm.service.client.quest.QuestService;
import com.pandawork.crm.service.client.visit.VisitService;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ClientServiceImpl
 * Created by shura
 * Date:  2017/7/19.
 * Time:  20:14
 */
@Service("clientService")
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private QuestService questService;

    @Autowired
    private VisitService visitService;

    @Autowired
    private PointsService pointsService;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;//core包

    /**
     *  添加患者信息
     * @param client
     * @return
     * @throws SSException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public Client newClient(Client client) throws SSException{
        try{
            //实体非空信息校验
            if(Assert.isNull(client.getName())){
                throw SSException.get(CrmException.ClientNameIsNull);
            }
            if(Assert.isNull(client.getTel())){
                throw SSException.get(CrmException.ClientTelIsNull);
            }
            //校验身份证号是否唯一
            if(Assert.isNotNull(client.getIdCardNum())){
                if(checkIdCardNumIsExist(client.getIdCardNum()) > 0){
                    throw SSException.get(CrmException.IdCardNumIsExist);
                }
            }
            //设置为普通患者
            client.setIsMember(0);
            Client newClient = commonDao.insert(client);
            return newClient;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.NewClientFailed, e);
        }
    }

    /**
     * 根据id查询患者
     * @param id
     * @return
     * @throws SSException
     */
    @Override
    public Client queryById(int id)throws SSException{
        try {
            if(Assert.isNull(id) || id < 0){
                throw SSException.get(CrmException.ClientIdError);
            }
            Client client = clientMapper.queryById(id);
            return client;
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryClientFailed, e);
        }
    }

    /**
     * 统计患者数量
     * @return
     * @throws SSException
     */
    @Override
    public int countBySearchDto(ClientSearchDto searchDto)throws SSException{
        try {
            int count = clientMapper.count(searchDto);
            return count;
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountClientFailed, e);
        }
    }

    /**
     * 根据会员组id查询列表
     * @param id
     * @return
     * @throws SSException
     */
    @Override
    public List<Client> listByMemberGroupId(int id)throws SSException{
        try {
            if(Assert.isNull(id) || id < 0){
                throw SSException.get(CrmException.ClientIdError);
            }
            List<Client> clientList = clientMapper.listByMemberGroupId(id);
            return clientList;
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryClientFailed, e);
        }
    }

    /**
     * 校验身份证号的唯一性
     *
     * @param name
     * @return
     * @throws SSException
     */
    @Override
    public int checkIdCardNumIsExist(String name) throws SSException {
        int count = 0;
        try {
            count = clientMapper.countIdCardNum(name);
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryClientFailed, e);
        }
        return count;
    }

    /**
     *  修改患者信息
     *
     * @throws SSException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void updateClient(Client client) throws SSException{
        try{
            if(client.getIsMember() == 1 && Assert.isNull(client.getIdCardNum())){
                throw SSException.get(CrmException.MemberIdCardIsNotNull);
            }
            commonDao.update(client);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.UpdateClientFailed, e);
        }
    }

    /**
     *  删除患者信息
     *
     * @param id
     * @throws SSException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void delClient(int id) throws SSException{
        try{
            Client client = this.queryById(id);
            //身份证为空才可以删除
            if(Assert.isNotNull(client.getIdCardNum())) {
                throw SSException.get(CrmException.IdCardNumExistCannotDelete);
            }
                if(Assert.isNull(id) || id < 0){
                    throw SSException.get(CrmException.ClientIdError);
                }else{
                    clientMapper.delClient(id, DeletedEnums.Deleted.getId());
                }
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.DelClientFailed, e);
        }
    }

    /**
     * 加入会员
     *
     * @throws SSException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void addMember(int id) throws SSException{
        try{
            clientMapper.addMember(id,MemberEnums.Member.getId());
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.AddMemberFailed, e);
        }
    }

    /**
     * 根据SearchDto查询列表
     * @param searchDto
     * @return
     * @throws SSException
     */
    @Override
    public List<Client> listBySearchDto(ClientSearchDto searchDto)throws SSException{
        List<Client> clientList = new ArrayList<Client>();
        try{
            clientList = clientMapper.listBySearchDto(searchDto);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListClientFailed, e);
        }
        return clientList;
    }


    /**
     * 获取全部患者信息分页
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    @Override
    public List<Client> listByPage(int offset,int pageSize) throws SSException{
        try{
            if(Assert.isNull(offset) || Assert.isNull(pageSize)){
                if(offset < 0 || pageSize < 0){
                    throw SSException.get(CrmException.OffsetAndPageSizeError);
                }
            }
            List<Client> clientList = clientMapper.listByPage(offset, pageSize);
            return clientList;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListClientFailed, e);
        }
    }

    /**
     * 模糊查询搜索框返回备选值
     * @param searchDto
     * @return
     * @throws SSException
     */
    @Override
    public List<Client> listForSearch(ClientSearchDto searchDto) throws SSException{
        try{
            List<Client> clientList = clientMapper.listForSearch(searchDto);
            return clientList;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListClientFailed, e);
        }
    }

    /**
     * 更新患者问卷次数
     *
     * @param id
     * @throws SSException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void updateQuestCount(int id)throws SSException{
        Client client = new Client();
        int questCount = 0;
        try{
            if(Assert.isNull(id) || Assert.lessOrEqualZero(id)){
                throw SSException.get(CrmException.ClientIdError);
            }
            client = queryById(id);
            //根据患者id查询问卷次数
            questCount = questService.countByClientId(id) - 1 ;
            client.setQuestCount(questCount);
            updateClient(client);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.UpdateQuestCountFailed, e);
        }
    }

    /**
     * 更新患者累计消费金额
     *
     * @param id
     * @throws SSException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void updateAllCost(int id)throws SSException{
        Client client = new Client();
        BigDecimal allCost = BigDecimal.ZERO;
        try{
            if(Assert.isNull(id) || Assert.lessOrEqualZero(id)){
                throw SSException.get(CrmException.ClientIdError);
            }
            client = queryById(id);
            allCost = BigDecimal.valueOf(visitService.getAllCost(id));
            //消费金额不可为负数
            if(Assert.lessZero(allCost.intValue())){
                allCost = BigDecimal.ZERO;
            }
            client.setAllCost(allCost);
            updateClient(client);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.UpdateAllCostFailed, e);
        }
    }

    /**
     * 更新当前积分和累计积分
     *
     * @param id
     * @throws SSException
     */
    @Override
    public void updateAllPointsAndMemberPoints(int id)throws SSException{
        Client client = new Client();
        int allPoints = 0;
        int memberPoints = 0;
        Points points = new Points();
        try{
            if(Assert.isNull(id) || Assert.lessOrEqualZero(id)){
                throw SSException.get(CrmException.ClientIdError);
            }
            client = queryById(id);
            points = pointsService.getLastPointsByClientId(id);
            if(Assert.isNotNull(points)) {
                client.setAllPoints(points.getCurrentSumpoints());
                client.setMemberPoints(points.getCurrentPoints());
            }else{
                client.setAllPoints(0);
                client.setMemberPoints(0);
            }
            updateClient(client);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.UpdateAllPointsAndMemberPointsFailed, e);
        }
    }

    /**
     * 下载模板
     *
     * @param response
     * @throws SSException
     */
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void downLoadTemplate(HttpServletResponse response)throws SSException{
        OutputStream os = null;
        try{
            //设置输出流
            //设置excel文件名和sheetName
            String filename = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            filename = ExcelExportTemplateEnums.AdminClientTemplate.getName() + sdf.format(new Date());
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(filename.getBytes("gbk"), "ISO8859-1") + ".xls");
            os = response.getOutputStream();
            //获取模板
            InputStream tplStream = IOUtil.getFileAsStream(ExcelExportTemplateEnums.AdminClientTemplate.getFilePath());
            Workbook tplWorkBook = Workbook.getWorkbook(tplStream);
            WritableWorkbook outBook = Workbook.createWorkbook(os, tplWorkBook);
            //获取sheet往sheet中写入数据
            WritableSheet sheet = outBook.getSheet(0);
            outBook.write();
            outBook.close();
            tplWorkBook.close();
            os.close();
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            response.setContentType("text/html");
            response.setHeader("Content-Type", "text/html");
            response.setHeader("Content-disposition", "");
            response.setCharacterEncoding("UTF-8");
            try {
                String eMsg = "系统内部异常，请联系管理员！";
                eMsg= java.net.URLEncoder.encode(eMsg.toString(),"UTF-8");
                response.sendRedirect("/admin/storage/report?eMsg="+eMsg);
                os.close();
            } catch (IOException e1) {
                LogClerk.errLog.error(e1.getMessage());
            }
            throw SSException.get(CrmException.DownLoadTemplateFailed, e);
        }
            finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                    throw SSException.get(CrmException.DownLoadTemplateFailed, e);
                }
            }
        }
    }

}
