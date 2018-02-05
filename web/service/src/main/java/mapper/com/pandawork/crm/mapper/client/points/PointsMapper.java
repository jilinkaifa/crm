package com.pandawork.crm.mapper.client.points;

import com.pandawork.crm.common.dto.client.points.PointsSearchDto;
import com.pandawork.crm.common.entity.client.points.Points;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * PointsMapper
 * Author： liping
 * Date: 2017/7/31
 * Time: 16:10
 */
public interface PointsMapper {
    /**
     * 根据searchDto查询积分
     *
     * @return
     * @throws Exception
     */
    public List<Points> listByPointsSearchDto(@Param("searchDto") PointsSearchDto searchDto) throws Exception;

    /**
     * 根据searchDto查询积分总条数
     *
     * @return
     * @throws Exception
     */
    public int countByPointsSearchDto(@Param("searchDto") PointsSearchDto searchDto) throws Exception;

    /**
     * 查询患者积分
     *
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<Points> listByClientId(@Param("searchDto") PointsSearchDto searchDto) throws Exception;

    /**
     * 查询患者最晚的积分记录
     *
     * @param clientId
     * @return
     * @throws Exception
     */
    public Points getLastPointsByClientId(@Param("clientId") Integer clientId) throws Exception;

    /**
     * 删除积分
     *
     * @param points
     * @throws Exception
     */
    public void delPoints(@Param("points") Points points) throws Exception;

    /**
     * 根据来访或活动查询积分
     *
     * @param visitId
     * @return
     * @throws Exception
     */
    public Points queryByVisit(@Param("visitId") Integer visitId) throws Exception;

    /**
     * 删除来访时，减少积分
     *
     * @param points
     * @throws Exception
     */
    public void decreaseByVisit(@Param("points") Points points) throws Exception;

    /**
     * 修改积分
     *
     * @param points
     * @throws Exception
     */
    public void updatePoints(@Param("points") Points points) throws Exception;

    /**
     * 修改来访时，增加新来访记录对应的积分值
     *
     * @param points
     * @throws Exception
     */
    public void increaseByVisit(@Param("points") Points points) throws Exception;

    /**
     * 去重统计积分条数
     *
     * @return
     * @throws Exception
     */
    public int count(@Param("searchDto") PointsSearchDto searchDto) throws Exception;

    /**
     * 查询积分详情条数
     *
     * @param searchDto
     * @return
     * @throws Exception
     */
    public int countDetail(@Param("searchDto") PointsSearchDto searchDto) throws Exception;

    /**
     * 获取操作时间之后的所有积分记录
     *
     * @param clientId
     * @param date
     * @return
     * @throws Exception
     */
    public List<Points> listAfterUpdate(@Param("clientId")Integer clientId,@Param("date")String date)throws Exception;
}
