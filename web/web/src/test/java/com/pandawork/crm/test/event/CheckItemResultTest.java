package com.pandawork.crm.test.event;

import com.pandawork.crm.common.entity.event.CheckItemResult;
import com.pandawork.crm.service.event.CheckItemResultService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * 查询项结果测试类
 * Author： linjie
 * Date: 2017/7/24
 * Time: 13:45
 */
public class CheckItemResultTest extends AbstractTestCase {
    @Autowired
    private CheckItemResultService checkItemResultService;

    @Test
    public void  newCheckItemResult() throws Exception{
        CheckItemResult checkItemResult = new CheckItemResult();
        checkItemResult.setCheckItemId(1);
        checkItemResult.setEventRecordNoticeId(3);
        checkItemResult.setCheckResult("hello");
        checkItemResult.setCreatedPartyId(1);
        try{
            checkItemResult = checkItemResultService.newCheckItemResult(checkItemResult);
            System.out.println(checkItemResult);
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }
    @Test
    public  void listAll() throws Exception{
        List<CheckItemResult> checkItemResults = Collections.emptyList();
        try{
            checkItemResults = checkItemResultService.listAll();
            for(CheckItemResult checkItemResult : checkItemResults){
                System.out.println(checkItemResult);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }

    }

    @Test
    public void listByEventRecordNoticeId() throws Exception{
        List<CheckItemResult> checkItemResults = Collections.emptyList();
        int eventRecordNoticeId = 1 ;
        try{
            checkItemResults = checkItemResultService.listByEventRecordNoticeId(eventRecordNoticeId);
            for(CheckItemResult checkItemResult : checkItemResults){
                System.out.println(checkItemResult);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }
    @Test
    public void  updateCheckItemResult() throws Exception{
        CheckItemResult checkItemResult = new CheckItemResult();
        checkItemResult.setId(2);
        checkItemResult.setEventRecordNoticeId(3);
        checkItemResult.setCheckItemId(1);
        checkItemResult.setCheckResult("no");
        checkItemResult.setCreatedPartyId(1);
        checkItemResultService.updateCheckItemResult(checkItemResult);
    }

}
