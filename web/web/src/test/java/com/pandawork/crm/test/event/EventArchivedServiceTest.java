package com.pandawork.crm.test.event;

import com.pandawork.crm.common.dto.event.archived.EventArchivedSearchDto;
import com.pandawork.crm.common.utils.DateUtils;
import com.pandawork.crm.service.client.basic.ClientService;
import com.pandawork.crm.service.event.archived.impl.EventArchivedServiceImpl;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * EventArchivedServiceTest
 * Author： csy
 * Date: 2017/7/27
 * Time: 8:38
 */
public class EventArchivedServiceTest extends AbstractTestCase {

    @Autowired
    private EventArchivedServiceImpl eventArchivedService;

    @Autowired
    private ClientService clientService;

    /**
     * 根据Dto获取已归档活动信息详情
     *
     * @throws Exception
     */
    @Test
    public void queryEventArchivedByEventDtoTest() throws Exception{

        EventArchivedSearchDto eventArchivedSearchDto = new EventArchivedSearchDto();

        eventArchivedSearchDto.setName("测");
//        eventArchivedSearchDto.setIdcardNum("1994");
//        eventArchivedSearchDto.setEventName("端午");
//        eventArchivedSearchDto.setEventType(2);
//        eventArchivedSearchDto.setEventLevel(2);


        eventArchivedService.queryEventArchivedByEventDto(eventArchivedSearchDto);

        System.out.println(eventArchivedService.queryEventArchivedByEventDto(eventArchivedSearchDto).size());
    }

    /**
     * 根据活动id获取活动详情信息
     *
     * @throws Exception
     */
    @Test
    public void queryEventArchivedByIdTest() throws Exception{
        eventArchivedService.queryEventArchivedById(2,0, 10);
        System.out.println("又成功啦~~");
    }

//    /**
//     * 根据患者姓名模糊查询活动记录信息
//     *
//     * @throws Exception
//     */
//    @Test
//    public void queryByNameAndIdcardNumTest() throws Exception{
//        eventArchivedService.queryByNameAndIdcardNum("李","10620");
//        System.out.println("又又成功啦~~~");
//    }

    @Test
    public void listEventRecordNoticeBySearchDtoTest() throws Exception{
        EventArchivedSearchDto eventArchivedSearchDto = new EventArchivedSearchDto();
        eventArchivedSearchDto.setName("测");
        eventArchivedService.listEventRecordNoticeBySearchDto(eventArchivedSearchDto);
        System.out.println("year~");
    }

    @Test
    public void countCurrentParticipation() throws Exception{
        int eventNoticeId = 1;
        System.out.println(eventArchivedService.countCurrentParticipation(eventNoticeId));

    }

    @Test
    public void countCumulativeParticipationTest() throws Exception{
        int eventId = 1;
        System.out.println(eventArchivedService.countCumulativeParticipationWithoutArchived(eventId));
    }

//    @Test
//    public void deleteRepeatBySearchDtoTest() throws Exception{
//        EventArchivedSearchDto eventArchivedSearchDto = new EventArchivedSearchDto();
//        eventArchivedSearchDto.setName("");
//        eventArchivedService.deleteRepeatBySearchDto(eventArchivedSearchDto);
//        System.out.println("ccc");
//    }

    @Test
    public void aheadDay()throws Exception{
        String str = "2017-07-31";
        Date date = DateUtils.formatDateSimpleAnother(str);
        String day = DateUtils.formatDateSimple(DateUtils.aheadDay(date,0));
        System.out.println(day);
    }

    @Test
    public void dateUntils()throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.aheadDay(new Date(),1));
        //calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String firstDay = format.format(calendar.getTime());
        System.out.println(firstDay);
    }

    @Test
    public void dateLastMonth() throws  Exception {
         // 获取本月第一天
        String date = DateUtils.getCurrentMonthFirstDay();
        for (int i = 0; i <= 4; i++) {
            for (int j = 30; j >= 0; j--) {
                //当前日期往前一天
                Date dd = DateUtils.formatDateSimpleAnother(date);
                Date day = DateUtils.aheadDay(dd, 1);
                date = DateUtils.formatDateSimple(day);
                System.out.println(DateUtils.formatDate(day));
            }
        }

        String lastMonthLastDay = DateUtils.getLastMonthLastDay();
        String lastMonthFirstDay = DateUtils.getLastMonthFirstDay();
        for(int i = 0 ; i <= 5 ; i++ ){
            lastMonthLastDay = DateUtils.formatDate(DateUtils.aheadDay(DateUtils.formatDateSimpleAnother(lastMonthLastDay),30));
            lastMonthFirstDay = DateUtils.formatDate(DateUtils.aheadDay(DateUtils.formatDateSimpleAnother(lastMonthFirstDay),30));
            System.out.println(lastMonthLastDay);
            System.out.println(lastMonthFirstDay);
        }


    }

    @Test
    public void queryEventTermByEventIdTest()throws Exception{
        int eventId = 1;
        eventArchivedService.queryEventTermByEventId(eventId);
        System.out.println("444");
    }

//    @Test
//    public void listEventArchivedByDto() throws Exception{
//
//        try{
//
//    }

    @Test
    public void sss() throws Exception{


    }

}
