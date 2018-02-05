package com.pandawork.crm.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author: zhangteng
 * @time: 2015/10/22 14:53
 **/
public class StringUtils {

    // 拼音类型：全拼
    public static final String PINYIN_TYPE_FULL = "full";

    // 拼音类型：首字母
    public static final String PINYIN_TYPE_HEAD_CHAR = "headChar";

    /**
     * 对传入的字符串计算汉字的个数
     *
     * @param str
     * @return
     */
    public static int countChinese(String str) {
        int count = 0;
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        while (m.find()) {
            count += (m.groupCount() + 1);
        }
        return count;
    }

    /**
     * 字符串转汉语拼音
     * 如果是多音字，只返回第一种读音
     * 支持返回首字母和全拼
     *
     * @param src
     * @param retType
     * @return
     * @throws Exception
     */
    public static String str2Pinyin(String src, String retType) throws Exception {
        if (src == null || ("").equals(src)) {
            return "";
        }

        // 转换格式设置
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] srcChar = src.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char ch : srcChar) {
            String[] tmp = PinyinHelper.toHanyuPinyinStringArray(ch, outputFormat);
            if (tmp == null) {
                sb.append(ch);
            } else {
                if (PINYIN_TYPE_HEAD_CHAR.equals(retType)) {
                    sb.append(tmp[0].charAt(0));
                } else {
                    sb.append(tmp[0]);
                }
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String str = "重0庆";

        System.out.println(str2Pinyin(str, PINYIN_TYPE_FULL));
    }
}
