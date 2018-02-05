package com.pandawork.crm.service.event.processing.impl;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.crm.common.dto.event.EventSearchDto;
import com.pandawork.crm.common.dto.event.processing.CountParticipantDto;
import com.pandawork.crm.common.entity.client.basic.Client;
import com.pandawork.crm.common.entity.client.points.Points;
import com.pandawork.crm.common.entity.client.points.PointsItem;
import com.pandawork.crm.common.entity.event.*;
import com.pandawork.crm.common.enums.event.*;
import com.pandawork.crm.common.entity.party.group.employee.Employee;
import com.pandawork.crm.common.enums.event.EventLevelEnums;
import com.pandawork.crm.common.enums.event.EventTermStatusEnums;
import com.pandawork.crm.common.enums.event.EventTypeEnums;
import com.pandawork.crm.common.exception.CrmException;
import com.pandawork.crm.mapper.event.ProcessingEventMapper;
import com.pandawork.crm.service.client.basic.ClientService;
import com.pandawork.crm.service.client.points.PointsService;
import com.pandawork.crm.service.event.EventRecordNoticeService;
import com.pandawork.crm.service.event.EventTermService;
import com.pandawork.crm.service.event.prepare.EventService;
import com.pandawork.crm.service.event.processing.ProcessingEventService;
import com.pandawork.crm.service.party.member.MemberGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ProcessingEventServiceImpl
 * Author： wychen
 * Date: 2017/7/28
 * Time: 8:40
 */
@Service("processingEventService")
public class ProcessingEventServiceImpl implements ProcessingEventService {

    @Autowired
    private ProcessingEventMapper processingEventMapper;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private MemberGroupService memberGroupService;

    @Autowired
    private EventTermService eventTermService;

    @Autowired
    private EventService eventService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EventRecordNoticeService eventRecordNoticeService;

    @Autowired
    private PointsService pointsService;

    @Override
    public int countProcessingEvent() throws SSException {
        try {
            return processingEventMapper.countProcessingEvent();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountProcessingEventFail, e);
        }
    }

    @Override
    public List<EventTerm> ListProcessingEvent() throws SSException {
        List<EventTerm> eventTerms = Collections.emptyList();
        try {
            eventTerms = processingEventMapper.listProcessingEvent();
            for (EventTerm eventTerm : eventTerms) {
                Integer tobeNoticedPerson = this.countToBeNoticedPerson(eventTerm.getId());
                if (Assert.isNotNull(tobeNoticedPerson)) {
                    eventTerm.setToBeNoticedPerson(tobeNoticedPerson);
                }
                Integer toBeFinishedPerson = this.countToBeFinishedPerson(eventTerm.getId());
                if (Assert.isNotNull(toBeFinishedPerson)) {
                    eventTerm.setToBeFinishedPerson(toBeFinishedPerson);
                }
                setExtraValue(eventTerm);
            }
            return eventTerms;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListProcessingEventFail, e);
        }
    }

    @Override
    public List<EventTerm> listProcessingEventBySearchDto(EventSearchDto eventSearchDto) throws SSException {
        List<EventTerm> eventTerms = Collections.emptyList();
        try {
            eventTerms = processingEventMapper.listProcessingEventBySearchDto(eventSearchDto);
            for (EventTerm eventTerm : eventTerms) {
                Integer tobeNoticedPerson = this.countToBeNoticedPerson(eventTerm.getId());
                if (Assert.isNotNull(tobeNoticedPerson)) {
                    eventTerm.setToBeNoticedPerson(tobeNoticedPerson);
                }
                Integer toBeFinishedPerson = this.countToBeFinishedPerson(eventTerm.getId());
                if (Assert.isNotNull(toBeFinishedPerson)) {
                    eventTerm.setToBeFinishedPerson(toBeFinishedPerson);
                }
                setExtraValue(eventTerm);
            }
            return eventTerms;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListProcessingEventBySearchDtoFail, e);
        }
    }

    @Override
    public int countProcessingEventBySearchDto(EventSearchDto eventSearchDto) throws SSException {
        try {
            return processingEventMapper.countProcessingEventBySearchDto(eventSearchDto);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountProcessingEventFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void pauseTerm(int eventTermId) throws SSException {
        try {
            processingEventMapper.pauseTerm(eventTermId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.PauseTermFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void pauseEvent(int eventId) throws SSException {
        try {
            processingEventMapper.pauseEvent(eventId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.PauseTermFail, e);
        }
    }

    @Override
    public List<EventRecordNotice> listByEventTermId(int eventTermId, int offset, int pageSize) throws SSException {
        List<EventRecordNotice> eventRecordNotices = Collections.emptyList();
        try {
            eventRecordNotices = processingEventMapper.listByEventTermId(eventTermId, offset, pageSize);
            return eventRecordNotices;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListEventRecordNoticeByEventTermIdFail, e);
        }
    }

    @Override
    public EventTerm queryById(int id) throws SSException {
        EventTerm eventTerm = new EventTerm();
        try {
            if (Assert.isNotNull(id)) {
                eventTerm = processingEventMapper.queryById(id);
                setExtraValue(eventTerm);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEventTermById, e);
        }
        return eventTerm;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void batchNotice(List<Integer> idList, int status, String comment) throws SSException {
        try {
            processingEventMapper.batchNotice(idList, status, comment);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEventTermById, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void batchAddPointsItems(int eventTernId, List<Integer> idList, int pointsItemId, int partyId) throws SSException {
        try {
            if (Assert.isNotNull(eventTernId) && Assert.isNotNull(idList) && Assert.isNotNull(pointsItemId) && Assert.isNotNull(partyId)) {
                for (int eventRecordNoticeId : idList) {
                    //新增活动记录积分项
                    EventRecordPointsItem eventRecordPointsItem = new EventRecordPointsItem();
                    eventRecordPointsItem.setEventRecordNoticeId(eventRecordNoticeId);
                    eventRecordPointsItem.setCreatedPartyId(partyId);
                    eventRecordPointsItem.setPointsItemId(pointsItemId);
                    commonDao.insert(eventRecordPointsItem);
                    //将活动记录通知表中参与次数+1
                    EventRecordNotice eventRecordNotice = commonDao.queryById(EventRecordNotice.class, eventRecordNoticeId);
                    int participantTimes = 0;
                    if (Assert.isNotNull(eventRecordNotice.getEventParticipantTimes())) {
                        participantTimes = eventRecordNotice.getEventParticipantTimes();
                    }
                    eventRecordNotice.setEventParticipantTimes(participantTimes + 1);
                    commonDao.updateFieldsById(eventRecordNotice, "eventParticipantTimes");

                    //给患者添加积分记录
                    if(Assert.isNotNull(eventRecordNotice.getClientId())){
                        int clientId = eventRecordNotice.getClientId();
                        Points memberPoint = pointsService.getLastPointsByClientId(clientId);
                        Points newPoints = new Points();
                        newPoints.setClientId(clientId);
                        newPoints.setPointsItemId(pointsItemId);
                        newPoints.setPointsFrom(1);//设为积分来源为活动积分
                        newPoints.setPointsDate(new Date());
                        newPoints.setCreatedPartyId(partyId);
                        newPoints.setEventId(eventRecordNotice.getEventId());
                        PointsItem pointsItem = commonDao.queryById(PointsItem.class,pointsItemId);
                        if(Assert.isNotNull(pointsItem)){
                            newPoints.setPointsSize(pointsItem.getPointsValue());
                            if(Assert.isNotNull(memberPoint)){
                                newPoints.setLastPoints(memberPoint.getCurrentPoints());
                                newPoints.setLastVisitDate(memberPoint.getLastVisitDate());
                                newPoints.setCurrentPoints(memberPoint.getCurrentPoints()+newPoints.getPointsSize());
                                if(pointsItem.getPointsValue() > 0){
                                    newPoints.setCurrentSumpoints(memberPoint.getCurrentSumpoints() + pointsItem.getPointsValue());
                                }
                            }else{
                                newPoints.setLastPoints(0);
                                newPoints.setCurrentPoints(pointsItem.getPointsValue());
                                newPoints.setCurrentSumpoints(pointsItem.getPointsValue());
                            }
                        }
                        commonDao.insert(newPoints);
                    }
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.BatchNewPointsFail, e);
        }
    }

    @Override
    public List<CountParticipantDto> listCountBQParticipantById(int eventRecordNoticeId, int offset, int pageSize) throws SSException {
        List<CountParticipantDto> countParticipants = new ArrayList<CountParticipantDto>();
        try {
            if (Assert.isNotNull(eventRecordNoticeId) && Assert.isNotNull(offset) && Assert.isNotNull(pageSize)) {
                countParticipants = processingEventMapper.listCountBQParticipantById(eventRecordNoticeId, offset, pageSize);
                EventRecordNotice eventRecordNotice = new EventRecordNotice();
                eventRecordNotice = commonDao.queryById(EventRecordNotice.class, eventRecordNoticeId);
                for (CountParticipantDto countParticipantDto : countParticipants) {
                    countParticipantDto.setName(eventRecordNotice.getParticipantName());
                    if (Assert.isNotNull(eventRecordNotice.getEventTermId())) {
                        countParticipantDto.setBQStartDate((commonDao.queryById(EventTerm.class, eventRecordNotice.getEventTermId())).getStartDate());
                    }
                    if(Assert.isNull(eventRecordNotice.getComment())){
                        countParticipantDto.setComment("无");
                    }else{
                        countParticipantDto.setComment(eventRecordNotice.getComment());
                    }

                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListCountParticipantByIdList, e);
        }
        return countParticipants;
    }

    @Override
    public int countParticipantById(int eventRecordNoticeId) throws SSException {
        try {
            return processingEventMapper.countParticipantById(eventRecordNoticeId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountParticipantByIdFail, e);
        }
    }

    @Override
    public List<EventTerm> listByClientId(int eventRecordNoticeId, int clientId, int offset, int pageSize) throws SSException {
        List<EventTerm> eventTerms = new ArrayList<EventTerm>();
        try {
            if (Assert.isNotNull(clientId) && Assert.isNotNull(offset) && Assert.isNotNull(pageSize)) {
                eventTerms = processingEventMapper.listByClientId(clientId, offset, pageSize);

                //迭代器 删除该页显示的活动记录通知
                Iterator<EventTerm> iterator = eventTerms.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getId() == eventRecordNoticeId) {
                        iterator.remove();
                    }
                }

                for (EventTerm eventTerm : eventTerms) {
                    setExtraValue(eventTerm);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListEventRecordNoticeByIdList, e);
        }
        return eventTerms;
    }

    @Override
    public int countByClientId(int eventRecordNoticeId, int clientId) throws SSException {
        try {
            return processingEventMapper.countByClientId(clientId) - 1;//减去该页显示的一条信息
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountByClientIdFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void handleNotice(int eventRecordNoticeId, int status, Date date, int checkItemId, String result,
                             int partyId) throws SSException {
        EventRecordNotice eventRecordNotice = new EventRecordNotice();
        try {
            //更新活动记录状态
            if (Assert.isNotNull(eventRecordNoticeId)) {
                int eventParticipantTimes = eventRecordNoticeService.queryById(eventRecordNoticeId).getEventParticipantTimes();
                processingEventMapper.updateParticipantStatusById(eventRecordNoticeId, status, eventParticipantTimes);
            }
            //新增检查项记录结果信息
            if (Assert.isNotNull(checkItemId)) {
                CheckItemResult checkItemResult = new CheckItemResult();
                checkItemResult.setEventRecordNoticeId(eventRecordNoticeId);
                checkItemResult.setCheckItemId(checkItemId);
                checkItemResult.setCheckResult(result);
                checkItemResult.setCreatedPartyId(partyId);
                commonDao.insert(checkItemResult);

            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.HandleNoticeFail, e);
        }
    }

    @Override
    public List<EventTerm> queryByClientId(int clientId, int offset, int pageSize) throws SSException {
        List<EventTerm> eventTerms = Collections.emptyList();
        try {
            if (Assert.isNull(clientId)) {
                throw SSException.get(CrmException.ClientIdNotNull);
            }
            eventTerms = processingEventMapper.queryByClientId(clientId, offset, pageSize);
            if (Assert.isNotNull(eventTerms)) {
                for (EventTerm eventTerm : eventTerms) {
                    setExtraValue(eventTerm);
                }
            }
            return eventTerms;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.QueryEventTermByClientIdFail, e);
        }
    }

    @Override
    public int countEventTermByClientId(int clientId) throws SSException {
        try {
            if (Assert.isNull(clientId)) {
                throw SSException.get(CrmException.ClientIdNotNull);
            }
            return processingEventMapper.countEventTermByClientId(clientId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountEventTermByClientIdFail, e);
        }
    }

    @Override
    public int countToBeNoticedPerson(int eventTermId) throws SSException {
        try {
            return processingEventMapper.countToBeNoticedPerson(eventTermId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountEventTermByClientIdFail, e);
        }
    }


    @Override
    public int countToBeFinishedPerson(int eventTermId) throws SSException {
        try {
            return processingEventMapper.countToBeFinishedPerson(eventTermId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountToBeFinishedPersonFail, e);
        }
    }

    @Override
    public int totalPointsItemAndCheckItem(int eventId) throws SSException {
        try {
            return processingEventMapper.countCheckItemByEventId(eventId) + processingEventMapper.countPointsItemByEventId(eventId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountToBeFinishedPersonFail, e);
        }
    }

    @Override
    public List<Event> listEventByClientId(int clientId, int offset, int pageSize) throws SSException {
        List<Event> events = Collections.emptyList();
        try {
            events = processingEventMapper.listEventByClientId(clientId, offset, pageSize);
            for (Event event : events) {
                setEventExtraValue(event);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListEventByClientIdFail, e);
        }
        return events;
    }

    @Override
    public int countEventByClientId(int clientId) throws SSException {
        try {
            return processingEventMapper.countEventByClientId(clientId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountEventByClientIdFail, e);
        }
    }

    @Override
    public int countAllParticipant(int eventRecordNoticeId) throws SSException{
        int result = 0;
        try{
            EventRecordNotice eventRecordNotice = commonDao.queryById(EventRecordNotice.class,eventRecordNoticeId);
            result =  processingEventMapper.countAllParticipant(eventRecordNotice.getEventId(),eventRecordNotice.getClientId());
            return result;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.CountAllParticipantFail, e);
        }
    }

    @Override
    public List<CountParticipantDto> allParticipant(int eventRecordNoticeId) throws SSException{
        List<CountParticipantDto> countParticipantDtos = new ArrayList<CountParticipantDto>();
        try{
            EventRecordNotice eventRecordNotice = new EventRecordNotice();
            eventRecordNotice = commonDao.queryById(EventRecordNotice.class,eventRecordNoticeId);
            int eventId = eventRecordNotice.getEventId();
            int clientId = eventRecordNotice.getClientId();
            if(Assert.isNotNull(eventId) && Assert.isNotNull(clientId)){
                List<Integer> eventRecordNoticeIds = processingEventMapper.listEventNoticeIdByEventId(eventId,clientId);
                for(Integer id : eventRecordNoticeIds){
                    countParticipantDtos.addAll(processingEventMapper.listAllBQParticipantById(id));
                }
            }
            for(CountParticipantDto countParticipantDto : countParticipantDtos){
                if(Assert.isNotNull(eventRecordNotice.getParticipantName())){
                    countParticipantDto.setName(eventRecordNotice.getParticipantName());
                }
                if (Assert.isNotNull(eventRecordNotice.getEventTermId())) {
                    countParticipantDto.setBQStartDate((commonDao.queryById(EventTerm.class, eventRecordNotice.getEventTermId())).getStartDate());
                }
                countParticipantDto.setName(eventRecordNotice.getParticipantName());
                if(Assert.isNull(countParticipantDto.getComment())){
                    countParticipantDto.setComment("无");
                }
            }
            return countParticipantDtos;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.AllParticipantFail, e);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void autoNextEventTerm() throws SSException{
        try{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            //获取所有执行期的活动
            List<EventTerm> doingEventList = eventTermService.listAllByStatus(EventTermStatusEnums.Doing.getId());
            //获取所有暂停一期的活动
            List<EventTerm> pauseEventList = eventTermService.listAllByStatus(EventTermStatusEnums.Pause.getId());
            for (EventTerm eventTerm : doingEventList){
                //获取本期的活动的结束时间
                Date endDate = eventTerm.getEndDate();

                Calendar cal = Calendar.getInstance();
                cal.setTime(endDate);
                cal.add(Calendar.DATE, 1);
                //加一天之后的时间
                Date last = cal.getTime();
                String sdfLast = sdf.format(last);
                Date sdfLastDate = sdf.parse(sdfLast);
                //获取今天的时间
                Date today = new Date();
                String sdfToday = sdf.format(today);
                Date sdfTodayDate = sdf.parse(sdfToday);
                if (sdfTodayDate.compareTo(sdfLastDate) == 0){
                    //处理每期活动记录表
                    EventTerm eventTerm1 = new EventTerm(); //修改当前期活动的状态为已归档
                    eventTerm1.setId(eventTerm.getId());
                    eventTerm1.setStatus(EventTermStatusEnums.Filed.getId());
                    commonDao.update(eventTerm1);

                    //同时生成下一期活动记录
                    //只处理会员关怀型活动
                    Event event = eventService.queryById(eventTerm.getEventId());
                    if (event.getType() == EventTypeEnums.Member.getId()){
                        EventTerm eventTerm2 = new EventTerm(); //生成下一期的活动记录
                        Calendar cal2 = Calendar.getInstance();
                        //根据活动的循环粒度设置下一期的结束时间
                        if (event.getPollingTime() == 1){
                            //活动粒度为年
                            cal2.setTime(today);
                            cal2.add(Calendar.YEAR, 1); //时间加一年
                            cal2.add(Calendar.DATE, - 1);
                            Date endYear = cal2.getTime();
                            if (endYear.compareTo(event.getEndDate()) >= 0){
                                eventTerm2.setEndDate(event.getEndDate());
                            } else {
                                eventTerm2.setEndDate(endYear);
                            }
                        } else {
                            //活动粒度为月
                            cal2.setTime(today);
                            cal2.add(Calendar.MONTH, 1); //加一个月
                            cal.add(Calendar.DATE, - 1); //日期减一天
                            Date lastEndDate = cal2.getTime();
                            if (lastEndDate.compareTo(event.getEndDate()) >= 0 ){
                                eventTerm2.setEndDate(event.getEndDate());
                            } else {
                                eventTerm2.setEndDate(lastEndDate);
                            }
                        }
                        eventTerm2.setEventId(eventTerm.getEventId());
                        eventTerm2.setStartDate(today);
                        eventTerm2.setStatus(EventTermStatusEnums.Noticing.getId());
                        eventTerm2.setMemberGroupId(event.getMemberGroupId());
                        EventTerm eventTerm3 = commonDao.insert(eventTerm2); //插入数据库

                        //同时生成下一期的活动记录通知
                        if (Assert.isNotNull(eventTerm3)){
                            List<Client> clientList = clientService.listByMemberGroupId(eventTerm3.getMemberGroupId());
                            for (Client client : clientList) {
                                //操作活动记录通知表
                                EventRecordNotice eventRecordNotice = new EventRecordNotice();
                                eventRecordNotice.setEventId(eventTerm3.getEventId());
                                eventRecordNotice.setEventStartDate(eventTerm3.getStartDate());
                                eventRecordNotice.setEventEndDate(eventTerm3.getEndDate());
                                eventRecordNotice.setMemberGroupId(eventTerm3.getMemberGroupId());
                                eventRecordNotice.setClientId(client.getId());
                                eventRecordNotice.setParticipantName(client.getName());
                                eventRecordNotice.setParticipantIdcard(client.getIdCardNum());
                                eventRecordNotice.setParticipantTel(client.getTel());
                                eventRecordNotice.setNoticeStatus(NoticeStatusEnums.NotNotified.getId());
                                eventRecordNotice.setEventParticipantTimes(0);
                                eventRecordNotice.setEventParticipantStatus(0);
                                eventRecordNotice.setEventStatus(EventStatusEnums.NotNotice.getId());
                                eventRecordNotice.setEventTermId(eventTerm3.getId());
                                eventRecordNoticeService.newEventRecordNotice(eventRecordNotice);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.AllParticipantFail, e);
        }

    }

    @Override
    public List<EventTerm> listByGroupId(int groupId) throws SSException{
        try{
            if(Assert.isNull(groupId)){
                throw SSException.get(CrmException.MemberGroupIdsNull);
            }
            return processingEventMapper.listByGroupId(groupId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListGroupIdFail, e);
        }

    }

    @Override
    public List<Employee> listTop20EmployeeByName(String name) throws Exception{
        try {
            return processingEventMapper.listTop20EmployeeByName(name);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListTop20EmployeeByNameFail, e);
        }
    }

    @Override
    public List<Employee> listTop20EmployeeByIdcard(String idcardNum) throws Exception{
        try {
            return processingEventMapper.listTop20EmployeeByIdcard(idcardNum);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.ListTop20EmployeeByIdcardFail, e);
        }
    }


    /*****************************私有方法*************************************/

    /**
     * 设置枚举值的value
     *
     * @param eventTerm
     * @throws Exception
     */

    private void setExtraValue(EventTerm eventTerm) throws Exception {
        try {
            if (Assert.isNotNull(eventTerm)) {
                if (Assert.isNotNull(eventTerm.getLevel())) {
                    eventTerm.setLevelValue(EventLevelEnums.valueOf(eventTerm.getLevel()).getLevel());
                }else {
                    eventTerm.setLevelValue("无");
                }
                if (Assert.isNotNull(eventTerm.getType())) {
                    eventTerm.setTypeValue(EventTypeEnums.valueOf(eventTerm.getType()).getType());
                }else{
                    eventTerm.setTypeValue("无");
                }
                if(Assert.isNotNull(eventTerm.getMemberGroupId())){
                    eventTerm.setMemberGroupName(memberGroupService.queryById(eventTerm.getMemberGroupId()).getName());
                }else{
                    eventTerm.setMemberGroupName("无");
                }
                if (Assert.isNotNull(eventTerm.getStatus())) {
                    eventTerm.setStatusValue(EventTermStatusEnums.valueOf(eventTerm.getStatus()).getStatus());
                }
                if(Assert.isNull(eventTerm.getNoticeContent())){
                    eventTerm.setNoticeContent("无");
                }
                if(Assert.isNull(eventTerm.getCreatedPartyName())){
                    eventTerm.setCreatedPartyName("无");
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.HandleNoticeFail, e);
        }
    }


    /**
     * 活动service私有方法
     * 处理枚举字段的value值
     *
     * @param event
     * @throws Exception
     */
    private void setEventExtraValue(Event event) throws Exception {
        try {
            if (Assert.isNotNull(event)) {
                if (Assert.isNotNull(event.getLevel())) {
                    event.setLevelValue(EventLevelEnums.valueOf(event.getLevel()).getLevel());
                }
                if (Assert.isNotNull(event.getType())) {
                    event.setTypeValue(EventTypeEnums.valueOf(event.getType()).getType());
                }
                if (Assert.isNotNull(event.getMemberGroupId())) {
                    event.setMemberGroupName(memberGroupService.queryById(event.getMemberGroupId()).getName());
                }
                if (Assert.isNull(event.getCreatedPartyName())) {
                    event.setCreatedPartyName("无");
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.SetExtraDataFail, e);
        }

    }

    /**
     * 查询下一期活动是否存在
     * @param eventId 活动id
     * @param flag 查询标识 next:下一期；all:全部
     * @return
     * @throws SSException
     */
    public int countNextTerm(Integer eventId,String flag) throws SSException {
        try {
            return processingEventMapper.countNextTerm(eventId,flag);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.PauseTermFail, e);
        }
    }

    /**
     * 暂停分期活动
     *
     * @param eventId 活动id
     * @param flag 查询标识 next:下一期；all:全部
     * @throws SSException
     */
    public void pauseNextTerm(Integer eventId,String flag) throws SSException{
        try {
            processingEventMapper.pauseNextTerm(eventId,flag);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(CrmException.PauseTermFail, e);
        }
    }
}
