package com.pandawork.crm.test.event;

import com.pandawork.core.common.entity.AbstractEntity;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.service.event.PointsItemService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * PointsItemTest
 *
 * @author Flying
 * @date 2017/8/5 11:50
 */
public class PointsItemTest extends AbstractTestCase {

    @Autowired
    private PointsItemService pointsItemService;

    @Test
    public void delByEventId() throws Exception{
        int eventId = 2;
        pointsItemService.delByEventId(eventId);
    }
}
