package com.pandawork.crm.test.party.dictionary;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.crm.common.dto.dictionary.DictionaryDto;
import com.pandawork.crm.common.entity.party.dictionary.Dictionary;
import com.pandawork.crm.common.enums.party.dictionary.DictionaryEnums;
import com.pandawork.crm.service.party.dictionary.DictionaryService;
import com.pandawork.crm.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * 字典值管理测试类
 * Created by shura on 2017/7/18.
 */
public class DictionaryServiceTest extends AbstractTestCase{

    @Autowired
    private DictionaryService dictionaryService;

    @Test
    public void newDictionary() throws SSException{
        Dictionary dictionary = new Dictionary();
        dictionary.setName("子节点1");
        dictionary.setParentId(1);
        dictionary.setCreatedPartyId(1);
        dictionary.setDeleted(0);
        dictionaryService.newDictionary(dictionary);
    }
    @Test
    public void queryById() throws SSException{
        Dictionary dictionary = dictionaryService.queryById(1);
        System.out.println(dictionary.getName());
    }

    @Test
    public void listByPId() throws SSException{
        List<Dictionary> dictionaryList = dictionaryService.listByPId(0);
        for(Dictionary dictionary : dictionaryList){
            System.out.println(dictionary.getName());
        }
    }

    @Test
    public void delDictionary() throws SSException{
        dictionaryService.delDictionary(6);
    }


    @Test
    public void updateDictionary( ) throws SSException{
        Dictionary dictionary = new Dictionary();
        dictionary.setId(2);
        dictionary.setName("测试数据1");
        dictionary.setParentId(0);
        dictionary.setCreatedPartyId(1);
        dictionary.setDeleted(0);
        dictionaryService.updateDictionary(dictionary);
    }

    @Test
    public void queryDictionaryByPIdAndName() throws SSException{
        int pid = 0;
        String name = "测试数据";
        Dictionary dictionary = dictionaryService.queryDictionaryByPIdAndName(pid,name);
        System.out.println(dictionary.getName());

    }

    @Test
    public void isParent() throws SSException{
        if(dictionaryService.isParent(0)){
            System.out.println("是父类节点");
        }else{
            System.out.println("不是父类节点");
        }
    }

    @Test
    public void listAll() throws SSException{
        List<DictionaryDto> dictionaryDtoList = dictionaryService.listAll();
    }

    @Test
    public void listForSearch()throws SSException{
        String s = "";
        List<Dictionary> dictionaryList = dictionaryService.listForSearch(s, DictionaryEnums.DIC_DOSAGE.getId());
        for(Dictionary dictionary : dictionaryList){
            System.out.println(dictionary.getName());
        }
    }

    @Test
    public  void listAllDictionary()throws SSException{
        List<Dictionary> dictionaryList =dictionaryService.listAllDictionary();
        for(Dictionary dictionary : dictionaryList){
            System.out.println(dictionary.getName());
        }
    }
}
