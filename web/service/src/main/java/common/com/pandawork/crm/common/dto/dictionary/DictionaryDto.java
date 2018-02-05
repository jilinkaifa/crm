package com.pandawork.crm.common.dto.dictionary;

import com.pandawork.crm.common.entity.party.dictionary.Dictionary;
import java.util.ArrayList;
import java.util.List;


/**
 * DictionaryDto
 * Author： shura
 * Date: 2017/7/16
 * Time: 16:18
 */
public class DictionaryDto{

    //父类字典值
    private Dictionary dictionary;

    //子类字典值
    private List<DictionaryDto> dictionaryList = new ArrayList<DictionaryDto>();

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public List<DictionaryDto> getDictionaryList() {
        return dictionaryList;
    }

    public void setDictionaryList(List<DictionaryDto> dictionaryList) {
        this.dictionaryList = dictionaryList;
    }
}


