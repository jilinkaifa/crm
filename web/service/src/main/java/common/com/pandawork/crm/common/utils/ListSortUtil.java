package com.pandawork.crm.common.utils;

import com.pandawork.crm.common.entity.client.basic.Client;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ListSortUtil
 * Created by shura
 * Date:  2017/9/29.
 * Time:  10:51
 */
public class ListSortUtil<E> {

    //截取数字
    public static Integer getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return Integer.parseInt(matcher.group(0));
        }
        return 0;
    }
    //排序
    public static void sortByStringNumber(List<Client> list){
        Collections.sort(list,new Comparator<Client>(){
            public int compare(Client arg0, Client arg1) {
                Integer a0= getNumbers(arg0.getRecordId());
                Integer a1 = getNumbers(arg1.getRecordId());
                return a0.compareTo(a1);
            }
        });
    }
}
