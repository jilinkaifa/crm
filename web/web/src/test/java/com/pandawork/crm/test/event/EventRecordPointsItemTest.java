package com.pandawork.crm.test.event;

import com.pandawork.crm.common.entity.event.EventRecordPointsItem;
import com.pandawork.crm.service.event.EventRecordPointsItemService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * EventRecordPointsItemTest
 * Author： wychen
 * Date: 2017/7/30
 * Time: 15:30
 */
public class EventRecordPointsItemTest extends AbstractTestCase {

    @Autowired
    private EventRecordPointsItemService eventRecordPointsItemService;

    @Test
    public void Item()throws Exception{
        EventRecordPointsItem eventRecordPointsItem = new EventRecordPointsItem();
        eventRecordPointsItem.setPointsItemId(1);
        eventRecordPointsItem.setCreatedPartyId(1);
        eventRecordPointsItem.setEventRecordNoticeId(1);
        try{
           eventRecordPointsItemService.newPointsItem(eventRecordPointsItem);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

}
