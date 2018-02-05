package com.pandawork.crm.common.utils;

import com.pandawork.core.common.exception.SSException;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期时间工具类
 *
 * @author: zhangteng
 * @time: 2014-10-13 16:36
 */
public class DateUtils {

    public static final int SECONDS_OF_ONE_MONTH = 30 * 24 * 60 * 60;

    public static final int SECONDS_OF_FIVE_MINUTE = 5 * 60;

    private static final SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日");
    private static final SimpleDateFormat WEEK_OF_DAY_FORMAT = new SimpleDateFormat("E");
    private static final SimpleDateFormat YEAR_MONTH_DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final int SECOND_IN_DAY = 60 * 60 * 24;

    private static final long MILLIS_IN_DAY = 1000L * SECOND_IN_DAY;

    /**
     * 获得当前时间
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }

    public static Calendar calendar() {
        Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        return cal;
    }

    /**
     * 格式化日期时间
     * 日期时间格式yyy-MM-dd HH:mm
     *
     * @return
     */
    public static String formatDatetime(Date date) {
        return DEFAULT_FORMAT.format(date);
    }

    /**
     * 格式化日期
     * 日期时间格式yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDateSimple(Date date)throws SSException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static Date formatDateSimpleAnother(String str) throws Exception{
        Date date = null;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(str);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return date;
    }

    /**
     * 格式化日期时间
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatDatetime2(Date date){ return sdf.format(date);}
    /**
     * 获取当前日期
     * 日期时间格式yyyy.MM.dd
     *
     * @return
     */
    public static String simpleDateFormat() {
        return SIMPLE_DATE_FORMAT.format(new Date());
    }

    public static String weekOfDayFormat(Date date) {
        return WEEK_OF_DAY_FORMAT.format(date);
    }

    public static String yearMonthDayFormat(Date date) {
        return YEAR_MONTH_DAY_FORMAT.format(date);
    }
    /**
     * 计算时间差
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public static String calculateDiffTime(Date beginTime, Date endTime) {
        long between = 0;
        long hour = 0;
        long min = 0;
        try {
            between = (endTime.getTime() - beginTime.getTime());// 得到两者的毫秒数
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        hour = (between / (60 * 60 * 1000));
        min = ((between / (60 * 1000)) - hour * 60);
        return (hour + ":" + min);
    }

    /**
     * 计算时间差，并转换为"X天X小时X分钟"的格式
     * @param beginTime
     * @param endTime
     * @return
     */
    public static String calculateDiffTimeAndFormat(Date beginTime, Date endTime) {
        long between = 0;
        long day = 0;
        long hour = 0;
        long min = 0;
        try {
            between = (endTime.getTime() - beginTime.getTime());// 得到两者的毫秒数
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        day = (between / (60 * 60 * 1000 * 24));
        hour = (between / (60 * 60 * 1000) - day * 24);
        min = ((between / (60 * 1000)) - (day * 24 + hour) * 60);
        return (day + "天" + hour + "小时" + min + "分钟");
    }

    /**
     * 计算时间差，并转换为"X小时X分钟X秒"的格式
     * @param beginTime
     * @param endTime
     * @return
     */
    public static String calculateDiffTimeAndFormat2(Date beginTime, Date endTime) {
        long between = 0;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            between = (endTime.getTime() - beginTime.getTime());// 得到两者的毫秒数
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        day = (between / (60 * 60 * 1000 * 24));
        hour = (between / (60 * 60 * 1000) - day * 24);
        min = ((between / (60 * 1000)) - (day * 24 + hour) * 60);
        sec = ((between / 1000) - (((day * 24 + hour) * 60 + min) * 60));
        return ( hour + "小时" + min + "分钟" + sec + "秒");
    }

    /**
     * 返回昨天
     * @param day
     * @return
     */
    public static Date yesterday(Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        return calendar.getTime();
    }

    /**
     * 返回明天
     * @param day
     * @return
     */
    public static Date tomorrow(Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        return calendar.getTime();
    }

    /**
     * 获取当前日期前x天的日期
     *
     * @param day
     * @param x
     * @return
     */
    public static Date aheadDay(Date day,int x) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        if(x != 0){
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - x);
        }
        return calendar.getTime();
    }

    public static Date getTodayStartTime() {
        Calendar calendar = new GregorianCalendar();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getTodayEndTime() {
        Calendar calendar = new GregorianCalendar();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(final Date date1, final Date date2) {
        final long interval = date1.getTime() - date2.getTime();
        return interval < MILLIS_IN_DAY
                && interval > -1L * MILLIS_IN_DAY
                && toDay(date1.getTime()) == toDay(date2.getTime());
    }

    public static String formatDate(Date date) {
        return sdf.format(date);
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param dateFormat
     * @return
     */
    public static String formatDate(Date date, DateFormat dateFormat) {
        return dateFormat.format(date);
    }

    public static Date getFirstDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = -1 * (dayOfWeek == 1 ? 6 : dayOfWeek - 2);
        calendar.add(Calendar.DAY_OF_MONTH, dayOfWeek);

        return calendar.getTime();
    }

    public static Date getLastDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek == 1 ? 0 : (7 - dayOfWeek + 1);
        calendar.add(Calendar.DAY_OF_MONTH, dayOfWeek);

        return calendar.getTime();
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        return DateUtils.formatDate(date, new SimpleDateFormat(format));
    }

    private static long toDay(long mills) {
        return (mills + TimeZone.getDefault().getOffset(mills)) / MILLIS_IN_DAY;
    }

    public static void main(String[] args) {
        System.out.println(weekOfDayFormat(new Date()));
    }

    /**
     * 获取上个月第一天
     *
     * @return
     */
    public static String getLastMonthFirstDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String firstDay = format.format(calendar.getTime());
        return firstDay;
    }

    /**
     * 获取上个月最后一天
     *
     * @return
     */
    public static String getLastMonthLastDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        String lastDay = format.format(calendar.getTime());
        return lastDay;
    }


    /**
     * 获取本月第一天
     *
     * @return
     */
    public static String getCurrentMonthFirstDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String firstDay = format.format(calendar.getTime());
        return firstDay;
    }

    /**
     * 获取本月最后一天
     *
     * @return
     */
    public static String getCurrentMonthLastDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String lastDay = format.format(calendar.getTime());
        return lastDay;
    }

    /**
     * 获取本天时间
     * @return
     */
    public static String getToday(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String today = format.format(calendar.getTime());
        return today;
    }
    /**
     * 获取昨天时间
     * @return
     */
    public static String getYesterday(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        String yesterday = format.format(calendar.getTime());
        return yesterday;
    }

    /**
     * 获取本周第一天
     * @return
     */
    public static String getCurrentWeekFirstDay(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, 0);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String firstDay = format.format(calendar.getTime());
        return firstDay;
    }
    /**获取本周最后一天
     * @return
     */
    public static String getCurrentWeekLastDay(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        String lastDay = format.format(calendar.getTime());
        return lastDay;
    }
    /**
     * 获取上周第一天
     * @return
     *
     */
    public static String getLastWeekFirstDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String firstDay = format.format(calendar.getTime());
        return firstDay;
    }
    /**
     * 获取上周最后一天
     * @return
     *
     */
    public static String getLastWeekLastDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        //calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String firstDay = format.format(calendar.getTime());
        return firstDay;
    }

    /**
     * 得到本年的第一天
     * @return
     */
    public static String getCurrentYearFirstDay(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 0);
        calendar.set(Calendar.DAY_OF_YEAR,calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        String firstDay = format.format(calendar.getTime());
        return firstDay;
    }

    /**
     * 得到本年的最后一天
     * @return
     */
    public static String getCurrentYearLastDay(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 0);
        calendar.set(Calendar.DAY_OF_YEAR,calendar.getActualMaximum(Calendar.DAY_OF_YEAR) );
        String firstDay = format.format(calendar.getTime());
        return firstDay;
    }

    /**
     * 得到去年的第一天
     * @return
     */
    public static String getLastYearFirstDay(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        String firstDay = format.format(calendar.getTime());
        return firstDay;
    }

    /**
     * 得到去年的最后一天
     * @return
     */
    public static String getLastYearLastDay(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        calendar.set(Calendar.DAY_OF_YEAR,calendar.getActualMaximum(Calendar.DAY_OF_YEAR) );
        String firstDay = format.format(calendar.getTime());
        return firstDay;
    }

    /**
     * 得到开始时间
     * @param startTime
     * @return
     */
    public static Date getStartTime(String startTime){
        ParsePosition pos =new ParsePosition(0);
        startTime = startTime.concat(" 00:00:00");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date result = sdf.parse(startTime, pos);
        return result;
    }

    /**
     * 得到结束时间
     * @param endTime
     * @return
     */
    public static Date getEndTime(String endTime){
        ParsePosition pos =new ParsePosition(0);
        endTime = endTime.concat(" 23:59:59");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date result = sdf.parse(endTime, pos);
        return result;
    }
}
