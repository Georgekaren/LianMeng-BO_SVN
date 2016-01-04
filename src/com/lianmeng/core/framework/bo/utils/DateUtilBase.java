package com.lianmeng.core.framework.bo.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.lianmeng.core.framework.exceptions.AppException;
import com.lianmeng.core.framework.exceptions.ExceptionHandler;

public class DateUtilBase
{
  private static final Logger logger = Logger.getLogger(DateUtilBase.class);
  public static final String DATE_FORMAT_1 = "yyyy-MM-dd";
  public static final String DATE_FORMAT_2 = "yyyyMMdd";
  public static final String DATETIME_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
  public static final String DATETIME_FORMAT_3 = "yyyy-MM-dd HH-mm-ss";
  public static final String DATETIME_FORMAT_2 = "yyyyMMddHHmmss";
  public static final String DATETIME_FORMAT_4 = "yyyy/MM/dd HH:mm:ss";
  public static final String DATETIME_FORMAT_5 = "yyyy-MM-dd HH:mm:ss.SSS";
  public static final String DATETIME_FORMAT_6 = "yyyy-MM-dd HH:mm:ss";
  public static final String DATE_FORMAT_3 = "yyyy年MM月dd日";
  public static final int DIFFER_IN_SECOND = 0;
  public static final int DIFFER_IN_MINUTE = 1;
  public static final int DIFFER_IN_HOUR = 2;
  public static final int DIFFER_IN_DAYS = 3;
  public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
  public static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static final String NAME_FILE_DATE_FORMAT = "yyyyMMdd_HHmmss";
  public static final String[] DATE_FORMAT_SUPPORT = { "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy-MM-dd HH-mm-ss", "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd", "yyyyMMdd", "yyyy年MM月dd日" };

  public static SimpleDateFormat getDateFormat(String format)
  {
    return new SimpleDateFormat(format);
  }

  public static String date2String(java.util.Date date, String format) {
    if (date == null) {
      return "";
    }

    SimpleDateFormat sdf = getDateFormat(format);
    return sdf.format(date);
  }

  public static String date2String(java.util.Date date) {
    return date2String(date, "yyyy-MM-dd");
  }

  public static String getCurrentDate() {
    java.util.Date date = new java.util.Date();
    return date2String(date, "yyyy-MM-dd");
  }

  public static String getNameFileCurrentDate() {
    java.util.Date date = new java.util.Date();
    return date2String(date, "yyyyMMdd_HHmmss");
  }

  public static java.sql.Date dateToSqlDate(java.util.Date date)
  {
    if (date == null) {
      return null;
    }
    if (date instanceof java.sql.Date) {
      return ((java.sql.Date)date);
    }

    return new java.sql.Date(date.getTime());
  }

  public static java.sql.Date string2SQLDate(String date)
  {
    java.sql.Date ret = null;
    if ((date == null) || (date.length() == 0)) {
      return ret;
    }
    if (date.length() > 11) {
      if (date.indexOf(45) > 0) {
        if (date.indexOf(58) > 0) {
          ret = string2SQLDate(date, "yyyy-MM-dd HH:mm:ss");
        }
        else {
          ret = string2SQLDate(date, "yyyy-MM-dd HH-mm-ss");
        }
      }
      else if (date.indexOf(47) > 0) {
        ret = string2SQLDate(date, "yyyy/MM/dd HH:mm:ss");
      }
      else {
        ret = string2SQLDate(date, "yyyyMMddHHmmss");
      }

    }
    else if (date.indexOf(45) > 0) {
      ret = string2SQLDate(date, "yyyy-MM-dd");
    }
    else if (date.length() == 8) {
      ret = string2SQLDate(date, "yyyyMMdd");
    }
    else {
      ret = string2SQLDate(date, "yyyy年MM月dd日");
    }

    return ret;
  }

  public static java.sql.Date string2SQLDate(String date, String format) {
    boolean isSucc = true;
    Exception operateException = null;
    if (!(ValidateUtil.validateNotEmpty(format))) {
      isSucc = false;
      operateException = new IllegalArgumentException("the date format string is null!");
    }
    SimpleDateFormat sdf = getDateFormat(format);
    if (!(ValidateUtil.validateNotNull(sdf))) {
      isSucc = false;
      operateException = new IllegalArgumentException("the date format string is not matching available format object");
    }

    java.util.Date tmpDate = null;
    try {
      if (isSucc) {
        tmpDate = sdf.parse(date);
        String tmpDateStr = sdf.format(tmpDate);
        if (!(tmpDateStr.equals(date))) {
          isSucc = false;
          operateException = new IllegalArgumentException("the date string " + date + " is not matching format: " + format);
        }
      }
    }
    catch (Exception e)
    {
      isSucc = false;
      operateException = e;
    }

    if (!(isSucc)) {
      logger.error("the date string " + date + " is not matching format: " + format, operateException);
      if (operateException instanceof IllegalArgumentException) {
        throw ((IllegalArgumentException)operateException);
      }

      throw new IllegalArgumentException("the date string " + date + " is not matching format: " + format + ".\n cause by :" + operateException.toString());
    }

    return new java.sql.Date(tmpDate.getTime());
  }

  public static java.util.Date string2Date(String date)
  {
    java.util.Date ret = null;
    if ((date == null) || (date.length() == 0)) {
      return ret;
    }
    if (date.length() > 11) {
      if (date.indexOf(45) > 0) {
        if (date.indexOf(58) > 0) {
          ret = string2Date(date, "yyyy-MM-dd HH:mm:ss");
        }
        else {
          ret = string2Date(date, "yyyy-MM-dd HH-mm-ss");
        }
      }
      else if (date.indexOf(47) > 0) {
        ret = string2Date(date, "yyyy/MM/dd HH:mm:ss");
      }
      else {
        ret = string2Date(date, "yyyyMMddHHmmss");
      }

    }
    else if (date.indexOf(45) > 0) {
      ret = string2Date(date, "yyyy-MM-dd");
    }
    else if (date.length() == 8) {
      ret = string2Date(date, "yyyyMMdd");
    }
    else {
      ret = string2Date(date, "yyyy年MM月dd日");
    }

    return ret;
  }

  public static java.util.Date string2Date(String date, String format) {
    if (!(ValidateUtil.validateNotEmpty(format))) {
      throw new IllegalArgumentException("the date format string is null!");
    }
    SimpleDateFormat sdf = getDateFormat(format);
    if (!(ValidateUtil.validateNotNull(sdf)))
      throw new IllegalArgumentException("the date format string is not matching available format object");
    try
    {
      return sdf.parse(date);
    }
    catch (ParseException e) {
      throw new IllegalArgumentException("the date string " + date + " is not matching format: " + format, e);
    }
  }

  public static String getStandardNowTime()
  {
    SimpleDateFormat sdf = getDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(new java.util.Date());
  }

  public static java.sql.Date getNowDate()
  {
    return new java.sql.Date(new java.util.Date().getTime());
  }

  public static java.sql.Date offsetSecond(java.sql.Date date, long seconds)
  {
    if (date == null) {
      return null;
    }

    long time = date.getTime();
    long time2 = time + seconds * 1000L;
    long time3 = time + seconds * 1000L - 3600000L;
    java.sql.Date date2 = new java.sql.Date(time2);
    java.sql.Date date3 = new java.sql.Date(time3);

    Calendar calendarDate = Calendar.getInstance();
    calendarDate.setTime(date);
    Calendar calendarDate2 = Calendar.getInstance();
    calendarDate2.setTime(date2);
    Calendar calendarDate3 = Calendar.getInstance();
    calendarDate3.setTime(date3);

    long dstDate = calendarDate.get(16);
    long dstDate2 = calendarDate2.get(16);
    long dstDate3 = calendarDate3.get(16);

    long dstOffset = dstDate - dstDate2;

    if ((dstOffset == 0L) || ((dstDate2 - dstDate3 != 0L) && (dstDate2 != 0L))) {
      return date2;
    }

    return new java.sql.Date(time2 + dstOffset);
  }

  public static java.sql.Date offsetMinute(java.sql.Date date, long minutes)
  {
    return offsetSecond(date, 60L * minutes);
  }

  public static java.sql.Date offsetHour(java.sql.Date date, long hours)
  {
    return offsetMinute(date, 60L * hours);
  }

  public static java.sql.Date offsetDay(java.sql.Date date, int days)
  {
    return offsetHour(date, 24 * days);
  }

  public static java.sql.Date offsetWeek(java.sql.Date date, int weeks)
  {
    return offsetDay(date, 7 * weeks);
  }

  public static java.sql.Date getLastDayOfMonth(java.sql.Date date)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int maxDay = calendar.getActualMaximum(5);
    calendar.set(5, maxDay);
    calendar.set(11, 23);
    calendar.set(12, 59);
    calendar.set(13, 59);
    date.setTime(calendar.getTimeInMillis());
    return date;
  }

  public static java.sql.Date getBeginDayOfMonth(java.sql.Date date)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(5, 1);
    calendar.set(11, 0);
    calendar.set(12, 0);
    calendar.set(13, 0);
    date.setTime(calendar.getTimeInMillis());
    return date;
  }

  public static java.sql.Date offsetMonth(java.sql.Date date1, int months)
  {
    if (date1 == null) {
      return null;
    }

    java.sql.Date date = new java.sql.Date(date1.getTime());
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    int curDay = calendar.get(5);
    int maxDay = calendar.getActualMaximum(5);
    calendar.set(5, 1);
    calendar.add(2, months);

    int newMaxDay = calendar.getActualMaximum(5);
    if (curDay == maxDay) {
      calendar.set(5, newMaxDay);
    }
    else if (curDay > newMaxDay) {
      calendar.set(5, newMaxDay);
    }
    else {
      calendar.set(5, curDay);
    }

    date.setTime(calendar.getTimeInMillis());
    return date;
  }

  public static java.sql.Date offsetYear(java.sql.Date date, int years)
  {
    if (date == null) {
      return null;
    }

    java.sql.Date newdate = (java.sql.Date)date.clone();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(newdate);
    calendar.add(1, years);
    newdate.setTime(calendar.getTimeInMillis());
    return newdate;
  }

  public static long differDateInDays(java.sql.Date beginDate, java.sql.Date endDate, int returnType)
  {
    long begin = beginDate.getTime();
    long end = endDate.getTime();
    long surplus = begin - end;

    Calendar calendarBeginDate = Calendar.getInstance();
    calendarBeginDate.setTime(beginDate);

    Calendar calendarEndDate = Calendar.getInstance();
    calendarEndDate.setTime(endDate);

    long dstOffset = calendarBeginDate.get(16) - calendarEndDate.get(16);
    surplus += dstOffset;

    long ret = 0L;
    switch (returnType)
    {
    case 0:
      ret = surplus / 1000L;
      break;
    case 1:
      ret = surplus / 1000L / 60L;
      break;
    case 2:
      ret = surplus / 1000L / 60L / 60L;
      break;
    case 3:
      ret = surplus / 1000L / 60L / 60L / 24L;
    }

    return ret;
  }

  private static boolean isAsc(String firstStr, String secondStr) {
    return (firstStr.compareTo(secondStr) < 0);
  }

  public static boolean isInRange(String date, String beginDate, String endDate)
    throws AppException
  {
    if ((!(ValidateUtil.validateNotNull(date))) || (!(ValidateUtil.validateNotNull(beginDate))) || (!(ValidateUtil.validateNotNull(endDate))))
    {
      ExceptionHandler.publish("");
    }

    int dateLen = date.length();
    int beginDateLen = date.length();
    int endDateLen = date.length();

    if ((beginDateLen != dateLen) || (endDateLen != endDateLen)) {
      ExceptionHandler.publish("");
    }

    boolean asc = isAsc(beginDate, endDate);

    if (asc) {
      if ((date.compareTo(beginDate) >= 0) && (date.compareTo(endDate) <= 0)) {
        return true;
      }

    }
    else if ((date.compareTo(beginDate) >= 0) || (date.compareTo(endDate) <= 0)) {
      return true;
    }

    return false;
  }

  public static boolean isInRange(java.util.Date date, java.util.Date beginDate, java.util.Date endDate)
  {
    long time = date.getTime();
    long beginTime = beginDate.getTime();
    long endTime = endDate.getTime();

    return ((time >= beginTime) && (time <= endTime));
  }

  public static int compare(java.util.Date beginDate, java.util.Date endDate)
  {
    int ret = 1;
    long beginTime = beginDate.getTime();
    long endTime = endDate.getTime();

    if (beginTime > endTime) {
      ret = 2;
    }
    if (beginTime == endTime) {
      ret = 1;
    }
    if (beginTime < endTime) {
      ret = 0;
    }
    return ret;
  }

  public static java.sql.Date getFullDate(java.sql.Date input)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(input);
    calendar.set(11, 0);
    calendar.set(12, 0);
    calendar.set(13, 0);
    calendar.set(14, 0);

    return new java.sql.Date(calendar.getTimeInMillis());
  }

  public static java.util.Date dateOffsetCalc(int offset, String dateTimeStr, int intout)
  {
    java.util.Date ret = string2Date(dateTimeStr);
    boolean hasTimeStr = false;
    if (dateTimeStr.length() > 11) {
      hasTimeStr = true;
    }
    else {
      hasTimeStr = false;
    }
    if (ret != null) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(ret);
      if (intout == 0) {
        if (hasTimeStr) {
          cal.add(13, offset);
        }
        else {
          cal.add(5, offset);
        }

      }
      else if (hasTimeStr) {
        cal.add(13, -1 * offset);
      }
      else {
        cal.add(5, -1 * offset);
      }

      ret = cal.getTime();
    }
    return ret;
  }

  public static java.sql.Date sqlDateOffsetCalc(int offset, String dateTimeStr, int intout)
  {
    java.util.Date ret = dateOffsetCalc(offset, dateTimeStr, intout);
    if (ret != null) {
      return new java.sql.Date(ret.getTime());
    }
    return null;
  }

  public static int getChannelDateTimeOffset(int channel)
  {
    

    return 0;
  }
}