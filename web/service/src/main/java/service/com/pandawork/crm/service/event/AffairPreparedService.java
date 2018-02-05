package com.pandawork.crm.service.event;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.event.Event;

import java.util.List;

/**
 * 待办事务Service
 * AffairPreparedService
 * Author： linjie
 * Date: 2017/7/29
 * Time: 9:08
 */
public interface AffairPreparedService {

    /**
     *分页查询待审批事务列表
     * @return
     * @throws SSException
     */
    public List<Event> listAllToApprovalByPage(int approvalId,int offset,int pageSize) throws SSException;

    /**
     * 分页查询被驳回事务列表
     * @return
     * @throws SSException
     */
    public List<Event> listAllRejectedByPage(int createdPartyId,int offset,int pageSize) throws SSException;

    /**
     * 查询被驳回事务条数
     * @param createdPartyId
     * @return
     * @throws SSException
     */
    public int countRejected(int createdPartyId)throws SSException;

    /**
     * 查询待审批事务条数
     * @param approvalId
     * @return
     * @throws SSException
     */
    public int countToApproval(int approvalId)throws SSException;

    /**
     * 将时间转换为例如2017.3.21格式
     * @param events
     * @throws SSException
     */
    public void dateConvert(List<Event> events) throws SSException;

    /**
     * 根据partyIdList和状态分页获取活动
     *
     * @param partyIdList
     * @param status
     * @return
     * @throws SSException
     */
    public List<Event> listEventByPartyIdListAndStatus(List<Integer> partyIdList, int status, int offset, int pageSize) throws SSException;

    /**
     * 根据partyIdList和状态获取数量
     *
     * @param partyIdList
     * @param status
     * @return
     * @throws SSException
     */
    public int countEventByPartyIdListAndStatus(List<Integer> partyIdList, int status) throws SSException;

}
