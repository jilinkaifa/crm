package com.pandawork.crm.service.event.prepare;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.event.EventSearchDto;
import com.pandawork.crm.common.entity.event.Event;
import java.util.Date;
import java.util.List;

/**
 * EventService
 * 活动Service
 *
 * @author Flying
 * @date 2017/7/20 20:44
 */
public interface EventService {

    /**
     * 获取所有待开展的活动
     *
     * @return
     * @throws SSException
     */
    public List<Event> listAll() throws SSException;

    /**
     * 根据id查询待开展活动的详情
     *
     * @param id
     * @return
     * @throws SSException
     */
    public Event queryById(int id) throws SSException;

    /**
     * 新建一个活动
     *
     * @return
     * @throws SSException
     */
    public Event newEvent(Event event) throws SSException;

    /**
     * 检查活动名称是否重复
     *
     * @param name
     * @return
     * @throws SSException
     */
    public boolean checkEventNameIsExit(String name) throws SSException;

    /**
     * 修改活动
     *
     * @param event
     * @throws SSException
     */
    public void updateEvent(Event event) throws SSException;

    /**
     * 根据id、partyId和status将审批状态修改为指定状态
     *
     * @param id
     * @param partyId
     * @param status
     * @throws SSException
     */
    public void updateApprovalStatus(int id, int partyId, int status, String comment) throws SSException;

    /**
     * 查询所有未被注销的活动
     *
     * @return
     * @throws SSException
     */
    public List<Event> listNotLogoutEvent() throws SSException;

    /**
     * 查询所有未通过审核的活动
     *
     * @return
     * @throws SSException
     */
    public List<Event> listNotAdoptEvent() throws SSException;

    /**
     * 查询所有审核未通过的和未被注销的活动
     *
     * @return
     * @throws SSException
     */
    public List<Event> listNotLogoutAndNotAdoptEvent() throws SSException;

    /**
     * 自动注销活动
     *
     * @throws SSException
     */
    public void autoLogoutEvent() throws SSException;

    /**
     * 根据id手动注销活动
     *
     * @param id
     * @throws SSException
     */
    public void toLogoutEvent(int id) throws SSException;

    /**
     * 根据EventSearchDto查询
     *
     * @return
     * @throws SSException
     */
    public List<Event> listByEventSearchDto(EventSearchDto eventSearchDto) throws SSException;

    /**
     * 根据eventSearchDto查询数量
     *
     * @param eventSearchDto
     * @return
     * @throws SSException
     */
    public int countByEventSearchDto(EventSearchDto eventSearchDto)throws SSException;

    /**
     * 根据活动id判断是否可以注销
     *
     * @param id
     * @param partyId
     * @return
     * @throws SSException
     */
    public boolean checkIsCanLogoutById(int id, int partyId) throws SSException;

    /**
     * 根据id去修改模板已被使用
     *
     * @param id
     * @throws SSException
     */
    public void updateTemplateToUsed(int id) throws SSException;

    /**
     * 自动为每期活动生成活动记录通知和每期活动记录
     *
     * @throws SSException
     */
    public void autoCreateEventRecordNoticeAndEventTerm() throws SSException;

//    /**
//     * 自动生成活动记录通知和本期活动记录
//     *
//     * @throws SSException
//     */
//    public void createEventRecordNoticeAndEventTerm() throws SSException;

    /**
     * 获取当前用户所具有的权限名
     *
     * @param partyId
     * @return
     */
    public String getRoleName(int partyId) throws SSException;

    /**
     *  活动审批通过后生成对应的全部分期活动
     *
     * @param event
     * @return
     * @throws SSException
     */
    public String createEventTerm(Event event) throws SSException;

    /**
     * 计算活动总期数
     * @param event
     * @return
     * @throws SSException
     */
    public int getTotalPeriods(Event event) throws SSException;
}
