package com.pandawork.crm.service.event;


import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.event.EventRecordNotice;

import java.util.List;

/**
 * EventRecordNoticeService
 * Author： wychen
 * Date: 2017/7/21
 * Time: 10:30
 */
public interface EventRecordNoticeService {

    /**
     * 新增活动记录通知
     *
     * @return
     * @throws SSException
     */
    public EventRecordNotice newEventRecordNotice(EventRecordNotice eventRecordNotice) throws SSException;

    /**
     * 根据id获取活动记录通知
     *
     * @param id
     * @return
     * @throws SSException
     */
    public EventRecordNotice queryById(int id) throws SSException;

    /**
     * 根据活动id查询
     *
     * @param eventTermId
     * @param participantName
     * @param participantIdcard
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<EventRecordNotice> listByEventTermId(int eventTermId, String participantName,String participantIdcard, int offset, int pageSize) throws SSException;

    /**
     * 获取该活动下的参与人员记录总数目
     *
     * @param eventTermId
     * @param participantName
     * @param participantIdcard
     * @return
     * @throws SSException
     */
    public int countByEventTermId(int eventTermId, String participantName,String participantIdcard ) throws SSException;

    /**
     * 根据活动id获取未通知的人员记录列表
     *
     * @param eventTermId
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<EventRecordNotice> queryByEventTermIdWithoutNotice(int eventTermId, int offset, int pageSize) throws SSException;

    /**
     * 修改活动记录实体
     *
     * @param eventRecordNotice
     * @return
     * @throws SSException
     */
    public void update(EventRecordNotice eventRecordNotice) throws SSException;

    /**
     * 根据clientId和groupId新增一条活动记录通知
     *
     * @param clientId
     * @param groupId
     * @throws SSException
     */
    public void newByClientIdAndGroupId(int clientId, int groupId) throws SSException;

    /**
     * 根据参与人名模糊查询前20条名称
     *
     * @param eventTermId
     * @param participantName
     * @return
     * @throws SSException
     */
    public List<EventRecordNotice> listNameTop20ByEventTermId(int eventTermId, String participantName) throws SSException;

    /**
     * 根据参与人idcard模糊查询前20条idcard
     *
     * @param eventTermId
     * @param participantIdcard
     * @return
     * @throws SSException
     */
    public List<EventRecordNotice> listIdcardTop20ByEventTermId(int eventTermId, String participantIdcard) throws SSException;



}
