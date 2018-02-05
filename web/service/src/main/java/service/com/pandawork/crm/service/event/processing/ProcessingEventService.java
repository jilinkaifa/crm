package com.pandawork.crm.service.event.processing;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.event.EventSearchDto;
import com.pandawork.crm.common.dto.event.processing.CountParticipantDto;
import com.pandawork.crm.common.entity.event.Event;
import com.pandawork.crm.common.entity.event.EventRecordNotice;
import com.pandawork.crm.common.entity.event.EventTerm;
import com.pandawork.crm.common.entity.party.group.employee.Employee;

import java.util.Date;
import java.util.List;


/**
 * ProcessingEventService
 * Author： wychen
 * Date: 2017/7/28
 * Time: 8:40
 */
public interface ProcessingEventService {

    /**
     * 获取进行中活动数目
     *
     * @return
     * @throws SSException
     */
    public int countProcessingEvent() throws SSException;

    /**
     * 获取进行中活动列表
     *
     * @return
     * @throws SSException
     */
    public List<EventTerm> ListProcessingEvent() throws SSException;

    /**
     * 根据查询条件获取所有
     *
     * @param eventSearchDto
     * @return
     * @throws SSException
     */
    public List<EventTerm> listProcessingEventBySearchDto(EventSearchDto eventSearchDto) throws SSException;

    /**
     * 根据条件获取进行中活动数目
     *
     * @param eventSearchDto
     * @return
     * @throws SSException
     */
    public int countProcessingEventBySearchDto(EventSearchDto eventSearchDto) throws SSException;

    /**
     * 暂停一期活动
     *
     * @param eventTermId
     * @return
     * @throws SSException
     */
    public void pauseTerm(int eventTermId) throws SSException;

    /**
     * 暂停整个活动
     *
     * @param eventId
     * @return
     * @throws SSException
     */
    public void pauseEvent(int eventId) throws SSException;

    /**
     * 根据该期活动id获取活动记录通知人员信息
     *
     * @param eventTermId
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<EventRecordNotice> listByEventTermId(int eventTermId,int offset, int pageSize) throws SSException;

    /**
     * 根据id获取本期活动信息
     *
     * @param id
     * @return
     * @throws SSException
     */
    public EventTerm queryById(int id)throws SSException;

    /**
     * 批量通知
     *
     * @param idList
     * @param status
     * @param comment
     * @throws SSException
     */
    public void batchNotice(List<Integer> idList, int status, String comment) throws SSException;

    /**
     * 批量添加积分项
     *
     * @param eventTernId
     * @param clientIdList
     * @param pointsItemId
     * @param partyId
     * @throws SSException
     */
    public void batchAddPointsItems(int eventTernId, List<Integer> clientIdList, int pointsItemId, int partyId ) throws SSException;

    /**
     * 根据页码显示某个患者的本期参与情况
     *
     * @param eventRecordNotice
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<CountParticipantDto> listCountBQParticipantById(int eventRecordNotice, int offset, int pageSize) throws SSException;

    /**
     * 获取某个患者本期活动参与记录数目
     *
     * @param eventRecordNoticeId
     * @return
     * @throws Exception
     */
    public int countParticipantById(int eventRecordNoticeId) throws SSException;

    /**
     * 根据患者id获取该患者的其他活动信息
     *
     * @param eventRecordNoticeId
     * @param clientId
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<EventTerm> listByClientId(int eventRecordNoticeId, int clientId, int offset, int pageSize)throws SSException;

    /**
     * 计算患者其他活动通知信息数目
     *
     * @param eventRecordNoticeId
     * @param clientId
     * @return
     * @throws SSException
     */
    public int countByClientId(int eventRecordNoticeId, int clientId ) throws SSException;

    /**
     * 处理活动通知记录
     *
     * @param eventRecordNoticeId
     * @param status
     * @param date
     * @param checkItemId
     * @param result
     * @throws SSException
     */
    public void handleNotice(int eventRecordNoticeId, int status, Date date, int checkItemId, String result, int partyId)throws SSException;

    /**
     * 根据患者id获取进入通知期但是未通知的活动列表
     *
     * @param clientId
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<EventTerm> queryByClientId(int clientId, int offset, int pageSize) throws SSException;

    /**
     * 根据患者id获取进入通知期但是未通知的活动列表数目
     *
     * @param clientId
     * @return
     * @throws SSException
     */
    public int countEventTermByClientId(int clientId) throws SSException;

    /**
     * 根据活动id获取该活动未通知人员数目
     *
     * @param eventTermId
     * @return
     * @throws SSException
     */
    public int countToBeNoticedPerson(int eventTermId) throws SSException;

    /**
     * 根据活动id获取该活动未完成人员
     *
     * @param eventTermId
     * @return
     * @throws SSException
     */
    public int countToBeFinishedPerson(int eventTermId) throws SSException;

    /**
     *
     * 根据活动id获取关联检查项和积分项总数目
     * @param eventId
     * @return
     * @throws SSException
     */
    public int totalPointsItemAndCheckItem(int eventId) throws SSException;

    /**
     * 根据患者id获取该患者参与的活动信息
     *
     * @param clientId
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<Event> listEventByClientId(int clientId, int offset, int pageSize) throws SSException;

    /**
     * 根据患者id获取该患者参与的活动数目
     *
     * @param clientId
     * @return
     * @throws SSException
     */
    public int countEventByClientId(int clientId) throws SSException;

    /**
     * 累计参与次数
     *
     * @param eventRecordNoticeId
     * @return
     * @throws SSException
     */
    public int countAllParticipant(int eventRecordNoticeId) throws SSException;

    /**
     * 累计参与情况
     *
     * @param eventRecordNoticeId
     * @return
     * @throws SSException
     */
    public List<CountParticipantDto> allParticipant(int eventRecordNoticeId) throws SSException;

    /**
     * 根据每期活动生成下一期活动
     *
     * @throws SSException
     */
    public void autoNextEventTerm() throws SSException;

    /**
     * 根据组id获取通知期和执行期的每期活动list
     *
     * @param groupId
     * @return
     * @throws SSException
     */
    public List<EventTerm> listByGroupId(int groupId) throws SSException;

    /**
     * 根据条件name模糊查询前20个创建人name
     *
     * @param name
     * @return
     * @throws Exception
     */
    public List<Employee> listTop20EmployeeByName(String name) throws Exception;

    /**
     * 根据条件idcardNum模糊查询20个创建人idcard
     *
     * @param idcardNum
     * @return
     * @throws Exception
     */
    public List<Employee> listTop20EmployeeByIdcard(String idcardNum) throws Exception;

    /**
     * 查询下一期活动是否存在
     * @param eventId 活动id
     * @param flag 查询标识 next:下一期；all:全部
     * @return
     * @throws SSException
     */
    public int countNextTerm(Integer eventId,String flag) throws SSException;

    /**
     * 暂停分期活动
     *
     * @param eventId 活动id
     * @param flag 查询标识 next:下一期；all:全部
     * @throws SSException
     */
    public void pauseNextTerm(Integer eventId,String flag) throws SSException;
}
