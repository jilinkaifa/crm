package com.pandawork.crm.service.client.points;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.client.points.PointsSearchDto;
import com.pandawork.crm.common.entity.client.points.Points;

import java.util.List;


/**
 * PointsService
 * Author： liping
 * Date: 2017/7/31
 * Time: 17:07
 */
public interface PointsService {

    /**
     * 根据条件查询积分
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<Points> listByPointsSearchDto(PointsSearchDto searchDto) throws SSException;

    /**
     * 根据searchDto查询积分总条数
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public int countByPointsSearchDto(PointsSearchDto searchDto) throws SSException;

    /**
     * 查询患者积分
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<Points> listByClientId(PointsSearchDto searchDto) throws SSException;

    /**
     * 查询患者最晚积分记录
     *
     * @param clientId
     * @return
     * @throws SSException
     */
    public Points getLastPointsByClientId(int clientId) throws SSException;

    /**
     * 新增积分
     *
     * @param points
     * @return
     * @throws SSException
     */
    public Points newPoints(Points points) throws SSException;

    /**
     * 删除积分
     *
     * @param points
     * @throws SSException
     */
    public void delPoints(Points points) throws SSException;

    /**
     * 根据来访查询积分
     *
     * @param visitId
     * @return
     * @throws SSException
     */
    public Points queryByVisit(int visitId) throws SSException;

    /**
     * 修改积分
     *
     * @param points
     * @throws SSException
     */
    public void updatePoints(Points points) throws SSException;

    /**
     * 去重统计积分条数
     *
     * @return
     * @throws SSException
     */
    public int count(PointsSearchDto searchDto) throws SSException;

    /**
     * 查询详情条数
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public int countDetail(PointsSearchDto searchDto) throws SSException;
}
