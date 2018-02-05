package com.pandawork.crm.test.event;

import com.pandawork.crm.common.dto.event.PrepareNoticeSearchDto;
import com.pandawork.crm.service.event.prepareNotice.PrepareNoticeService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * PrepareNoticeTest
 *
 * @author Daydreamer
 * @date 2017/8/1 10:12
 */
public class PrepareNoticeTest extends AbstractTestCase {

    @Autowired
    private PrepareNoticeService prepareNoticeService;
//
//    @Test
//    public void listAll() throws Exception{
//        try {
//            prepareNoticeService.listAll();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    @Test
    public void listBySearchDto() throws Exception{
        PrepareNoticeSearchDto prepareNoticeSearchDto = new PrepareNoticeSearchDto();
        prepareNoticeSearchDto.setPageNo(0);
        prepareNoticeSearchDto.setPageSize(10);
        prepareNoticeSearchDto.setLevel(2);
        try {
            prepareNoticeService.listBySearchDto(prepareNoticeSearchDto);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public  void countAll() throws Exception{
        try {
            int count = prepareNoticeService.countAll();
            System.out.println(count);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void countPrepareNotice() throws Exception{
        PrepareNoticeSearchDto prepareNoticeSearchDto = new PrepareNoticeSearchDto();
        prepareNoticeSearchDto.setPageNo(0);
        prepareNoticeSearchDto.setLevel(2);
        try {
            int count = prepareNoticeService.countPrepareNotice(prepareNoticeSearchDto);
            System.out.println(count);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
