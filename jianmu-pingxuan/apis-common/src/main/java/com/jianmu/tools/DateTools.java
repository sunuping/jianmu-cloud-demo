package com.jianmu.tools;

import com.jianmu.exception.ErrorException;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期处理工具
 *
 * @author kong
 */
@Slf4j
public class DateTools {
    /**
     * 完整格式化日期 分钟和秒归0
     */
    public static final String COMPLETE_FORMAT_HOUR_PATTERN = "yyyy-MM-dd HH:00:00";
    /**
     * 完整格式化日期  小时分钟和秒归0
     */
    public static final String COMPLETE_FORMAT_DAY_PATTERN = "yyyy-MM-dd 00:00:00";
    public static final String COMPLETE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String COMPLETE_FORMAT_DAY_D_PATTERN = "yyyy-MM-dd";
    public static final String COMPLETE_FORMAT_DAY_D_PATTERN_CAPITAL = "yyyy年MM月dd日";
    /**
     * 只返回小时
     */
    public static final String FORMAT_HOUR_PATTERN = "HH";
    private static final ThreadLocal<SimpleDateFormat> FORMAT_THREAD_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<SimpleDateFormat> HOUR_FORMAT_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 随机时间
     *
     * @param start
     * @param end
     * @return
     */
    public static String randomTime(Date start, Date end) {
        StringBuilder sb = new StringBuilder();
        List<String> dates = DateTools.getBetweenTime(start, end);
        int h = RandomTools.random(0, 23);
        int m = RandomTools.random(1, 60);
        int s = RandomTools.random(1, 60);
        sb.append(dates.get(RandomTools.randInt(dates.size()) - 1))
                .append(" ")
                .append(h > 9 ? h : "0" + h)
                .append(":")
                .append(m > 9 ? m : "0" + m)
                .append(":")
                .append(s > 9 ? s : "0" + s);
        return sb.toString();
    }

    public static Date getDateOfPattern(String time, String pattern) {
        SimpleDateFormat f = FORMAT_THREAD_LOCAL.get();
        if (Objects.isNull(f)) {
            f = new SimpleDateFormat(pattern);
        }
        try {
            return f.parse(time);
        } catch (ParseException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            FORMAT_THREAD_LOCAL.remove();
        }
    }

    public static String getDateStringOfPattern(Date date, String pattern) {
        SimpleDateFormat f = HOUR_FORMAT_THREAD_LOCAL.get();
        if (Objects.isNull(f)) {
            f = new SimpleDateFormat(pattern);
        }
        return f.format(date);
    }

    public static Date getDateOfPattern(Date date, String pattern) {
        SimpleDateFormat f = FORMAT_THREAD_LOCAL.get();
        if (Objects.isNull(f)) {
            f = new SimpleDateFormat(pattern);
        }
        try {
            return f.parse(f.format(date));
        } catch (ParseException e) {
            throw new ErrorException(e.getMessage());
        } finally {
            FORMAT_THREAD_LOCAL.remove();
        }

    }

    public static List<String> getBetweenTime(Date startDate, Date endDate) {
        List<String> betweenTime = new ArrayList<String>();
        try {
            Calendar sCalendar = Calendar.getInstance();
            sCalendar.setTime(startDate);
            int year = sCalendar.get(Calendar.YEAR);
            int month = sCalendar.get(Calendar.MONTH);
            int day = sCalendar.get(Calendar.DATE);
            sCalendar.set(year, month, day, 0, 0, 0);

            Calendar eCalendar = Calendar.getInstance();
            eCalendar.setTime(endDate);
            year = eCalendar.get(Calendar.YEAR);
            month = eCalendar.get(Calendar.MONTH);
            day = eCalendar.get(Calendar.DATE);
            eCalendar.set(year, month, day, 0, 0, 0);

            while (sCalendar.before(eCalendar)) {
                betweenTime.add(getDateStringOfPattern(sCalendar.getTime(), "yyyy-MM-dd"));
                sCalendar.add(Calendar.DAY_OF_YEAR, 1);
            }
            betweenTime.add(getDateStringOfPattern(sCalendar.getTime(), "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return betweenTime;
    }

    public static void main(String[] args) throws ParseException {
//        log.debug(getDateOfPattern(new Date(), COMPLETE_FORMAT_HOUR_PATTERN).toString());
//        List<String> lists = getBetweenTime(new Date(), new Date());
//        for (String list : lists) {
//            System.out.println(list);
//        }
        System.out.println(getHour(new Date()));
    }


    /**
     * 功能描述：返回月
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回日期
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }


    /**
     * 功能描述：返回分
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫
     *
     * @param date 日期
     * @return 返回毫
     */
    public static long getMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }


    /**
     * 得到格式化后当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * 格式化日期字符串转为Date
     *
     * @param time
     * @return
     * @throws Exception
     */
    public static Date getDateByString(String time) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
    }


    /**
     * 获取某年某月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getCurrentMonthDayCount(int year, int month) {
        int days = 0;
        if (month != 2) {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    days = 31;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    days = 30;

            }
        } else {
            // 闰年
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                days = 29;
            } else {
                days = 28;
            }
        }
        return days;

    }


    /**
     * 当前时间  计算添加时间结果
     *
     * @param calendarType Calendar.SECOND YEAR MONTH MINUTE HOUR
     */
    public static Date addDate(int calendarType, int i) {
        Calendar calendar = Calendar.getInstance();
        switch (calendarType) {
            case Calendar.SECOND:
                calendar.add(Calendar.SECOND, i);
                break;
            case Calendar.YEAR:
                calendar.add(Calendar.YEAR, i);
                break;
            case Calendar.MONTH:
                calendar.add(Calendar.MONTH, i);
                break;
            case Calendar.MINUTE:
                calendar.add(Calendar.MINUTE, i);
                break;
            case Calendar.HOUR:
                calendar.add(Calendar.HOUR, i);
                break;
            default:
                break;
        }

        return calendar.getTime();
    }

    /**
     * 获取今天剩下的时间 秒
     *
     * @return
     */
    public static long getTodayRemainingTime() {
        Calendar curr = Calendar.getInstance();
        final int hour = 24 - curr.get(Calendar.HOUR_OF_DAY);
        final int minute = 60 - curr.get(Calendar.MINUTE);
        final int second = 60 - curr.get(Calendar.SECOND);
        final long remainingTime = hour * 60 * 60 + minute * 60 + second;
        if (log.isDebugEnabled()) {
            log.debug("今天剩余时间秒：{}", remainingTime);
        }
        return remainingTime;
    }

//    public static void main(String[] args) throws Exception {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        Date d1 = df.parse("2020-02-04");
//        Date d2 = new Date();
//        long diff = d1.getTime() - d2.getTime();
//        log.debug(diff / 1000 / 60 / 60 / 24 + "");
//        log.debug(diff / 1000 / 60 / 60 / 24 / 30 + "");
//        log.info(getCurrentMonthDayCount(2019, 7) + "");
//    }
}
