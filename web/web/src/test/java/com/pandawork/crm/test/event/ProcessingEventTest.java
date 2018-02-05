package com.pandawork.crm.test.event;

import com.pandawork.crm.common.dto.event.EventSearchDto;
import com.pandawork.crm.common.entity.event.EventTerm;
import com.pandawork.crm.service.event.processing.ProcessingEventService;
import com.pandawork.crm.test.AbstractTestCase;
import junit.framework.TestResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ProcessingEventTest
 * Author： wychen
 * Date: 2017/7/28
 * Time: 20:39
 */
public class ProcessingEventTest extends AbstractTestCase {

    @Autowired
    private ProcessingEventService processingEventService;

    @Test
    public void listProcessingEventBySearchDto()throws Exception{
        EventSearchDto eventSearchDto = new EventSearchDto();
        eventSearchDto.setPageSize(10);
        eventSearchDto.setOffset(0);
        eventSearchDto.setLevel(1);
        eventSearchDto.setName("test001");
        try {
            List<EventTerm> events = processingEventService.listProcessingEventBySearchDto(eventSearchDto);
            System.out.println(events);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void countProcessingEventBySearchDto() throws Exception{
        EventSearchDto eventSearchDto = new EventSearchDto();
        eventSearchDto.setPageSize(10);
        eventSearchDto.setOffset(0);
        eventSearchDto.setName("活动");
        try {
            processingEventService.countProcessingEventBySearchDto(eventSearchDto);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void pauseTerm() throws Exception{
        int eventTermId = 1;
        try{
            processingEventService.pauseTerm(eventTermId);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void pauseEvent() throws Exception{
        int eventId = 1;
        try{
            processingEventService.pauseEvent(eventId);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void listByEventTermId() throws Exception{
        int offset = 0;
        int pageSize = 10;
        int eventTermId = 1;
        try{
            processingEventService.listByEventTermId(eventTermId,offset,pageSize);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void queryById() throws Exception{
        int id = 1;
        try{
           processingEventService.queryById(id);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void batchNotice() throws Exception{
        List<Integer> idList = new ArrayList<Integer>();
        idList.add(1);
        idList.add(2);
        int status = 1;
        String comment = "备注。。。";
        try{
            processingEventService.batchNotice(idList,status,comment);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void batchPointsItems() throws Exception{
        int eventTermId = 1;
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        ids.add(2);
        int pointsItem = 1;
        int partyId = 1;
        try{
            processingEventService.batchAddPointsItems(eventTermId,ids,pointsItem,partyId);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void listCountBQParticipantById() throws Exception{
        int eventRecordNoticeId = 1;
        int offset = 0;
        int pageSize = 10;
        try{
           processingEventService.listCountBQParticipantById(eventRecordNoticeId,offset,pageSize);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void listByClientId()throws Exception{
        int eventRecordNoticeId = 1;
        int clientId = 1;
        int offset = 0;
        int pageSize = 10;
        try{
            processingEventService.listByClientId(eventRecordNoticeId,clientId,offset,pageSize);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void countByClientId()throws Exception{
        try{
            processingEventService.countByClientId(1,1);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void handleNotice() throws Exception{
        try{
            processingEventService.handleNotice(1,1,new Date(),1,"result",1);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void queryByClientId() throws Exception{
        int clientId = 1, offset = 0, pageSize = 10;
        try{
            processingEventService.queryByClientId(clientId,offset,pageSize);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void countEventTermByClientId() throws Exception{
        int clientId = 1;
        try {
            processingEventService.countEventTermByClientId(clientId);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void totalPointsItemAndCheckItem() throws Exception{
        int eventId = 1;
        try {
            processingEventService.totalPointsItemAndCheckItem(eventId);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void listEventByClientId() throws Exception{
        int clientId = 1, offset = 0, pageSize = 10;
        try {
            processingEventService.listEventByClientId(clientId,offset,pageSize);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void countEventByClientId() throws Exception{
        int clientId = 1;
        try{
            processingEventService.countEventTermByClientId(clientId);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void countParticipantById() throws Exception{
        int eventRecordNoticeId = 1;
        try{
            processingEventService.countParticipantById(eventRecordNoticeId);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void allParticipant() throws Exception{
        int eventRecordNoticeId = 1;
        try{
            processingEventService.allParticipant(eventRecordNoticeId);
        }catch (Exception e){
            throw new Exception(e);
        }
    }


    @Test
    public void countAllParticipant() throws Exception{
        int eventRecordNoticeId = 1;
        try{
            processingEventService.countAllParticipant(eventRecordNoticeId);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void listByGroupId() throws Exception{
        int groupId = 1;
        try{
            processingEventService.listByGroupId(groupId);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void listTop20EmployeeByName() throws Exception{
        String name = "sd";
        try {
            processingEventService.listTop20EmployeeByName(name);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void listTop20EmployeeByIdcard() throws Exception{
        String idcard = "12";
        try {
            processingEventService.listTop20EmployeeByName(idcard);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

//    @Test
//    public void listAllBQParticipantById() throws Exception{
//        int id = 1;
//        try{
//            processingEventService.listAllBQParticipantById();
//        }catch (Exception e){
//            throw new Exception(e);
//        }
//    }
}
