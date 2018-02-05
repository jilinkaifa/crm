package com.pandawork.crm.test.client.points;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.client.points.PointsSearchDto;
import com.pandawork.crm.common.entity.client.points.Points;
import com.pandawork.crm.common.utils.DateUtils;
import com.pandawork.crm.mapper.client.points.PointsMapper;
import com.pandawork.crm.service.client.points.PointsService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * PointsServiceTest
 * Author： liping
 * Date: 2017/7/31
 * Time: 17:18
 */
public class PointsServiceTest extends AbstractTestCase{
    @Autowired
    private PointsService pointsService;

    @Autowired
    private PointsMapper pointsMapper;

    @Test
    public void listByPointsSearchDto() throws SSException {
        PointsSearchDto searchDto = new PointsSearchDto();
        List<Points> pointsList =new ArrayList<Points>();
        pointsList = pointsService.listByPointsSearchDto(searchDto);
        for (Points points:pointsList) {
            System.out.println(points);
        }
    }
    @Test
    public void listByClientId() throws SSException{
        PointsSearchDto searchDto = new PointsSearchDto();
        searchDto.setClientId(2);
        //searchDto.setEventName("中秋节活动的升级版");
        List<Points> pointsList = new ArrayList<Points>();
        pointsList = pointsService.listByClientId(searchDto);
        for (Points points:pointsList) {
            System.out.println(points);
        }
    }
    @Test
    public void count() throws SSException{
        PointsSearchDto searchDto = new PointsSearchDto();
        Date date1 = null;
        Date date2 = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date1 = sdf.parse("2017-07-01");
            date2 = sdf.parse("2017-08-01");
            searchDto.setStartDate(date1);
            searchDto.setEndDate(date2);
            System.out.println(pointsService.count(searchDto));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void countDeatil() throws SSException{
        PointsSearchDto searchDto = new PointsSearchDto();
        searchDto.setClientId(1);
        //searchDto.setEventName("中秋节活动的升级版");
        //searchDto.setPointsFrom(1);
        //Date date1 = null;
        //Date date2 = null;
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //try {
            //date1 = sdf.parse("2017-07-01");
            //date2 = sdf.parse("2017-08-01");
            //searchDto.setStartDate(date1);
            //searchDto.setEndDate(date2);
            System.out.println(pointsService.countDetail(searchDto));
        //} catch (ParseException e) {
            //e.printStackTrace();
        //}
    }

    @Test
    public void listAfterUpdate()throws Exception{
        Date date = pointsService.queryByVisit(23).getCreatedTime();
        String daytime = DateUtils.formatDate(date);
        List<Points> pointsList = pointsMapper.listAfterUpdate(18,daytime);
        for(Points points : pointsList){
            System.out.println(points.getPointsSize());
        }
    }

    @Test
    public void getLastPointsByClientId()throws Exception{
        Points point = pointsService.getLastPointsByClientId(18);
        System.out.println(point.getPointsSize());
    }

}
