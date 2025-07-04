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

    /**
     * String �뺤쓽 �좎쭨瑜� �щĸ�뺤떇�쇰줈 諛섑솚
     *
     * @param dateStr - yy.m.d
     * @return
     */
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

    /**
     * �좎쭨 String�� Calendar 媛앹껜濡� 蹂��섑빐二쇰뒗 硫붿냼��.
     *
     * @param dateStr
     * @return
     */
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

    /**
     * �좎쭨 String�� 湲몄씠�� �곕씪 �곸젅�� DateFormat�� 諛섑솚�섎뒗 硫붿냼��.
     *
     * @param dateStr
     * @return
     */
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

    /**
     * �좎쭨 String�� 諛쏆븘�� i 媛믪뿉�� 吏��뺥븳 �좎쭨留뚰겮 �뷀븳 �좎쭨 String�� 諛섑솚�섎뒗 硫붿냼��.
     *
     * @param dateStr
     * @param i
     * @return
     */
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

    /**
     * �붿쓣 �곗궛�쒕떎.
     *
     * @param dateStr yyyyMM or yyyyMMdd
     * @param i
     * @return dateStr 湲몄씠�� �곕씪 媛숈� �뺤떇 諛섑솚
     */
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

    /**
     * �좎쭨李⑥씠瑜� 援ы븳�� �쒖옉怨� 醫낅즺�쇱쓣 �ы븿 ��) 2008-03-05 - 2008-03-02 �좎쭨李⑥씠�� 4��
     *
     * @param destDate
     * @param srcDate
     * @return
     */
    public static long dateDiff(String destDate, String srcDate) {
        return dateDiff(destDate, srcDate, true);
    }

    /**
     * �좎쭨李⑥씠援ы븿
     *
     * @param destDate
     * @param srcDate
     * @param containStartEndDt �쒖옉,醫낅즺�� �ы븿 true: 2008-03-05 - 2008-03-02 => 4 false: 2008-03-05
     *                          - 2008-03-02 => 3
     * @return
     */
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

    /**
     * �� �좎쭨 �ъ씠�� 媛쒖썡 �� 李⑥씠瑜� 援ы븳�� ��) 202210 - 202208 媛쒖썡 �� 李⑥씠�� 2
     *
     * @param destMonth
     * @param srcMonth
     * @return
     */
    public static long monthDiff(String destMonth, String srcMonth) {
        return monthDiff(destMonth, srcMonth, false);
    }

    /**
     * �좎쭨李⑥씠援ы븿
     *
     * @param destMonth
     * @param srcMonth
     * @param containStartEndMon �쒖옉,醫낅즺�� �ы븿 true: 202210 - 202208 => 3 false: 202210 - 202208 => 2
     * @return
     */
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

    /**
     * �쒖옉怨� 醫낅즺�쇱쓽 �꾨룄 援ы븯湲�
     *
     * @param fromDt
     * @param toDt
     * @return
     */
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

    /**
     * �쒖옉怨� 醫낅즺�쇱쓽 �꾨룄/�� 援ы븯湲�
     *
     * @param fromDt
     * @param toDt
     * @return
     */
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

    /**
     * �ъ쓽 留덉�留� �좎쭨瑜� 援ы븿.
     *
     * @param dateStr yyyyMM or yyyyMMdd
     * @return yyyyMMdd
     */
    public static String getLastDateOfMonth(String dateStr) {
        if (dateStr == null || dateStr.length() < 6) {
            return "";
        }

        String str = dateStr.substring(0, 6) + "01";

        return dateStr.substring(0, 6) + StringUtils.leftPad(getLastDayOfMonth(str) + "", 2, "0");
    }

    /**
     * 泥ル쾲吏� �좎쭨�� �붿씪�� 援ы븿.
     *
     * @param dateStr 201301, 20130101
     * @return
     */
    public static int getFirstDayOfWeek(String dateStr) {
        if (dateStr == null || dateStr.length() < 6) {
            return -1;
        }
        String str = dateStr.substring(0, 6) + "01";

        Calendar cal = toCalendar(str);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * �대떦 �좎쭨�� �붿씪�� 援ы븿.
     *
     * @param dateStr
     * @return
     */
    public static String getDayOfWeek(String dateStr) {
        String str = dateStr;

        Calendar cal = toCalendar(str);

        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                str = "��";
                break;
            case 2:
                str = "��";
                break;
            case 3:
                str = "��";
                break;
            case 4:
                str = "��";
                break;
            case 5:
                str = "紐�";
                break;
            case 6:
                str = "湲�";
                break;
            case 7:
                str = "��";
                break;
        }

        return str;
    }

    /**
     * <pre>
     *  �대떦�섎뒗 �좎씠 洹몃떖�� 留덉�留됱뿉 李⑥씠媛� �섎뒗 �좊쭔�� �곗궛 寃곌낵 �좊룄 �숈씪�섍쾶 怨꾩궛��
     *  �낅젰 : 留덉�留됰궇 (31) - �ㅼ뼱�⑤궇(30) = 李⑥씠(1)
     *  異쒕젰 : 紐⑺몴 �좎뿉�� 臾댁“嫄� 李⑥씠留뚰겮 鍮쇱꽌 怨꾩궛
     * </pre>
     *
     * @param ymd   yyyyMMdd
     * @param month
     * @return 20130131 -> 20130228
     */
    public static String getLastRemindDate(String ymd, int month) {
        String lastDate = getLastDateOfMonth(ymd);

        int thisDiff = (int) (Long.parseLong(lastDate) - Long.parseLong(ymd));

        String targetDate = addMonth(ymd, month);

        String retStr = (Long.parseLong(getLastDateOfMonth(targetDate)) - thisDiff) + "";

        return retStr;
    }

    /**
     * <pre>
     * �대떦�섎뒗 �ъ쓽 �좎쭨�� �대떦�섎뒗 �좎쓣 �ㅼ쓬�щ룄 媛숈� �좎쭨濡� 諛섑솚�쒕떎.
     * 1��31�� �쒕떖 �뷀븯硫� 2��31�대릺�� �섎뒗�� �좎쭨媛� �놁쑝誘�濡�, 2�� 28�쇰줈 諛섑솚
     * </pre>
     *
     * @param ymd
     * @param month
     * @return 20130131 -> 20130228
     */
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

    /**
     * �붾퉬�� �낅젰�섎뒗 �묒떇�쇰줈 蹂���
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateDBStr() {
        return getCurrentDate(DB_DATE_STR);
    }

    /**
     * [timeDiff�� �ㅻ쫫]�쒓컙李⑥씠瑜� 援ы븳��, sec(珥�), min(遺�), hr(�쒓컙)
     * 1200 - 1300 = 1, 60, 3600
     * @param startTime 1200
     * @param stopTime 1300
     * @param type (sec, min, hr - 湲곕낯媛� �쒓컙)
     * @return long (�붾툝�꾨떂)
     */
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

    /**
     * utc time�� gmt濡� 蹂�寃�
     * @param utcTime 2023-04-16T11:13:29.000Z
     * @return 2023-04-16 20:13:29
     */
    public static String utcToGmtDBStr(String utcTime) {
        Instant instant = Instant.parse(utcTime);
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Asia/Seoul"));

        return zonedDateTime.format(DateTimeFormatter.ofPattern(DB_DATE_STR));
    }

    /**
     * <p>�대떦 �좎쭨 踰붿쐞 �댁뿉 �대떦 �좎쭨媛� �ы븿�섏뼱�덈뒗吏� �щ�</p>
     * <pre>
     * containDateBetween("20240601", "20240630", "20240601") = true
     * containDateBetween("20240601", "20240630", "20240630") = true
     * containDateBetween("20240521", "20240630", "20240630") = false
     * </pre>
     * @param startRange �쒖옉踰붿쐞 �좎쭨 (yyyymmdd)
     * @param endRange 醫낅즺踰붿쐞 �좎쭨 (yyyymmdd)
     * @param targetDate �대떦 �좎쭨 (yyyymmdd)
     * @return �대떦 踰붿쐞 �댁뿉 �대떦 �좎쭨媛� �ы븿�섏뼱�덈떎硫� {@code true} �꾨땲�쇰㈃ {@code false}
     */
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