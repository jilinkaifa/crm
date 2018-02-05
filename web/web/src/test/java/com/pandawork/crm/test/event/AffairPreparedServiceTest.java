package com.pandawork.crm.test.event;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.client.visit.Visit;
import com.pandawork.crm.common.entity.event.Event;
import com.pandawork.crm.service.event.AffairPreparedService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * AffairPreparedServiceTest
 * Author： linjie
 * Date: 2017/7/29
 * Time: 10:33
 */
public class AffairPreparedServiceTest extends AbstractTestCase {
    @Autowired
    private AffairPreparedService affairPreparedService;

    @Test
    public void listAllToApprovalByPageTest() throws SSException {
        int approvalId = 1;
        int offset = 0;
        int pageSize = 2;
        try {
            List<Event> events = affairPreparedService.listAllToApprovalByPage(approvalId, offset, pageSize);
            affairPreparedService.dateConvert(events);
            for (Event event : events) {
                System.out.println(event.getName());
                System.out.println(event.getCreatedTime());
                System.out.println(event.getDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void listAllRejectedByPageTest() throws SSException {
        int createdPartyId = 1;
        int offset = 0;
        int pageSize = 2;
        try {
            List<Event> events = affairPreparedService.listAllRejectedByPage(createdPartyId, offset, pageSize);
            affairPreparedService.dateConvert(events);
            for (Event event : events) {
                System.out.println(event.getName());
                System.out.println(event.getCreatedTime());
                System.out.println(event.getDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void countRejected() throws SSException {
        int createdPartyId = 1;
        try {
            int count = affairPreparedService.countRejected(createdPartyId);
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void countToApproval() throws SSException {
        int approvalId = 1;
        try {
            int count = affairPreparedService.countToApproval( approvalId );
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listEventByPartyIdListAndStatus() throws Exception{
        List<Integer> list = new ArrayList<Integer>();
        list.add(133);
        List<Event> list1 = affairPreparedService.listEventByPartyIdListAndStatus(list, 1, 0, 10);
        System.out.println(list1.size());
    }
}
