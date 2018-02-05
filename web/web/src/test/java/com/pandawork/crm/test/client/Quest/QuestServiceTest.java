package com.pandawork.crm.test.client.Quest;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.client.quest.QuestItemSearchDto;
import com.pandawork.crm.common.dto.client.quest.QuestSearchDto;
import com.pandawork.crm.common.entity.client.quest.Quest;
import com.pandawork.crm.common.entity.client.quest.QuestItem;
import com.pandawork.crm.service.client.quest.QuestItemService;
import com.pandawork.crm.service.client.quest.QuestService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * QuestServiceTest
 * Created by shura
 * Date:  2017/7/26.
 * Time:  15:09
 */
public class QuestServiceTest extends AbstractTestCase {

    @Autowired
    private QuestService questService;

    @Autowired
    private QuestItemService questItemService;

    @Test
    public void listQuestItemBySearchDto()throws SSException{
        QuestItemSearchDto searchDto = new QuestItemSearchDto();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.MONTH, 0);
        calendar2.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate =calendar2.getTime();
        Date startDate = calendar.getTime();
        searchDto.setClientId(1);
        searchDto.setStartDate(startDate);
        searchDto.setEndDate(endDate);
        List<QuestItem> questItemList = questItemService.listBySearchDto(searchDto);
        for(QuestItem questItem : questItemList){
            System.out.println(questItem.getDicQuestItemName());
        }
    }

    @Test
    public void listQuestBySearchDto()throws SSException{
        QuestSearchDto searchDto = new QuestSearchDto();
        List<Quest> questList = questService.listBySearchDto(searchDto);
        for(Quest quest : questList){
            System.out.println(quest.getMemberGroupName());
        }
    }

    @Test
    public void countBySearchDto()throws SSException{
        QuestItemSearchDto questItemSearchDto = new QuestItemSearchDto();
        questItemSearchDto.setClientId(1);
        int count  = questItemService.countBySearchDto(questItemSearchDto);
        System.out.println(count);
    }

    @Test
    public void countQuest()throws SSException{
        QuestSearchDto searchDto = new QuestSearchDto();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.MONTH, 0);
        calendar2.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate =calendar2.getTime();
        Date startDate = calendar.getTime();
        searchDto.setStartDate(startDate);
        searchDto.setEndDate(endDate);
        int count  = questService.countBySearchDto(searchDto);
        System.out.println(count);
    }

    @Test
    public void queryByClientId()throws SSException{
        Quest quest =questService.queryByClientId(1);
        System.out.println(quest.getNextQuestTime());
    }

    @Test
    public void countByClientId()throws SSException{
        int count = 0;
        count = questService.countByClientId(1);
        System.out.println(count);
    }

}
