package com.pandawork.crm.test.client.visit;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.client.visit.VisitDetailSearchDto;
import com.pandawork.crm.common.dto.client.visit.VisitSearchDto;
import com.pandawork.crm.common.entity.client.visit.PointsConvert;
import com.pandawork.crm.common.entity.client.visit.Visit;
import com.pandawork.crm.common.utils.DateUtils;
import com.pandawork.crm.service.client.visit.VisitService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * VisitServiceImpl
 * Author： liping
 * Date: 2017/7/20
 * Time: 15:48
 */
public class VisitServiceTest extends AbstractTestCase {
    @Autowired
    private VisitService visitService;

    @Test
    public void listByVisitSearchDto() throws SSException{
        VisitSearchDto visitSearchDto = new VisitSearchDto(null,null,null,null,null,10,24,0);
        try {
            List<Visit> list = visitService.listByVisitSearchDto(visitSearchDto);
            System.out.println(list.size());
            for (Visit visit : list) {
                System.out.println(visit.getClientName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void queryByClientId() throws SSException{
        try{
            List<Visit> list = visitService.queryByClientId(1,0,10);
            System.out.println(list.size());
            for (Visit visit:list) {
                System.out.println(visit);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void listByVisitDetailSearchDto() throws SSException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse("2017-01-01");
            date2 = sdf.parse("2018-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        VisitDetailSearchDto searchDto = new VisitDetailSearchDto(1,0,null,date1,date2,0,10);
        try {
            List<Visit> list = visitService.listByVisitDetailSearchDto(searchDto);
            System.out.println(list.size());
            for (Visit visit:list) {
                System.out.println(visit.getVisitFrom());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void delById() throws SSException{
        try{
            visitService.delById(4);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void updateVisit() throws SSException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Visit visit = new Visit();
        visit.setId(1);
        visit.setCost(BigDecimal.valueOf(2000000.0));
        visit.setCreatedPartyId(1);
        try{
            date = sdf.parse("2017-07-24");
            visit.setVisitTime(date);
            visitService.updateVisit(visit);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void queryById() throws SSException{
        try {
            Visit visit = visitService.queryById(1);
            System.out.println(visit);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void newVisit()throws SSException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Visit visit = new Visit();
        visit.setClientId(1);
        visit.setCost(BigDecimal.valueOf(2000.0));
        visit.setVisitFrom(1);
        visit.setCreatedPartyId(1);
        try {
            date = sdf.parse("2017-07-24");
            visit.setVisitTime(date);
            visitService.newVisit(visit);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void count() throws SSException{
        System.out.println(visitService.count());
    }

    @Test
    public void countByVisitDetailSearchDto() throws SSException{
        VisitDetailSearchDto searchDto = new VisitDetailSearchDto();
        searchDto.setClientId(1);
        System.out.println(visitService.countByVisitDetailSearchDto(searchDto));
    }

    @Test
    public void delByClientId() throws SSException{
        visitService.delByClientId(1);
    }

    @Test
    public void getAllCost() throws SSException{
        System.out.println(visitService.getAllCost(1));
    }

    @Test
    public void countCostByDate()throws SSException{
        Date date = DateUtils.aheadDay(new Date(),20);
        String day = "2017-07-10";
        BigDecimal cost =  visitService.countCostByDate(day,1);
        System.out.println(cost);
    }

    @Test
    public void updatePointsConvert()throws SSException{
        PointsConvert pointsConvert = new PointsConvert();
        pointsConvert.setMoneyToPoints(BigDecimal.TEN);
        pointsConvert.setCreatedPartyId(1);
        visitService.updatePointsConvert(pointsConvert);
    }

    @Test
    public void testDateUntil()throws SSException{
        String str = DateUtils.formatDateSimple(DateUtils.aheadDay(new Date(),1));
        System.out.println(str);
    }

    @Test
    public void testEncode()throws SSException{
        String s = Charset.defaultCharset().name();
        System.out.println(s);
    }
}
