package com.example.common.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final String  DATE_PATTERN  = "yyyy-MM-dd HH:mm:ss";
    /**
     * 将日期转化为指定格式的字符串
     * @param date
     * @param pattern
     * @return
     */
    public  static  String DateToString(Date date, String pattern){
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }


    /**
     * Date格式转化String
     * @param date
     * @param pattern
     * @return
     */
    public  static Date stringToDate(String date, String pattern){
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return  dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  null;
    }

    /**
     * 获取当前时间的string 格式
     * @param pattern
     * @return
     */
    public  static String getCurrentDate( String pattern){
        Calendar calendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(calendar.getTime());
    }

    /**
     * 获取几天之前的日期
     * @param startDay
     * @param days
     * @param pattern
     * @return
     */
     public static String getAfterDay(String startDay, int days, String pattern, boolean otherZero){

        DateFormat dateFormat = new SimpleDateFormat(pattern);
         try {
             Date  date= dateFormat.parse(startDay);
             Calendar calendar = Calendar.getInstance();
             calendar.setTime(date);
             calendar.add(Calendar.DATE, days);
             if(otherZero){
                 calendar.set(Calendar.HOUR_OF_DAY, 0);
                 calendar.set(Calendar.MINUTE, 0);
                 calendar.set(Calendar.SECOND,0);
             }
             return  dateFormat.format(calendar.getTime());
         } catch (ParseException e) {
             e.printStackTrace();
         }

        return  null;
    }

    /**
     * 获取几天之前的日期
     * @param startDay
     * @param days
     * @param pattern
     * @return
     */
    public static String getBeforeDay(String startDay, int days, String pattern, boolean otherZero){
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            Date  date= dateFormat.parse(startDay);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-days);
            if(otherZero){
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND,0);
            }
            return  dateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 两种时间间隔
     * @param startDay
     * @param endDay
     * @param pattern
     * @return
     */
    public static int daysBetween(String startDay, String endDay,String pattern){
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = dateFormat.parse(startDay);
            endDate = dateFormat.parse(endDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);
        long startTime = calendar.getTimeInMillis();


        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);
        long endTime = calendar.getTimeInMillis();

        long days = (endTime - startTime )/(1000*3600*24);
        return Integer.parseInt(String.valueOf(days));

    }


    public static List<Date> getAfterListDate(List<Long> days, String pattern, boolean otherZero){
        String today = getCurrentDate(pattern);
        List<Date> dates = new ArrayList<>();
        for(Long day: days){
            String afterDay = getAfterDay(today, day.intValue(), pattern, otherZero);
            dates.add(stringToDate(afterDay, pattern));
        }

        return  dates;
    }

    /**
     * 将时间转换为今天的开始时间，精度毫秒
     * @param dateTime
     * @return
     */
    public static Long convertTimeToDayBegin(Long dateTime) {
        return convertTimeToDayBegin(LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneId.systemDefault()));
    }

    /**
     * 将时间转换为今天的开始时间，精度毫秒
     * @param dateTime
     * @return
     */
    public static Long convertTimeToDayBegin(LocalDateTime dateTime) {
        LocalDate localDate = dateTime.toLocalDate();
        long epochMilli = LocalDateTime.of(localDate, LocalTime.MIN).toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
        return epochMilli;
    }

    /**
     * 将时间转换为今天的结束时间，精度毫秒
     * @param dateTime
     * @return
     */
    public static Long convertTimeToDayEnd(Long dateTime) {
        return convertTimeToDayEnd(LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneId.systemDefault()));
    }

    /**
     * 将时间转换为今天的结束时间，精度毫秒
     * @param dateTime
     * @return
     */
    public static Long convertTimeToDayEnd(LocalDateTime dateTime) {
        LocalDate localDate = dateTime.toLocalDate();
        long epochMilli = LocalDateTime.of(localDate, LocalTime.MAX).toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
        return epochMilli;
    }

    /**
     * 将时间转换为这个月的开始时间，精度毫秒
     * @param dateTime
     * @return
     */
    public static Long convertTimeToMonthBegin(Long dateTime) {
        return convertTimeToMonthBegin(LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneId.systemDefault()));
    }

    /**
     * 将时间转换为这个月的开始时间，精度毫秒
     * @param dateTime
     * @return
     */
    public static Long convertTimeToMonthBegin(LocalDateTime dateTime) {
        LocalDate localDate = dateTime.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate();
        long epochMilli = LocalDateTime.of(localDate, LocalTime.MIN).toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
        return epochMilli;
    }

    /**
     * 将时间转换为这个月的结束时间，精度毫秒
     * @param dateTime
     * @return
     */
    public static Long convertTimeToMonthEnd(Long dateTime) {
        return convertTimeToMonthEnd(LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneId.systemDefault()));
    }

    /**
     * 将时间转换为这个月的结束时间，精度毫秒
     * @param dateTime
     * @return
     */
    public static Long convertTimeToMonthEnd(LocalDateTime dateTime) {
        LocalDate localDate = dateTime.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate();
        long epochMilli = LocalDateTime.of(localDate, LocalTime.MAX).toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
        return epochMilli;
    }

    /**
     * 转化为当地时间
     * @param date
     * @return
     */
    public static LocalDateTime convertToDateTime(Long date) {

        if (date == null) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault());
        return localDateTime;
    }

    /**
     * 转化为字符串
     * @param date
     * @return
     */
    public static String convertToDateTimeString(Long date) {
        if (date == null) {
            return null;
        }
        LocalDateTime dateTime = convertToDateTime(date);
        return formatter.format(dateTime);
    }

    public static Date getBeforeDayDate(Date date, int days, boolean otherZero){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-days);
        if(otherZero){
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND,0);
        }

        return  calendar.getTime();
    }
}
