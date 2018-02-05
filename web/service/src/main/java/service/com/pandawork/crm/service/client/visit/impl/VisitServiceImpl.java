package com.pandawork.crm.service.client.visit.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.dto.client.visit.VisitDetailSearchDto;
import com.pandawork.crm.common.dto.client.visit.VisitSearchDto;
import com.pandawork.crm.common.entity.client.basic.Client;
import com.pandawork.crm.common.entity.client.points.Points;
import com.pandawork.crm.common.entity.client.visit.PointsConvert;
import com.pandawork.crm.common.entity.client.visit.Visit;
import com.pandawork.crm.common.enums.DeletedEnums;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.common.utils.DataUtils;
import com.pandawork.crm.common.utils.DateUtils;
import com.pandawork.crm.mapper.client.visit.VisitMapper;
import com.pandawork.crm.service.client.basic.ClientService;
import com.pandawork.crm.service.client.points.PointsService;
import com.pandawork.crm.service.client.visit.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * VisitServiceImpl
 * Author： liping
 * Date: 2017/7/20
 * Time: 9:52
 */
@Service("visitService")
public class VisitServiceImpl implements VisitService{

    @Autowired
    private VisitMapper visitMapper;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Resource
    private ClientService clientService;

    @Resource
    private VisitService visitService;

    @Resource
    private PointsService pointsService;


    /**
     * 分页获取来访信息
     *
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    @Override
    public List<Visit> listByPage(int offset, int pageSize) throws SSException {
        List<Visit> list = new ArrayList<Visit>();
        try{
            list = visitMapper.listByPage(offset,pageSize);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryVisitFailed, e);
        }
        return list;
    }

    /**
     * 根据输入条件查询来访
     *
     * @param visitSearchDto
     * @return
     * @throws SSException
     */
    @Override
    public List<Visit> listByVisitSearchDto(VisitSearchDto visitSearchDto) throws SSException {

        List<Visit> list = new ArrayList<Visit>();
        try {
            list = visitMapper.listByVisitSearchDto(visitSearchDto);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryVisitFailed, e);
        }
        return list;
    }

    /**
     * 点击详情，根据clientId查询该client的所有来访详情
     *
     * @param clientId
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    @Override
    public List<Visit> queryByClientId(int clientId,int offset,int pageSize) throws SSException {
        List<Visit> list =new ArrayList<Visit>();
        try{
            if(Assert.isNull(clientId) || clientId < 0){
                throw SSException.get(CrmException.ClientIdError);
            }
            if(Assert.isNull(offset) || Assert.isNull(pageSize) || offset < 0 || pageSize < 0){
                throw SSException.get(CrmException.OffsetOrPageSizeError);
            }
            list = visitMapper.queryByClientId(clientId,offset,pageSize);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryVisitByClientIdFailed,e);
        }
        return list;
    }

    /**
     * 根据clientId删除该client的所有来访记录
     *
     * @param clientId
     * @throws SSException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void delByClientId(int clientId) throws SSException {
        try{
            if(Assert.isNull(clientId) || clientId < 0){
                throw SSException.get(CrmException.ClientIdError);
            }else{
                visitMapper.delByClientId(clientId);
                //删除来访详情后要修改积分？
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.DelByClientIdFailed,e);
        }
    }

    /**
     * 详情页面(同一个client的来访详情)输入查询条件查询
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    @Override
    public List<Visit> listByVisitDetailSearchDto(VisitDetailSearchDto searchDto) throws SSException {
        List<Visit> list = new ArrayList<Visit>();
        try{
            list=visitMapper.listByVisitDetailSearchDto(searchDto);
            //详情页面显示操作者是创建者么？
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryVisitByVisitDetailSearchDto,e);
        }
        return list;
    }

    /**
     * 计算总金额
     *
     * @param clientId
     * @return
     * @throws SSException
     */
    @Override
    public double getAllCost(int clientId) throws SSException {
        double cost = 0;
        try{
            if(Assert.isNull(clientId) || clientId < 0){
                throw SSException.get(CrmException.ClientIdError);
            }else{
                cost = visitMapper.getAllCost(clientId);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.GetAllCostFailed,e);
        }
        return cost;
    }

    /**
     * 删除详情
     *
     * @param id
     * @throws SSException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void delById(int id) throws SSException {
        try{
            //修改患者来访总金额
            Visit visit = visitService.queryById(id);
            Client client = clientService.queryById(visit.getClientId());
            if(Assert.isNull(id) || id < 0){
                throw SSException.get(CrmException.VisitIdError);
            }else{
                visitMapper.delById(id);
            }
            clientService.updateAllCost(visit.getClientId());
            //若为会员产生积分记录
            if(client.getIsMember() == 1){
                //删除积分修改积分
                Points points = new Points();
                points.setVisitId(id);
                //更新会员积分
                pointsService.delPoints(points);
                clientService.updateAllPointsAndMemberPoints(visit.getClientId());
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.DelById,e);
        }
    }

    /**
     * 修改（点击确定）来访详情
     *
     * @param visit
     * @throws SSException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void updateVisit(Visit visit) throws SSException {
        try{
            if(visit != null){
                if(Assert.isNull(visit.getId()) || visit.getId() < 0){
                    throw SSException.get(CrmException.VisitIdError);
                }
                else{
                    visitMapper.updateVisit(visit);
                }
            }
            Visit visit1 = visitService.queryById(visit.getId());
            //修改患者来访总金额
            clientService.updateAllCost(visit1.getClientId());
            Client client = clientService.queryById(visit1.getClientId());
            //若为会员产生积分记录
            if(client.getIsMember() == 1) {
                //修改积分
                Points points = new Points();
                points.setVisitId(visit1.getId());
                points.setLastVisitDate(new Date());
                int pointSize = visitService.moneyToPoints(visit1.getCost().doubleValue());
                points.setPointsSize(pointSize);
                pointsService.updatePoints(points);
                //更新会员当前积分
                clientService.updateAllPointsAndMemberPoints(visit1.getClientId());
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.UpdateVisit,e);
        }
    }

    /**
     * 根据id查询来访详情（点击修改）
     *
     * @param id
     * @throws SSException
     */
    @Override
    public Visit queryById(int id) throws SSException{
        Visit visit = null;
        try {
            if(Assert.isNull(id) || id < 0){
                throw SSException.get(CrmException.VisitIdError);
            }else{
                visit = visitMapper.queryById(id);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryVisitByIdFailed,e);
        }
        return visit;
    }

    /**
     * 新增来访详情(client已确定)
     *
     * @param visit
     * @throws SSException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public Visit newVisit(Visit visit) throws SSException {
        Visit visit1=null;
        try{
            if(visit != null){
                if(Assert.isNull(visit.getClientId()) || visit.getClientId() < 0){
                    throw SSException.get(CrmException.VClientIdError);
                }
                visit1 = commonDao.insert(visit);
            }
            //修改患者来访总金额
            clientService.updateAllCost(visit.getClientId());
            Client client = clientService.queryById(visit.getClientId());
            //若为会员产生积分记录
            if(client.getIsMember() == 1){
                //新增积分修改积分记录
                Points points = new Points();
                points.setVisitId(visit1.getId());
                points.setClientId(visit1.getClientId());
                int pointSize = visitService.moneyToPoints(visit1.getCost().doubleValue());
                points.setPointsSize(pointSize);
                points.setLastVisitDate(visit1.getVisitTime());
                points.setCreatedPartyId(visit.getCreatedPartyId());
                //设置积分调整日期为当天
                points.setPointsDate(new Date());
                //积分来源设置来访
                points.setPointsFrom(0);
                //产生积分记录
                pointsService.newPoints(points);
                //更新会员当前积分
                clientService.updateAllPointsAndMemberPoints(visit.getClientId());
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.NewVisitFailed,e);
        }
        return visit1;
    }

    /**
     * 去重查来访条数
     *
     * @return
     * @throws SSException
     */
    @Override
    public int count() throws SSException {
        int count = 0;
        try {
            count = visitMapper.count();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountVisitFailed,e);
        }
        return count;
    }

    /**
     * 患者来访详情数目
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    @Override
    public int countByVisitDetailSearchDto(VisitDetailSearchDto searchDto) throws SSException {
        int count = 0;
        try {
            count = visitMapper.countByVisitDetailSearchDto(searchDto);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountVisitFailed,e);
        }
        return count;
    }

    /**
     * 消费金额转换为积分
     *
     * @param cost
     * @return
     * @throws SSException
     */
    @Override
    public int moneyToPoints(double cost) throws SSException {
        int points = 0;
        try{
            points = (int)Math.round(cost*(visitMapper.moneyToPoints()));
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ConvertFailed,e);
        }
        return points;
    }

    /**
     * 根据日期获取当天改分组的消费总金额
     *
     * @param date
     * @param memberGroupId
     * @return
     * @throws SSException
     */
    @Override
    public BigDecimal countCostByDate(String date, int memberGroupId)throws SSException{
        try{
            BigDecimal cost = BigDecimal.ZERO;
            cost = visitMapper.countCostByDate(date,memberGroupId);
            return cost;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountCostByDate,e);
        }
    }

    /**
     * 修改消费积分转换比率
     *
     * @param pointsConvert
     * @throws SSException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void updatePointsConvert(PointsConvert pointsConvert)throws SSException{
        try{
            if(Assert.isNull(pointsConvert)){
                throw SSException.get(CrmException.PointsConvertIsNotNull);
            }
            //数据库中固定位一条数据
            pointsConvert.setId(1);
            commonDao.update(pointsConvert);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.UpdatePointsConvertFailed,e);
        }
    }

    /**
     * 根据id查询积分兑换规则
     *
     * @param id
     * @return
     * @throws SSException
     */
    @Override
    public PointsConvert queryPointsConvertById(int id)throws SSException{
        try{
            if(Assert.isNull(id) || Assert.lessOrEqualZero(id)){
                throw SSException.get(CrmException.PointsIConvertIdError);
            }
            PointsConvert pointsConvert = commonDao.queryById(PointsConvert.class,id);
            return pointsConvert;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.UpdatePointsConvertFailed,e);
        }
    }

    /**
     * 根据患者id查询来访信息
     *
     * @param id
     * @return
     * @throws SSException
     */
    @Override
    public Visit getLastVisitByClientId(int id)throws SSException{
        try{
            if(Assert.isNull(id) || Assert.lessOrEqualZero(id)){
                throw SSException.get(CrmException.VisitIdError);
            }
            Visit visit = visitMapper.getLastVisitByClientId(id) ;
            return visit;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryVisitByClientIdFailed,e);
        }
    }

    /**
     * 近半年会员患者及销售情况分析 月份
     *
     * @return
     * @throws SSException
     */
    public List<String> getMonth() throws SSException{
        try{
            return visitMapper.getMonth();
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountCostByDate,e);
        }
    }

    /**
     * 近半年会员患者及销售情况分析 销售总额
     *
     * @return
     * @throws SSException
     */
    public List<BigDecimal> getGrossSales() throws SSException{
        try{
            return visitMapper.getGrossSales();
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountCostByDate,e);
        }
    }

    /**
     *  近半年会员患者及销售情况分析 会员患者数
     *
     * @return
     * @throws SSException
     */
    public List<Integer> getNumberOfMembers() throws SSException{
        try{
            return visitMapper.getNumberOfMembers();
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountCostByDate,e);
        }
    }
}
