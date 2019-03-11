package com.becoda.bkms.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtilSchedule {

	public static final String Format_Date = "yyyy-MM-dd";
	public static final String Format_YM = "yyyy-MM";
	public static final String Format_Time = "HH:mm:ss";
	public static final String Format_DateTime = "yyyy-MM-dd HH:mm:ss";
	public static final SimpleDateFormat sdfd = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final SimpleDateFormat sdft = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat sdfdt = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final String ydwt = "yyyy年M月d日 E H时m分s秒"; // 年－月－日 星期 时:分:秒
	public static final String ydw = "yyyy年M月d日 E"; // 年－月－日 星期
	public static final String Format_ymd = "yyyy年M月d日"; // 年－月－日
	
	/**
	 * 获取当前时间之前或之后几分钟 minute
	 */
    public static String getTimeByMinute(int minute) {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MINUTE, minute);

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

    }
    /**
     * 取当前时间之前或之后几小时 hour 
     */
    public static String getTimeByHour(int hour) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

    }
	/**
	 * 获取当前时间[包含星期]
	 * 
	 * @author lxj
	 * @return String
	 */
	public static String getCurrentDateWeek() {
		SimpleDateFormat fmt = new SimpleDateFormat(ydw, Locale.CHINA);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		// 当前时间，与当前系统时间一致
		cal.setTimeInMillis(System.currentTimeMillis());
		return fmt.format(cal.getTime());
	}

	public static String getCurrentDate() {
		return sdfd.format(new Date());
	}

	public static String getCurrentDate(String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(new Date());
	}

	public static String getCurrentTime() {
		return sdft.format(new Date());
	}

	public static String getCurrentTime(String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(new Date());
	}

	public static String getCurrentDateTime() {
		String format = "yyyy-MM-dd HH:mm:ss";
		return getCurrentDateTime(format);
	}
	//当前年
	public static int getDayOfNf() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}
	//当前月
	public static int getDayOfYf() {
		Calendar cal = Calendar.getInstance();
		return (cal.get(Calendar.MONTH)) + 1;
	}
	//当前月的第几天：即当前日
	public static int getDayOfTs() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	//当前时：HOUR_OF_DAY-24小时制；HOUR-12小时制
	public static int getDayOfXS() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.HOUR_OF_DAY);
	}
	//当前分钟
	public static int getDayOfFz() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MINUTE);
	}
	//当前秒钟
	public static int getDayOfMs() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.SECOND);
	}
	// 0-上午；1-下午
	public static int getDayOfSwXw() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.AM_PM);
	}
	//当前年的第几周
	public static int getDayOfDqnZs() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	//当前月的第几周
	public static int getDayOfDqYZs() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	// 当前年的第几天
	public static int getDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(7);
	}

	public static int getDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(5);
	}

	public static int getDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(5);
	}

	public static int getMaxDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(5);
	}

	public static String getFirstDayOfMonth(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(date));
		cal.set(5, 1);
		return sdfd.format(cal.getTime());
	}

	public static int getDayOfYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(6);
	}

	public static int getDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(6);
	}

	public static int getDayOfWeek(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(date));
		return cal.get(7);
	}

	public static int getDayOfMonth(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(date));
		return cal.get(5);
	}

	public static int getDayOfYear(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(date));
		return cal.get(6);
	}

	public static String getCurrentDateTime(String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(new Date());
	}

	public static String toString(Date date) {
		if (date == null)
			return "";

		return sdfd.format(date);
	}

	public static String toDateTimeString(Date date) {
		if (date == null)
			return "";

		return sdfdt.format(date);
	}

	public static String toString(Date date, String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(date);
	}

	public static String toTimeString(Date date) {
		if (date == null)
			return "";

		return sdft.format(date);
	}

	public static int compare(String date1, String date2) {
		return compare(date1, date2, "yyyy-MM-dd");
	}

	public static int compareTime(String time1, String time2) {
		return compareTime(time1, time2, "HH:mm:ss");
	}

	public static int compare(String date1, String date2, String format) {
		Date d1 = parse(date1);
		Date d2 = parse(date2);
		return d1.compareTo(d2);
	}

	public static int compareTime(String time1, String time2, String format) {
		String[] arr1 = time1.split(":");
		String[] arr2 = time2.split(":");
		if (arr1.length < 2)
			throw new RuntimeException("时间比较异常：" + time1);

		if (arr2.length < 2)
			throw new RuntimeException("时间比较异常:" + time2);

		int h1 = Integer.parseInt(arr1[0]);
		int m1 = Integer.parseInt(arr1[1]);
		int h2 = Integer.parseInt(arr2[0]);
		int m2 = Integer.parseInt(arr2[1]);
		int s1 = 0;
		int s2 = 0;
		if (arr1.length == 3)
			s1 = Integer.parseInt(arr1[2]);

		if (arr2.length == 3)
			s2 = Integer.parseInt(arr2[2]);

		if ((h1 < 0) || (h1 > 23) || (m1 < 0) || (m1 > 59) || (s1 < 0)
				|| (s1 > 59))
			throw new RuntimeException("时间比较异常：" + time1);

		if ((h2 < 0) || (h2 > 23) || (m2 < 0) || (m2 > 59) || (s2 < 0)
				|| (s2 > 59))
			throw new RuntimeException("时间比较异常：" + time2);

		if (h1 != h2)
			return ((h1 > h2) ? 1 : -1);

		if (m1 == m2) {
			if (s1 == s2)
				return 0;

			return ((s1 > s2) ? 1 : -1);
		}

		return ((m1 > m2) ? 1 : -1);
	}

	public static boolean isTime(String time) {
		String[] arr = time.split(":");
		if (arr.length < 2)
			return false;
		try {
			int h = Integer.parseInt(arr[0]);
			int m = Integer.parseInt(arr[1]);
			int s = 0;
			if (arr.length == 3)
				s = Integer.parseInt(arr[2]);

			if ((h >= 0) && (h <= 23) && (m >= 0) && (m <= 59) && (s >= 0)
					&& (s <= 59))
				;
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isWeekend(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int t = cal.get(7);

		return ((t == 7) || (t == 1));
	}

	public static boolean isWeekend(String str) {
		return isWeekend(parse(str));
	}

	public static Date parse(String str) {
		if (StringUtilSchedule.isEmpty(str))
			return null;
		try {
			return sdfd.parse(str);
		} catch (ParseException e) {
			
		}
		return null;
	}

	public static Date parse(String str, String format) {
		if (StringUtilSchedule.isEmpty(str))
			return null;
		try {
			SimpleDateFormat t = new SimpleDateFormat(format);
			return t.parse(str);
		} catch (ParseException e) {
			
		}
		return null;
	}

	public static Date parseDateTime(String str) {
		if (StringUtilSchedule.isEmpty(str))
			return null;

		if (str.length() == 10)
			return parse(str);
		try {
			return sdfdt.parse(str);
		} catch (ParseException e) {
			
		}
		return null;
	}

	public static Date parseDateTime(String str, String format) {
		if (StringUtilSchedule.isEmpty(str))
			return null;
		try {
			SimpleDateFormat t = new SimpleDateFormat(format);
			return t.parse(str);
		} catch (ParseException e) {
			
		}
		return null;
	}

	public static Date addMinute(Date date, int count) {
		return new Date(date.getTime() + 60000L * count);
	}

	public static Date addHour(Date date, int count) {
		return new Date(date.getTime() + 3600000L * count);
	}

	public static Date addDay(Date date, int count) {
		return new Date(date.getTime() + 86400000L * count);
	}

	public static Date addWeek(Date date, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(3, count);
		return c.getTime();
	}

	public static Date addMonth(Date date, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(2, count);
		return c.getTime();
	}

	public static Date addYear(Date date, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(1, count);
		return c.getTime();
	}

	public static Date nowDate() {
		return new Date();
	}

	public static String nowTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(new Date());
	}

	public static String nowYearMonthDay() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(new Date());
	}

	public static String nowYearMonth() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		return formatter.format(new Date());
	}

	public static String nowYear() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		return formatter.format(new Date());
	}
	public static String nowMonth() {
		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		String yf= formatter.format(new Date());
		if(yf.substring(0, 1).equals("0")){
			yf=yf.substring(1, 2);
		}else{
			
		}
		return yf;
	}


	public static String getYYYY(Date date) {
		if (null == date)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		return formatter.format(date);
	}

	public static String getYYYYMM(Date date) {
		if (null == date)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		return formatter.format(date);
	}

	public static String getYYYYMM2(Date date) {
		if (null == date)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		return formatter.format(date);
	}

	public static String getMM(Date date) {
		if (null == date)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		return formatter.format(date);
	}

	public static String getYYYYMMDD(Date date) {
		if (null == date)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	public static String getYYYYMMDD(Timestamp timeStamp) {
		if (null == timeStamp)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(timeStamp);
	}

	public static String getMMDD(Date date) {
		if (null == date)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
		return formatter.format(date);
	}

	public static String getYYYYMMDDHHMMSS(Timestamp timeStamp) {
		if (null == timeStamp)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(timeStamp);
	}

	public static String getHHMMSS(Timestamp timeStamp) {
		if (null == timeStamp)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		return formatter.format(timeStamp);
	}

	public static Date getDate(String date) {
		if (null == date)
			return null;
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date newdate = myFmt.parse(date);
			return newdate;
		} catch (Exception e) {
			
			return null;
		}

	}

	/**
	 * @param timestampStr
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp StrToTimestamp(String timestampStr, String pattern)
			throws ParseException {
		java.util.Date date = null;
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			date = format.parse(timestampStr);
		} catch (ParseException e) {
			throw e;
		}
		return date == null ? null : new Timestamp(date.getTime());
	}

	@SuppressWarnings("deprecation")
	public static void toDayStart(Timestamp ts) {
		ts.setHours(0);
		ts.setMinutes(0);
		ts.setSeconds(0);
		ts.setNanos(0);
	}

	// 23:59:59.999
	@SuppressWarnings("deprecation")
	public static void toDayEnd(Timestamp ts) {
		ts.setHours(23);
		ts.setMinutes(59);
		ts.setSeconds(59);
		ts.setNanos(999);
	}

	// 获取当月第一天和最后一天
	public static String getFirstDateByNY(String y, String m) {
		String firstDate = "";
		// 获取当前年份、月份、日期
		Calendar cale = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String firstday, lastday;
		// 获取前月的第一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = format.format(cale.getTime());
		// 获取前月的最后一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastday = format.format(cale.getTime());
		return firstDate;
	}

	// 获取当月第一天和最后一天
	public static String getLastDateByNY(String y, String m) {
		String lastDay = "";
		// 获取当前年份、月份、日期
		Calendar cale = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String firstday, lastday;
		// 获取前月的第一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = format.format(cale.getTime());
		// 获取前月的最后一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastday = format.format(cale.getTime());
		return lastday;
	}
	//获取当前年月的上一个年月
	public static String[] getBeforeNY(){
		String[] ny=new String[2];
		String nf=DateUtilSchedule.nowYear();
		String yf=DateUtilSchedule.nowMonth();
		if(yf.equals("1")){
			ny[0]=String.valueOf(Integer.parseInt(nf)-1);
			ny[1]="12";
		}else{
			ny[0]=nf;
			ny[1]=String.valueOf(Integer.parseInt(yf)-1);
		}
		return ny;
		
	}
	
	// public static void main(String[] args) {
	// // getDate("2007-09-10");
	// String str = getYYYYMMDD(new Date());
	// }

}
