package com.pandawork.crm.test.event;

import com.pandawork.crm.common.entity.event.CheckItem;
import com.pandawork.crm.service.event.CheckItemService;
import com.pandawork.crm.test.AbstractTestCase;
import org.apache.xpath.SourceTree;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * CheckItemTest
 * Author： wychen
 * Date: 2017/7/21
 * Time: 8:12
 */
public class CheckItemTest extends AbstractTestCase{

    @Autowired
    private CheckItemService checkItemService;

    @Test
    public void listAll()throws Exception{
        List<CheckItem> checkItems = Collections.emptyList();
        try{
            checkItems = checkItemService.listAll();
            for(CheckItem checkItem : checkItems){
                System.out.println(checkItem);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Test
    public void listByEventId()throws Exception{
        List<CheckItem> checkItems = Collections.emptyList();
        int eventId = 1;
        try{
            checkItems = checkItemService.listByEventId(eventId);
            for(CheckItem checkItem : checkItems){
                System.out.println(checkItem);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Test
    public void newCheckItem() throws Exception{
        CheckItem checkItem = new CheckItem();
        checkItem.setName("心脏病");
        checkItem.setContent("心电图");
        checkItem.setCreatedPartyId(12);
        checkItem.setEventId(1);
        try{
            checkItem = checkItemService.newCheckItem(checkItem);
            System.out.println(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Test
    public void checkExistByName() throws Exception{
        String name = "心脏病";
        Boolean result = false;
        try {
            result = checkItemService.checkExistByName(name);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Test
    public void deleteByEventId() throws Exception{
        int eventId = 22;
        try{
            checkItemService.deleteByEventId(eventId);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
