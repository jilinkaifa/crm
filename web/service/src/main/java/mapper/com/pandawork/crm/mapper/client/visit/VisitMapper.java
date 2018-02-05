package com.pandawork.crm.mapper.client.visit;

import com.pandawork.crm.common.dto.client.visit.VisitDetailSearchDto;
import com.pandawork.crm.common.dto.client.visit.VisitSearchDto;
import com.pandawork.crm.common.entity.client.visit.Visit;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * VisitMapper
 * Author： liping
 * Date: 2017/7/20
 * Time: 10:00
 */
public interface VisitMapper {

    /**
     * 分页查询来访信息
     *
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<Visit> listByPage(@Param("offset") int offset, @Param("pageSize") int pageSize) throws Exception;

    /**
     * 根据输入的关键字，查询来访信息
     *
     * @param visitSearchDto
     * @return
     * @throws Exception
     */
    public List<Visit> listByVisitSearchDto(@Param("visitSearchDto") VisitSearchDto visitSearchDto) throws Exception;

    /**
     * 根据患者id查询最后来访信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Visit getLastVisitByClientId(@Param("id") Integer id) throws Exception;

    /**
     * 根据clientId查询client的所有来访详情
     *
     * @param clientId
     * @return
     * @throws Exception
     */
    public List<Visit> queryByClientId(@Param("clientId") Integer clientId, @Param("offset") int offset,
                                       @Param("pageSize") int pageSize) throws Exception;

    /**
     * 根据clientId删除client的所有来访详情
     *
     * @param clientId
     * @throws Exception
     */
    public void delByClientId(@Param("clientId") Integer clientId) throws Exception;

    /**
     * 详情页面输入查询条件查询
     *
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<Visit> listByVisitDetailSearchDto(@Param("searchDto") VisitDetailSearchDto searchDto) throws Exception;

    /**
     * 计算来访消费总金额
     *
     * @param clientId
     * @return
     * @throws Exception
     */
    public double getAllCost(@Param("clientId") Integer clientId)throws Exception;

    /**
     * 删除来访详情
     *
     * @param id
     * @throws Exception
     */
    public void delById(@Param("id") Integer id) throws Exception;

    /**
     * 修改来访详情
     *
     * @param visit
     * @throws Exception
     */
    public void updateVisit(@Param("visit") Visit visit) throws Exception;

    /**
     * 根据id查询来访详情
     *
     * @param id
     * @throws Exception
     */
    public Visit queryById(@Param("id") Integer id) throws Exception;

    /**
     * 新增来访详情
     *
     * @param visit
     * @throws Exception
     */
    public Visit newVisit(@Param("visit") Visit visit) throws Exception;

    /**
     * 去重查来访条数
     *
     * @return
     * @throws Exception
     */
    public int count() throws Exception;

    /**
     * 详情页根据条件查数量
     *
     * @param searchDto
     * @return
     * @throws Exception
     */
    public int countByVisitDetailSearchDto(@Param("searchDto") VisitDetailSearchDto searchDto) throws Exception;

    /**
     *得到转换率
     *
     * @return
     * @throws Exception
     */
    public int moneyToPoints() throws Exception;

    /**
     * 根据当前日期获取该分组当日消费总金额
     *
     * @param date
     * @param id
     * @return
     * @throws Exception
     */
    public BigDecimal countCostByDate(@Param("date") String date, @Param("id") Integer id)throws Exception;

    /**
     * 近半年会员患者及销售情况分析 月份
     *
     * @return
     * @throws Exception
     */
    public List<String> getMonth() throws Exception;

    /**
     *  近半年会员患者及销售情况分析 销售总额
     *
     * @return
     * @throws Exception
     */
    public List<BigDecimal> getGrossSales() throws Exception;

    /**
     *  近半年会员患者及销售情况分析 会员患者数
     *
     * @return
     * @throws Exception
     */
    public List<Integer> getNumberOfMembers() throws Exception;
}
