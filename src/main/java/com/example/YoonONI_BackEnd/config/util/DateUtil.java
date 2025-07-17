package com.example.YoonONI_BackEnd.config.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cglib.core.Local;

public class DateUtil {
    private static Log log = LogFactory.getLog(DateUtil.class);
    public static final String DATE_DIV = "-";

    public static final String LONG_DATE_STR = "yyyyMMddHHmmss";
    public static final String LONG_MILISECOND_DATE_STR = "yyyyMMddHHmmssSSS";
    public static final String MEDIUM_DATE_STR = "yyyyMMdd HH:mm";
    public static final String SHORT_DATE_STR = "yyyyMMdd";

    public static final String SHORT_MONTH_STR = "yyyyMM";
    public static final String SHORT_TIME_STR = "HH:mm";
    public static final String DB_DATE_STR = String.format("yyyy%sMM%sdd HH:mm:ss", DATE_DIV, DATE_DIV);
    public static final SimpleDateFormat DEFAULT_DATETIME_FORMAT = new SimpleDateFormat(LONG_DATE_STR);
    public static final SimpleDateFormat MEDIUM_DATETIME_FORMAT = new SimpleDateFormat(MEDIUM_DATE_STR);
    public static final SimpleDateFormat LONG_MILISECOND_DATETIME_FORMAT = new SimpleDateFormat(LONG_MILISECOND_DATE_STR);

    public static final SimpleDateFormat DB_DATETIME_FORMAT = new SimpleDateFormat(DB_DATE_STR);

    public static final SimpleDateFormat MIN_DATE_FORMAT = new SimpleDateFormat(SHORT_DATE_STR);
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(SHORT_DATE_STR);

    public static final SimpleDateFormat DEFAULT_MONTH_FORMAT = new SimpleDateFormat(SHORT_MONTH_STR);

    public static final SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat(SHORT_TIME_STR);

    public static final int MILLI_SEC_A_DAY = 1000 * 60 * 60 * 24;
    public static final int MILLI_SEC_A_TIME = 1000 * 60 * 60;
    public static final String DATE_DIV_REGEXP = "[/�꾩썡��\\-\\.:�쒕텇珥�]";

    public static String getCurrentDatetime() {
        return DEFAULT_DATETIME_FORMAT.format(new Date());
    }
    public static String getCurrentDatetimeMilisecond() {
        return LONG_MILISECOND_DATETIME_FORMAT.format(new Date());
    }

    public static String getCurrentMediumDatetime() {
        return MEDIUM_DATETIME_FORMAT.format(new Date());
    }

    public static String getCurrentDate() {
        return DEFAULT_DATE_FORMAT.format(new Date());
    }

    public static LocalDate getStringToDate(String date) {
        return LocalDate.parse(date);
    }

    public static String getCurrentDate(int i) {
        long now = System.currentTimeMillis();
        now += (i * MILLI_SEC_A_DAY);
        return DEFAULT_DATE_FORMAT.format(new Date(now));
    }

    public static String getCurrentDate(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        long now = System.currentTimeMillis();
        return dateFormat.format(new Date(now));
    }

    public static String getConvertFormatDate(String dateStr, String format) {
        int year = Integer.parseInt(dateStr.substring(0, 4));
        int month = Integer.parseInt(dateStr.substring(4, 6)) - 1;
        int date = Integer.parseInt(dateStr.substring(6));

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date);

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(cal.getTime());
    }

    public static String getConvertStringToDate(String dateStr, String format) throws Exception {
        String[] dateSplit = dateStr.split("\\.");
        int year = Integer.parseInt("20" + dateSplit[0].replaceAll(" ", ""));
        int month = Integer.parseInt(dateSplit[1].replaceAll(" ", "")) - 1;
        int day = Integer.parseInt(dateSplit[2].replaceAll(" ", ""));

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(cal.getTime());
    }

    public static Calendar toCalendar(String dateStr) {
        DateFormat format = getProperFormat(dateStr);

        Date convDate = null;
        try {
            convDate = format.parse(dateStr);
        } catch (ParseException e) {
            log.error("", e);
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convDate);

        return calendar;
    }

    private static DateFormat getProperFormat(String dateStr) {
        DateFormat format;
        if (dateStr.length() == SHORT_DATE_STR.length()) {
            format = DEFAULT_DATE_FORMAT;
        } else if(dateStr.length() == SHORT_MONTH_STR.length()) {
            format = DEFAULT_MONTH_FORMAT;
        } else {
            format = DEFAULT_DATETIME_FORMAT;
        }
        return format;
    }

    public static String add(String dateStr, int i) {
        String retStr = dateStr;
        if (!StringUtils.isEmpty(dateStr) && dateStr.length() >= SHORT_DATE_STR.length()) {
            dateStr = dateStr.trim();
            Calendar calendar = toCalendar(dateStr);
            calendar.add(Calendar.DATE, i);
            retStr = getProperFormat(dateStr).format(calendar.getTime());
        }

        return retStr;
    }

    public static String addMonth(String dateStr, int i) {
        String retStr = dateStr;

        if (StringUtils.isNotBlank(dateStr) && retStr.length() >= 6) {
            retStr = retStr.trim();
            if (retStr.length() == 6) {
                retStr += "01";
            }
            Calendar calendar = toCalendar(retStr);
            calendar.add(Calendar.MONTH, i);
            retStr = getProperFormat(dateStr).format(calendar.getTime());

            retStr = dateStr.length() == 6 ? retStr.substring(0, 6) : retStr;
        }
        return retStr;
    }

    public static String toDefaultDateFormat(String srcDate) {
        Date date = null;
        try {
            date = MIN_DATE_FORMAT.parse(srcDate);
        } catch (ParseException e) {
            log.error("", e);
            return srcDate;
        }
        return DEFAULT_DATE_FORMAT.format(date);
    }

    public static String toDefaultDateFormat(Timestamp datetime) {
        Date date = new Date(datetime.getTime());
        return DEFAULT_DATE_FORMAT.format(date);
    }

    /**
     * timestamp to DB Date format
     * @param timestamp
     * @return
     */
    public static String toDBDateFormat(long timestamp) {
        Date date = new Date(timestamp);
        return DB_DATETIME_FORMAT.format(date);
    }

    public static long dateDiff(String destDate, String srcDate) {
        return dateDiff(destDate, srcDate, true);
    }

    public static long dateDiff(String destDate, String srcDate, boolean containStartEndDt) {
        long diffDate = 0;
        try {
            if (StringUtils.isNotEmpty(destDate) && destDate.length() >= SHORT_DATE_STR.length() && StringUtils.isNotEmpty(srcDate) && srcDate.length() >= SHORT_DATE_STR.length()) {
                Date destDt = DEFAULT_DATE_FORMAT.parse(destDate);
                Date srcDt = DEFAULT_DATE_FORMAT.parse(srcDate);

                diffDate = new Long((destDt.getTime() - srcDt.getTime()) / MILLI_SEC_A_DAY);

                if (containStartEndDt) {
                    diffDate += 1;
                }
            }
        } catch (Exception ignore) {
        }
        return diffDate;
    }

    public static long monthDiff(String destMonth, String srcMonth) {
        return monthDiff(destMonth, srcMonth, false);
    }

    public static long monthDiff(String destMonth, String srcMonth, boolean containStartEndMon) {
        long diffMonth = 0;

        try {
            if(StringUtils.isNotEmpty(destMonth) && destMonth.length() >= SHORT_MONTH_STR.length() && StringUtils.isNotEmpty(srcMonth) && srcMonth.length() >= SHORT_MONTH_STR.length()) {
                Calendar destCal = toCalendar(destMonth);
                Calendar srcCal = toCalendar(srcMonth);
                diffMonth = destCal.get(Calendar.YEAR) * 12 + destCal.get(Calendar.MONTH) - ( srcCal.get(Calendar.YEAR) * 12 + srcCal.get(Calendar.MONTH));
            }

            if (containStartEndMon) {
                diffMonth += 1;
            }
        } catch (Exception ignore) {
        }

        return diffMonth;
    }

    public static String[] calBetweenYear(String fromDt, String toDt) {
        if (StringUtils.isBlank(fromDt) || StringUtils.isBlank(toDt)) {
            return null;
        }
        String[] retArray = new String[0];

        Calendar fromCal = toCalendar(fromDt);
        Calendar toCal = toCalendar(toDt);

        int fromYear = fromCal.get(Calendar.YEAR);
        int toYear = toCal.get(Calendar.YEAR);

        for (; fromYear <= toYear; fromYear++) {
            retArray = (String[]) ArrayUtils.add(retArray, fromYear + "");
        }

        return retArray;
    }

    public static String[] calBetweenYearMonth(String fromDt, String toDt) {
        if (StringUtils.isBlank(fromDt) || StringUtils.isBlank(toDt)) {
            return null;
        }
        String[] retArray = new String[0];

        Calendar fromCal = toCalendar(fromDt);
        Calendar toCal = toCalendar(toDt);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        int fromYM = 0;
        int toYM = 0;
        do {
            fromYM = Integer.parseInt(sdf.format(fromCal.getTime()));
            toYM = Integer.parseInt(sdf.format(toCal.getTime()));

            retArray = (String[]) ArrayUtils.add(retArray, fromYM + "");
            fromCal.add(Calendar.MONTH, 1);
        } while (fromYM < toYM);

        return retArray;
    }
    public static int getLastDayOfMonth(String dateStr) {
        dateStr = StringUtils.length(dateStr) == 6 ? dateStr + "01" : dateStr;
        Calendar calendar = toCalendar(dateStr);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static String getLastDateOfMonth(String dateStr) {
        if (dateStr == null || dateStr.length() < 6) {
            return "";
        }

        String str = dateStr.substring(0, 6) + "01";

        return dateStr.substring(0, 6) + StringUtils.leftPad(getLastDayOfMonth(str) + "", 2, "0");
    }
    public static int getFirstDayOfWeek(String dateStr) {
        if (dateStr == null || dateStr.length() < 6) {
            return -1;
        }
        String str = dateStr.substring(0, 6) + "01";

        Calendar cal = toCalendar(str);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static String getLastRemindDate(String ymd, int month) {
        String lastDate = getLastDateOfMonth(ymd);

        int thisDiff = (int) (Long.parseLong(lastDate) - Long.parseLong(ymd));

        String targetDate = addMonth(ymd, month);

        String retStr = (Long.parseLong(getLastDateOfMonth(targetDate)) - thisDiff) + "";

        return retStr;
    }

    public static String getSameMonthDate(String ymd, int month) {
        String target = addMonth(ymd, month);

        String retStr = target;

        if ((Long.parseLong(target) - Long.parseLong(ymd)) != (100 * month)) {
            retStr = getLastRemindDate(ymd, month);
        }

        return retStr;
    }

    public static long getCurrentDateLong(String format) {
        return Long.parseLong(getCurrentDate(format));
    }

    public static int getCurrentDateInt(String format) {
        return Integer.parseInt(getCurrentDate(format));
    }

    public static String getCurrentDateDBStr() {
        return getCurrentDate(DB_DATE_STR);
    }

    public static long timeDiffTo(String startTime, String stopTime, String type) {
        String returnType = type.toUpperCase();
        long diffTime = 0;
        try {
            if (startTime.indexOf(":") == -1) {
                startTime = startTime.substring(0, 2)+ ":" + startTime.substring(2, startTime.length());
            }
            if (stopTime.indexOf(":") == -1) {
                stopTime = stopTime.substring(0, 2)+ ":" + stopTime.substring(2, stopTime.length());
            }
            Date d1 = DEFAULT_TIME_FORMAT.parse(startTime);
            Date d2 = DEFAULT_TIME_FORMAT.parse(stopTime);
            long diff = d2.getTime() - d1.getTime();
            switch (returnType) {
                case "SEC":
                    diffTime = diff / 1000;
                    break;
                case "MIN":
                    diffTime = diff / (60 * 1000);
                    break;
                default:
                    diffTime = diff / (60 * 60 * 1000);
                    break;
            }
        } catch (Exception ignore) {
        }
        return diffTime;
    }

    /**
     * strDt -> date
     * @param strDt
     * @param format default yyyy-MM-dd
     * @return
     */
    public static Date toDate(String strDt, String format) {
        if (strDt == null) {
            return null;
        } else {
            if (format == null) {
                format = "yyyy-MM-dd";
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.parse(strDt);
            } catch (Exception e) {
                return null;
            }
        }
    }



    public static String calcDate(String dt, int year, int month, int day) {

        String dateStr = dt;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        try {
            cal.setTime(sdf.parse(dateStr));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }

        if (year != 0)
            cal.add(Calendar.YEAR, year);
        if (month != 0)
            cal.add(Calendar.MONTH, month);
        if (day != 0)
            cal.add(Calendar.DATE, day);
        return sdf.format(cal.getTime());
    }

    /**
     * �쒓컙�� 怨꾩궛�� dateStr�� 援ы븳��.
     *
     * @param dt
     * @param hh
     * @param mm
     * @param ss
     * @return
     */
    public static String calcTime(String dt, int hh, int mm, int ss) {

        String dateStr = dt;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(LONG_DATE_STR, Locale.getDefault());
        try {
            cal.setTime(sdf.parse(dateStr));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }

        if (hh != 0)
            cal.add(Calendar.HOUR, hh);
        if (mm != 0)
            cal.add(Calendar.MINUTE, mm);
        if (ss != 0)
            cal.add(Calendar.SECOND, ss);
        return sdf.format(cal.getTime());
    }

    public static String utcToGmtDBStr(String utcTime) {
        Instant instant = Instant.parse(utcTime);
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Asia/Seoul"));

        return zonedDateTime.format(DateTimeFormatter.ofPattern(DB_DATE_STR));
    }

    public static boolean containDateBetween(String startRange, String endRange, String targetDate) {
        // param length must be...
        int length = SHORT_DATE_STR.length();
        if (startRange.length() != length || endRange.length() != length || targetDate.length() != length) {
            return false;
        }
        // target date - start date >= 0
        if (DateUtil.dateDiff(targetDate, startRange, false) >= 0) {
            // end date - target date >= 0
            return DateUtil.dateDiff(endRange, targetDate, false) >= 0;
        }
        return false;
    }
}