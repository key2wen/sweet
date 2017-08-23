package com.wenwen.sweet.util;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class DateUtils extends PropertyEditorSupport {
    public static final Logger log = LoggerFactory.getLogger(DateUtils.class);
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    private String dateFormat = "yyyy-MM-dd";

    public DateUtils() {
    }


    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setAsText(String text) throws IllegalArgumentException {
        log.info("set Text");
        SimpleDateFormat frm = new SimpleDateFormat(this.dateFormat);

        try {
            Date exp = frm.parse(text);
            this.setValue(exp);
        } catch (Exception var4) {
            log.error("parse date error ", var4);
        }

    }

    public static Date parse(String text) throws ParseException {
        SimpleDateFormat frm = new SimpleDateFormat("yyyy-MM-dd");
        return frm.parse(text);
    }

    public static String parse(Date date) {
        if(date == null) {
            return "";
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(date);
        }
    }

    public static String parse(Date date, String formatString) {
        if(date == null) {
            return "";
        } else {
            SimpleDateFormat format = new SimpleDateFormat(formatString);
            return format.format(date);
        }
    }

    public static Date getDateZero(Date date) {
        date = org.apache.commons.lang.time.DateUtils.setHours(date, 0);
        date = org.apache.commons.lang.time.DateUtils.setMinutes(date, 0);
        date = org.apache.commons.lang.time.DateUtils.setSeconds(date, 0);
        date = org.apache.commons.lang.time.DateUtils.setMilliseconds(date, 0);
        return date;
    }

    public static Calendar getDateZeroCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(13, 0);
        cal.set(12, 0);
        cal.set(14, 0);
        cal.set(11, 0);
        return cal;
    }

    public static Date add(Date date, int zoom, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(zoom, amount);
        return cal.getTime();
    }

    public static Date getDate(String date) {
        return getDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date getDate(String date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);

        try {
            return df.parse(date);
        } catch (ParseException var4) {
            log.error("parse date error", var4);
            return null;
        }
    }

    public static int getDayCount(Date date1, Date date2) {
        Calendar cal1 = getDateZeroCalendar(date1);
        Calendar cal2 = getDateZeroCalendar(date2);
        long between_days = Math.abs((cal1.getTime().getTime() - cal2.getTime().getTime()) / 86400000L);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static String getDate(Date date) {
        return getDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getDate(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(7);
    }

    public static int getDayNumber(Date date) {
        int week = getDay(date) - 1;
        return week == 0?7:week;
    }

    public static Date getLastMonthStartDay(Date date, int lastMonthCount) {
        Calendar calendar = Calendar.getInstance();
        if(date != null) {
            calendar.setTime(date);
            calendar.set(5, 1);
            calendar.set(10, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            int month = calendar.get(2);
            calendar.set(2, month - lastMonthCount);
            return calendar.getTime();
        } else {
            return null;
        }
    }

    public static Date getDayStartOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, calendar.getActualMinimum(5));
        calendar.set(10, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return calendar.getTime();
    }

    public static boolean isToday(Date date) {
        Date d = new Date();
        return getDate(date, "yyyyMMdd").equals(getDate(d, "yyyyMMdd"));
    }

    public static Date getMaxMonthDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, calendar.getActualMaximum(5));
        calendar.set(10, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        return calendar.getTime();
    }

    public static List<Long[]> splitTime(long start, long end) {
        Preconditions.checkArgument(start > 0L && end > start);
        start = getDate(parse(new Date(start)), "yyyy-MM-dd").getTime();
        end = getDate(parse(new Date(end)), "yyyy-MM-dd").getTime();
        ArrayList result = Lists.newArrayList();

        for(int i = 0; i < 2147483647; ++i) {
            long endTime = getMaxMonthDate(new Date(start)).getTime();
            if(start > end) {
                break;
            }

            Long[] timePair;
            if(endTime > end) {
                timePair = new Long[]{Long.valueOf(start), Long.valueOf(end)};
                result.add(timePair);
                break;
            }

            timePair = new Long[]{Long.valueOf(start), Long.valueOf(endTime)};
            result.add(timePair);
            start = getLastMonthStartDay(new Date(start), -1).getTime();
        }

        return result;
    }

    public static Date getPreWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        while(cal.get(7) != 1) {
            cal.add(7, -1);
        }

        return cal.getTime();
    }

}
