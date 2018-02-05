package com.pandawork.crm.test.event;

import com.pandawork.crm.service.event.EventTermService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * EventTermTest
 * Author： wychen
 * Date: 2017/8/10
 * Time: 14:27
 */
public class EventTermTest extends AbstractTestCase {

    @Autowired
    private EventTermService eventTermService;

    @Test
    public void searchTOP20ByName() throws Exception{
        String name = "活动";
        try{
            eventTermService.searchTOP20ByName(name);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Test
    public void autoUpdateEventTermStatus() throws Exception{
        eventTermService.autoUpdateEventTermStatus();
    }
}
