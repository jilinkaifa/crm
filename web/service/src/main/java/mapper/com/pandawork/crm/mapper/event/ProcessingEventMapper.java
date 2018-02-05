package com.pandawork.crm.mapper.event;

import com.pandawork.crm.common.dto.event.EventSearchDto;
import com.pandawork.crm.common.dto.event.processing.CountParticipantDto;
import com.pandawork.crm.common.entity.event.Event;
import com.pandawork.crm.common.entity.event.EventRecordNotice;
import com.pandawork.crm.common.entity.event.EventTerm;
import com.pandawork.crm.common.entity.party.group.employee.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * CountProcessingEventMapper
 * Author： wychen
 * Date: 2017/7/28
 * Time: 8:54
 */
public interface ProcessingEventMapper {

    /**
     * 获取进行中活动数目
     *
     * @return
     * @throws Exception
     */
    public Integer countProcessingEvent()throws Exception;

    /**
     * 获取进行中活动信息
     *
     * @return
     * @throws Exception
     */
    public List<EventTerm> listProcessingEvent() throws Exception;

    /**
     * 根据活动id获取该活动未通知人数
     *
     * @param eventTermId
     * @return
     * @throws Exception
     */
    public Integer countToBeNoticedPerson(@Param("eventTermId") Integer eventTermId) throws Exception;

    /**
     * 获取待完成人数
     *
     * @param eventTermId
     * @return
     * @throws Exception
     */
    public Integer countToBeFinishedPerson(@Param("eventTermId") Integer eventTermId) throws Exception;

    /**
     * 根据查询条件获取进行中活动列表
     *
     * @param eventSearchDto
     * @return
     * @throws Exception
     */
    public List<EventTerm> listProcessingEventBySearchDto(@Param("eventSearchDto") EventSearchDto eventSearchDto) throws Exception;

    /**
     * 根据条件获取进行中活动数目
     *
     * @param eventSearchDto
     * @return
     * @throws Exception
     */
    public Integer countProcessingEventBySearchDto(@Param("eventSearchDto") EventSearchDto eventSearchDto) throws Exception;

    /**
     * 暂停一期活动
     *
     * @param id
     * @throws Exception
     */
    public void pauseTerm(@Param("id") Integer id)throws Exception;

    /**
     * 暂停整个活动
     *
     * @param id
     * @throws Exception
     */
    public void pauseEvent(@Param("id") Integer id)throws Exception;

    /**
     * 根据该期活动id获取该期下的所有参与人员记录信息
     *
     * @param eventTermId
     * @return
     * @throws Exception
     */
    public List<EventRecordNotice> listByEventTermId(@Param("eventTermId") Integer eventTermId,
                                                     @Param("offset") Integer offset,
                                                     @Param("pageSize") Integer pageSize) throws Exception;

    /**
     * 根据id获取本期活动信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    public EventTerm queryById(@Param("id") Integer id) throws Exception;

    /**
     * 批量通知
     *
     * @param idList
     * @param status
     * @param comment
     * @throws Exception
     */
    public void batchNotice(@Param("idList") List<Integer> idList,
                            @Param("status") Integer status,
                            @Param("comment") String comment)throws Exception;

    /**
     * 根据活动记录通知id获取该患者累计参与情况
     *
     * @param eventRecordNoticeId
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<CountParticipantDto> listCountBQParticipantById(@Param("eventRecordNoticeId") Integer eventRecordNoticeId,
                                                              @Param("offset") Integer offset,
                                                              @Param("pageSize") Integer pageSize)throws Exception;

    /**
     * 根据患者id获取该患者的其他活动信息
     *
     * @param clientId
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<EventTerm> listByClientId(@Param("clientId") Integer clientId,
                                          @Param("offset") Integer offset,
                                          @Param("pageSize") Integer pageSize)throws Exception;

    /**
     * 计算患者其他活动通知信息数目
     *
     * @param clientId
     * @return
     * @throws Exception
     */
    public Integer countByClientId(@Param("clientId") Integer clientId)throws Exception;

    /**
     * 根据id更逊活动参与状态
     *
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateParticipantStatusById(@Param("id") Integer id,
                                            @Param("status") Integer status,
                                            @Param("eventParticipantTimes") Integer eventParticipantTimes)throws Exception;

    /**
     * 根据患者id获取进入通知期但是未通知的活动列表
     *
     * @param clientId
     * @return
     * @throws Exception
     */
    public List<EventTerm> queryByClientId(@Param("clientId") Integer clientId,
                                           @Param("offset") Integer offset,
                                           @Param("pageSize") Integer pageSize) throws Exception;

    /**
     * 根据患者id获取进入通知期但是未通知的活动列表数目
     *
     * @param clientId
     * @return
     * @throws Exception
     */
    public Integer countEventTermByClientId(@Param("clientId") Integer clientId) throws Exception;

    /**
     * 根据活动id获取关联检查项和积分项总数目
     *
     * @param eventId
     * @return
     * @throws Exception
     */
//    public Integer totalPointsItemAndCheckItem(@Param("eventId") Integer eventId) throws Exception;

    /**
     * 根据活动id计算关联检查项总数
     *
     * @param eventId
     * @return
     * @throws Exception
     */
    public Integer countCheckItemByEventId(@Param("eventId") Integer eventId) throws Exception;

    /**
     * 根据活动id计算关联积分项总数
     *
     * @param eventId
     * @return
     * @throws Exception
     */
    public Integer countPointsItemByEventId(@Param("eventId") Integer eventId) throws Exception;

    /**
     * 根据患者id获取该患者参与的活动信息
     *
     * @param clientId
     * @return
     * @throws Exception
     */
    public List<Event> listEventByClientId(@Param("clientId") Integer clientId,
                                           @Param("offset") Integer offset,
                                           @Param("pageSize") Integer pageSize) throws Exception;

    /**
     * 根据患者id获取该患者参与的活动数目
     *
     * @param clientId
     * @return
     * @throws Exception
     */
    public Integer countEventByClientId(@Param("clientId") Integer clientId) throws Exception;

    /**
     * 根据活动通知id获取参与积分项和检查项数目
     *
     * @param eventRecordNoticeId
     * @return
     * @throws Exception
     */
    public Integer countParticipantById(@Param("eventRecordNoticeId") Integer eventRecordNoticeId) throws Exception;

    /**
     * 根据活动id获取累计参与次数
     *
     * @param eventId
     * @return
     * @throws Exception
     */
    public Integer countAllParticipant(@Param("eventId") Integer eventId,
                                       @Param("clientId") Integer clientId) throws Exception;

    /**
     * 根据活动id获取所有参与期数
     *
     * @param eventId
     * @return
     * @throws Exception
     */
    public List<Integer> listEventNoticeIdByEventId(@Param("eventId") Integer eventId,
                                                    @Param("clientId") Integer clientId) throws Exception;

    /**
     * 根据活动记录id获取本期活动积分项和检查项信息
     *
     * @param eventRecordNoticeId
     * @return
     * @throws Exception
     */
    public List<CountParticipantDto> listAllBQParticipantById(@Param("eventRecordNoticeId") Integer eventRecordNoticeId) throws Exception;

    /**
     * 根据组id获取通知期和执行期的每期活动list
     *
     * @param groupId
     * @return
     * @throws Exception
     */
    public List<EventTerm> listByGroupId(@Param("groupId") Integer groupId) throws Exception;

    /**
     * 根据条件name模糊查询前20个创建人name
     *
     * @param name
     * @return
     * @throws Exception
     */
    public List<Employee> listTop20EmployeeByName(@Param("name") String name) throws Exception;

    /**
     * 根据条件idcardNum模糊查询20个创建人idcard
     *
     * @param idcardNum
     * @return
     * @throws Exception
     */
    public List<Employee> listTop20EmployeeByIdcard( @Param("idcardNum") String idcardNum) throws Exception;

    /**
     * 查询下一期活动是否存在
     * @param eventId 活动id
     * @param flag 查询标识 next:下一期；all:全部
     * @return
     * @throws Exception
     */
    public Integer countNextTerm(@Param("eventId") Integer eventId,@Param("flag") String flag)throws Exception;

    /**
     * 暂停分期活动
     *
     * @param eventId 活动id
     * @param flag 查询标识 next:下一期；all:全部
     * @throws Exception
     */
    public void pauseNextTerm(@Param("eventId") Integer eventId,@Param("flag") String flag)throws Exception;

}
