package com.pandawork.crm.common.utils;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang.StringUtils.join;


/**
 * DataUtils
 * Author： wychen
 * Date: 2017/7/25
 * Time: 21:07
 */
public final class DataUtils {

    /**
     * string转List<Integer>
     *
     * @param idStr
     * @return
     */
    public static List<Integer> stringToListInt(String idStr){
        String[] idStrArr = idStr.split(",");
        List<Integer> idArr = new ArrayList<Integer>();
        for (String item: idStrArr) {
            idArr.add(Integer.parseInt(item.trim()));
        }
        return idArr;
    }

    /**
     * Object转Int
     *
     * @param obj
     * @return
     */
    public static int objectToInt(Object obj){
        int result =  Integer.parseInt(obj==null? "" : obj.toString());
        return result;
    }

    /**
     * 根据页面大小及总数获取分页数目
     *
     * @param pageSize
     * @param count
     * @return
     */
    public static int getPageCount(int pageSize, int count){
        int pageCount = (int)Math.ceil(((double)count)/pageSize);
        return pageCount;
    }

    /**
     * List<String>转String类型字符串
     *
     * @param stringList
     * @return
     * @throws Exception
     */
    public static String listStringToString(List<String> stringList){
        String[] array = (String[])stringList.toArray(new String[stringList.size()]);
        return join(array, ",");
    }

    /**
     * 根据身份证号获取年龄
     *
     * @param idCard
     * @return
     */
    public static int getAgeByIdCard(String idCard)throws SSException {
        String birthDate = idCard.substring(6, 10) + "-" + idCard.substring(10, 12) + "-" + idCard.substring(12, 14);
        int age = 0;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        String todayStr = sdf.format(date);
        try {
            Date today = sdf.parse(todayStr);
            Date birth = sdf.parse(birthDate);
            age = today.getYear() - birth.getYear();
        }catch(Exception e){
            LogClerk.errLog.error(e);
        }
        return age;
    }
    /**
     * 手机号验证
     *
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 验证是否为数字
     *
     * @param str
     * @return 通过验证返回true
     */
    public static boolean isNumber(String str)
    {
        java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("[0-9]*");
        java.util.regex.Matcher match=pattern.matcher(str);
        if(match.matches()==false) {
            return false;
        }
        else {
            return true;
        }
    }

}
