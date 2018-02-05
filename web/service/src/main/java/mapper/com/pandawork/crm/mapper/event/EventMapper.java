package com.pandawork.crm.mapper.event;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.event.EventSearchDto;
import com.pandawork.crm.common.entity.event.Event;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * EventMapper
 *
 * @author Flying
 * @date 2017/7/20 21:11
 */
public interface EventMapper {

    /**
     * 查询所有的待开展活动
     *
     * @return
     * @throws Exception
     */
    public List<Event> listAll() throws Exception;

    /**
     * 根据活动名称查询数量
     *
     * @return
     * @throws Exception
     */
    public int countByName(@Param("name") String name) throws Exception;

    /**
     * 查询所有未被注销的活动
     *
     * @return
     * @throws Exception
     */
    public List<Event> listNotLogoutEvent() throws Exception;

    /**
     * 根据id和status修改审核状态
     *
     * @param id
     * @throws Exception
     */
    public void updateApprovalStatus(@Param("id") Integer id,
                                     @Param("partyId") Integer partyId,
                                     @Param("status") Integer status,
                                     @Param("comment") String comment) throws Exception;

    /**
     * 根据id修改活动状态为已注销
     *
     * @param id
     * @throws Exception
     */
    public void updateEventToLogout(@Param("id") Integer id) throws Exception;

    /**
     * 查询所有驳回的和未审核的活动
     *
     * @return
     * @throws Exception
     */
    public List<Event> listNotAdoptEvent() throws Exception;

    /**
     * 根据EventSearchDto查询
     *
     * @return
     * @throws Exception
     */
    public List<Event> listByEventSearchDto(@Param("eventSearchDto") EventSearchDto eventSearchDto) throws Exception;

    /**
     * 根据eventSearchDto查询数量
     *
     * @param eventSearchDto
     * @return
     * @throws Exception
     */
    public int countByEventSearchDto(@Param("eventSearchDto") EventSearchDto eventSearchDto) throws Exception;

    /**
     * 根据id修改模板状态为正在被使用
     *
     * @param id
     * @throws Exception
     */
    public void updateTemplateToUsed(@Param("id") Integer id) throws Exception;

    /**
     * 查询所有审核未通过的切未被注销的活动
     *
     * @return
     * @throws Exception
     */
    public List<Event> listNotLogoutAndNotAdoptEvent() throws Exception;

    /**
     * 查询所有审核通过的活动
     *
     * @return
     * @throws Exception
     */
    public List<Event> listAdoptEvent() throws Exception;

}
