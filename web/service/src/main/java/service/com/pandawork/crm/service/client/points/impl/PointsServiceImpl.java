package com.pandawork.crm.service.client.points.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.dto.client.points.PointsSearchDto;
import com.pandawork.crm.common.entity.client.points.Points;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.common.utils.DateUtils;
import com.pandawork.crm.mapper.client.points.PointsMapper;
import com.pandawork.crm.service.client.points.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * PointsServiceImpl
 * Author： liping
 * Date: 2017/7/31
 * Time: 17:07
 */
@Service("pointsService")
public class PointsServiceImpl implements PointsService{
    @Autowired
    private PointsMapper pointsMapper;

    @Autowired
    private CommonDao commonDao;

    /**
     * 根据条件查询积分（积分列表页）
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<Points> listByPointsSearchDto(PointsSearchDto searchDto) throws SSException {
        List<Points> pointsList = new ArrayList<Points>();
        try {
            pointsList = pointsMapper.listByPointsSearchDto(searchDto);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryPointsFailed, e);
        }
        return pointsList;
    }

    /**
     * 根据searchDto查询积分总条数
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public int countByPointsSearchDto(PointsSearchDto searchDto) throws SSException{
        int count = 0;
        try{
            count = pointsMapper.countByPointsSearchDto(searchDto);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryPointsFailed,e);
        }
        return count;
    }

    /**
     * 查询患者积分（积分详情页）
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<Points> listByClientId(PointsSearchDto searchDto) throws SSException {
        List<Points> pointsList = new ArrayList<Points>();
        try {
            if(Assert.isNull(searchDto.getClientId()) || searchDto.getClientId() < 0){
                throw SSException.get(CrmException.ClientIdError);
            }else {
                pointsList = pointsMapper.listByClientId(searchDto);
            }
            for (Points points:pointsList) {
                if(points.getPointsFrom() == 1){
                    points.setPointsFrom1("活动积分");
                }else{
                    points.setPointsFrom1("来访消费");
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryPointsFailed, e);
        }
        return pointsList;
    }

    /**
     * 查询患者最晚积分记录（新增积分记录时，用来计算当前积分、累计积分）
     *
     * @param clientId
     * @return
     * @throws SSException
     */
    public Points getLastPointsByClientId(int clientId) throws SSException {
        Points points = null;
        try{
            points = pointsMapper.getLastPointsByClientId(clientId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryPointsFailed, e);
        }
        return points;
    }

    /**
     * 新增积分
     * (新增来访points：clientId,visitId(id),pointsSize(cost),lastVisitDate(visitTime),pointsFrom(0))
     * (新增活动points:clientId(参加活动人id),eventId(活动id),lastVisitDate(参加时间),pointsItemId(积分项id),pointsSize(活动积分),pointsFrom(1))
     *
     * @param points
     * @return
     * @throws SSException
     */
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public Points newPoints(Points points) throws SSException {
        Points newPoints = null;
        try {
            if(Assert.isNull(points.getClientId()) || points.getClientId() < 0){
                throw SSException.get(CrmException.ClientIdError);
            }
            //获得client的最晚时间的当前积分，当前累计积分
            Points points1 = this.getLastPointsByClientId(points.getClientId());
            if(Assert.isNotNull(points1)){
                //设置调整前积分
                points.setLastPoints(points1.getCurrentPoints());
                //设置当前积分
                points.setCurrentPoints(points1.getCurrentPoints()+points.getPointsSize());
                //如果积分>0，设置当前累计积分
                if(points.getPointsSize() > 0){
                    points.setCurrentSumpoints(points1.getCurrentSumpoints()+points.getPointsSize());
                }
            }else{
                //若为第一条记录
                points.setCurrentSumpoints(points.getPointsSize());
                points.setCurrentPoints(points.getPointsSize());
            }
            //新增
            points.setCreatedTime(null);
            newPoints = commonDao.insert(points);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.NewPointsFailed, e);
        }
        return newPoints;
    }

    /**
     * 删除积分
     * (删除来访points：visitId(来访id))
     * (删除活动points:eventId(活动id),pointsItemId(积分项id))？？
     *
     * @param points
     * @throws SSException
     */
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void delPoints(Points points) throws SSException {
        try {
            //修改所删来访记录对应的时间点之后的当前积分，当前累计积分
            if(Assert.isNull(points.getVisitId())  && points.getVisitId() < 0) {
                throw SSException.get(CrmException.PointsIdError);
            }
             //得到该来访对应的积分记录
             Points delPoints = this.queryByVisit(points.getVisitId());
             //删除积分记录
             pointsMapper.delPoints(points);
            //根据该了积分记录修改该记录时间点后的当前积分、累计积分
            //修改该条记录之后所有的积分记录
            String createdTime = DateUtils.formatDate(delPoints.getCreatedTime());
             List<Points> pointsList = pointsMapper.listAfterUpdate(delPoints.getClientId(),createdTime);
             int pointsSize = delPoints.getPointsSize();
             int lastPoints = 0;
             int curPoints = 0;
             int sumPoints = 0;
             for(Points updatePoints : pointsList){
                    lastPoints = updatePoints.getLastPoints() - pointsSize;
                    if(lastPoints < 0)lastPoints = 0;
                    updatePoints.setLastPoints(lastPoints);
                    curPoints = updatePoints.getCurrentPoints() - pointsSize;
                    if(curPoints < 0)curPoints = 0;
                    updatePoints.setCurrentPoints(curPoints);
                    sumPoints = updatePoints.getCurrentSumpoints() - pointsSize;
                    if(sumPoints < 0)sumPoints = 0;
                    updatePoints.setCurrentSumpoints(sumPoints);
                 commonDao.update(updatePoints);
             }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.DelPointsFailed,e);
        }
    }

    /**
     * 根据来访查询积分（删除来访时，查询该来访对应的积分值和时间）
     *
     * @param visitId
     * @return
     * @throws SSException
     */
    public Points queryByVisit(int visitId) throws SSException {
        Points points = null;
        try {
            if(Assert.isNull(visitId) || visitId < 0){
                throw SSException.get(CrmException.VisitIdError);
            }else {
                points = pointsMapper.queryByVisit(visitId);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryByVisitFailed,e);
        }
        return points;
    }

    /**
     * 修改积分（修改来访时修改积分）
     * （来访points:visitId(id),pointsSize(新cost),lastVisitDate(新visitTime)）
     *
     * @param points
     * @throws SSException
     */
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void updatePoints(Points points) throws SSException {
        try{
            //修改来访记录修改积分
            if(Assert.isNull(points.getVisitId())  && points.getVisitId() < 0) {
                throw SSException.get(CrmException.PointsIdError);
            }
            //得到原来访对应的积分记录
            Points updatePoints = this.queryByVisit(points.getVisitId());
            //原来访纪录时间点之后的当前积分、累计积分-原来访记录对应的积分值
            int pointsSize = points.getPointsSize() -  updatePoints.getPointsSize();//前后差值
            //修改积分记录  --来访日期预积分日期一同修改
            updatePoints.setLastVisitDate(points.getPointsDate());
            updatePoints.setPointsDate(points.getCreatedTime());
            updatePoints.setPointsSize(points.getPointsSize());
            updatePoints.setCurrentPoints(updatePoints.getCurrentPoints() + pointsSize);
            updatePoints.setCurrentSumpoints(updatePoints.getCurrentSumpoints() + pointsSize);
            pointsMapper.updatePoints(updatePoints);
            //修改该条记录之后所有的积分记录
            List<Points> pointsList = pointsMapper.listAfterUpdate(updatePoints.getClientId(),DateUtils.formatDate(updatePoints.getCreatedTime()));
            int lastPoints = 0;
            int curPoints = 0;
            int sumPoints = 0;
            for(Points pointsUpdate : pointsList){
                lastPoints = pointsUpdate.getLastPoints() + pointsSize;
                if(lastPoints < 0){lastPoints = 0;}
                pointsUpdate.setLastPoints(lastPoints);
                curPoints = pointsUpdate.getCurrentPoints() + pointsSize;
                if(curPoints < 0){curPoints = 0;}
                pointsUpdate.setCurrentPoints(curPoints);
                sumPoints = pointsUpdate.getCurrentSumpoints() + pointsSize;
                if(sumPoints < 0){sumPoints = 0;}
                pointsUpdate.setCurrentSumpoints(sumPoints);
                commonDao.update(pointsUpdate);
            }
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.UpdatePointsFailed,e);
        }
    }

    /**
     * 去重统计积分条数（积分列表页）
     *
     * @return
     * @throws SSException
     */
    public int count(PointsSearchDto searchDto) throws SSException {
        int count = 0;
        try{
            count = pointsMapper.count(searchDto);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountPointsFailed,e);
        }
        return count;
    }

    /**
     * 查询详情条数（积分详情页）
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public int countDetail(PointsSearchDto searchDto) throws SSException {
        int count = 0;
        try{
            if(Assert.isNull(searchDto.getClientId()) || searchDto.getClientId() < 0){
                throw SSException.get(CrmException.ClientIdError);
            }else {
                count = pointsMapper.countDetail(searchDto);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountPointsFailed,e);
        }
        return count;
    }


}
