package com.pandawork.crm.mapper.event;

import com.pandawork.crm.common.dto.event.archived.EventArchivedSearchDto;
import com.pandawork.crm.common.dto.event.archived.EventParticipantDto;
import com.pandawork.crm.common.entity.client.points.PointsItem;
import com.pandawork.crm.common.entity.event.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * EventArchivedMapper
 * Author： csy
 * Date: 2017/7/26
 * Time: 20:07
 */
public interface EventArchivedMapper {

    /**
     * 根据eventArchivedDto获取已归档活动信息详情
     *
     * @param eventArchivedSearchDto
     * @return
     * @throws Exception
     */
    public List<Event> listEventArchivedByDto(@Param("eventArchivedSearchDto")EventArchivedSearchDto eventArchivedSearchDto) throws  Exception;

    /**
     * 根据id获取已归档活动详情信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    public List<Event> queryEventArchivedById(@Param("id") Integer id,
                                              @Param("offset") Integer offset,
                                              @Param("pageSize") Integer pageSize) throws Exception;

    /**
     * 根据eventArchivedSearchDto获取活动记录通知
     *
     * @param eventArchivedSearchDto
     * @return
     * @throws Exception
     */
    public List<EventRecordNotice> queryEventRecordNoticeBySearchDto(@Param("eventArchivedSearchDto")EventArchivedSearchDto eventArchivedSearchDto) throws  Exception;

    public List<EventRecordNotice> countQueryEventRecordNoticeBySearchDto(@Param("eventArchivedSearchDto")EventArchivedSearchDto eventArchivedSearchDto) throws  Exception;

    /**
     * 根据eventArchivedSearchDto查询数量
     *
     * @param eventArchivedSearchDto
     * @return
     * @throws Exception
     */
    public Integer countByEventSearchDto(@Param("eventArchivedSearchDto")EventArchivedSearchDto eventArchivedSearchDto) throws Exception;

    public Integer countLogoutByEventSearchDto() throws Exception;

    /**
     * 根据idcard模糊查询创建者身份证号
     *
     * @param idcardNum
     * @return
     * @throws Exception
     */
    public List<String> queryIdcardByIdcard(@Param("idcardNum") String idcardNum) throws Exception;

    /**
     * 根据idcard模糊查询参与人员身份证号
     *
     * @param idcarNum
     * @return
     * @throws Exception
     */
    public List<String> queryParticipantIdcardByIdcard(@Param("idcardNum") String idcarNum) throws Exception;

    /**
     *根据活动id计算积分的数量
     *
     * @param eventeventRecordNoticeIdId
     * @return
     * @throws Exception
     */
    public Integer countPointsByEventId(@Param("eventRecordNoticeId") Integer eventeventRecordNoticeIdId) throws Exception;

    /**
     * 根据活动id获取检查项结果
     *
     * @param eventRecordNoticeId
     * @return
     * @throws Exception
     */
    public Integer countCheckItemByEventId(@Param("eventRecordNoticeId") Integer eventRecordNoticeId) throws Exception;

    /**
     * 根据活动记录通知id获取活动id
     *
     * @param eventNoticeId
     * @return
     * @throws Exception
     */
    public EventRecordNotice queryEventIdByEventNoticeId(@Param("eventNoticeId") Integer eventNoticeId) throws  Exception;

    /**
     * 根据活动id获取活动记录通知列表
     *
     * @param eventId
     * @return
     * @throws Exception
     */
    public List<EventRecordNotice> listByEventId(@Param("eventId") Integer eventId) throws Exception;

    /**
     * 根据活动id获取活动记录通知列表
     *
     * @param eventId
     * @return
     * @throws Exception
     */
    public List<EventRecordNotice> listByEventIdWithoutArchived(@Param("eventId") Integer eventId) throws Exception;


    /**
     * 根据活动id获取积分项名称及积分值
     *
     * @param eventRecordNoticeId
     * @return
     * @throws Exception
     */
    public List<PointsItem> queryPointsByEventId(@Param("eventRecordNoticeId") Integer eventRecordNoticeId) throws Exception;

    /**
     * 根据活动id获取检查项名称
     *
     * @param eventRecordNoticeId
     * @return
     * @throws Exception
     */
    public List<CheckItem> queryCheckItemByEventId(@Param("eventRecordNoticeId") Integer eventRecordNoticeId) throws Exception;

    public List<EventRecordNotice> listEventRecordNoticeByEventId(@Param("eventId") Integer eventId)throws Exception;

    /**
     * 根据eventId湖获取EventTerm
     *
     * @param eventId
     * @return
     * @throws Exception
     */
    public EventTerm queryEventTermByEventId(@Param("eventId") Integer eventId)throws Exception;
}
