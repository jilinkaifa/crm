package com.pandawork.crm.test.profile.label;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.profile.label.LabelItem;
import com.pandawork.crm.service.profile.label.LabelItemService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * LabelItemServiceImpl
 *
 * @author Daydreamer
 * @date 2017/7/20 16:53
 */
public class LabelItemServiceTest extends AbstractTestCase {

    @Autowired
    private LabelItemService labelItemService;

    @Test
    public void listByTypeId() throws Exception{
        List<LabelItem> labelItems = labelItemService.listByTypeId(1);
        for (LabelItem labelItem : labelItems){
            System.out.println(labelItem.getDicLabelName());
        }
    }

    @Test
    public void listTextByTypeId() throws Exception{
        List<LabelItem> labelItems = labelItemService.listTextByTypeId(1);
        for (LabelItem labelItem : labelItems){
            System.out.println(labelItem.getDicLabelName());
        }
    }

    @Test
    public void newLabelItem() throws Exception{
        LabelItem labelItem = new LabelItem();
        labelItem.setLabelTypeId(1);
        labelItem.setDicLabelId(1);
        labelItem.setDicLabelName("满族");
        labelItem.setCreatedPartyId("1");
        labelItem.setDeleted(0);
        labelItemService.newLabelItem(labelItem);

    }

    @Test
    public void updateLabelItem() throws Exception{
        LabelItem labelItem = new LabelItem();
        labelItem.setId(1);
        labelItem.setLabelTypeId(2);
        labelItem.setDicLabelId(1);
        labelItem.setDicLabelName("汉族");
        labelItem.setCreatedPartyId("1");
        labelItem.setDeleted(0);
        try {
            labelItemService.updateLabelItem(labelItem);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void delLabelItem() throws Exception{
        try {
            labelItemService.delLabelItem(1);
        }catch (SSException e){
            e.printStackTrace();
        }
    }

    @Test
    public void count() throws Exception{
        int num = labelItemService.count();
        System.out.println(num);
    }

    @Test
    public void countNotDeleted() throws Exception{
        int num = labelItemService.countNotDeleted();
        System.out.println(num);
    }

    @Test
    public void countByTypeId() throws Exception{
        int num = labelItemService.countByTypeId(2);
        System.out.println(num);
    }

    @Test
    public void countNotDeletedByTypeId() throws Exception{
        int num = labelItemService.countNotDeletedByTypeId(2);
        System.out.println(num);
    }

    @Test
    public void listByPage() throws Exception{
        labelItemService.listByPage(1,10,2);
        System.out.println();
    }
}
