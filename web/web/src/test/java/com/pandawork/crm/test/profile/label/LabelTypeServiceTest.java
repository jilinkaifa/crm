package com.pandawork.crm.test.profile.label;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.entity.profile.label.LabelType;
import com.pandawork.crm.service.profile.label.LabelTypeService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * LabelTypeServiceTest
 * Author： Daydreamer
 * Date: 2017/7/19
 * Time: 13:51
 */
public class LabelTypeServiceTest extends AbstractTestCase {

    @Autowired
    private LabelTypeService labelTypeService;

    @Test
    public void newLabelType() throws SSException{
        LabelType labelType = new LabelType();
        labelType.setName("民");
        labelType.setIsMultiple(0);
        labelType.setLabelCount(56);
        labelType.setLabelText("民族");
        labelType.setLabelComment("民族");
        labelType.setCreatedPartyId(1);
        labelType.setCreatedTime(new Date());
        labelType.setLastModifiedTime(new Date());
        labelType.setDeleted(0);
        try{
            labelTypeService.newLabelType(labelType);
        }catch (SSException e){
            e.printStackTrace();
        }
    }

    @Test
    public void listAll() throws SSException{
        List<LabelType> labelTypes = labelTypeService.listAll();
        for (LabelType labelType : labelTypes){
            System.out.println(labelType.getName());
        }
    }

    @Test
    public void delById() throws SSException{
        try{
            labelTypeService.delById(1);
        }catch (SSException e){
            e.printStackTrace();
        }
    }

    @Test
    public void updateLabelType() throws SSException{
        LabelType labelType = new LabelType();
        labelType.setId(1);
        labelType.setName("民族a");
        labelType.setIsMultiple(0);
        labelType.setLabelCount(56);
        labelType.setLabelText("民族");
        labelType.setLabelComment("民族");
        labelType.setCreatedPartyId(1);
        labelType.setCreatedTime(new Date());
        labelType.setLastModifiedTime(new Date());
        labelType.setDeleted(0);
        try{
            labelTypeService.updateLabelType(labelType);
        }catch (SSException e){
            e.printStackTrace();
        }
    }

    @Test
    public void labelTypeUsed() throws SSException{
            int id = 1;
            try {
                labelTypeService.labelTypeUsed(1);
                if (true){
                    System.out.println("true");
                }else {
                    System.out.println("false");
                }
            }catch (SSException e){
                e.printStackTrace();
            }
    }

    @Test
    public void listByPage() throws SSException{
        int pageNo = 1;
        int pageSize = 10;
        List<LabelType> labelTypes = labelTypeService.listByPage(pageNo, pageSize);
        for (LabelType labelType : labelTypes){
            if (labelTypes.size() == 0){
                System.out.println("没有数据");
            }
//            System.out.println(labelType.getName());
        }
    }

    @Test
    public void queryByKeyWord() throws SSException{
        int pageNo = 1;
        String key = "1";
        List<LabelType> labelTypes = Collections.emptyList();
        labelTypes = labelTypeService.queryByKeyWord(key, pageNo, 10);
        for (LabelType labelType : labelTypes) {
            System.out.println(labelType.getName());
        }
    }

    @Test
    public void count() throws SSException{
        String key = "";
        int num = labelTypeService.count(key);
        System.out.println(num);
    }

    @Test
    public void queryByName() throws Exception{
        String name = "民族";
        LabelType labelType = labelTypeService.queryByName(name);
        System.out.println(labelType);

    }
}
