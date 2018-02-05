package com.pandawork.crm.mapper.event;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.event.Event;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AffairPreparedMapper
 * Author： linjie
 * Date: 2017/7/29
 * Time: 9:21
 */
public interface AffairPreparedMapper {
    /**
     * 分页查询被驳回事务列表
     * @param createdPartyId
     * @return
     * @throws Exception
     */
    public List<Event> listAllRejectedByPage( @Param("createdPartyId") Integer createdPartyId,
                                              @Param("offset") Integer offset,
                                              @Param("pageSize") Integer pageSize) throws Exception;

    /**
     * 分页查询待审批事务列表
     * @param approvalId
     * @return
     * @throws Exception
     * listAllToApproval
     */
    public List<Event> listAllToApprovalByPage(@Param("approvalId") Integer approvalId,
     @Param("offset") Integer offset,
     @Param("pageSize") Integer pageSize) throws Exception;

     /**
      * 查询当前创建者审批驳回记录条数
      * @param createdPartyId
     * @return
     * @throws Exception
     */
    public int countRejected(@Param("createdPartyId") Integer createdPartyId) throws Exception;

    /**
     * 显示未审批记录条数
     * @param approvalId
     * @return
     * @throws Exception
     */
    public int countToApproval(@Param("approvalId") Integer approvalId) throws Exception;

    /**
     * 根据活动状态和partyId获取活动
     *
     * @param partyIdList
     * @param status
     * @return
     * @throws Exception
     */
    public List<Event> listEventByPartyIdListAndStatus(@Param("partyIdList") List<Integer> partyIdList,
                                                       @Param("status") Integer status,
                                                       @Param("offset") Integer offset,
                                                       @Param("pageSize") Integer pageSize) throws Exception;

    public int countEventByPartyIdListAndStatus(@Param("partyIdList") List<Integer> partyIdList,
                                                @Param("status") Integer status) throws Exception;
}

