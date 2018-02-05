package com.pandawork.crm.service.client.visit;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.client.visit.VisitDetailSearchDto;
import com.pandawork.crm.common.dto.client.visit.VisitSearchDto;
import com.pandawork.crm.common.entity.client.visit.PointsConvert;
import com.pandawork.crm.common.entity.client.visit.Visit;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * VisitService
 * Author： liping
 * Date: 2017/7/20
 * Time: 9:23
 */
public interface VisitService {

    /**
     * 分页查询来访信息
     *
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<Visit> listByPage(int offset, int pageSize) throws SSException;

    /**
     * 根据输入条件查询来访
     *
     * @param visitSearchDto
     * @return
     * @throws SSException
     */
    public List<Visit> listByVisitSearchDto(VisitSearchDto visitSearchDto) throws SSException;

    /**
     * 点击详情，根据clientId查询该client的所有来访详情
     *
     * @param clientId
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<Visit> queryByClientId(int clientId, int offset, int pageSize) throws SSException;

    /**
     * 根据clientId删除client所有来访
     *
     * @param clientId
     * @throws SSException
     */
    public void delByClientId(int clientId) throws SSException;

    /**
     * 详情页面输入查询条件查询
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<Visit> listByVisitDetailSearchDto(VisitDetailSearchDto searchDto) throws SSException;

    /**
     * 计算来访总金额
     *
     * @param clientId
     * @return
     * @throws SSException
     */
    public double getAllCost(int clientId) throws SSException;

    /**
     * 删除来访详情
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 修改（点击确定）来访详情
     *
     * @param visit
     * @throws SSException
     */
    public void updateVisit(Visit visit) throws SSException;

    /**
     * 根据id查询来访详情（点击修改）
     *
     * @param id
     * @return
     * @throws SSException
     */
    public Visit queryById(int id) throws SSException;

    /**
     * 增加来访详情
     *
     * @param visit
     * @throws SSException
     */
    public Visit newVisit(Visit visit) throws SSException;

    /**
     * 来访条数（去重）
     *
     * @return
     * @throws SSException
     */
    public int count() throws SSException;

    /**
     * 详情页面输入查询条件查询数量
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public int countByVisitDetailSearchDto(VisitDetailSearchDto searchDto) throws SSException;

    /**
     * 消费金额转换为积分
     *
     * @param cost
     * @return
     * @throws SSException
     */
    public int moneyToPoints(double cost) throws SSException;

    /**
     * 根据日期获取当天改分组的消费总金额
     *
     * @param date
     * @param memberGroupId
     * @return
     * @throws SSException
     */
    public BigDecimal countCostByDate(String date, int memberGroupId)throws SSException;

    /**
     * 修改积分转换比率
     *
     * @throws SSException
     */
    public void updatePointsConvert(PointsConvert pointsConvert)throws SSException;

    /**
     * 根据id查询积分兑换规则
     *
     * @param id
     * @return
     * @throws SSException
     */
    public PointsConvert queryPointsConvertById(int id)throws SSException;

    /**
     * 根据患者id查询来信息
     *
     * @param id
     * @return
     * @throws SSException
     */
    public Visit getLastVisitByClientId(int id)throws SSException;

    /**
     * 近半年会员患者及销售情况分析 月份
     *
     * @return
     * @throws SSException
     */
    public List<String> getMonth() throws SSException;

    /**
     * 近半年会员患者及销售情况分析 销售总额
     *
     * @return
     * @throws SSException
     */
    public List<BigDecimal> getGrossSales() throws SSException;

    /**
     *  近半年会员患者及销售情况分析 会员患者数
     *
     * @return
     * @throws SSException
     */
    public List<Integer> getNumberOfMembers() throws SSException;

}
