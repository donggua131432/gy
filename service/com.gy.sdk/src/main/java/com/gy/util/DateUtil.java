package com.gy.util;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 日期处理工具
 * 
 * @author Administrator
 *
 */
public class DateUtil extends DateUtils {

	public static final String YYYYMMDD_SPLIT = "yyyy-MM-dd";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String YYYYMMDDHHMMSS_SPLIT = "yyyy-MM-dd HH:mm:ss";
	public static final String HHMM_SPLIT = "HH:mm";
	

	/**
	 * 判断是否在时间段内
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static boolean isInZone(String startTime, String endTime) throws ParseException {
		long tStart = getLong(startTime);
		long tEnd = getLong(endTime);
		long t = getCurrentTime();
		return tStart <= t && t <= tEnd;
	}

	/**
	 * 获取字符串HH:mm时间戳
	 * @param timeStr
	 * @return
	 * @throws ParseException
	 */
	public static long getLong(String timeStr) throws ParseException {
		return getDateFormat(HHMM_SPLIT).parse(timeStr).getTime();
	}

	public static long getLongTime(){
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 获取当前时间，HH:mm 时间戳
	 * @return
	 * @throws ParseException
	 */
	public static long getCurrentTime() throws ParseException {
		return getLong(getDateFormat(HHMM_SPLIT).format(new Date()));
	}

	public static DateFormat getDateFormat(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	public static String formatDate(Date date, String pattern) {
		return getDateFormat(pattern).format(date);
	}

	/**
	 * 获取当前日期 
	 * @return
	 */
	public static String getCurrentDate() {
		return getCurrentDate(YYYYMMDD_SPLIT);
	}

	public static String getCurrentDate(String pattern) {
		return getDateFormat(pattern).format(new Date());
	}

	public static String stringToDateStringFormat(String strDate) {
		DateFormat sdf = getDateFormat(YYYYMMDDHHMMSS);
		Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		}
		DateFormat sdf2 = getDateFormat(YYYYMMDDHHMMSS_SPLIT);
		return sdf2.format(date);
	}

	public static String getNowDateAndTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
	}

	/**
	 * 比较日期大小
	 * @param dateStr0
	 * @param dateStr1
	 * @return
	 * @throws ParseException 
	 */
	public static int compareDate(String dateStr0, String dateStr1, String pattern) throws ParseException {
		Date date1 = convertDateStrToDate(dateStr0, pattern);
		Date date2 = convertDateStrToDate(dateStr1, pattern);
		return compareDate(date1, date2);
	}

	public static int compareDate(String dateStr0, String dateStr1) throws ParseException {
		return compareDate(dateStr0, dateStr1, null);
	}

	public static int compareDate(Date date1, Date date2) {
		return date1.compareTo(date2);
	}

	/**
	 * 把日期字符串转换成日期
	 * @param dateStr 日期字符串
	 * @param pattern "date":日期, "datetime":日期和时间
	 * @return
	 * @throws ParseException 
	 */
	public static Date convertDateStrToDate(String dateStr, String pattern) {
		if (!StringUtil.hasValue(dateStr)) {
			return null;
		}

		if (!StringUtil.hasValue(pattern)) {
			pattern = YYYYMMDD_SPLIT;
		}
		try {
			return getDateFormat(pattern).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 是否闰年
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		boolean flag = (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
		return flag;
	}

	/**
	 * 是否闰年
	 * @param date
	 * @return
	 */
	public static boolean isLeapYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		boolean flag = isLeapYear(cal.get(Calendar.YEAR));
		return flag;
	}

	/**
	 * 计算给定日期当年总天数
	 * @param date
	 * @return
	 */
	public static int getDaysOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 计算给定日期当年剩余天数
	 * @param date
	 * @return
	 */
	public static int getDaysLeftOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int cnt = cal.getActualMaximum(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR);
		return cnt;
	}

	/**
	 * 获得星期几(周日为1，周六为7)
	 * @param date 给定日期
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获得星期几（中文）
	 * @param date
	 * @return
	 */
	public static String getDayCN(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case 1:
			return "星期日";
		case 2:
			return "星期一";
		case 3:
			return "星期二";
		case 4:
			return "星期三";
		case 5:
			return "星期四";
		case 6:
			return "星期五";
		case 7:
			return "星期六";
		default:
			return "";
		}
	}

	/**
	 * 计算给定日期所在月的第一天
	 * @param date 给定日期
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	/**
	 * 计算给定日期所在月的下一个月的第一天
	 * @param date 给定日期
	 * @return
	 */
	public static Date getFirstDayOfNextMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 计算给定日期所在月的最后一天
	 * @param date 给定日期
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 计算给定日期所在周的第一天(周一)
	 * @param date 给定日期
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		dayOfWeek = dayOfWeek == Calendar.SUNDAY ? 8 : dayOfWeek;
		int dValue = Calendar.MONDAY - dayOfWeek;
		calendar.add(Calendar.DAY_OF_WEEK, dValue);
		return calendar.getTime();
	}

	/**
	 * 计算给定日期所在月的最后一天(周日)
	 * @param date 给定日期
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		dayOfWeek = dayOfWeek == Calendar.SUNDAY ? 8 : dayOfWeek;
		int diff = 8 - dayOfWeek;
		calendar.add(Calendar.DAY_OF_WEEK, diff);
		return calendar.getTime();
	}

	public static String getNow(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}

	public static String getNow() {
		return getNow("yyyyMMddHHmmss");
	}

	/**
	 * 获取指定日期时间向前后向后推移n天的时间日期
	 * @param days  推移天数 把日期往后增加一天.整数往后推,负数往前移动
	 */
	public static String dateTimeAddOrReduceDays(String dateTimeStr, int days) {
		String dateStr = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(dateTimeStr);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, days);
			date = calendar.getTime();
			dateStr = sdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}

	/**
	 * 获取指定日期时间向前后向后推移n天的时间日期
	 * @param days 推移天数 把日期往后增加一天.整数往后推,负数往前移动
	 */
	public static Date addDay(Date date, int days) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

	/**
	 * 将时间戳转换为时间
	 * @param s
	 * @return
	 */
	public static String stampToDate(String s) {
		if (s == null || s.length() == 0)
			return s;
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	/**
	 * 将Date转换为时间
	 * @param date
	 * @return
	 */
	public static String stampToDate(Date date) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		res = simpleDateFormat.format(date);
		return res;
	}
	
	/**
	 * 获取指定日期时间向前后向后推移n分钟的时间日期
	 * @param minutes  推移分钟数 把日期往后增加一分钟.整数往后推,负数往前移动
	 */
	public static String dateTimeAddOrReduceMinutes(String dateTimeStr, int minutes) {
		String dateStr = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(dateTimeStr);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.MINUTE, minutes);
			date = calendar.getTime();
			dateStr = sdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}
	/**
	 * string to long
	 * @param strTime
	 * @param formatType
	 * @return
	 * @throws ParseException
	 */
	public static long stringToLong(String strTime, String formatType){
 		Date date = convertDateStrToDate(strTime, formatType); // String类型转成date类型
 		if (date == null) {
 			return 0;
 		} else {
 			long currentTime = dateToLong(date); // date类型转成long类型
 			return currentTime;
 		}
 	}
	/**date to long*/
	public static long dateToLong(Date date) {
 		return date.getTime();
 	}

	/**
	 * 根据类型获取两个日期相差的年 月  日
	 * @param fromDate
	 * @param toDate
	 * @param type
	 * @return
	 */
	public static int dateCompare(Date fromDate,Date toDate, String type){
		Calendar from  =  Calendar.getInstance();
		from.setTime(fromDate);
		Calendar  to  =  Calendar.getInstance();
		to.setTime(toDate);
		//只要年月
		int fromYear = from.get(Calendar.YEAR);
		int fromMonth = from.get(Calendar.MONTH);
		int toYear = to.get(Calendar.YEAR);
		int toMonth = to.get(Calendar.MONTH);
		int year = toYear  -  fromYear;
		int month = toYear *  12  + toMonth  -  (fromYear  *  12  +  fromMonth);
		int day = (int) ((to.getTimeInMillis()  -  from.getTimeInMillis())  /  (24  *  3600  *  1000));

		if("year".equals(type)){
			return year;
		}else if("month".equals(type)){
			return month;
		}else if("day".equals(type)){
			return day;
		}else{
			return 0;
		}
	}
	
	/**
	 * 根据年级判断地址是否为最新地址
	 * 从上一年度的11月始至今年的10月止为一个时间段，如2019级学员识别2017年11月至2018年10月的地址为一个时间段，超过这个时间段的为非最新地址
	 * @param year
	 * @return
	 */
	public static boolean judgeIfNewAddresByYear(String year){
		String startYear= NumberUtils.toInt(year)-2+"-11";
		String endYear = NumberUtils.toInt(year)-1+"-10";
		long startLong = DateUtil.convertDateStrToDate(startYear, "yyyy-MM").getTime();
		long endLong = DateUtil.convertDateStrToDate(endYear, "yyyy-MM").getTime();
		long nowLong = DateUtil.convertDateStrToDate(DateUtil.formatDate(new Date(), "yyyy-MM"),"yyyy-MM").getTime();
		if(nowLong>=startLong && nowLong <=endLong){
			return true;
		}else{
			return false;
		}
	}
}
