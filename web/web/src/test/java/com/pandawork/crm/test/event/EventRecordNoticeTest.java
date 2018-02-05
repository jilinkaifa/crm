package com.pandawork.crm.test.event;

import com.pandawork.crm.mapper.event.EventRecordNoticeMapper;
import com.pandawork.crm.service.event.EventRecordNoticeService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * EventRecordNoticeTest
 * Author： wychen
 * Date: 2017/8/1
 * Time: 16:02
 */
public class EventRecordNoticeTest extends AbstractTestCase {

    @Autowired
    private EventRecordNoticeService eventRecordNoticeService;

    @Test
    public void queryByEventId()throws Exception{
        int eventId = 1, offset = 0, pageSize = 10;
        String participantName = "王", participantIdcard = "123";
        try{
            eventRecordNoticeService.listByEventTermId(eventId,participantName,participantIdcard,offset,pageSize);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void countByEventId()throws Exception{
        int eventId = 1;
        String participantName = "王", participantIdcard = "123";
        try{
            eventRecordNoticeService.countByEventTermId(eventId,participantName,participantIdcard);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void listNameTop20ByEventTermId() throws Exception{
        int eventTermId = 1;
        String participantName = "王";
        try{
            eventRecordNoticeService.listNameTop20ByEventTermId(eventTermId,participantName);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void listIdcardTop20ByEventTermId() throws Exception{
        int eventTermId = 1;
        String participantIdcard = "123";
        try{
            eventRecordNoticeService.listIdcardTop20ByEventTermId(eventTermId,participantIdcard);
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}
