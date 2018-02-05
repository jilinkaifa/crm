package com.pandawork.crm.service.event.archived;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.event.archived.EventArchivedSearchDto;
import com.pandawork.crm.common.entity.client.points.PointsItem;
import com.pandawork.crm.common.entity.event.CheckItem;
import com.pandawork.crm.common.entity.event.Event;
import com.pandawork.crm.common.entity.event.EventRecordNotice;
import com.pandawork.crm.common.entity.event.EventTerm;

import java.util.List;

/**
 * EventArchivedService
 * Author： csy
 * Date: 2017/7/26
 * Time: 19:45
 */
public interface EventArchivedService {

    /**
     * 根据eventArchivedSearchDto获取已归活动信息详情
     *
     * @param eventArchivedSearchDto
     * @return
     * @throws SSException
     */
    public List<Event> queryEventArchivedByEventDto(EventArchivedSearchDto eventArchivedSearchDto) throws SSException;

    /**
     * 根据活动id获取已归档活动详情
     *
     * @param id
     * @return
     * @throws SSException
     */
    public List<Event> queryEventArchivedById(int id, int offset, int pageSize) throws SSException;


    public int countBySearchDto(EventArchivedSearchDto eventArchivedSearchDto) throws SSException;

    public int countNOLogoutBySearchDto(EventArchivedSearchDto eventArchivedSearchDto) throws SSException;

    /**
     * 根据idcard模糊查询创建人的身份证号
     *
     * @param idcardNum
     * @return
     * @throws SSException
     */
    public List<String> queryIdcardByIdcard(String idcardNum) throws SSException;

    /**
     * 根据idcard模糊查询参与人员的身份证号
     *
     * @param idcardNum
     * @return
     * @throws SSException
     */
    public List<String> queryParticipantIdcardByIdcard(String idcardNum) throws SSException;

    /**
     * 根据searchDto获取活动通知记录
     *
     * @param eventArchivedSearchDto
     * @return
     * @throws SSException
     */
    public List<EventRecordNotice> listEventRecordNoticeBySearchDto(EventArchivedSearchDto eventArchivedSearchDto) throws  SSException;

    /**
     * 计算本期参与情况
     *
     * @param eventNoticeId
     * @return
     * @throws SSException
     */
    public int countCurrentParticipation(int eventNoticeId) throws SSException;

//    /**
//     * 计算累计参与情况
//     *
//     * @param eventId
//     * @return
//     * @throws SSException
//     */
//    public int countCumulativeParticipation(int eventId) throws SSException;


    /**
     * 计算累计参与情况
     *
     * @param eventId
     * @return
     * @throws SSException
     */
    public int countCumulativeParticipationWithoutArchived(int eventId) throws SSException;

    /**
     * 根据活动id获取积分项
     *
     * @param eventRecordNoticeId
     * @return
     * @throws SSException
     */
    public List<PointsItem> queryPointsItemByEventId(int eventRecordNoticeId) throws SSException;

    /**
     * 根据活动id获取检查项
     *
     * @param eventRecordNoticeId
     * @return
     * @throws SSException
     */
    public List<CheckItem> queryCheckItemByEventId(int eventRecordNoticeId) throws SSException;

    /**
     * 根据活动ID获取eventTerm的信息
     *
     * @param eventId
     * @return
     * @throws SSException
     */
    public EventTerm queryEventTermByEventId(int eventId) throws SSException;

}
