package org.example.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @AUTHOR yan
 * @DATE 2020/2/11
 */


public class DateUtils {

    public static final Long MILLIS_SECOND = 1000L;
    public static final Long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    public static final Long MILLIS_HOUR = 60 * MILLIS_MINUTE;
    public static final Long MILLIS_DAY = 24 * MILLIS_HOUR;
    private static final String DATE_PATTEN = "(\\d{2,4})[\\s/\\-年]?(\\d{1,2})[\\s/\\-月]?(\\d{1,2})[日]?";
    private static final String TIME_PATTEN = "(\\d{2})[:时]?(\\d{2})[:分]?(\\d{2})[秒.+]?(.*)";
    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.sss";
    public static String DATE_MINUTE_SECOND_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_SIMPLE_FORMAT = "yyyy-MM-dd";
    public static String DATE_MONTH_FORMAT = "yyyy-MM";
    public static String DATE_YEAR_FORMAT = "yyyy";
    public static String DATE_COMPACT_FORMAT = "yyyyMMdd";
    public static String DATE_EN_SIMPLE_FORMAT = "yyyy/MM/dd";
    public static String NUMBER_GENERATE_PATTERN = "yyyyMMddHHmmss";

    public static Date localDateToDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static Date stringToDate(CharSequence text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        LocalDate ld = LocalDate.parse(text);
        Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static String dateFormat(String date, String format) {
        if (org.apache.commons.lang3.StringUtils.isNoneBlank(format)) {
            DATE_FORMAT = format;
        }
        if (StringUtils.isBlank(date)) {
            return "";
        }
        if (!date.matches("^\\d{2,4}[.\\s/\\-年]?\\d{1,2}[.\\s/\\-月]?\\d{1,2}[日]?.*")) {
            return date;
        }

        String result = DATE_FORMAT;
        String[] dateGroup = getDateGroup(date);
        String[] timeGroup = getTimeGroup(date);

        if (dateGroup != null) {
            result = result.replaceFirst("[Yy]{2,4}", dateGroup[0])
                    .replaceFirst("M{1,2}", dateGroup[1].length() == 2 ? dateGroup[1] : "0" + dateGroup[1])
                    .replaceFirst("d{1,2}", dateGroup[2].length() == 2 ? dateGroup[2] : "0" + dateGroup[2]);
        }

        result = result.replaceFirst("[Hh]{2}", timeGroup[0])
                .replaceFirst("m{1,2}", timeGroup[1])
                .replaceFirst("s{1,2}", timeGroup[2])
                .replaceAll("\\..*?$", timeGroup[3]);

        return result;
    }

    private static String[] getDateGroup(String dateTime) {
        Pattern datePattern = Pattern.compile(DATE_PATTEN);
        Matcher dateMatcher = datePattern.matcher(dateTime);
        if (dateMatcher.find()) {
            String[] dateGroup = new String[3];
            for (int i = 1, n = dateMatcher.groupCount(); i <= n; ++i) {
                dateGroup[i - 1] = dateMatcher.group(i);
            }
            return dateGroup;
        }
        return null;
    }

    private static String[] getTimeGroup(String dateTime) {
        Pattern timePattern = Pattern.compile(TIME_PATTEN);
        Matcher timeMatcher = timePattern.matcher(dateTime);
        String[] timeGroup = new String[]{"00", "00", "00", ".0000"};
        if (timeMatcher.find()) {
            for (int i = 1, n = timeMatcher.groupCount(); i <= n; ++i) {
                timeGroup[i - 1] = timeMatcher.group(i);
            }
            return timeGroup;
        }
        return timeGroup;
    }

    public static Date getFormatDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        if (date.contains(".") && date.indexOf(".") > 8) {
            date = date.substring(0, date.lastIndexOf("."));
        }
        try {
            return parseDate(date, "yyyyMMdd", "yyyy.MM.dd", "yyyy-MM-dd", "yyyy-M-d", "yyyy/MM/dd",
                    "yyyy/M/d", "yyyy-MM-dd HH:mm:ss", "yyyy-M-d H:m:s", "yyyy年MM月dd日", "yyyy年M月d日",
                    "yyyy年MM月dd", "yyyy年M月d", "yyyy-MM");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Date parseDate(String date, String... patterns) throws ParseException {
        for (String pattern : patterns) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                return format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getFormatDateStr(String dateStr) {
        Date date = getFormatDate(dateStr);
        if (Objects.nonNull(date)) {
            return new DateTime(date).toString("yyyy-MM-dd");
        }
        return dateStr;
    }

    public static String getFormatDateStr(String dateStr, String format) {
        Date date = getFormatDate(dateStr);
        if (Objects.nonNull(date)) {
            return new DateTime(date).toString(format);
        }
        return dateStr;
    }

    public static String getFormatDateStr(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getChinaDateFormat(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return dateStr;
        }
        return new DateTime(dateStr).toString("yyyy年M月d日");
    }

    public static String toDate(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        String str = sdf.format(date);
        return dateFormat(str, format);
    }

    public static Date getDateBefore(Long milliseconds) {
        return new Date(System.currentTimeMillis() - milliseconds);
    }

    /**
     * 获取两个时间差 多少小时多少分钟
     *
     * @param endDate
     * @param nowDate
     * @return
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        StringBuilder dateStr = new StringBuilder();
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;

        if (day > 0) {
            dateStr.append(day).append("天");
        }
        if (hour > 0) {
            dateStr.append(hour).append("小时");
        }
        if (min > 0) {
            dateStr.append(min).append("分钟");
        }
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return dateStr.toString();
    }

    public static String getStringDateAndTime(DateTime dateTime) {
        return dateTime.withZone(DateTimeZone.getDefault())
                .toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static Date getStartTime(Calendar calendar) {
        //一天的开始时间 yyyy:MM:dd 00:00:00
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndTIme(Calendar calendar) {
        //一天的结束时间 yyyy:MM:dd 23:59:59
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Calendar getFirstDayMonth(Calendar calendar) {
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }

    public static Calendar getLastDayMonth(Calendar calendar) {
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar;
    }
}

