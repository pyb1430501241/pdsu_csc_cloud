package com.pdsu.csc.utils;

import org.springframework.lang.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 半梦
 * @create 2020-11-26 19:38
 */
public final class DateUtils {

    private static final String DAY_DATE_PATTERN = "yyyy-MM-dd";
    private static final String SECOND_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final DateFormat DAY_DATE_FORMAT = new SimpleDateFormat(DAY_DATE_PATTERN);
    private static final DateFormat SECOND_DATE_FORMAT = new SimpleDateFormat(SECOND_DATE_PATTERN);

    /**
     * 返回当前时间
     * @return
     */
    @NonNull
    public static String getSimpleDate() {
        return DAY_DATE_FORMAT.format(new Date());
    }

    /**
     * 返回当前时间, 带时分秒
     * @return
     */
    @NonNull
    public static String getSimpleDateSecond() {
        return SECOND_DATE_FORMAT.format(new Date());
    }

    /**
     * 返回一段时间的差值
     * 单位: 秒
     * @param startDate 开始的时间
     * @param endDate   结束的时间
     * @return
     */
    public static long getSimpleDateDifference(@NonNull final String startDate, @NonNull final String endDate) {
        long diff = -1;
        try {
            Date start = SECOND_DATE_FORMAT.parse(startDate);
            Date end = SECOND_DATE_FORMAT.parse(endDate);
            diff = (end.getTime() - start.getTime())/1000;
        } catch (ParseException e) {
        }
        return diff;
    }

    private static final long CSC_SECOND = 1;

    private static final long CSC_MINUTE = CSC_SECOND * 60;

    private static final long CSC_HOURS = CSC_MINUTE * 60;

    private static final long CSC_DAY = CSC_HOURS * 24;

    private static final long CSC_WEEK = CSC_DAY * 7;

    private static final long CSC_MONTH = CSC_DAY * 30;

    private static final long CSC_YEAR = CSC_DAY * 365;

    private static final Map<Long, String> MAP = new HashMap<Long, String>();

    @SuppressWarnings("all")
    private static final Set<Long> KETSET = new TreeSet<Long>(Comparator.reverseOrder());

    static {
        MAP.put(CSC_WEEK, "周前");
        MAP.put(CSC_HOURS, "小时前");
        MAP.put(CSC_MINUTE, "分钟前");
        MAP.put(CSC_DAY, "天前");
        KETSET.addAll(MAP.keySet());
    }

    /**
     * 获取固定形式的时间
     * @param startDate
     * 	初始时间
     * @return
     * 	当时间差值小于一分钟时返回: 刚刚,
     * 	当时间差值大于一月时返回: startDate,
     *  当时间差值位于两者之间时返回: xxx前.
     */
    @NonNull
    public static String getSimpleDateDifferenceFormat(@NonNull final String startDate) {
        long t = getSimpleDateDifference(startDate, getSimpleDateSecond());
        StringBuilder builder = new StringBuilder();
        if (t < CSC_MINUTE) {
            builder.append("刚刚");
            return builder.toString();
        }
        if (t >= CSC_MONTH) {
            return startDate;
        }
        for(Long l : KETSET) {
            if (t >= l) {
                builder.append(t / l);
                builder.append(MAP.get(l));
                return builder.toString();
            }
        }
        return startDate;
    }

    /**
     * @return
     *  获取 Session 有效时间, 由于 Shiro 以毫秒为单位,
     *  本系统以秒为单位, 故乘以1000
     */
    public static long getSessionTimeout() {
        return CSC_WEEK * 1000;
    }

}
